package dz_spring5;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
@Transactional
public class ItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SQL_GET_ITEM_BY_ID = "SELECT * FROM ITEM_SPRING WHERE ID = :idParam";

    public Item save(Item item){
        entityManager.persist(item);
        return item;
    }

    public void update(Item item){
        entityManager.merge(item);
    }

    public void delete(Long id){

        Item item = entityManager.find(Item.class, id);
        entityManager.remove(item);
    }

    @SuppressWarnings("unchecked")
    public Item findById(Long id){
        TypedQuery<Item> query = (TypedQuery<Item>) entityManager.createNativeQuery(SQL_GET_ITEM_BY_ID, Item.class);
        query.setParameter("idParam", id);

        Item item;

        try {
            item = query.getSingleResult();
        }catch (NoResultException e){
            System.err.println(e.getMessage());
            return null;
        }

        return item;
    }
}
