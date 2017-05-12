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

@DatabaseTable(tableName = "section_tbl")
public class Section implements Serializable {

    private static final long serialVersionUID = -222864131214757024L;
    public static final String
            COL_ID = "_id",
            COL_TITLE = "_title",
            COL_DESC = "_desctiption",
            COL_ASSESSMENTID = "_assessmentid";

    @DatabaseField(generatedId = true, columnName = COL_ID)
    private Integer id;

    @DatabaseField(columnName = COL_TITLE)
    private String title;

    @DatabaseField(columnName = COL_DESC)
    private String description;

    @DatabaseField(columnName = COL_ASSESSMENTID)
    private int assessmentId;
    private List<Question> questions;

    public Section() {
    }

    public Section(Integer id, String title, String description, int assessmentId, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assessmentId = assessmentId;
        this.questions = questions;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Section{" + "id=" + id + ", title=" + title + ", description=" + description + ", assessmentId=" + assessmentId + ", questions=" + questions + '}';
    }
    
}
