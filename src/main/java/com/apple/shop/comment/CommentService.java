package com.apple.shop.comment;

import com.apple.shop.item.Item;
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

    public void editComment(@RequestParam Long id,
                            @RequestParam String username,
                            @RequestParam String content,
                            @RequestParam Long parentId) {

//        if (title == null || title.trim().isEmpty()) {
//            throw new IllegalArgumentException("제목을 입력하세요.");
//        }
//        if (title.length() > 255) {
//            throw new IllegalArgumentException("제목이 너무 깁니다.");
//        }
//        if (price == null || price < 0) {
//            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
//        }
        var comment = new Comment();
        comment.setId(id);
        comment.setUsername(username);
        comment.setContent(content);
        comment.setParentId(parentId);
        commentRepository.save(comment);

    }
}
