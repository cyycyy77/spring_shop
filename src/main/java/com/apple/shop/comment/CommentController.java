package com.apple.shop.comment;

import com.apple.shop.item.Item;
import com.apple.shop.item.ItemRepository;
import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import com.apple.shop.member.MemberRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PostMapping("/commentsave")
    public String saveComment(
            @RequestParam String content,
            @RequestParam Long parentId, Authentication auth) {

        commentService.saveComment(content,parentId, auth);

        return "redirect:/detail/" + parentId;
    }


}
