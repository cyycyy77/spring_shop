package com.apple.shop.test;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString
public class Announcement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String title;

    public String date;

}
