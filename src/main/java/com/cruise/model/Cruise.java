package com.cruise.model;

import java.io.Serializable;
import java.sql.Date;

public class Cruise implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Date start;
    private Date end;
    private String status;
    private double ticketPrice;
    private CruiseShip cruiseShip;
    private Route route;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cruise){
            Cruise another = (Cruise) obj;
            return this.getId() == another.getId();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", status='" + status + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", route=" + route +
                ", cruiseShip=" + cruiseShip +
                '}';
    }
}
