package com.apple.shop.comment;

import com.apple.shop.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void saveComment(
            @RequestParam String content,
            @RequestParam Long parentId, Authentication auth) {

        Comment comment = new Comment();
        CustomUser user = (CustomUser) auth.getPrincipal();
        comment.setUsername(user.getUsername());
        comment.setContent(content);
        comment.setParentId(parentId);
        commentRepository.save(comment);
    }
}
