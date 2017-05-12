package com.mumbi.qbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mumbi.qbank.config.Config;
import com.mumbi.qbank.entity.Answer;
import com.mumbi.qbank.entity.Assessment;
import com.mumbi.qbank.entity.Question;
import com.mumbi.qbank.entity.Section;
import com.mumbi.qbank.model.AssessmentModel;
import com.mumbi.qbank.model.DatabaseHelper;
import com.mumbi.qbank.ui.UISection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewSingleLocalAssessment extends AppCompatActivity {
    private Assessment assessment = null;
    private LinearLayout layout;
    private DatabaseHelper databaseHelper = null;
    private Button editAssessment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_local_assessment);

        layout = (LinearLayout) findViewById(R.id.list_local_assessment);

        editAssessment = (Button) findViewById(R.id.btn_add_questions);

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


        editAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewSingleLocalAssessment.this, AddAssessmentLayout.class);
                intent.putExtra("ASSESSMENT", assessment);
                startActivity(intent);
            }
        });

    }


    private void sendRequest() throws SQLException {
        System.out.print("SendRequest");

        final ProgressDialog progressDialog = new ProgressDialog(ViewSingleLocalAssessment.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading assessments from web server...");
        progressDialog.show();

        try {
            final Dao<Assessment, Integer> assessmentDao = getHelper().getAssessmentDao();
            assessment = assessmentDao.queryForId(assessment.getId());
            assessment = getOldAssessmentById(assessment);
        } catch (Exception e) {
            Log.e("AssessmentModel", ViewSingleOnineAssesment.class.toString(), e);
        }

        for (Section section : assessment.getSections()) {
            LayoutInflater inflater = (LayoutInflater) ViewSingleLocalAssessment.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.view_section, null);

            Button sectionHeader = (Button) rowView.findViewById(R.id.btn_section);
            sectionHeader.setText(section.getTitle());
            layout.addView(rowView);
            LinearLayout questionLayout = (LinearLayout) rowView.findViewById(R.id.view_question);

            getQuestionsForSectionView(section, questionLayout);
        }

        Log.d(ViewSingleOnineAssesment.class.toString(), assessment.toString());
        progressDialog.dismiss();
    }

    private Assessment getOldAssessmentById(Assessment assessment) throws SQLException {

        final Dao<Section, Integer> sectionDao = getHelper().getSectionDao();
        assessment.setSections(sectionDao.queryForEq("_assessmentid", assessment.getId()));

        for (int x = 0; x < assessment.getSections().size(); x++) {
            final Dao<Question, Integer> questionDao = getHelper().getQuestionDao();
            assessment.getSections().get(x).setQuestions(questionDao.queryForEq("_number", assessment.getSections().get(x).getId()));

            for (int y = 0; y < assessment.getSections().get(x).getQuestions().size(); y++) {
                final Dao<Answer, Integer> answerDao = getHelper().getAnswerDao();
                assessment.getSections().get(x).getQuestions().get(y).setAnswers(answerDao.queryForEq("_questionid", assessment.getSections().get(x).getQuestions().get(y).getId()));

            }
        }

        return assessment;
    }

    private void getQuestionsForSectionView(Section section, LinearLayout questionLayout) {

        for (Question question : section.getQuestions()) {
            LayoutInflater inflater = (LayoutInflater) ViewSingleLocalAssessment.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View questionView = inflater.inflate(R.layout.view_question, null);
            TextView txt_question = (TextView) questionView.findViewById(R.id.txt_question);
            txt_question.setText(question.getQuestion());

            LinearLayout answerLayout = (LinearLayout) questionView.findViewById(R.id.view_answers);

            getAnswersFromAnswerView(question, answerLayout);

            questionLayout.addView(questionView);
        }

    }

    private void getAnswersFromAnswerView(Question question, LinearLayout answerLayout) {
        final List<RadioButton> radioButtons = new ArrayList<>();

        int x = 1;

        if (question.getQuestionType() == 2) {
            x = 0;
        }

        for (Answer answer : question.getAnswers()) {

            if (question.getQuestionType() == 1) {
                LayoutInflater inflater = (LayoutInflater) ViewSingleLocalAssessment.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View answerView = inflater.inflate(R.layout.view_answer_text, null);

                LinearLayout layout = (LinearLayout) answerView.findViewById(R.id.answer_text);

                EditText text_answer = (EditText) answerView.findViewById(R.id.text_answer);
                TextView lbl_answer = (TextView) answerView.findViewById(R.id.lbl_answer);
                text_answer.setText(answer.getAnswer());

                lbl_answer.setText(x + ") ");
                answerLayout.addView(answerView);
                x++;
            } else {
                LayoutInflater inflater = (LayoutInflater) ViewSingleLocalAssessment.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View answerView = inflater.inflate(R.layout.view_answer_multiple, null);
                final int temp_var = x;

                TextView answer_text = (TextView) answerView.findViewById(R.id.txt_answer_creator_text);
                final RadioButton radioButton = (RadioButton) answerView.findViewById(R.id.rb_current_answer_correct);

                answer_text.setText(answer.getAnswer());

                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<radioButtons.size();i++){
                            if(i==temp_var){
                                radioButton.setChecked(true);
                            }else {
                                radioButton.setChecked(false);
                            }
                        }
                    }
                });

                radioButtons.add(radioButton);

                answerLayout.addView(answerView);
                x++;

            }
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

		/*
         * You'll need this in your class to release the helper when done.
		 */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
