package dz_spring6;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ItemDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final String SQL_GET_ALL_ITEMS = "SELECT * FROM ITEM_SPRING";

    public Item save(Item item){
        entityManager.persist(item);
        return item;
    }

    public void update(Item item){
        entityManager.merge(item);
    }

    public void delete(Long id){
        entityManager.remove(findById(id));
    }

    public Item findById(Long id){
        return entityManager.find(Item.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Item> getItems(){
        return entityManager.createNativeQuery(SQL_GET_ALL_ITEMS, Item.class).getResultList();
    }
}
