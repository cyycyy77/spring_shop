package com.apple.shop.sales;

import com.apple.shop.item.Item;
import com.apple.shop.item.ItemRepository;
import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final ItemRepository itemRepository;
    private final SalesRepository salesRepository;

    @Transactional
    public void addSales(String itemName,
                         Integer price,
                         Integer count,
                         Long id,
                         Authentication auth) {
        Optional<Item> result = itemRepository.findById(id);
        if (result.isPresent()){
            var item = result.get();
            item.setCount(item.getCount()-count);
            itemRepository.save(item);
        }

        if (result.get().getCount() < 0){
            throw new RuntimeException("수량이 부족함");
        }

//상품 구매 기능
        Sales sales = new Sales();
        sales.setItemName(itemName);
        sales.setPrice(price);
        sales.setCount(count);
//        Member member = memberRepository.findByUsername(auth.getName()).orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다"));

        CustomUser user = (CustomUser) auth.getPrincipal();
        var member = new Member();
        member.setId(user.id);
        sales.setMember(member);
//        sales.setMemberId(user.id); //원래는 id만 가져다가 썼는데, 이후에 아예 member table을 외래키로 사용
        salesRepository.save(sales);
    }
}
