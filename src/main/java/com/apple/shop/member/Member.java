package com.apple.shop.member;

import com.apple.shop.sales.Sales;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Setter
@Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String displayName;

//    @OneToMany(mappedBy = "컬럼명")
    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<Sales> sales = new ArrayList<>();
}
