package dz_spring2_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
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

    @Autowired
    Item item;

    @Autowired
    ItemService itemService;

    @RequestMapping(method = RequestMethod.POST, value = "/saveItem", produces = "text/plain")
    @ResponseBody
    public String save(HttpServletRequest req)throws IOException{

        item = mappingObject(req);

        if (item.getId() == null){
            try {

                item.validationObjectForSave(item);

                itemService.save(item);

            } catch (BadRequestException e) {
                e.printStackTrace();
                return String.valueOf(e);
            }
            catch (HibernateException e){
                System.err.println(e.getMessage());
                throw new HibernateException("Operation failed");
            }
            return "Object with id " + item.getId() + " saved success.";
        }
        else
            return "Item with id " + item.getId() + " can`t be registered in the database";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateItem", produces = "text/plain")
    @ResponseBody
    public String update(HttpServletRequest req)throws IOException{

        item = mappingObject(req);

        try {

            item.validationObject(item);

        } catch (BadRequestException e) {
            e.printStackTrace();
            return String.valueOf(e);
        }

        try {

            if (findById(item.getId()) != null){

                itemService.update(item);
            }else
                return "Updating is not possible. Item with id - " + item.getId() +
                        " is missing in the database.";

        }catch (HibernateException e){
            System.err.println(e.getMessage());
            throw new HibernateException("Operation failed");
        }

        return "Object with id " + item.getId() + " updated successfully.";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/item", produces = "text/plain")
    @ResponseBody
    public String getAll(){
        try {

            return itemService.getAllFiles().toString();

        }catch (HibernateException e){
            System.err.println(e.getMessage());
            throw new HibernateException("Operation failed");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteItem", produces = "text/plain")
    @ResponseBody
    public String delete(Long id){

        if (findById(id) != null){
            try {

                itemService.delete(id);

                return "Object with id " + id + " deleted successfully.";

            }catch (HibernateException e){
                System.err.println(e.getMessage());
                throw new HibernateException("Operation failed");
            }
        }
        else
            return "The ID - " + id + " does not exist";
    }

    private Item mappingObject(HttpServletRequest req)throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        BufferedReader reader = req.getReader();

        while ((line = reader.readLine()) != null){
            stringBuilder.append(line);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String input = objectMapper.writeValueAsString(stringBuilder.toString());

        return objectMapper.convertValue(input, Item.class);
    }

    private Item findById(Long id){

        if (id != null){

            try {

                return itemService.findById(id);

            }catch (HibernateException e){
                System.err.println(e.getMessage());
                throw new HibernateException("Operation failed");
            }
        }
        return null;
    }

    /*private void validationObject(Item item) throws BadRequestException {

        System.out.println("Input item - " + item);

        Class cls = item.getClass();

        Field[] fields = cls.getDeclaredFields();

        System.out.println("Fields in List: " + Arrays.toString(fields));

        for (Field field : fields) {
            try {
                if (field.get(item) == null) {

                    //System.out.println("Field value in loop: - " + field.get(item));

                    //System.out.println("Field in loop: - " + field);
                    throw new BadRequestException("Check the entered data. One of the object fields is missing.");
                }
                //else
                //    throw new BadRequestException("Check the entered data. One of the object fields is missing.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }*/

    /*private List<String> getFieldsItem(Item item)throws BadRequestException{
        if (item == null)
            throw new BadRequestException("Item is not existing");

        List<String> fields = new LinkedList<>();

        fields.add(item.getName());
        fields.add(String.valueOf(item.getDateCreated()));
        fields.add(String.valueOf(item.getLastUpdateDate()));
        fields.add(item.getDescription());

        return fields;
    }*/
}
