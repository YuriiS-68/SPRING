package dz_spring7.dao;

import dz_spring7.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MessageDAO extends GeneralDAO<Message> {

    private GeneralDAO generalDAO;
    private static final String SQL_GET_ALL_MESSAGES = "SELECT * FROM MESSAGE";

    @Autowired
    public MessageDAO(GeneralDAO generalDAO) {
        this.generalDAO = generalDAO;
    }

    public Message save(Message message){
        generalDAO.getEntityManager().persist(message);
        return message;
    }

    public void update(Message message){
        generalDAO.getEntityManager().merge(message);
    }

    public void delete(Long id){
        setType(Message.class);
        generalDAO.getEntityManager().remove(generalDAO.findById(id));
    }

    /*public Message findById(Long id){
        return generalDAO.getEntityManager().find(Message.class, id);
    }*/

    @SuppressWarnings("unchecked")
    public List<Message> getMessages(){
        return generalDAO.getEntityManager().createNativeQuery(SQL_GET_ALL_MESSAGES, Message.class).getResultList();
    }

    public void setGeneralDAO(GeneralDAO generalDAO) {
        this.generalDAO = generalDAO;
    }
}
