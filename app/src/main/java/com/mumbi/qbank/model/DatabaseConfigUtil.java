package com.mumbi.qbank.model;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Mumbi Chishimba Jr on 13 March 2017.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    public static void main(String[] args) throws SQLException, IOException{
        writeConfigFile("ormlite_config.txt");
    }
}
