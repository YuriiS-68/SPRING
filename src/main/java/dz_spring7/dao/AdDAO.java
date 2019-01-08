package dz_spring7.dao;

import dz_spring7.model.Ad;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("messageDAO")
@Transactional
public class AdDAO extends GeneralDAO<Ad> {

}
