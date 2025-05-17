package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, Integer price , String username, String imgUrl, Authentication auth, Integer count) {
        if (price < 0) {
            throw new RuntimeException("가격은 양수여야함");
        } else if (title.length() > 255) {
            throw new RuntimeException("너무 긺");
        } else if (count < 0) {
            throw new RuntimeException("개수는 음수가 아니여야함");
        } else {
            Item item = new Item();
            item.setTitle(title);
            item.setPrice(price);
            username = auth.getName();
            item.setUserid(username);
            item.setImgUrl(imgUrl);
            item.setCount(count);
            itemRepository.save(item);
        }
    }

    //지워도 될듯
    public void addPost(Map formData) {
        System.out.println(formData);
        HashMap<String, Object> test = new HashMap<>();
        test.put("name", "kim");
        test.put("age", 20);
        System.out.println(test.get("name"));
    }

    public void editItem(@RequestParam Long id,
                         @RequestParam String title,
                         @RequestParam String username,
                         @RequestParam Integer price,
                         @RequestParam String imgUrl,
                         @RequestParam Integer count) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력하세요.");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("제목이 너무 깁니다.");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
        var item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setUserid(username);
        item.setPrice(price);
        item.setImgUrl(imgUrl);
        item.setCount(count);
        itemRepository.save(item);
    }
}
