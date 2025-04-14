package com.apple.shop.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
@Table(indexes = @Index(columnList = "title", name = "작명"))
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    public void setTitle(String title){
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목이 null입니다.");
        }
        if (title.length() > 255 ){
            throw new IllegalArgumentException("제목은 255자를 넘을 수 없다.");
        } else {
            this.title = title;
        }
    }

    private Integer price;

    private String userid;

    private String imgUrl;

    private Integer count;

}
