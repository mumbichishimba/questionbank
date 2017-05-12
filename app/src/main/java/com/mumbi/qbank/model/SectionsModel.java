package com.mumbi.qbank.model;

import android.util.Log;

import com.mumbi.qbank.entity.Question;
import com.mumbi.qbank.entity.Section;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mumbi Chishimba Jr on 16 March 2017.
 */

public class SectionsModel {

    public static List<Section> getSectionsFromSectionsJSONString(String jsonString) throws JSONException {
        List<Section> sections = new ArrayList<>();
        JSONArray array = new JSONArray(jsonString);
        if (array != null) {
            for (int x = 0; x < array.length(); x++) {
                JSONObject object = array.getJSONObject(x);
                List<Question> questions = new ArrayList<>();

                try {
                    questions = QuestionsModel.getQuestionsArrayFromJsonArray(object.getJSONArray("questions"));
                }catch (Exception e){
                    Log.e("AssessmentModel", AssessmentModel.class.toString(), e);
                }

                Section section = new Section(object.getInt("id"),
                        object.getString("title"),
                        object.getString("description"),
                        object.getInt("assessmentId"),
                        questions);
                sections.add(section);
            }
        }

        return sections;
    }

    public static List<Section> getSectionsFromSectionsJsonArray(JSONArray array) throws JSONException {
        List<Section> sections = new ArrayList<>();

        if (array != null) {
            for (int x = 0; x < array.length(); x++) {
                JSONObject object = array.getJSONObject(x);
                List<Question> questions = new ArrayList<>();

                try {
                    questions = QuestionsModel.getQuestionsArrayFromJsonArray(object.getJSONArray("questions"));
                }catch (Exception e){
                    Log.e("AssessmentModel", AssessmentModel.class.toString(), e);
                }

                Section section = new Section(object.getInt("id"),
                        object.getString("title"),
                        object.getString("description"),
                        object.getInt("assessmentId"),
                        questions);
                sections.add(section);
            }
        }

        return sections;
    }

}
