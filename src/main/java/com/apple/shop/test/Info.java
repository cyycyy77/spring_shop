package com.apple.shop.test;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
//@Setter
public class Info {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    private String name;

    private Integer age;

    public void ageplus(){
        this.age = this.age+1;
    }

    public void setAge(Integer age) {
        if (age>=0 && age<=100){
            this.age = age;
        }
    }
}
