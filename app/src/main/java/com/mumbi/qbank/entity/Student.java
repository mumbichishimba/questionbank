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

@DatabaseTable(tableName = "student_tbl")
public class Student implements Serializable {

    public static final String
            COL_ID = "_userid",
            COL_GRADE = "_grade";
    private static final long serialVersionUID = -222864131214757024L;

    @DatabaseField(unique = true, columnName = COL_ID)
    private Integer userId;

    @DatabaseField(columnName = COL_GRADE)
    private String grade;

    public Student() {
    }

    public Student(Integer userId, String grade) {
        this.userId = userId;
        this.grade = grade;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getGrade() {
        return grade;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
}
