package config_java;

import dz_spring7.controller.MessageController;
import dz_spring7.controller.UserController;
import dz_spring7.dao.GeneralDAO;
import dz_spring7.dao.MessageDAO;
import dz_spring7.dao.UserDAO;
import dz_spring7.service.MessageService;
import dz_spring7.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@ComponentScan("dz_spring7")
public class AppConfig {

    @Bean
    public GeneralDAO generalDAO(){
        GeneralDAO generalDAO = new GeneralDAO();
        return generalDAO;
    }

    @Bean
    public UserDAO userDAO(){
        UserDAO userDAO = new UserDAO(generalDAO());
        userDAO.setGeneralDAO(generalDAO());
        return userDAO;
    }

    @Bean
    public MessageDAO messageDAO(){
        MessageDAO messageDAO = new MessageDAO(generalDAO());
        messageDAO.setGeneralDAO(generalDAO());
        return messageDAO;
    }

    @Bean
    public UserService userService(){
        UserService userService = new UserService(userDAO());
        userService.setUserDAO(userDAO());
        return userService;
    }

    @Bean
    public MessageService messageService(){
        MessageService messageService = new MessageService(messageDAO());
        messageService.setMessageDAO(messageDAO());
        return messageService;
    }

    @Bean
    public MessageController messageController(){
        MessageController messageController = new MessageController(messageService(), messageDAO());
        messageController.setMessageService(messageService());
        messageController.setMessageDAO(messageDAO());
        return messageController;
    }

    @Bean
    public UserController userController(){
        UserController userController = new UserController(userService(), userDAO());
        userController.setUserService(userService());
        userController.setUserDAO(userDAO());
        return userController;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("dz_spring7");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        //em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@grome.ckmizrptx9hw.eu-central-1.rds.amazonaws.com:1521:ORCL");
        dataSource.setUsername("main");
        dataSource.setPassword("12345678");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    /*Properties additionalProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");

        return properties;
    }*/
}
