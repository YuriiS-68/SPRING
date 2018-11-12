package dz_spring6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private ItemDAO itemDAO;

    @Autowired
    public ItemService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }
    
    public Item save(Item item){
        
        return itemDAO.save(item);
    }
    
    public void update(Item item){
        
        itemDAO.update(item);
    }
    
    public void delete(Long id){
        
        itemDAO.delete(id);
    }
    
    public List<Item> getItems(){
        
        return itemDAO.getItems();
    }
}
