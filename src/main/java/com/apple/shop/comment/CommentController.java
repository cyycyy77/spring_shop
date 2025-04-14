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

//    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
//    private final ItemRepository itemRepository; // 댓글 작성후 redirect를 위해서

    @PostMapping("/commentsave")
    public String saveComment(
            @RequestParam String content,
            @RequestParam Long parentId, Authentication auth) {
        Comment comment = new Comment();
        CustomUser user = (CustomUser) auth.getPrincipal();
        comment.setUsername(user.getUsername());
        comment.setContent(content);
        comment.setParentId(parentId);
//        Optional<Member> member = memberRepository.findByUsername(username);
////        if (member.isPresent()) {
////            // 실제 Member 객체를 꺼내서 세팅
////            comment.setMember(member.get());
////        }
//        comment.setMember(member.get());
        commentRepository.save(comment);
//        return "redirect:/list"; // 댓글창(/detail/{id})으로 redirect
        return "redirect:/detail/" + parentId;
    }


}
