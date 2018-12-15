package dz_spring7.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz_spring7.dao.MessageDAO;
import dz_spring7.execption.BadRequestException;
import dz_spring7.model.Message;
import dz_spring7.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class MessageController {

    private MessageService messageService;
    private MessageDAO messageDAO;

    @Autowired
    public MessageController(MessageService messageService, MessageDAO messageDAO) {
        this.messageService = messageService;
        this.messageDAO = messageDAO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveMessage", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req) throws IOException, BadRequestException {
        Message message = mappingMessage(req);
        System.out.println("Message after mapping: " + message);
        try {
            messageService.save(message);

        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            throw e;
        }
        return "Message saved success.";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateMessage", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req)throws IOException, BadRequestException{
        Message message = mappingMessage(req);

        try {
            messageService.update(message);

        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            throw e;
        }
        return "Message updated success";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteMessage", produces = "text/plain")
    public @ResponseBody
    String delete(HttpServletRequest req)throws BadRequestException{
        Message message = messageDAO.findById(Long.parseLong(req.getParameter("messageId")));

        try {
            messageService.delete(message.getId());

        }catch (BadRequestException e){
            System.err.println(e.getMessage());
            throw e;
        }
        return "Message deleted success";
    }

    private Message mappingMessage(HttpServletRequest req)throws IOException{
        StringBuilder stringBuilder = new StringBuilder();

        try(BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(stringBuilder.toString());
        return objectMapper.convertValue(input, Message.class);
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setMessageDAO(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
}
