//Objective: Create a static class to connect to database stored in a web service (Azure)
package DesktopInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TravelExpertsDB {
    //Create class to connect to database
    public static Connection getConnection(){
        Connection conn = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://projectteamno7.database.windows.net:1433;" +
                    "database=travelexperts;user=MoS;password=Travel123;hostNameInCertificate=*.database.windows.net;");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
