package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
//    private final MemberDto memberDto;

    public void saveMember(String username,
                           String password,
                           String displayName) throws Exception{
//        var result = memberRepository.findByUsername(username);
//        if (result.isPresent()){
//            throw new Exception("존재하는아이디");
//        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("아이디를 입력하세요.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력하세요.");
        }
        if (username.length() > 255) {
            throw new IllegalArgumentException("제목이 너무 깁니다.");
        }
        if (username.length() < 8 || password.length() < 8){
            throw new Exception("너무짧음");
        }
        var hashPassword = passwordEncoder.encode(password); //dependecy injection
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(hashPassword);
        member.setDisplayName(displayName);
        memberRepository.save(member);
    }

//    public MemberDto getUser(){
//        var a = memberRepository.findById(1L);
//        var result = a.get();
//        var data = new MemberDto(result.getUsername(), result.getDisplayName()); //db에서 가져온 정보들
//        var data2 = new MemberDto(result.getUsername(), result.getDisplayName(), result.getId()); //db에서 가져온 정보들
//        return data;
//    }
}
