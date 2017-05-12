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
@DatabaseTable(tableName = "educator_tbl")
public class Educator implements Serializable {
    public static final String
            COL_USER_ID = "_userid",
            COL_SSN = "_ssn",
            COL_POS = "_position";
    private static final long serialVersionUID = -222864131214757024L;

    @DatabaseField(id = true, unique = true, columnName = COL_USER_ID)
    private Integer userId;

    @DatabaseField(unique = true, columnName = COL_SSN)
    private String ssn;

    @DatabaseField(unique = true, columnName = COL_POS)
    private String position;

    public Educator() {
    }

    public Educator(Integer userId, String ssn, String position) {
        this.userId = userId;
        this.ssn = ssn;
        this.position = position;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getSsn() {
        return ssn;
    }

    public String getPosition() {
        return position;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Educator{" + "userId=" + userId + ", ssn=" + ssn + ", position=" + position + '}';
    }
    
}
