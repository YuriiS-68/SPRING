package com;

import java.util.List;

public class Service {

    private Long id;
    private String name;
    private List paramsToCall;

    public Service() {
    }

    public Service(Long id, String name, List paramsToCall) {
        this.id = id;
        this.name = name;
        this.paramsToCall = paramsToCall;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List getParamsToCall() {
        return paramsToCall;
    }
}
