package com.mumbi.qbank.model;

import android.util.Log;

import com.mumbi.qbank.entity.Answer;
import com.mumbi.qbank.entity.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mumbi Chishimba Jr on 16 March 2017.
 */

public class QuestionsModel {
    public static List<Question> getQuestionsArrayFromJsonString(String jsonString) throws JSONException {
        List<Question> questions = new ArrayList<>();
        JSONArray array = new JSONArray(jsonString);
        if (questions != null) {
            for (int x = 0; x < array.length(); x++) {
                JSONObject object = array.getJSONObject(x);
                List<Answer> answers = new ArrayList<>();

                try {
                    answers = AnswerModel.getAnswersFromJsonArray(object.getJSONArray("answers"));
                }catch (Exception e){
                    Log.e("AssessmentModel", AssessmentModel.class.toString(), e);
                }

                Question question = new Question(
                        object.getInt("id"),
                        object.getString("question"),
                        object.getInt("questionType"), object.getString("description"),
                        object.getInt("number"), object.getInt("questionId"),
                        answers
                );
                questions.add(question);
            }
        }

        return questions;
    }

    public static List<Question> getQuestionsArrayFromJsonArray(JSONArray array) throws JSONException {
        List<Question> questions = new ArrayList<>();

        if (questions != null) {
            for (int x = 0; x < array.length(); x++) {
                JSONObject object = array.getJSONObject(x);
                List<Answer> answers = new ArrayList<>();

                try {
                    answers = AnswerModel.getAnswersFromJsonArray(object.getJSONArray("answers"));
                }catch (Exception e){
                    Log.e("AssessmentModel", AssessmentModel.class.toString(), e);
                }

                Question question = new Question(
                        object.getInt("id"),
                        object.getString("question"),
                        object.getInt("questionType"),
                        object.getString("description"),
                        object.getInt("number"),
                        object.getInt("sectionId"),
                        answers
                );
                questions.add(question);
            }
        }

        return questions;
    }
}
