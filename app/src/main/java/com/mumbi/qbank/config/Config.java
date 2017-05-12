package com.mumbi.qbank.config;

/**
 * Created by Mumbi Chishimba Jr on 15 March 2017.
 */

public class Config {
    private static final String PROTOCOL = "http://";
    private static final String PORT = "8084";
    private static final String ADDRESS = "169.254.1.233";
    private static final String ALL_ONLINE_ASSESSMENTS = "/questionbank/assessment/all";
    private static final String ONLINE_ASSESSMENT = "/questionbank/assessment/";

    public static final String getAllAssessmentsURL(){
        return PROTOCOL+ADDRESS+":"+PORT+ALL_ONLINE_ASSESSMENTS;
    }

    public static final String getAllAssessmentByIDFromURL(int id){
        return PROTOCOL+ADDRESS+":"+PORT+ONLINE_ASSESSMENT+id;
    }
}
