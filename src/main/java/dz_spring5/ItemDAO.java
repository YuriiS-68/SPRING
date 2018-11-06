package dz_spring5;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class ItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

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
}
