package com.mumbi.qbank.model;

import android.util.Log;

import com.mumbi.qbank.entity.Assessment;
import com.mumbi.qbank.entity.Section;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mumbi Chishimba Jr on 15 March 2017.
 */

public class AssessmentModel {
    public static List<Assessment>  jsonAssessmentArrayToAssessmentList(String assessmentString) throws JSONException {
        JSONArray array = new JSONArray(assessmentString);
        List<Assessment> assessments = new ArrayList<>();
        for(int x=0;x<array.length();x++){
            JSONObject object = array.getJSONObject(x);
            List<Section> sections = new ArrayList<>();
            try {
                sections = SectionsModel.getSectionsFromSectionsJsonArray(object.getJSONArray("sections"));
            }catch (Exception e){
                Log.e("AssessmentModel", AssessmentModel.class.toString(), e);
            }

            Assessment assessment = new Assessment(object.getInt("id"), object.getString("name"), object.getString("description"), sections);
            assessments.add(assessment);
        }

        return assessments;
    }

    public static Assessment getSingleAssessmentFromAssessmentString(String assessmentString) throws JSONException {
        JSONObject object = new JSONObject(assessmentString);

        Assessment assessment = new Assessment(object.getInt("id"), object.getString("name"), object.getString("description"), null);

        assessment.setSections(SectionsModel.getSectionsFromSectionsJsonArray(object.getJSONArray("sections")));

        return assessment;
    }

    public boolean updateAssessment(Assessment assessment){



        return false;
    }

}
