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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit/comment/{id}")
    public String editComment(@PathVariable Long id, Model model) {
        Optional<Comment> result = commentRepository.findById(id);

        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return "editcomment.html";
        } else{
            return "redirect:/list";
        }

    }

    @PostMapping("/edit/comment")
    public String editComment(@RequestParam Long id,
                              @RequestParam String username,
                              @RequestParam String content,
                              @RequestParam Long parentId) {

        commentService.editComment(id, username, content, parentId);
        return "redirect:/list";
    }

    @DeleteMapping("/comment")
    ResponseEntity<String> deleteComment(@RequestParam Long id){
        commentRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

}
