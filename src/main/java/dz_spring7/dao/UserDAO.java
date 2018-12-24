package dz_spring7.dao;

import dz_spring7.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userDAO")
@Transactional
public class UserDAO extends GeneralDAO<User> {

    private static final String SQL_GET_ALL_USERS = "SELECT * FROM USER_DZ7";

    @Override
    public List<User> findAll() {
        setType(User.class);
        return findAll(SQL_GET_ALL_USERS);
    }

    private void registerUser(User user){

    }

    private User loginUser(User user){

        return user;
    }
}
