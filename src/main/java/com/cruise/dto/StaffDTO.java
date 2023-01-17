package com.cruise.dto;

import java.io.Serializable;

public class StaffDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private int cruiseShipId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getCruiseShipId() {
        return cruiseShipId;
    }

    public void setCruiseShipId(int cruiseShipId) {
        this.cruiseShipId = cruiseShipId;
    }
}
