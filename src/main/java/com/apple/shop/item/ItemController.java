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

    //    @Autowired
//    public ItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
//    -> lombok 사용하지 않고 등록하는법
    @GetMapping("/list")
//    @ResponseBody() //html 파일 보여주는거니까 필요 x
    String page(Model model) {
//        var result = itemRepository.findAll();
//        Optional<Item> r = itemRepository.findById(id);
        List<Item> result = itemRepository.findAll();

//        System.out.println(result.get(0));
//        System.out.println(result.get(0).price); // 1번째 행 데이터의 가격정보
//        model.addAttribute("name", "kim");
        model.addAttribute("items", result);
//        model.addAttribute("id", r);

        var a = new Item();
        System.out.println(a); // 얘도 ToString을 호출하네??
        System.out.println(a.toString());

//        var b = new Info();
//        b.setAge(20);
//        b.ageplus();
//        b.setAge(-1);
//        b.ageplus();
//
//        System.out.println(b.getAge());
        return "list.html";
    }

    // 로그인 상태에서만 작성 가능하도록?
    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    //    @PostMapping("/add")
//    String addPost(@RequestParam(name="title") String title,
//                   @RequestParam(name="price") String price){
//        System.out.println(title);
//        System.out.println(price);
//            return "redirect:/list";
//    }
    @PostMapping("/add")
//    String addPost(String title, String price){
    String addPost(Map formData) { // 사용자가 보낸 폼 데이터를 매핑하여 저장
//        System.out.println(title);
//        System.out.println(price);
//        System.out.println(formData);
//        var test = new HashMap<>();
//        HashMap<String, Object> test = new HashMap<>(); // 그냥 자료형 소개
//        // key의 type (보통 string), value의 type
//        test.put("name", "kim");
//        test.put("age", 20);
//        System.out.println(test.get("name"));
        itemService.addPost(formData);
        return "redirect:/list";
    }

//    @PostMapping("/additem")
//    String addItem(Long id, String title, Integer price){
//
//
//        Item item = Item.createItem(id, title, price);
//        List<Item> result = itemRepository.save(item);
//
//        return "redirect:/list";
//    }

    //    gpt
    @PostMapping("/itemsave") // ✅write.html에 의해 실행됨
//    public String saveItem(@ModelAttribute Item item){
    public String saveItem(String title, Integer price, String username, String imgUrl, Authentication auth, Integer count) {
//        Item item = new Item();
//        item.setTitle(title);
//        item.setPrice(price);
//        itemRepository.save(item);
//        new ItemService().saveItem(String title, Integer price);

        itemService.saveItem(title, price, username, imgUrl, auth, count);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
//    String detail(Model model){
    String detail(@PathVariable Long id, Model model) {
//        List<Item> result = itemRepository.findById(Long id);
//        try {
//            throw new Exception("이런저런이유임");
        Optional<Item> result = itemRepository.findById(id); //findbyId 쓰려면 optional 써야함
        List<Comment> comment = commentRepository.findByParentId(id);
//        model.addAttribute("items", result);
        if (result.isPresent()) { //optional 변수안에 무언가 존재하면 .get 실행
//            System.out.println(result.get());
            model.addAttribute("data", result.get());
            model.addAttribute("comment", comment);
            return "detail.html";
        } else {
            return "redirect:/list";
        }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return "redirect:/list";
//        }
    }


    @GetMapping("/edit/{id}") // getmapping을 api라고 하는듯
    public String edit(@PathVariable Long id, Model model) {
//    public String edit() {
        Optional<Item> result = itemRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("data", result.get());
//            System.out.println(result.get());
            return "edit.html";
        } else{
            return "redirect:/list";
        }

    }


//    @PatchMapping("/item/update/{id}")
//    public String itemUpdate(@PathVariable Long id, @RequestParam String title, @RequestParam Integer price, Model model){
//        itemService.updateItem(id, title, price);
//        return "redirect:/list";
//    }
//}

    @PostMapping("/edit/item") // ✅write.html에 의해 실행됨
