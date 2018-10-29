package com.lesson2;

import java.util.Map;

public class Step {

    private Long id;
    private Service serviceFrom;
    private Service serviceTo;
    private Map paramServiceFrom;
    private Map paramServiceTo;

    public Step() {
    }

    public Step(Long id, Service serviceFrom, Service serviceTo, Map paramServiceFrom, Map paramServiceTo) {
        this.id = id;
        this.serviceFrom = serviceFrom;
        this.serviceTo = serviceTo;
        this.paramServiceFrom = paramServiceFrom;
        this.paramServiceTo = paramServiceTo;
    }

    public Long getId() {
        return id;
    }

    public Service getServiceFrom() {
        return serviceFrom;
    }

    public Service getServiceTo() {
        return serviceTo;
    }

    public Map getParamServiceFrom() {
        return paramServiceFrom;
    }

    public Map getParamServiceTo() {
        return paramServiceTo;
    }
}
