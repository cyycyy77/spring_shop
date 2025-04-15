package com.apple.shop.sales;

import com.apple.shop.item.Item;
import com.apple.shop.item.ItemController;
import com.apple.shop.item.ItemRepository;
import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import com.apple.shop.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SalesController {
    private final SalesRepository salesRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemController itemController;
    private final SalesService salesService;

    @PostMapping("/order")
    public String buyitem(String itemName,
                          Integer price,
                          Integer count,
                          Long id,
                          Authentication auth){

        salesService.addSales(itemName, price, count, id, auth);
        return "redirect:/list";
    }

    @GetMapping("/order/all")
    public String salesList(Model model){
        List<Sales> result = salesRepository.customFindAll();
        System.out.println(result);
        model.addAttribute("sales", result);
        var result2 = memberRepository.findById(1L);
        System.out.println(result2.get().getSales());
        return "saleslist.html";
    }


}

