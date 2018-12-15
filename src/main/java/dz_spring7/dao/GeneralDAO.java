package dz_spring7.dao;

import dz_spring7.model.IdEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Repository
@Transactional
public class GeneralDAO <T extends IdEntity> {

    @PersistenceContext
    private EntityManager entityManager;
    private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss");
    private Class<T> type;



    public T findById(long id){
        return entityManager.find(type, id);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static DateFormat getFORMAT() {
        return FORMAT;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }
}
