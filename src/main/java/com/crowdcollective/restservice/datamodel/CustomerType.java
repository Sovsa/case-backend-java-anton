package com.crowdcollective.restservice.datamodel;

public enum CustomerType {
    SMALL_ENTERPRISE("small_enterprise"),
    LARGE_ENTERPRISE("large_enterprise"),
    PRIVATE("private");

    private String name;

    CustomerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CustomerType getCustomerTypeFromName(String name) {
        for (CustomerType value : CustomerType.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
