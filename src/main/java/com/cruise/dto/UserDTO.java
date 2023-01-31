package com.cruise.dto;

import java.io.Serializable;

/**
 * UserDTO class. Fields are similar to User entity.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private int roleId;
    private double balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
