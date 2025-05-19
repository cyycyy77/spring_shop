package com.apple.shop.comment;

import com.apple.shop.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByParentId(Long parentId);
    Page<Comment> findByParentId(Long parentId, Pageable pageable);
//    Page<Comment> findPageBy(Pageable pageable);
    List<Comment> findByUsername(String username);
}
