package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.function.LongFunction;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

//    @GetMapping("/register")
////    public String signup(Model model){
//    public String signup(Authentication auth){
////        Member member = new Member();
////        model.addAttribute("members", member);
//        if (auth.isAuthenticated() == true) {
//            return "redirect:/list";
//        }
//        return "register.html";
//    }

//✅로그인 되어있을때 처리 시도해봄
    @GetMapping("/register")
//    public String signup(Model model){
    public String signup_login_version(Authentication auth) {
        if (auth == null) {
            return "register.html";
        } else if (auth.isAuthenticated() == true){
            return "redirect:/list";
        }
        return "register.html"; //❌없애도 되는 부분 아닌가? -> 있어야 되지 뭔소리야
    }

    @PostMapping("/savemember")
    public String saveMember(String username,
                             String password,
                             String displayName) throws Exception{
////        var hashPassword = new BCryptPasswordEncoder().encode(password);
//        var hashPassword = passwordEncoder.encode(password);
//        Member member = new Member();
//        member.setUsername(username);
//        member.setPassword(hashPassword);
//        member.setDisplayName(displayName);
//        memberRepository.save(member);
//        System.out.println(member);
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
//    public String myPage(Principal p){
    public String myPage(Authentication auth){
//        System.out.println(auth);
//        System.out.println(auth.getName());
//        System.out.println(auth.isAuthenticated());{
//        System.out.println(auth.getAuthorities().contains(
//                new SimpleGrantedAuthority("일반유저")
//        )); // 이건 true false로 결과가 나오나? -> ㅇㅇ

        CustomUser result = (CustomUser) auth.getPrincipal();
        System.out.println(result.displayName);
        return "mypage.html";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser(){
        var a = memberRepository.findById(1L);
        var result = a.get();
//        result.setPassword("");
////        var map = new HashMap();
//        var data = new MemberDto();
//        data.username = result.getUsername();
//        data.displayname = result.getDisplayName();
//        return result;
        var data = new MemberDto(result.getUsername(), result.getDisplayName()); //db에서 가져온 정보들
        var data2 = new MemberDto(result.getUsername(), result.getDisplayName(), result.getId()); //db에서 가져온 정보들
        return data;

//        return memberService.getUser();
    }

    class MemberDto{
        public String username;
        public String displayName;
        public Long id;

        MemberDto(String a, String b){
            this.username = a;
            this.displayName = b;
        }
        MemberDto(String a, String b, Long c){
            this.username = a;
            this.displayName = b;
            this.id = c;
        }
    }
}
