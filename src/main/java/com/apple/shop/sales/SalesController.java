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

    //transaction
    private final ItemRepository itemRepository;
    private final ItemController itemController;
    private final SalesService salesService;

    // main 함수에 작성해줘야 하나?
//    @Transactional
//    public void orderitem(Long id,String itemName, Integer price, Integer count, Authentication auth){
//        buyitem(itemName, price, count, auth);
//        var item = new Item();
//        var sales = new Sales();
//        Integer currentCount;
//        Integer remainCount;
//        currentCount = item.getCount();
//        count = sales.getCount();
//        remainCount = currentCount - count;
//        itemController.editItem(id, itemName, price, remainCount);
//    }

    @PostMapping("/order")
    public String buyitem(String itemName,
                          Integer price,
                          Integer count,
                          Long id,
                          Authentication auth){
//auth를 사용한 이유는, auth 변수 안에 user 정보 들어있고(기존 정보에 우리가 여러가지 추가함)
//        거기에 id가 들어있어서 그거 이용하기 위해서
        salesService.addSales(itemName, price, count, id, auth);
        return "redirect:/list";
//        return "list.html";
    }


// 주문 보여주는 페이지는 getmapping만 존재하고 postmapping은 없는데,
//    그 이유는 html page로부터 전달받는 값이 없기 때문이다.
    @GetMapping("/order/all")
    public String salesList(Model model){
//        var result = new Sales();
//        List<Sales> result = salesRepository.findAll(); //mansaytoone 사용시
//        System.out.println(result.get(0).getMemberId());
//        System.out.println(result.get(0));
//        model.addAttribute("sales", result);
//        System.out.println(result.getMember());


        List<Sales> result = salesRepository.customFindAll(); //mansaytoone 사용시
        System.out.println(result);
        model.addAttribute("sales", result);
//        var salesDto = new SalesDto();
//        salesDto.itemName = result.get(0).getItemName();
//        salesDto.price = result.get(0).getPrice();
//        salesDto.username = result.get(0).getMember().getUsername();
//        List<SalesDto> salesdto = new SalesDto();
//        salesdto = salesRepository.findAll();

        var result2 = memberRepository.findById(1L);
        System.out.println(result2.get().getSales());
        return "saleslist.html";
    }


}

class SalesDto{
    public String itemName;
    public Integer price;
    protected String username;
}