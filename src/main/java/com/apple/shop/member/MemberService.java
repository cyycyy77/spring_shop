package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveMember(String username,
                           String password,
                           String displayName) throws Exception{

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("아이디를 입력하세요.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력하세요.");
        }
        if (username.length() > 255) {
            throw new IllegalArgumentException("너무 깁니다.");
        }
        if (username.length() < 8 || password.length() < 8){
            throw new IllegalArgumentException("너무짧음");
        }

        var hashPassword = passwordEncoder.encode(password);
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(hashPassword);
        member.setDisplayName(displayName);
        memberRepository.save(member);
    }

}
