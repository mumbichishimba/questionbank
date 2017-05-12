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

@DatabaseTable(tableName = "question_tbl")
public class Question  implements Serializable {

    private static final long serialVersionUID = -222864131214757024L;
    public static final String
            COL_ID = "_id",
            COL_QUESTION = "_question",
            COL_QUESTION_TYPE = "_questiontype",
            COL_DESC = "_desctiption",
            COL_NUMBER = "_number",
            COL_SECTION_ID = "_sectionid";

    @DatabaseField(generatedId = true, columnName = COL_ID)
    private Integer id;

    @DatabaseField(columnName = COL_QUESTION)
    private String question;

    @DatabaseField(columnName = COL_QUESTION_TYPE)
    private int questionType;

    @DatabaseField(columnName = COL_DESC)
    private String description;

    @DatabaseField(columnName = COL_SECTION_ID)
    private int sectionId;

    @DatabaseField(columnName = COL_NUMBER)
    private Integer number;

    private List<Answer> answers;

    public Question() {
    }

    public Question(Integer id, String question, int questionType, String description, Integer number, int sectionId, List<Answer> answers) {
        this.id = id;
        this.question = question;
        this.questionType = questionType;
        this.description = description;
        this.number = number;
        this.sectionId = sectionId;
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionType() {
        return questionType;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNumber() {
        return number;
    }

    public int getSectionId() {
        return sectionId;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", question=" + question + ", questionType=" + questionType + ", description=" + description + ", number=" + number + ", sectionId=" + sectionId + ", answers=" + answers + '}';
    }

    
}
