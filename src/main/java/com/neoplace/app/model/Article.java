package com.neoplace.app.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {

    public enum Status {
        published,
        sold,
        sent,
        delivered,
        conflict;
    }

    public enum Condition {
        New, Good, Middle, Poor;
    }

    private Long id;
    private String title;
    private String subtitle;

    @OneToOne
    private Category category;
    private String description;

    private String tags;

    private List<Gallery> gallery;

    @Setter
    private Status status;
    private int price;
    private String ean;



    private float latitude;
    private float longitude;
    private String brand;
    private Condition condition;

    private float rating;
    private int ratingCount;


}
