package com.mumbi.qbank.model;

import com.mumbi.qbank.entity.Answer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mumbi Chishimba Jr on 16 March 2017.
 */

public class AnswerModel {
    public static List<Answer> getAnswersFromJsonString(String jsonString) throws JSONException {
        List<Answer> answers = new ArrayList<>();
        JSONArray array = new JSONArray(jsonString);

        if(array!=null){
            for (int x=0;x<array.length();x++){
                JSONObject object = array.getJSONObject(x);
                Answer answer = new Answer(
                        object.getInt("id"),
                        object.getString("answer"),
                        object.getInt("isCorrect"),
                        object.getInt("number"), object.getInt("questionId")
                );
                answers.add(answer);
            }
        }

        return answers;
    }

    public static List<Answer> getAnswersFromJsonArray(JSONArray array) throws JSONException {
        List<Answer> answers = new ArrayList<>();

        if(array!=null){
            for (int x=0;x<array.length();x++){
                JSONObject object = array.getJSONObject(x);
                Answer answer = new Answer(
                        object.getInt("id"),
                        object.getString("answer"),
                        object.getInt("isCorrect"),
                        object.getInt("number"), object.getInt("questionId")
                );
                answers.add(answer);
            }
        }

        return answers;
    }
}
