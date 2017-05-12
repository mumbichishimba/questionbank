package com.mumbi.qbank;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mumbi.qbank.entity.Assessment;
import com.mumbi.qbank.entity.Section;
import com.mumbi.qbank.model.DatabaseHelper;

import java.sql.SQLException;

public class AddQuestionList extends AppCompatActivity {
    private TextView name, description;
    Button add;
    private DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question_list);

        name = (TextView) findViewById(R.id.txt_assessment_name);
        description = (TextView) findViewById(R.id.txt_assessment_description);
        add = (Button) findViewById(R.id.btn_add_assessment);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = AddQuestionList.this.name.getText().toString();
                String description = AddQuestionList.this.description.getText().toString();

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
                    AddQuestionList.this.name.setText("");
                    AddQuestionList.this.description.setText("");
                    Intent intent = new Intent(AddQuestionList.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } catch (SQLException e) {
                    Log.e(AddQuestionList.this.toString(), "Error", e);
                }

            }
        });

    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
