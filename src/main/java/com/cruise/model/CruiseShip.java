package com.cruise.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class CruiseShip implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int capacity;
    private int freeSpaces;
    private String status;
    private Date availableFrom;
    private List<Staff> staff;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFreeSpaces() {
        return freeSpaces;
    }

    public void setFreeSpaces(int freeSpaces) {
        this.freeSpaces = freeSpaces;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof CruiseShip){
            CruiseShip another = (CruiseShip) o;
            return Objects.equals(this.getName(), another.getName());
        }
        return false;
    }

}