package com.mumbi.qbank;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mumbi.qbank.entity.Answer;
import com.mumbi.qbank.entity.Assessment;
import com.mumbi.qbank.entity.Question;
import com.mumbi.qbank.entity.Section;
import com.mumbi.qbank.model.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddAssessmentLayout extends AppCompatActivity {
    private LinearLayout view_section_pane;
    private Button btn_section_header, save;
    private int SECTION_INDEX = 0;
    private Assessment assessment = null;
    private LinearLayout layout;
    private DatabaseHelper databaseHelper = null;
    private PopupWindow mPopupWindow;
    private List<Section> sections = new ArrayList<>();
    private List<View> SECTIONS_VIEW = new ArrayList<>();
    private ScrollView scroll_section;

    private Assessment old_assessments = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment_layout);
        view_section_pane = (LinearLayout) findViewById(R.id.view_section_pane);
        btn_section_header = (Button) findViewById(R.id.btn_addsection);
        scroll_section = (ScrollView) findViewById(R.id.scroll_section);
        save = (Button) findViewById(R.id.save_assessment_btn);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                assessment = (Assessment) extras.get("ASSESSMENT");
                Log.d(ViewSingleOnineAssesment.class.toString(), assessment.toString());
            }
        } catch (Exception e) {
            Log.e("SingleAssessment", ViewSingleOnineAssesment.class.toString(), e);
        }


        try {
            old_assessments = getOldAssessmentById(assessment);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadOldAssessments();

        btn_section_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View sectionView = layoutInflater.inflate(R.layout.creator_section, null);
                final int CURRENT_SECTION = SECTION_INDEX;
                final Section section = new Section(SECTION_INDEX, "", "", 0, new ArrayList<Question>());
                section.setAssessmentId(assessment.getId());
                sections.add(section);
                final int QUESTION_INDEX = 0;

                Button section_header = (Button) sectionView.findViewById(R.id.btn_section_header);
                Button addQuestion = (Button) sectionView.findViewById(R.id.btn_add_question);
                EditText title = (EditText) sectionView.findViewById(R.id.txt_section_title);
                final EditText description = (EditText) sectionView.findViewById(R.id.txt_section_description);
                section_header.setText("Section "+(SECTION_INDEX+1));
                final LinearLayout questionLayout = (LinearLayout) sectionView.findViewById(R.id.view_questions_list);

                // Section header change
                title.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        sections.get(CURRENT_SECTION).setTitle(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                description.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        sections.get(CURRENT_SECTION).setDescription(s.toString());
                    }
                });

                addQuestion(addQuestion, questionLayout, QUESTION_INDEX, CURRENT_SECTION);


                title.setText("");
                description.setText("");
                SECTION_INDEX++;
                view_section_pane.addView(sectionView);
                SECTIONS_VIEW.add(sectionView);
                scroll_section.fullScroll(View.FOCUS_DOWN);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assessment.setSections(sections);
                try {
                    updateAssessment(assessment);
                    Intent intent = new Intent(AddAssessmentLayout.this, ViewSingleLocalAssessment.class);
                    intent.putExtra("ASSESSMENT", assessment);
                    startActivity(intent);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addQuestion(Button addQuestion, final LinearLayout questionLayout, final int QUESTION_INDEX, final int CURRENT_SECTION){
        // Click addQuestion button
        int ANSWER_INDEX = 0;

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int THIS_QUESTION_INDEX = QUESTION_INDEX;
                List<String> questiontypes = new ArrayList<>();
                questiontypes.add("Single Answer");
                questiontypes.add("Multiple Choice");

                Toast.makeText(AddAssessmentLayout.this, "SECTION: "+CURRENT_SECTION, Toast.LENGTH_SHORT).show();
                final LayoutInflater inflater = (LayoutInflater) AddAssessmentLayout.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View questionView = inflater.inflate(R.layout.creator_question, null);

                final EditText questionTitle = (EditText) questionView.findViewById(R.id.txt_question_title);
                final EditText description = (EditText) questionView.findViewById(R.id.txt_question_description);
                final EditText answer_create = (EditText) questionView.findViewById(R.id.txt_answer_create);
                Spinner question_types = (Spinner) questionView.findViewById(R.id.question_type_spinner);
                Button answer_button = (Button) questionView.findViewById(R.id.btn_add_answer);
                final LinearLayout answerLayout = (LinearLayout) questionView.findViewById(R.id.answers_list);

                questionLayout.addView(questionView);
                final Question question = new Question(THIS_QUESTION_INDEX, "", 1, "", THIS_QUESTION_INDEX+1, SECTION_INDEX, new ArrayList<Answer>());
                sections.get(CURRENT_SECTION).getQuestions().add(question);

                // add answer to question
                answer_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int temp = sections.get(CURRENT_SECTION).getQuestions().get(THIS_QUESTION_INDEX).getAnswers().size();

                        final int ans_index = temp;


                        View answer_view = inflater.inflate(R.layout.view_answer_multiple, null);
                        TextView answer_creator_text = (TextView)answer_view.findViewById(R.id.txt_answer_creator_text);
                        final RadioButton rb_answer_correct = (RadioButton)answer_view.findViewById(R.id.rb_current_answer_correct);

                        TextView ans = new TextView(AddAssessmentLayout.this);
                        answer_creator_text.setText(answer_create.getText().toString());

                        rb_answer_correct.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final int iscorrect = sections.get(CURRENT_SECTION).getQuestions().get(THIS_QUESTION_INDEX).getAnswers().get(ans_index).getIsCorrect();

                                if(iscorrect==1&&rb_answer_correct.isChecked()) {
                                    sections.get(CURRENT_SECTION).getQuestions().get(THIS_QUESTION_INDEX).getAnswers().get(ans_index).setIsCorrect(0);
                                    rb_answer_correct.setChecked(false);

                                }else{
                                    sections.get(CURRENT_SECTION).getQuestions().get(THIS_QUESTION_INDEX).getAnswers().get(ans_index).setIsCorrect(1);
                                    rb_answer_correct.setChecked(true);

                                }
                            }
                        });

                        answerLayout.addView(answer_view);
                        sections.get(CURRENT_SECTION).getQuestions().get(THIS_QUESTION_INDEX).getAnswers().add(
                                new Answer(0, answer_create.getText().toString(), 0, 0, THIS_QUESTION_INDEX));
                        answer_create.setText("");

                    }
                });

                // Change Question title
                questionTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        sections.get(CURRENT_SECTION).getQuestions().get(THIS_QUESTION_INDEX).setQuestion(questionTitle.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                // Change question description
                description.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        sections.get(CURRENT_SECTION).getQuestions().get(THIS_QUESTION_INDEX).setDescription(description.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                // Spinner options
                ArrayAdapter<String> spinnerArray =  new ArrayAdapter<String>(AddAssessmentLayout.this, R.layout.support_simple_spinner_dropdown_item, questiontypes);
                question_types.setAdapter(spinnerArray);

                question_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sections.get(CURRENT_SECTION).getQuestions().get(THIS_QUESTION_INDEX).setQuestionType(position+1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });
    }


    public boolean updateAssessment(Assessment assessment) throws SQLException {

        for(Section section:assessment.getSections()){
            final Dao<Section, Integer> sectionDao = getHelper().getSectionDao();
            sectionDao.create(section);
            final int sectionId = section.getId();

            for(Question question:section.getQuestions()){
                final Dao<Question, Integer> questionDao = getHelper().getQuestionDao();
                question.setSectionId(sectionId);
                question.setNumber(sectionId);
                questionDao.create(question);

                final int questionId = question.getId();

                for (Answer answer:question.getAnswers()){
                    final Dao<Answer, Integer> answerDao = getHelper().getAnswerDao();
                    answer.setQuestionId(questionId);
                    answerDao.create(answer);
                }

            }
        }

        return false;
    }

    private Assessment getOldAssessmentById(Assessment assessment) throws SQLException {

        final Dao<Section, Integer> sectionDao = getHelper().getSectionDao();
        assessment.setSections(sectionDao.queryForEq("_assessmentid", assessment.getId()));

        for(int x=0;x<assessment.getSections().size();x++){
            final Dao<Question, Integer> questionDao = getHelper().getQuestionDao();
            assessment.getSections().get(x).setQuestions(questionDao.queryForEq("_number", assessment.getSections().get(x).getId()));

            for(int y=0;y<assessment.getSections().get(x).getQuestions().size();y++){
                final Dao<Answer, Integer> answerDao = getHelper().getAnswerDao();
                assessment.getSections().get(x).getQuestions().get(y).setAnswers(answerDao.queryForEq("_questionid", assessment.getSections().get(x).getQuestions().get(y).getId()));

            }
        }

        return assessment;
    }

    private void loadOldAssessments() {

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_new_assessment) {
            Toast.makeText(this, sections.toString(), Toast.LENGTH_SHORT).show();
            try {
                assessment.setSections(sections);
                updateAssessment(assessment);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
