package com.apple.shop.member;

public class MemberDto {
    public String username;
    public String displayName;
    public Long id;

    MemberDto(String a, String b){
        this.username = a;
        this.displayName = b;
    }
    MemberDto(String a, String b, Long c) {
        this.username = a;
        this.displayName = b;
        this.id = c;
    }
}
