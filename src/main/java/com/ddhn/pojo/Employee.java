/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.pojo;

import org.apache.commons.codec.digest.DigestUtils;

public class Employee {

    private int id;
    private String name;
    private String phone;
    private String username;
    private String password;
    private int branch_id;
    private int role;

    public Employee() {
        id = 0;
        name = "";
        phone = "";
        username = "";
        password = "";
        branch_id = 0;
        role = 0;
    }

    public Employee(int id, String name, String phone, String username, String password, int branch_id, int role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.branch_id = branch_id;
        this.role = role;
    }

    public Employee(String name, String phone, String username, String password, int branch_id, int role) {
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.branch_id = branch_id;
        this.role = role;
    }

    public Employee(String name, String phone, String username, String password, int branch_id) {
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.branch_id = branch_id;
    }

//    public Employee() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = DigestUtils.md5Hex(password);
    }

    /**
     * @return the branch_id
     */
    public int getBranch_id() {
        return branch_id;
    }

    /**
     * @param branch_id the branch_id to set
     */
    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    /**
     * @return the role
     */
    public int getRole() {
        return role;
    }

    /**
     * @param role the branch_id to set
     */
    public void setRole(int role) {
        this.role = role;
    }
}
