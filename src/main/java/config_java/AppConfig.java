package config_java;

import dz_spring7.controller.AdController;
import dz_spring7.controller.UserController;
import dz_spring7.dao.AdDAO;
import dz_spring7.dao.UserDAO;
import dz_spring7.service.AdService;
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
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan("dz_spring7")
public class AppConfig {

    @Bean
    public UserDAO userDAO(){
        return new UserDAO();
    }

    @Bean
    public AdDAO messageDAO(){
        return new AdDAO();
    }

    @Bean
    public UserService userService(){
        UserService userService = new UserService(userDAO());
        userService.setUserDAO(userDAO());
        return userService;
    }

    @Bean
    public AdService messageService(){
        AdService adService = new AdService(messageDAO());
        adService.setAdDAO(messageDAO());
        return adService;
    }

    @Bean
    public AdController messageController(){
        AdController adController = new AdController(messageService(), messageDAO());
        adController.setAdService(messageService());
        adController.setAdDAO(messageDAO());
        return adController;
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
