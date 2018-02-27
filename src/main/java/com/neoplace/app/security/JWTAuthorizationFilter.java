package com.neoplace.app.security;

import alicoin.io.user.ApplicationUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.internal.NonNull;
import com.google.firebase.tasks.OnFailureListener;
import com.google.firebase.tasks.OnSuccessListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static alicoin.io.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING).replace("Bearer ", "");
        if (token != null) {
            ApplicationUser user = new ApplicationUser();
            final CountDownLatch cdl = new CountDownLatch(1);

            FirebaseAuth.getInstance().verifyIdToken(token)
                    .addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
                        @Override
                        public void onSuccess(FirebaseToken decodedToken) {
                            user.setUsername(decodedToken.getEmail());
                            user.setName(decodedToken.getEmail());
                            cdl.countDown();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // log error, ...
                            cdl.countDown();
                        }
                    });

            try {
                cdl.await(); // This line blocks execution till count down latch is 0
            } catch (InterruptedException ie) {

            }

            if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getName())) {
                return new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
