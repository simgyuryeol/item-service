package hello.itemservice.dmain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    //실제로는 멀티 스레드 환경에서 해쉬맵을 사용하면 안됨 사용하고 싶으면 ConcurrentHashMap을 사용해야 한다.
    private static long sequence =0L; //static안하면 새로운 객체가 생겨난다
    //이것도 그냥 long을 사용하면 멀티 스레드 환경에서 꼬일 수 잇으므로 atomic long등 다른것을 사용해야 한다.

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values()); //한번 감싸서 리턴, 이렇게 리턴하면 실제 store에는 값변화가 없어 안전하다
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }
}
