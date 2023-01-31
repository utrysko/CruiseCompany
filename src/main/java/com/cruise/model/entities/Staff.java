package com.cruise.model.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Staff class represents staff entity. Matches table 'staff' in database.
 * Use default constructor to create an instance.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class Staff implements Serializable {

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

    @Override
    public boolean equals(Object o){
        if (o instanceof Staff){
            Staff another = (Staff) o;
            return this.getId() == another.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, position, cruiseShipId);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", cruiseShipId=" + cruiseShipId +
                '}';
    }
}
