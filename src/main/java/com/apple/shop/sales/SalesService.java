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

            if (item.getCount() == null) {
                throw new RuntimeException("상품 재고를 확인할 수 없음");
                }

            if (item.getCount() < count) {
                throw new RuntimeException("수량이 부족함");
            }
            item.setCount(item.getCount() - count);
            itemRepository.save(item);
        } else {
            throw new RuntimeException("수량이 부족함");
        }

        Sales sales = new Sales();
        sales.setItemName(itemName);
        sales.setPrice(price);
        sales.setCount(count);

        CustomUser user = (CustomUser) auth.getPrincipal();
        var member = new Member();
        member.setId(user.id);
        sales.setMember(member);
        salesRepository.save(sales);
    }
}
