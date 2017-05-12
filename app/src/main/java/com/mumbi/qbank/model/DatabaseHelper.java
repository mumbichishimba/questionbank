package com.mumbi.qbank.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mumbi.qbank.R;
import com.mumbi.qbank.entity.Answer;
import com.mumbi.qbank.entity.Assessment;
import com.mumbi.qbank.entity.Educator;
import com.mumbi.qbank.entity.Question;
import com.mumbi.qbank.entity.Section;
import com.mumbi.qbank.entity.Student;
import com.mumbi.qbank.entity.User;

import java.sql.SQLException;

/**
 * Created by Mumbi Chishimba Jr on 14 March 2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "qbank.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Answer, Integer> answerDao;
    private Dao<Assessment, Integer> assessmentDao;
    private Dao<Educator, Integer> educatorDao;
    private Dao<Question, Integer> questionDao;
    private Dao<Section, Integer> sectionDao;
    private Dao<Student, Integer> studentDao;
    private Dao<User, Integer> userDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, Assessment.class);
            TableUtils.createTable(connectionSource, Answer.class);
            TableUtils.createTable(connectionSource, Section.class);
            TableUtils.createTable(connectionSource, Question.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Educator.class);
            TableUtils.createTable(connectionSource, Student.class);

        }catch (Exception e){
            Log.e(DatabaseHelper.class.getName(), "Unable to create database",e);
            System.out.print(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, Answer.class, true);
            TableUtils.dropTable(connectionSource, Assessment.class, true);
            TableUtils.dropTable(connectionSource, Educator.class, true);
            TableUtils.dropTable(connectionSource, Question.class, true);
            TableUtils.dropTable(connectionSource, Section.class, true);
            TableUtils.dropTable(connectionSource, Student.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(sqliteDatabase, connectionSource);

        }  catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    public Dao<Answer, Integer> getAnswerDao() throws SQLException{
        if(answerDao==null){
            answerDao = getDao(Answer.class);
        }
        return answerDao;
    }

    public Dao<Assessment, Integer> getAssessmentDao() throws SQLException {
        if(assessmentDao==null){
            assessmentDao = getDao(Assessment.class);
        }
        return assessmentDao;
    }

    public Dao<Educator, Integer> getEducatorDao() throws SQLException {
        if(educatorDao==null){
            educatorDao = getDao(Educator.class);
        }
        return educatorDao;
    }

    public Dao<Question, Integer> getQuestionDao() throws SQLException {
        if(questionDao==null){
            questionDao = getDao(Question.class);
        }
        return questionDao;
    }

    public Dao<Section, Integer> getSectionDao() throws SQLException {
        if(sectionDao==null){
            sectionDao = getDao(Section.class);
        }
        return sectionDao;
    }

    public Dao<Student, Integer> getStudentDao() throws SQLException {
        if(studentDao==null){
            studentDao = getDao(Student.class);
        }
        return studentDao;
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if(userDao==null){
            userDao = getDao(User.class);
        }
        return userDao;
    }
}