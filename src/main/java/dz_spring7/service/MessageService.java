package dz_spring7.service;

import dz_spring7.dao.MessageDAO;
import dz_spring7.execption.BadRequestException;
import dz_spring7.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageDAO messageDAO;

    @Autowired
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message save(Message message)throws BadRequestException{
        if (message != null && message.getId() != null){
            throw new BadRequestException("This Message with ID - " + message.getId() + " can not save in DB.");
        }
        else {
            messageDAO.save(message);
        }
        return message;
    }

    public void update(Message message)throws BadRequestException{
        if (message == null){
            throw new BadRequestException("Message is not exist");
        }

        if (messageDAO.findById(message.getId()) == null){
            throw new BadRequestException("Message with ID - " + message.getId() + " does not exist in the DB.");
        }
        else {
            messageDAO.update(message);
        }
    }

    public void delete(Long id)throws BadRequestException{
        if (id == null){
            throw new BadRequestException("Input id is not exist");
        }
        else {
            messageDAO.delete(id);
        }
    }

    public List<Message> getMessages()throws BadRequestException{
        if (messageDAO.getMessages() == null)
            throw new BadRequestException("List is not exist.");

        return messageDAO.getMessages();
    }

    public void setMessageDAO(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
}
