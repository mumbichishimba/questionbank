/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mumbi.qbank.entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 *
 * @author Mumbi Chishimba Jr
 */

@DatabaseTable(tableName = "user_tbl")
public class User  implements Serializable {


    private static final long serialVersionUID = -222864131214757024L;
    public static final String
            COL_ID = "_id",
            COL_FNAME = "_fname",
            COL_LNAME = "_lname",
            COL_GENDER = "_gender",
            COL_UNAME = "_uname",
            COL_PWD = "_pwd",
            COL_ROLE = "_role",
            COL_INSTITUTION = "_institution";

    @DatabaseField(generatedId = true, columnName = COL_ID)
    protected Integer id;

    @DatabaseField(columnName = COL_FNAME)
    protected String fname;

    @DatabaseField(columnName = COL_LNAME)
    protected String lname;

    @DatabaseField(columnName = COL_GENDER)
    protected String gender;

    @DatabaseField(columnName = COL_UNAME)
    protected String username;

    @DatabaseField(columnName = COL_PWD)
    protected String password;

    @DatabaseField(columnName = COL_ROLE)
    protected int role;

    @DatabaseField(columnName = COL_INSTITUTION)
    protected String institution;

    public User() {
    }

    public User(Integer id, String fname, String lname, String gender, 
            String username, String password, int role, String institution) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.role = role;
        this.institution = institution;
    }

    public User(String fname, String lname, String gender,
                String username, String password, int role, String institution) {
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.role = role;
        this.institution = institution;
    }

    public Integer getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public String getInstitution() {
        return institution;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id 
                + ", fname=" + fname 
                + ", lname=" + lname 
                + ", gender=" + gender 
                + ", username=" + username 
                + ", password=" + password 
                + ", role=" + role 
                + ", institution=" + institution + '}';
    }
    
}
