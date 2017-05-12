package com.mumbi.qbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mumbi.qbank.config.Config;
import com.mumbi.qbank.entity.Answer;
import com.mumbi.qbank.entity.Assessment;
import com.mumbi.qbank.entity.Question;
import com.mumbi.qbank.entity.Section;
import com.mumbi.qbank.model.AssessmentModel;
import com.mumbi.qbank.ui.UISection;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ViewSingleOnineAssesment extends AppCompatActivity {
    private Assessment assessment = null;
    private LinearLayout layout;
    List<UISection> uiSections = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_onine_assesment);
        layout = (LinearLayout) findViewById(R.id.view_assessments);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                assessment = (Assessment) extras.get("ASSESSMENT");
                Log.d(ViewSingleOnineAssesment.class.toString(), assessment.toString());
                sendRequest();
            }
        } catch (Exception e) {
            Log.e("SingleAssessment", ViewSingleOnineAssesment.class.toString(), e);
        }
    }

    private void sendRequest() {
        System.out.print("SendRequest");

        final ProgressDialog progressDialog = new ProgressDialog(ViewSingleOnineAssesment.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading assessments from web server...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Config.getAllAssessmentByIDFromURL(assessment.getId()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    assessment = AssessmentModel.getSingleAssessmentFromAssessmentString(response);
                } catch (Exception e) {
                    Log.e("AssessmentModel", ViewSingleOnineAssesment.class.toString(), e);
                }
                for (Section section : assessment.getSections()) {
                    LayoutInflater inflater = (LayoutInflater) ViewSingleOnineAssesment.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View rowView = inflater.inflate(R.layout.view_section, null);

                    UISection uiSection = new UISection(ViewSingleOnineAssesment.this, section);

                    uiSection.setSection(section);
                    uiSections.add(uiSection);

                    Button sectionHeader = (Button) rowView.findViewById(R.id.btn_section);
                    sectionHeader.setText(section.getTitle());
                    layout.addView(rowView);
                    LinearLayout questionLayout = (LinearLayout) rowView.findViewById(R.id.view_question);

                    getQuestionsForSectionView(section, questionLayout);
                }

                Log.d(ViewSingleOnineAssesment.class.toString(), assessment.toString());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getQuestionsForSectionView(Section section, LinearLayout questionLayout) {

        for (Question question : section.getQuestions()) {
            LayoutInflater inflater = (LayoutInflater) ViewSingleOnineAssesment.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View questionView = inflater.inflate(R.layout.view_question, null);
            TextView txt_question = (TextView) questionView.findViewById(R.id.txt_question);
            txt_question.setText(question.getQuestion());

            LinearLayout answerLayout = (LinearLayout) questionView.findViewById(R.id.view_answers);

            getAnswersFromAnswerView(question, answerLayout);

            questionLayout.addView(questionView);
        }

    }

    private void getAnswersFromAnswerView(Question question,  LinearLayout answerLayout){
        for(Answer answer:question.getAnswers()){
            LayoutInflater inflater = (LayoutInflater) ViewSingleOnineAssesment.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View answerView = inflater.inflate(R.layout.view_answer_text, null);

            LinearLayout layout = (LinearLayout) answerView.findViewById(R.id.answer_text);

            EditText text_answer = (EditText) answerView.findViewById(R.id.text_answer);
            text_answer.setText(answer.getAnswer());
            text_answer.setEnabled(false);

            answerLayout.addView(answerView);
        }
    }

}
