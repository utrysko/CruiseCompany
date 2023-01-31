package com.cruise.model.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Cruise class represents cruise entity. Matches table 'cruises' in database.
 * Use default constructor to create an instance.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class Cruise implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Date start;
    private Date end;
    private String status;
    private double ticketPrice;
    private int freeSpaces;
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

    public int getFreeSpaces() {
        return freeSpaces;
    }

    public void setFreeSpaces(int freeSpaces) {
        this.freeSpaces = freeSpaces;
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
    public int hashCode() {
        return Objects.hash(id, start, end, status, ticketPrice, freeSpaces, cruiseShip, route);
    }

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", status='" + status + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", freeSpaces=" + freeSpaces +
                ", cruiseShip=" + cruiseShip +
                ", route=" + route +
                '}';
    }
}
