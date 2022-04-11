package hello.itemservice.web.basic;

import hello.itemservice.dmain.item.Item;
import hello.itemservice.dmain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //final인 생성자를 자동으로 만들어줌
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                        Model model){//form name을 이용해서 확인 가능

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item",item);


        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){//form name을 이용해서 확인 가능
        //ModelAttribute는 모델 객체를 만들어주고, 모델에 넣어주는 역할 2가지를 수행한다.
        itemRepository.save(item);
        //model.addAttribute("item",item); //모델에 넣어주는 역할을 함으로 이거를 생략해도 된다

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){//form name을 이용해서 확인 가능
        //ModelAttribute에서 ("item")을 지우면 클래스명 첫글자를 소문자로 바꾼 값을 담긴다 Item-> item
        itemRepository.save(item);
        //model.addAttribute("item",item); //모델에 넣어주는 역할을 함으로 이거를 생략해도 된다

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4(Item item){ //modelAttribute 생략 가능
        itemRepository.save(item);
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV5(Item item){ //modelAttribute 생략 가능
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",saveItem.getId());
        redirectAttributes.addAttribute("status",true); //못들어간 나머지는 쿼리 파라미터 형식으로출력
        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);

        return "basic/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId,item);

        return "redirect:/basic/items/{itemId}";
    }

    //테스트용용
   @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
       itemRepository.save(new Item("itemB",25000,5));
    }
}
