package dz_spring7.dao;

import dz_spring7.model.Ad;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("messageDAO")
@Transactional
public class AdDAO extends GeneralDAO<Ad> {

    private static final String SQL_GET_ALL_AD = "SELECT * FROM MESSAGE";

    @Override
    public List<Ad> findAll() {
        setType(Ad.class);
        return findAll(SQL_GET_ALL_AD);
    }
}
