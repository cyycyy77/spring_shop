package com.apple.shop.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;

    @GetMapping("/announcement")
    String announcement(Model model){

        List<Announcement> result = announcementRepository.findAll();
        model.addAttribute("announcements", result);
        return "announcement.html";
    }

}
