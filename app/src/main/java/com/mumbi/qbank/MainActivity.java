package com.mumbi.qbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mumbi.qbank.adapter.AssessmentAdapter;
import com.mumbi.qbank.entity.Answer;
import com.mumbi.qbank.entity.Assessment;
import com.mumbi.qbank.entity.Question;
import com.mumbi.qbank.entity.Section;
import com.mumbi.qbank.model.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // Reference of DatabaseHelper class to access its DAOs and other components
    private DatabaseHelper databaseHelper = null;
    List<Assessment> assessments = new ArrayList<>();
    LinearLayout assessmentList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        assessmentList = (LinearLayout) findViewById(R.id.main_content);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddQuestionList.class);
                //startActivity(intent);
                createAssessment(assessmentList);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadAssessments(assessmentList);

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

    private void loadAllQuestions(LinearLayout layout) throws SQLException {
        layout.removeAllViews();

        final LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        Dao<Question, Integer> questionsDao = getHelper().getQuestionDao();
        List<Question> questions = questionsDao.queryForAll();

        for(int x=0;x<questions.size();x++){
            final Dao<Answer, Integer> answerDao = getHelper().getAnswerDao();
            questions.get(x).setAnswers(answerDao.queryForEq("_questionid", questions.get(x).getId()));
        }

        getQuestionsForSectionView(questions, layout);

    }

    private void getQuestionsForSectionView(List<Question> questions, LinearLayout questionLayout) {

        for (Question question : questions) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View questionView = inflater.inflate(R.layout.view_question, null);
            TextView txt_question = (TextView) questionView.findViewById(R.id.txt_question);
            txt_question.setText(question.getQuestion());

            LinearLayout answerLayout = (LinearLayout) questionView.findViewById(R.id.view_answers);

            getAnswersFromAnswerView(question, answerLayout);

            questionLayout.addView(questionView);
        }

    }

    private void getAnswersFromAnswerView(Question question,  LinearLayout answerLayout){
        int x = 1;
        for(Answer answer:question.getAnswers()){
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(question.getQuestionType()==1) {
                View answerView = inflater.inflate(R.layout.view_answer_text, null);

                LinearLayout layout = (LinearLayout) answerView.findViewById(R.id.answer_text);

                EditText text_answer = (EditText) answerView.findViewById(R.id.text_answer);
                TextView lbl_answer = (TextView) answerView.findViewById(R.id.lbl_answer);
                text_answer.setText(answer.getAnswer());

                lbl_answer.setText(x + ") ");
                answerLayout.addView(answerView);
                x++;
            }else{
                View answerView = inflater.inflate(R.layout.view_answer_multiple, null);
                TextView answer_text = (TextView) answerView.findViewById(R.id.txt_answer_creator_text);
                RadioButton answer_Button = (RadioButton) answerView.findViewById(R.id.rb_current_answer_correct);

                answer_text.setText(answer.getAnswer());
                answerLayout.addView(answerView);
            }
        }
    }

    private void loadAssessments(LinearLayout view){
        view.removeAllViews();
        ListView local_list_main;

        final LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View assessmentList = inflater.inflate(R.layout.activity_view_local_assessment_list, null);

        local_list_main = (ListView) assessmentList.findViewById(R.id.local_list);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading assessments");
        progressDialog.show();

        try {
            final Dao<Assessment, Integer> assessmentDao = getHelper().getAssessmentDao();

            assessments = assessmentDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AssessmentAdapter assessmentAdapter = new AssessmentAdapter(MainActivity.this, assessments);
        local_list_main.setAdapter(assessmentAdapter);
        progressDialog.dismiss();

        local_list_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Assessment", assessments.get(position).toString());
                Intent intent = new Intent(MainActivity.this, ViewSingleLocalAssessment.class);
                intent.putExtra("ASSESSMENT", assessments.get(position));
                startActivity(intent);
            }
        });

        view.addView(assessmentList);
    }

    private void createAssessment(final LinearLayout layout){
        layout.removeAllViews();

        final LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View assessmentList = inflater.inflate(R.layout.activity_add_question_list, null);

        final TextView assessment_name, assessment_description;
        Button add;

        assessment_name = (TextView) assessmentList.findViewById(R.id.txt_assessment_name);
        assessment_description = (TextView) assessmentList.findViewById(R.id.txt_assessment_description);
        add = (Button) assessmentList.findViewById(R.id.btn_add_assessment);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = assessment_name.getText().toString();
                String description = assessment_description.getText().toString();

                System.out.println("Name: "+name+", Description: "+description);
                try {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    final Dao<Assessment, Integer> assessmentDao = getHelper().getAssessmentDao();
                    Assessment assessment = new Assessment(0, name, description, null);

                    int create = assessmentDao.create(assessment);

                    System.out.print("New ID: "+create);
                    Log.d("New Record: ", "Record: "+create);
                    Snackbar.make(v, "Created new assessment", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    assessment_name.setText("");
                    assessment_description.setText("");

                    loadAssessments(layout);

                } catch (SQLException e) {
                    Log.e(MainActivity.this.toString(), "Error", e);
                }

            }
        });
        layout.addView(assessmentList);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.online_assessments) {
            Intent intent = new Intent(this, ViewOnlineAssessment.class);
            startActivity(intent);
        } else if (id == R.id.add_question_list) {
            Intent intent = new Intent(this, AddQuestionList.class);
            //startActivity(intent);

            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Loading ...");
            progressDialog.show();
            createAssessment(assessmentList);
            progressDialog.dismiss();
        } else if (id == R.id.local_question_list) {
            Intent intent = new Intent(this, ViewLocalAssessmentList.class);
            //startActivity(intent);



            loadAssessments(assessmentList);

        } else if (id == R.id.add_question) {

        } else if(id == R.id.view_all_questions){
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Loading ...");
            progressDialog.show();
            try {
                loadAllQuestions(assessmentList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