//    public String saveItem(@ModelAttribute Item item){
    public String editItem(@RequestParam Long id,
                           @RequestParam String title,
                           @RequestParam Integer price,
                           @RequestParam Integer count) { //@RequestParm은 생략가능
//        System.out.println(id);
//        System.out.println(title);
//        System.out.println(price);

//        var item = new Item();
//        item.setId(id);
//        item.setTitle(title);
//        item.setPrice(price);
//        itemRepository.save(item);

        
        itemService.editItem(id, title, price, count);
        return "redirect:/list";
    }

//    @GetMapping("/test1") ✅상품 삭제 1 강의 내용
////    String test1(@RequestBody Map<String, Object> body){
//    String test1(@RequestParam String name, @RequestParam Integer age){
//        System.out.println(age);
//        return "redirect:/list";
//    }


//    @PostMapping("/delete/{id}")
//    String deleteItem(@RequestParam Long id){
    @DeleteMapping("/item")
//    String deleteItem(@PathVariable Long id){
    ResponseEntity<String> deleteItem(@RequestParam Long id){ // html에서 query string으로 정보 보내니까 RequestParam 써야함
        itemRepository.deleteById(id);
//        return "redirect:/list"; //ajax로 data 주고 받으면 redirect 작동 안함
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/test2")
    String test2(){
        var result = new BCryptPasswordEncoder().encode("문자~~");
        System.out.println(result);
        return "redirect:/list";
    }

//    @GetMapping("/list/page/1")
////    @ResponseBody() //html 파일 보여주는거니까 필요 x
//    String getPageList(Model model, Pageable pageable, @PathVariable Integer page) {
////        itemRepository.findPageBy(pageable);
//        Page<Item> result = itemRepository.findPageBy((Pageable) PageRequest.of(page -1, 3));
//        System.out.println(result.getTotalPages());
//        itemRepository.findAll(pageable).getConetent
////        result.hasNext()
////        System.out.println(itemRepository.findPageBy(PageRequest.of(page -1), 3));
////        Pageable pageable = PageRequest.of(Page, Size);
////        PageRequest.of(Page, Size, Sort.Direction.ASC, "id");
//        model.addAttribute("items", result);
//        return "list.html";
//
//    }



    @GetMapping("/list/page/{page}")
    String getListPage(Model model, @PathVariable Integer page) {
//    String list(Model model) {
        var pageSize = 3;
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(page -1, pageSize));
//        PageRequest.of(몇번째페이지, 한페이지에몇개)
//        아 pagesize만큼 뽑아서 result 변수에 넣는거구나
        model.addAttribute("items", result);
        model.addAttribute("pages", page);
//        model.addAttribute("total", result.getTotalPages());
////         model.addAttribute("total", new int[]{1, 2, 3});
//        System.out.println(result.getTotalElements());
//
        Integer totalPage = result.getTotalPages();
        model.addAttribute("last", totalPage);
        System.out.println(totalPage); //test
        List<Integer> pageList = new ArrayList<>();
        Integer i;
//        for(i=totalPage;i>0;i-=1){
//            pageList.add(totalPage-i+1);
//        }

//        for(i=1;i<=totalPage;i+=1){
//            pageList.add(totalPage-i+1);
//        }
//          totalPage-i니까 i는 역순으로 저장해야 맞는거지, 차라리
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
        // presigned url을 발급해주는 과정
        var result = s3Service.createPresignedUrl("test/" + filename);
        System.out.println(result);
        return result;
//        return "list.html";
    }

//    @PostMapping("/search")
//    String postSearch(@RequestParam String searchText){
////        var result = itemRepository.findByTitleContains(searchText);
//        var result = itemRepository.rawQuery1(searchText);
////        System.out.println(result);
////        System.out.println(result.get(0));
//        return "list.html";
//    }

    @PostMapping("/search")
    String postSearch(@RequestParam String searchText, Model model){
        List<Item> result = itemRepository.rawQuery1(searchText);
        model.addAttribute("items", result); // 검색 결과를 뷰에 전달
        return "list.html";
    }

}