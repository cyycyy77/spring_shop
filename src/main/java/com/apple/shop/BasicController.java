package com.apple.shop;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class BasicController {
    @GetMapping("/")
//    @ResponseBody
    String hello(){
//        return "<h4>안녕하세요</h4>";
        return "index.html";
    }


    @GetMapping("/about")
    @ResponseBody
    String about(){
        return "web site";
    }

    @GetMapping("/date")
    @ResponseBody
    String date() {
        return LocalDateTime.now().toString();

    }
}
