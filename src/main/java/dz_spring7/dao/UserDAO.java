package dz_spring7.dao;

import dz_spring7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAO extends GeneralDAO<User> {

    private GeneralDAO generalDAO;
    private static final String SQL_GET_ALL_USERS = "SELECT * FROM USER_DZ7";

    @Autowired
    public UserDAO(GeneralDAO generalDAO) {
        this.generalDAO = generalDAO;
    }

    public User save(User user){
        generalDAO.getEntityManager().persist(user);
        return user;
    }

    public void delete(Long id){
        setType(User.class);
        generalDAO.getEntityManager().remove(findById(id));
    }

    /*public User findById(Long id){
        return generalDAO.getEntityManager().find(User.class, id);
    }*/

    @SuppressWarnings("unchecked")
    public List<User> getUsers(){
        return generalDAO.getEntityManager().createNativeQuery(SQL_GET_ALL_USERS, User.class).getResultList();
    }

    public void setGeneralDAO(GeneralDAO generalDAO) {
        this.generalDAO = generalDAO;
    }

    private void registerUser(User user){

    }

    private User loginUser(User user){

        return user;
    }
}
