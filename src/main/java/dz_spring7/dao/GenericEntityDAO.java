package dz_spring7.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericEntityDAO<T> {

    T findById(Serializable id);

    void update(T t);

    List<T> findAll();
}
