package com.cruise.dto;

import java.io.Serializable;
import java.util.List;

/**
 * RouteDTO class. Fields are similar to Route entity.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class RouteDTO implements Serializable {

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
}
