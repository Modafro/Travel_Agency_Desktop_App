//Objective: Create various queries to manipulate the database based on end-user requests
package DesktopInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelExpertsQuery {

    //create method to verify customer login
    public static boolean isLoginVerified(Agents agt)
    {
        boolean verified =false;
        int count=0;
        //prepare connection
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "SELECT count(*) from Agents where AgtUserName=? AND AgtPassword=?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, agt.getAgtUserName());
            stmt.setString(2,agt.getAgtPassword());
            ResultSet res = stmt.executeQuery();
            while (res.next()){
                count = res.getInt(1);
            }

            if(count != 0){
                verified = true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return verified;
    }
}