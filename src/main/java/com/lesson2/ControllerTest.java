package com.lesson2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerTest {

    @Autowired
    private Route route;

    @Autowired
    private Service service;

    @Autowired
    Step step;

    @Bean
    private void callByBean(){

        if (route != null){
            route.getId();
            route.getSteps();
        }

        if (service != null){
            service.getId();
            service.getName();
            service.getParamsToCall();
        }

        if (step !=  null){
            step.getId();
            step.getParamServiceFrom();
            step.getParamServiceTo();
            step.getServiceFrom();
            step.getServiceTo();
        }
    }
}
