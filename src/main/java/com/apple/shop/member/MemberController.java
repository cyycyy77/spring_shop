package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.LongFunction;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @GetMapping("/register")
    public String signup_login_version(Authentication auth) {
        if (auth == null) {
            return "register.html";
        } else if (auth.isAuthenticated() == true){
            return "redirect:/list";
        }
        return "register.html";
    }

    @PostMapping("/savemember")
    public String saveMember(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String displayName) throws Exception{

        memberService.saveMember(username, password, displayName);
        return "redirect:/list";
    }

    @GetMapping("/login")
    public String login(Authentication auth){
        if (auth == null) {
            return "login.html";
        } else if (auth.isAuthenticated() == true){
            return "redirect:/list";
        }
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth){
        CustomUser result = (CustomUser) auth.getPrincipal();
        System.out.println(result.displayName);
        return "mypage.html";
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public MemberDto getUser(@PathVariable Long id){
        var a = memberRepository.findById(id);
        var result = a.get();
        var data = new MemberDto(result.getUsername(), result.getDisplayName(), result.getId());
        return data;
    }

}
