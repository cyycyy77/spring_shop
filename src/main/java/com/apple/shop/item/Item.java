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
    public Long id;
//    private Long id;

    @Column //column 마다 제약조건 부여 가능
//    @Setter
//    public String title;
    private String title;

//    public void setTitle(String title) {
//        // 여기다가 ex) 255 이하면 수정, 이런식으로 조건을 걸어줘야 안정성이 생김.
//        // 그냥 setter 한다고 안전해지는것이 아님!!
//        this.title = title;
//    }

    // 아래 코드 왜 안돌아가는거지??????? -> empty 값이 아니라서
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

//    public Integer price;
    private Integer price;

//    private Item(Long id, String title, Integer price) {
//        this.id = id;
//        this.title = title;
//        this.price = price;
//    }
//
//    public static Item createItem(Long id, String title, Integer price) {
//        return new Item(id, title, price);
//    }

    private String userid;
    private String imgUrl;

    private Integer count;
}
