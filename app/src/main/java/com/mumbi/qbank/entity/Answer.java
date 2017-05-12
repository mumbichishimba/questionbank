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

public class Answer implements Serializable {

    public static final String
            COL_ID = "_id",
            COL_ANSWER = "_answer",
            COL_ISCORRECT = "_iscorrect",
            COL_NUMBER = "_number",
            COL_QUESTIONID = "_questionid";
    private static final long serialVersionUID = -222864131214757024L;

    @DatabaseField(generatedId = true, columnName = COL_ID)
    private Integer id;

    @DatabaseField(columnName = COL_ANSWER)
    private String answer;

    @DatabaseField(columnName = COL_ISCORRECT)
    private Integer isCorrect;

    @DatabaseField(columnName = COL_NUMBER)
    private Integer number;

    @DatabaseField(columnName = COL_QUESTIONID)
    private int questionId;

    public Answer() {
    }

    public Answer(Integer id, String answer, Integer isCorrect, Integer number, Integer questionId) {
        this.id = id;
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.number = number;
        this.questionId = questionId;
    }

    public Integer getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Answer{" + "id=" + id + ", answer=" + answer + ", isCorrect=" + isCorrect + ", number=" + number + ", questionId=" + questionId + '}';
    }
    
}
