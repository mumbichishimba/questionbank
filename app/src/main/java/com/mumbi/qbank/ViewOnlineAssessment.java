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
import com.mumbi.qbank.adapter.AssessmentAdapter;
import com.mumbi.qbank.config.Config;
import com.mumbi.qbank.entity.Assessment;
import com.mumbi.qbank.model.AssessmentModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ViewOnlineAssessment extends AppCompatActivity {
    private ListView onlineAssessments;
    List<Assessment> assessments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_online_assessment);
        onlineAssessments = (ListView) findViewById(R.id.assessmentsListOnline);

        onlineAssessments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Assessment", assessments.get(position).toString());
                Intent intent = new Intent(ViewOnlineAssessment.this, ViewSingleOnineAssesment.class);
                intent.putExtra("ASSESSMENT", assessments.get(position));
                startActivity(intent);
            }
        });

        sendRequest();
    }

    private void sendRequest(){
        System.out.print("SendRequest");

        final ProgressDialog progressDialog = new ProgressDialog(ViewOnlineAssessment.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading assessments from web server...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Config.getAllAssessmentsURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    assessments = AssessmentModel.jsonAssessmentArrayToAssessmentList(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(Assessment a:assessments) {
                    Log.d("Assessment", a.toString());
                }
                AssessmentAdapter assessmentAdapter = new AssessmentAdapter(ViewOnlineAssessment.this, assessments);
                onlineAssessments.setAdapter(assessmentAdapter);
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
}
