package dz_spring7.service;

import dz_spring7.dao.UserDAO;
import dz_spring7.execption.BadRequestException;
import dz_spring7.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User save(User user)throws BadRequestException{

        if (user != null && user.getId() != null){
            throw new BadRequestException("This User with ID - " + user.getId() + " can not save in DB.");
        }
        else
            userDAO.save(user);

        return null;
    }

    public void delete(Long id)throws BadRequestException{
        if (id == null){
            throw new BadRequestException("Input id is not exist");
        }
        else {
            userDAO.delete(id);
        }
    }

    public List<User> getUsers()throws BadRequestException{
        if (userDAO.getUsers() == null)
            throw new BadRequestException("List is not exist.");

        return userDAO.getUsers();
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
