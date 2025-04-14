package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        위와 같이 코드 작성하면, 알아서 로그인 기능 작동한다
//        DB에서 username을 가진 유저를 찾아와서
//        return new User(유저아이디, 비번, 권한) 해주세요
        var result = memberRepository.findByUsername(username);
        if (result.isEmpty()){
            throw new UsernameNotFoundException("그런 아이디 없음");
        }
        var user = result.get();

        List<GrantedAuthority> authorities = new ArrayList<>(); // 권한 추가
        authorities.add(new SimpleGrantedAuthority("일반유저"));
//        return new User(user.getUsername(), user.getPassword(), authorities);

        var a = new CustomUser(user.getUsername(), user.getPassword(), authorities);
        a.displayName = user.getDisplayName();
        a.id = user.getId(); //sales 입력받을때, sales table에 유저 id 넣으려고 추가함
        return a;
    }
}

