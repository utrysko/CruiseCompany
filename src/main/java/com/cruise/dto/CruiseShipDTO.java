package com.cruise.dto;

import com.cruise.model.entities.Staff;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * CruiseShipDTO class. Fields are similar to CruiseShip entity.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class CruiseShipDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int capacity;
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
}
