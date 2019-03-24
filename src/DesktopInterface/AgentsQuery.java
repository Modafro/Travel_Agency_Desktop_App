//Objective: Create various queries to manipulate the database based on end-user requests
package DesktopInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgentsQuery {

    //create method to verify customer login
    public static boolean isLoginVerified(Agents agt)
    {
        boolean verified =false;
        int count=0;
        //prepare connection
        Connection conn = TravelExpertsDB.getConnection();

        String sql_verify = "SELECT count(*) from Agents where AgtUserName= ? AND AgtPassword=HASHBYTES('SHA1',convert(varchar,?))";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql_verify);
            stmt.setString(1, agt.getAgtUserName());
            stmt.setString(2,agt.getAgtPassword());
            ResultSet res = stmt.executeQuery();
            while (res.next()){
                count = res.getInt(1);
            }

            if(count != 0)
            {
                verified = true;

                //fetch remaining agent information
                String sql_getInfo = "SELECT AgentId, AgtFirstName, AgtMiddleInitial, AgtLastName, AgtBusPhone, AgtEmail, AgtPosition, AgencyId " +
                        "from Agents where AgtUserName = ?";
                try
                {
                    PreparedStatement stmt2 = conn.prepareStatement(sql_getInfo);
                    stmt2.setString(1,agt.getAgtUserName());
                    ResultSet res2 = stmt2.executeQuery();
                    while (res2.next())
                    {
                        //Agents agt = new Agents();
                        agt.setAgentId(res2.getInt(1));
                        agt.setAgtFirstName(res2.getString(2));
                        agt.setAgtMiddleInitial(res2.getString(3));
                        agt.setAgtLastName(res2.getString(4));
                        agt.setAgtBusPhone(res2.getString(5));
                        agt.setAgtEmail(res2.getString(6));
                        agt.setAgtPosition(res2.getString(7));
                        agt.setAgencyId(res2.getInt(8));
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
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