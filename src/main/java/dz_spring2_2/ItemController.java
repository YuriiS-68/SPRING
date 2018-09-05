package dz_spring2_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @RequestMapping(method = RequestMethod.POST, value = "/saveItem", produces = "text/plain")
    @ResponseBody
    public String save(HttpServletRequest req)throws IOException{

        Item item = mappingObject(req);

        if (checkId(item))
            return "Item with id " + item.getId() + " can`t be registered in the database";

        try {

            validationFieldObject(item);

        }catch (BadRequestException e) {
            e.printStackTrace();
            return String.valueOf(e);
        }

        itemService.save(item);

        return "Object with id " + item.getId() + " saved success.";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateItem", produces = "text/plain")
    @ResponseBody
    public String update(HttpServletRequest req)throws IOException{

        Item item = mappingObject(req);

            if (findById(item.getId()) != null){
                try {

                    validationFieldObject(item);

                }catch (BadRequestException e) {
                    e.printStackTrace();
                    return String.valueOf(e);
                }

                itemService.update(item);

                return "Object with id " + item.getId() + " updated successfully.";

            }else
                return "Updating is not possible. Item with id - " + item.getId() +
                        " is missing in the database.";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/item", produces = "text/plain")
    @ResponseBody
    public String getAll(){

        return itemService.getAllFiles().toString();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteItem", produces = "text/plain")
    @ResponseBody
    public String delete(Long id){

        if (findById(id) != null){

            itemService.delete(id);

            return "Object with id " + id + " deleted successfully.";
        }
        else
            return "The ID - " + id + " does not exist";
    }

    private Item mappingObject(HttpServletRequest req)throws IOException {

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

    private Item findById(Long id){

        return itemService.findById(id);
    }

    private void validationFieldObject(Item item) throws BadRequestException {

        if (item.getName() == null || item.getDateCreated() == null || item.getLastUpdateDate() == null || item.getDescription() == null)
            throw new BadRequestException("Check the entered data. One of the object fields is missing.");
    }

    private boolean checkId(Item item){

        return item.getId() != null && item.getId() != 0;
    }
}
