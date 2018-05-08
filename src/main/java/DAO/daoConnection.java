package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Dost Muhammad on 5/4/2018.
 */
public class daoConnection {
    public static Connection getConnection(){
        String url = "jdbc:postgresql://localhost:5432/DBFacedetection";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","password,123");
        props.setProperty("ssl","false");
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(url,props);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
