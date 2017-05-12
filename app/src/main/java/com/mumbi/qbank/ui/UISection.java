package com.mumbi.qbank.ui;

import android.content.Context;
import android.widget.LinearLayout;

import com.mumbi.qbank.entity.Section;

/**
 * Created by Mumbi Chishimba on 04 April 2017.
 */

public class UISection extends LinearLayout {
    private Context context;
    private Section section;
    public UISection(Context context, Section section) {
        super(context);
        this.context = context;
        this.section = section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Section getSection() {
        return section;
    }
}
