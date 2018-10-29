package config_java;

import dz_spring2_2.ItemController;
import dz_spring2_2.ItemDAO;
import dz_spring2_2.ItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("dz_spring2_2")
public class AppContext{

    @Bean()
    @Scope("prototype")
    public ItemDAO itemDAO(){
        ItemDAO itemDAO = new ItemDAO();

        return itemDAO;
    }

    @Bean
    public ItemService itemService(){
        ItemService itemService = new ItemService();

        itemService.setItemDAO(itemDAO());

        return itemService;
    }

    @Bean
    public ItemController itemController(){
        ItemController itemController = new ItemController();

        itemController.setItemService(itemService());

        return itemController;
    }
}
