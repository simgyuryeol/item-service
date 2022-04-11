package hello.itemservice.dmain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item=new Item("itemA",10000,10);

        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll(){
        //given
        Item item=new Item("itemA",10000,10);
        Item item2=new Item("itemB",20000,30);

        itemRepository.save(item);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item,item2); //item과 item2를 가지고 있느냐
    }

    @Test
    void updateItem(){
        //given
        Item item=new Item("itemA",10000,10);
        Item saveitem = itemRepository.save(item);
        Long itemId = saveitem.getId();

        //when
        Item updateParam = new Item("item2", 2000, 100);
        itemRepository.update(itemId,updateParam);

        //then
        Item finditem = itemRepository.findById(itemId);
        assertThat(finditem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(finditem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(finditem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }

}