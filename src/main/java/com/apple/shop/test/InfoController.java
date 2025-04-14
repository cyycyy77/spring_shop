package com.apple.shop.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class InfoController {
    @GetMapping("/info")

    void page(Model model){

        model.addAttribute("info", "hi");
        var b = new Info();
        b.setAge(20);
        b.ageplus();
        b.setAge(12);
        b.ageplus();
        // info page가 제대로 설정되어있지 않아 작동 안하는듯

        System.out.println(b.getAge());
    }
}
