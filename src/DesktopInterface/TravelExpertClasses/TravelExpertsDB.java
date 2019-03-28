package DesktopInterface.TravelExpertClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TravelExpertsDB {

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
