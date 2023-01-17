package com.cruise.dto;

import com.cruise.model.CruiseShip;
import com.cruise.model.Route;

import java.io.Serializable;
import java.sql.Date;

public class CruiseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Date start;
    private Date end;
    private String status;
    private double ticketPrice;
    private Route route;
    private CruiseShip cruiseShip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public CruiseShip getCruiseShip() {
        return cruiseShip;
    }

    public void setCruiseShip(CruiseShip cruiseShip) {
        this.cruiseShip = cruiseShip;
    }
}
