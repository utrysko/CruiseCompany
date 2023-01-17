package com.cruise.model;

import java.io.Serializable;
import java.util.List;

public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int numberOfPorts;
    private String startPort;
    private List<String> middlePorts;
    private String endPort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }

    public String getStartPort() {
        return startPort;
    }

    public void setStartPort(String startPort) {
        this.startPort = startPort;
    }

    public List<String> getMiddlePorts() {
        return middlePorts;
    }

    public void setMiddlePorts(List<String> middlePorts) {
        this.middlePorts = middlePorts;
    }

    public String getEndPort() {
        return endPort;
    }

    public void setEndPort(String endPort) {
        this.endPort = endPort;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Route) {
            Route another = (Route) o;
            return this.getId() == another.getId();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", numberOfPorts=" + numberOfPorts +
                ", startPort='" + startPort + '\'' +
                ", middlePorts=" + middlePorts +
                ", endPort='" + endPort + '\'' +
                '}';
    }
}
