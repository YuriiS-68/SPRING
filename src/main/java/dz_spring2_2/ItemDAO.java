package dz_spring2_2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import javax.persistence.TypedQuery;
import java.util.List;

public class ItemDAO {

    private static SessionFactory sessionFactory;

    private static final String SQL_GET_ITEM_BY_ID = "SELECT * FROM ITEM WHERE ID = ?";
    private static final String SQL_GET_ITEM_ALL = "SELECT * FROM ITEM";

    Item save(Item item){

        Transaction transaction = null;
        try(Session session = createSessionFactory().openSession()){
            transaction = session.getTransaction();

            transaction.begin();

            session.save(item);

            System.out.println("Class DAO, ID item - " + item.getId());

            transaction.commit();

        }catch (HibernateException e){

            if (transaction != null) {
                transaction.rollback();
            }

            System.err.println(e.getMessage());
            throw new HibernateException("Operation failed");
        }
        return item;
    }

    @SuppressWarnings("unchecked")
    List<Item> getAllFiles(){

        List<Item> items;

        try (Session session = createSessionFactory().openSession()){

            NativeQuery query = session.createNativeQuery(SQL_GET_ITEM_ALL);
            items = query.addEntity(Item.class).list();

        }catch (HibernateException e){

            System.err.println(e.getMessage());
            throw new HibernateException("Operation failed");
        }
        return items;
    }

    void update(Item item){

        Transaction transaction = null;
        try (Session session = createSessionFactory().openSession()){
            transaction = session.getTransaction();

            transaction.begin();

            session.update(item);

            transaction.commit();

        }catch (HibernateException e){

            if (transaction != null)
                transaction.rollback();

            System.err.println(e.getMessage());
            throw new HibernateException("Operation failed");
        }
    }

    void delete(Long id){

        Transaction transaction = null;
        try (Session session = createSessionFactory().openSession()){
            transaction = session.getTransaction();

            transaction.begin();

            session.delete(findById(id));

            transaction.commit();

        }catch (HibernateException e){

            if (transaction != null)
                transaction.rollback();

            System.err.println(e.getMessage());
            throw new HibernateException("Operation failed");
        }
    }

    @SuppressWarnings("unchecked")
    Item findById(Long id) {

        Item item = null;

        try (Session session = createSessionFactory().openSession()) {

            if (id != null){
                NativeQuery query = session.createNativeQuery(SQL_GET_ITEM_BY_ID);

                if (getSingleResult(query, id) == null){

                    item = null;
                }
                else
                    item = (Item) query.addEntity(Item.class).setParameter(1, id).getSingleResult();
            }
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            throw new HibernateException("Operation failed");
        }
        return item;
    }

    private <T> T getSingleResult(TypedQuery<T> query, Long id){
        query.setMaxResults(1);

        List<T> list = query.setParameter(1, id).getResultList();

        if (list == null || list.isEmpty()){

            return null;
        }
        return list.get(0);
    }

    private static SessionFactory createSessionFactory(){
        if (sessionFactory == null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

}
