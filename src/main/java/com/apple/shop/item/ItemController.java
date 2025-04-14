package com.apple.shop.item;

import com.apple.shop.comment.Comment;
import com.apple.shop.comment.CommentRepository;
import com.apple.shop.sales.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3Service s3Service;
    private final CommentRepository commentRepository;

    @GetMapping("/list")
    String page(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);
        return "list.html";
    }

    @GetMapping("/write")
    String write(Authentication auth) {
        if (auth == null){
            return "redirect:/";
        }
        return "write.html";
    }

    //지워도 되나? 어디에 사용되지 service까지 나눠놨으면 쓰는거 아닌가
    @PostMapping("/add")
    String addPost(Map formData) {
        itemService.addPost(formData);
        return "redirect:/list";
    }

    @PostMapping("/itemsave")
    public String saveItem(@RequestParam String title,
                           @RequestParam Integer price,
                           String username,
                           @RequestParam String imgUrl,
                           Authentication auth,
                           @RequestParam Integer count) {

        itemService.saveItem(title, price, username, imgUrl, auth, count);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);
        List<Comment> comment = commentRepository.findByParentId(id);

        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            model.addAttribute("comment", comment);
            return "detail.html";
        } else {
            return "redirect:/list";
        }

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);

        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit.html";
        } else{
            return "redirect:/list";
        }

    }

    @PostMapping("/edit/item")
    public String editItem(@RequestParam Long id,
                           @RequestParam String title,
                           @RequestParam Integer price,
                           @RequestParam Integer count) {

        itemService.editItem(id, title, price, count);
        return "redirect:/list";
    }

    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long id){
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/list/page/{page}")
    String getListPage(Model model, @PathVariable Integer page) {
        var pageSize = 3;
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(page -1, pageSize));
        model.addAttribute("items", result);
        model.addAttribute("pages", page);

        Integer totalPage = result.getTotalPages();
        model.addAttribute("last", totalPage);
        List<Integer> pageList = new ArrayList<>();
        Integer i;

        for(i=1;i<=totalPage;i+=1){
            pageList.add(i);
        }

        model.addAttribute("pageList", pageList);
        return "list.html";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        System.out.println(filename);
        var result = s3Service.createPresignedUrl("test/" + filename);
        System.out.println(result);
        return result;
    }

    @PostMapping("/search")
    String postSearch(@RequestParam String searchText, Model model){
        List<Item> result = itemRepository.rawQuery1(searchText);
        model.addAttribute("items", result);
        return "list.html";
    }

}