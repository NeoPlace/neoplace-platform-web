package com.neoplace.app.utils;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class IPFSUtils {

    private static IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");

    public static String uploadToIPFS(MultipartFile multipartFile) throws IOException {
        IPFSUtils.uploadToIPFS(multipartFileToFile(multipartFile));
    }

    public static Multihash uploadToIPFS(File file) throws IOException {
        NamedStreamable.FileWrapper fileStreamable = new NamedStreamable.FileWrapper(file);
        MerkleNode addResult = ipfs.add(fileStreamable).get(0);
        return addResult.hash;
    }


    public static File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convertedFile;
    }

    public static String uploadToIPFS(String content) throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper("hello.txt", content.getBytes());
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toBase58();
    }
}
