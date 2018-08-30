package com;

import java.util.List;

public class Route {

    private String id;
    private List steps;

    public Route() {
    }

    public Route(String id, List steps) {
        this.id = id;
        this.steps = steps;
    }

    String getId() {
        return id;
    }

    public List getSteps() {
        return steps;
    }
}
