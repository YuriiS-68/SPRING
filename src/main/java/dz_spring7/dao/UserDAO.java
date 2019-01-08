package dz_spring7.dao;

import dz_spring7.model.Ad;
import dz_spring7.model.Filter;
import dz_spring7.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository("userDAO")
@Transactional
public class UserDAO extends GeneralDAO<User> {

    private void registerUser(User user){

    }

    private User loginUser(User user){

        return user;
    }

    public List<Ad> findAds(Filter filter){



        return new ArrayList<>();
    }
}
