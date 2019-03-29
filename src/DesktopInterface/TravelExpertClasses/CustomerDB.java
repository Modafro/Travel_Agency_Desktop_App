//Objective: class to create methods that fetch various queries in travelexperts database
package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDB {
//    public static ObservableList<Customer> getCustometTableView(Agents agt, ObservableList<Customer> custData)
//    {
//        custData = FXCollections.observableArrayList();
//        Connection conn = TravelExpertsDB.getConnection();
//
//        String sql = "select CustFirstName, CustLastName, CustAddress, CustCity, " +
//                "CustProv, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail from Customers where " +
//                "AgentId=?";
//
//        try
//        {
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, agt.getAgentId());
//
//            ResultSet res = stmt.executeQuery();
//
//            while(res.next())
//            {
//                custData.add(new Customer(res.getString(1),
//                        res.getString(2),
//                        res.getString(3),
//                        res.getString(4),
//                        res.getString(5),
//                        res.getString(6),
//                        res.getString(7),
//                        res.getString(8),
//                        res.getString(9),
//                        res.getString(10)
//                        ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return custData;
//    }
}
