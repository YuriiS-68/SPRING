package dz_spring7.controller;

import dz_spring7.dao.UserDAO;
import dz_spring7.execption.BadRequestException;
import dz_spring7.model.User;
import dz_spring7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class UserController extends Utils<User> {

    private UserService userService;
    private UserDAO userDAO;

    @Autowired
    public UserController(UserService userService, UserDAO userDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @RequestMapping (method = RequestMethod.POST, value = "/saveUser", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req) throws IOException, BadRequestException {
        User user = mappingObject(req);
        System.out.println("User after mapping: " + user);

        try {
            userService.save(user);

        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            throw e;
        }
        return "User saved success.";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteUser", produces = "text/plain")
    public @ResponseBody
    String delete(HttpServletRequest req)throws BadRequestException{
        User user = userDAO.findById(Long.parseLong(req.getParameter("userId")));
        long userId = Long.parseLong(req.getParameter("userId"));

        try {
            if (user == null){
                return "The User with ID " + userId + " does not exist in the DB.";
            }
            else {
                userService.delete(user.getId());
            }
        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            throw e;
        }
        return "User deleted success";
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
