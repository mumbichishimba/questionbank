package com.mumbi.qbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mumbi.qbank.R;
import com.mumbi.qbank.entity.Assessment;

import java.util.List;

/**
 * Created by Mumbi Chishimba Jr on 15 March 2017.
 */

public class AssessmentAdapter extends ArrayAdapter<Assessment> {
    private List<Assessment> assessments;
    private final Context context;
    public AssessmentAdapter(Context context, List<Assessment> assessments) {
        super(context, R.layout.assessment, assessments);
        this.assessments = assessments;this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.assessment, parent, false);

        TextView assessnameName = (TextView) rowView.findViewById(R.id.txt_assessment_name);
        TextView assessnameDescription = (TextView) rowView.findViewById(R.id.assessmentDescription);

        Assessment currentAssessment = assessments.get(position);

        assessnameName.setText(currentAssessment.getName());
        assessnameDescription.setText(currentAssessment.getDescription());

        return rowView;
    }
}
