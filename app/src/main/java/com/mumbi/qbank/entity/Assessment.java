/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mumbi.qbank.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Mumbi Chishimba Jr
 */

@DatabaseTable(tableName = "assessment_tbl")
public class Assessment implements Serializable {

    private static final long serialVersionUID = -222864131214757024L;
    public static final String COL_ID = "_id", COL_NAME = "_name", COL_DESC = "_desctiption";

    @DatabaseField(generatedId = true, columnName = COL_ID)
    private Integer id;

    @DatabaseField(columnName = COL_NAME)
    private String name;

    @DatabaseField(columnName = COL_DESC)
    private String description;

    private List<Section> sections;

    public Assessment() {
    }

    public Assessment(Integer id, String name, String description, List<Section> sections) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sections = sections;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return "Assessment{" + "id=" + id + ", name=" + name + ", description=" + description + ", sections=" + sections + '}';
    }
    
}
