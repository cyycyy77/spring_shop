package com.apple.shop.comment;

import com.apple.shop.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username; // member username?
    @Column(length = 1000)
    private String content;
    private Long parentId; // member Id? no, item id (댓글은 아이템 페이지에 적으니까)

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parentId") // comment 테이블에 member_id 칼럼이 생김
//    private Member member;


}
