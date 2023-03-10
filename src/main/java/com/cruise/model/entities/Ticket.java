package com.cruise.model.entities;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Objects;

/**
 * Ticket class represents ticket entity. Matches table 'ticket' in database.
 * Use default constructor to create an instance.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int clientId;
    private int cruiseId;
    private Cruise cruise;

    private String status;
    private Blob document;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCruiseId() {
        return cruiseId;
    }

    public void setCruiseId(int cruiseId) {
        this.cruiseId = cruiseId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Blob getDocument() {
        return document;
    }

    public void setDocument(Blob document) {
        this.document = document;
    }


    @Override
    public boolean equals(Object o){
        if (o instanceof Ticket) {
            Ticket another = (Ticket) o;
            return this.getId() == another.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, cruiseId);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", cruiseId=" + cruiseId +
                ", cruise=" + cruise +
                ", status='" + status + '\'' +
                '}';
    }
}
