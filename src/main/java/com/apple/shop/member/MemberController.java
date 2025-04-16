package com.apple.shop.member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.function.LongFunction;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    public final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data,
                           HttpServletResponse response){

        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());

        var cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(10000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return jwt;
    }

    @GetMapping("/my-page/jwt")
    @ResponseBody
    String myPageJWT(HttpServletRequest request, Authentication auth) {
        Cookie[] cookies = request.getCookies();
        var jwtCookie = "";
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("jwt")) {
                jwtCookie = cookies[i].getValue();
            }
        }
        System.out.println(jwtCookie);

        var user = (CustomUser) auth.getPrincipal();
        System.out.println(user);
        System.out.println(user.displayName);
        System.out.println(user.id);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        return "마이페이지 데이터";
    }

}
