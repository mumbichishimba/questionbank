package com.mumbi.qbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mumbi.qbank.adapter.AssessmentAdapter;
import com.mumbi.qbank.config.Config;
import com.mumbi.qbank.entity.Assessment;
import com.mumbi.qbank.model.AssessmentModel;
import com.mumbi.qbank.model.DatabaseHelper;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewLocalAssessmentList extends AppCompatActivity {
    private ListView localList;
    private DatabaseHelper databaseHelper = null;

    List<Assessment> assessments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_local_assessment_list);
        localList = (ListView) findViewById(R.id.local_list);
        localList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Assessment", assessments.get(position).toString());
                Intent intent = new Intent(ViewLocalAssessmentList.this, ViewSingleLocalAssessment.class);
                intent.putExtra("ASSESSMENT", assessments.get(position));
                startActivity(intent);
            }
        });

        loadAssessments();
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

    private void loadAssessments(){
        final ProgressDialog progressDialog = new ProgressDialog(ViewLocalAssessmentList.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading assessments from web server...");
        progressDialog.show();

        try {
            final Dao<Assessment, Integer> assessmentDao = getHelper().getAssessmentDao();

            assessments = assessmentDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AssessmentAdapter assessmentAdapter = new AssessmentAdapter(ViewLocalAssessmentList.this, assessments);
        localList.setAdapter(assessmentAdapter);
        progressDialog.dismiss();
    }
}
