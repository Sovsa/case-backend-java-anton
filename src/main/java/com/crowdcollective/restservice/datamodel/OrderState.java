package com.crowdcollective.restservice.datamodel;

public enum OrderState {
    CANCELED("canceled"),
    ADDED("added"),
    COMPLETED("completed");

    private String name;

    OrderState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
