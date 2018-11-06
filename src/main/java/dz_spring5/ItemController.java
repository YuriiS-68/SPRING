package dz_spring5;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class ItemController {

    private ItemDAO itemDAO;

    @Autowired
    public ItemController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/item/save", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req)throws IOException{
        Item item = mappingItem(req);

        if (item == null)
            return "Item does not exist.";

        if (item.getId() != null){
            return "This Item with ID - " + item.getId() + " can not save in DB.";
        }
        else
            itemDAO.save(item);

        return "ok";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/item/update", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req)throws Exception{
        Item item = mappingItem(req);

        if (item == null)
            return "Item does not exist.";

        if (itemDAO.findById(item.getId()) == null){
            return "Item with ID - " + item.getId() + " does not exist in the DB.";
        }
        else
            itemDAO.update(item);

        return "ok";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/item/delete", produces = "text/plain")
    public @ResponseBody
    String delete(HttpServletRequest req){

        Item item = itemDAO.findById(Long.parseLong(req.getParameter("itemId")));
        long itemId = Long.parseLong(req.getParameter("itemId"));

        if (item == null){
            return "The Item with ID " + itemId + " does not exist in the DB.";
        }
        else
            itemDAO.delete(item.getId());

        return "ok";
    }

    private Item mappingItem(HttpServletRequest req)throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        try(BufferedReader reader = req.getReader()) {
            String line;

            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String input = objectMapper.writeValueAsString(stringBuilder.toString());

        return objectMapper.convertValue(input, Item.class);
    }
}
