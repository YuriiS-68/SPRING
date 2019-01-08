package dz_spring7.dao;

import java.io.Serializable;

public interface GenericEntityDAO<T> {

    T findById(Serializable id);

    void update(T t);
}
