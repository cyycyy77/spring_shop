package com.apple.shop.sales;

import com.apple.shop.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@ToString
@Getter
@Setter
public class Sales {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Integer Price;
    private Integer Count;
//    String username;
//    String displayName;
//    @ManyToOne
//    @JoinColumn(name="member_id")
////    @JoinColumn(name="displayName")
////    Optional<Member> member;
//    private Member member;
//    @ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="member_id", //이 컬럼의 이름을 정해줌
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;
//    Long memberId;

    @CreationTimestamp
    private LocalDateTime created;
}
