package dz_spring6;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz_spring5.BadRequestException;
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

    private ItemService itemService;
    private ItemDAO itemDAO;

    @Autowired
    public ItemController(ItemService itemService, ItemDAO itemDAO) {
        this.itemService = itemService;
        this.itemDAO = itemDAO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/item/save", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req)throws Exception{
        Item item = mappingItem(req);

        if (item == null)
            throw new BadRequestException("Item does not exist.");

        if (item.getId() != null){
            throw new BadRequestException("This Item with ID - " + item.getId() + " can not save in DB.");
        }
        else
            itemService.save(item);

        return "ok";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/item/update", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req)throws Exception{
        Item item = mappingItem(req);

        if (item == null)
            throw new BadRequestException("Item does not exist.");

        if (itemDAO.findById(item.getId()) == null){
            throw new BadRequestException("Item with ID - " + item.getId() + " does not exist in the DB.");
        }
        else
            itemService.update(item);

        return "ok";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/item/delete", produces = "text/plain")
    public @ResponseBody
    String delete(HttpServletRequest req)throws Exception{

        Item item = itemDAO.findById(Long.parseLong(req.getParameter("itemId")));
        long itemId = Long.parseLong(req.getParameter("itemId"));

        if (item == null){
            throw new BadRequestException("The Item with ID " + itemId + " does not exist in the DB.");
        }
        else
            itemService.delete(item.getId());

        return "ok";
    }

    @RequestMapping(method = RequestMethod.GET, value = "item/getItems", produces = "text/plain")
    @ResponseBody
    public String getAllItems(){

        return itemService.getItems().toString();
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
