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

    //method to get list of customers for a given agent targeted for Customer GUI
    public static ObservableList<Customer> getCustomerTableViewByAgtId(Agents agt, ObservableList<Customer> custData)
    {
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "select CustomerId, CustFirstName, CustLastName, CustAddress, CustCity, " +
                "CustProv, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail from Customers where " +
                "AgentId=?";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, agt.getAgentId());

            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                custData.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return custData;
    }

    //method to get list of customers with specific columns for a given agent targeted for Bookings GUI
    public static ObservableList<Customer> getSpecificCustomerTableViewByAgtId(Agents agt, ObservableList<Customer> custData)
    {
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "select CustomerId, CustFirstName, CustLastName, CustHomePhone, CustAddress, CustEmail from Customers where AgentId=?";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, agt.getAgentId());

            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                custData.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return custData;
    }

    //method to add new customer for a given agt
    public static boolean addCustomerForAgtId(Agents agt, Customer c)
    {
        boolean isCustAdded = false;
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "INSERT INTO Customers (CustFirstName, CustLastName, CustAddress, CustCity, " +
                "CustProv, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail, AgentId) Values (?,?,?,?,?,?,?,?,?,?,?)";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getCustFirstName());
            stmt.setString(2, c.getCustLastName());
            stmt.setString(3, c.getCustAddress());
            stmt.setString(4, c.getCustCity());
            stmt.setString(5, c.getCustProv());
            stmt.setString(6, c.getCustPostal());
            stmt.setString(7, c.getCustCountry());
            stmt.setString(8, c.getCustHomePhone());
            stmt.setString(9, c.getCustBusPhone());
            stmt.setString(10, c.getCustEmail());
            stmt.setInt(11, agt.getAgentId());

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0 )
            {
                isCustAdded = true;
            }
        }
        catch (SQLException e)
        {
        e.printStackTrace();
        }

        return isCustAdded;
    }

    //method to verify if customer already exists
    public static boolean customerExist(Customer c)
    {
        boolean custExist = true;
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "select CustFirstName, CustLastName, CustAddress, CustCity, " +
                "CustProv, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail from Customers where " +
                "CustEmail=?";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getCustEmail());
            //stmt.setString(2, c.getCustHomePhone());

            ResultSet rs = stmt.executeQuery();

            //check if there are any result
            if(!rs.isBeforeFirst())
            {
                custExist = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return custExist;
    }

    //method to search values in database (all columns) targeted for the Customer GUI
    public static ObservableList<Customer> CustomerSearchResult(ObservableList<Customer> custData, String userInput, Agents agt)
    {
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "select CustomerId, CustFirstName, CustLastName, CustAddress, CustCity, " +
                "CustProv, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail from Customers where " +
                "lower(CONCAT(CustFirstName, CustLastName, CustAddress, CustCity,CustProv, CustPostal, CustCountry," +
                " CustHomePhone, CustBusPhone, CustEmail)) like ? AND AgentId=?";

        try
        {
            PreparedStatement stmt = null;
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%"+userInput.toLowerCase()+"%");
                stmt.setInt(2, agt.getAgentId());

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    custData.add(new Customer(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getString(11)
                    ));
                }
            }
            catch(SQLException e)
            {
                    e.printStackTrace();
            }
        return custData;
    }

    //method to search values in database (specific columns) targeted for the Bookings GUI
    public static ObservableList<Customer> CustomerSearchResultSpecific(ObservableList<Customer> custData, String userInput, Agents agt)
    {
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "select CustomerId, CustFirstName, CustLastName, CustHomePhone, CustAddress, CustEmail from Customers where " +
                    "lower(CONCAT(CustFirstName, CustLastName)) like ? AND AgentId=?";

        try
        {
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%"+userInput.toLowerCase()+"%");
            stmt.setInt(2, agt.getAgentId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                custData.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                ));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return custData;
    }

    //method to update existing information of customer
    public static boolean updateCustomer(Customer cust)
    {
        boolean isCustUpdated=false;
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "update Customers SET CustFirstName=?, CustLastName=?, CustAddress=?, CustCity=?, " +
                "CustProv=?, CustPostal=?, CustHomePhone=?, CustBusPhone=?, CustEmail=? where CustomerId=?";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cust.getCustFirstName());
            stmt.setString(2, cust.getCustLastName());
            stmt.setString(3, cust.getCustAddress());
            stmt.setString(4, cust.getCustCity());
            stmt.setString(5, cust.getCustProv());
            stmt.setString(6, cust.getCustPostal());
            stmt.setString(7, cust.getCustHomePhone());
            stmt.setString(8, cust.getCustBusPhone());
            stmt.setString(9, cust.getCustEmail());
            stmt.setInt(10, cust.getCustomerId());

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0 )
            {
                isCustUpdated = true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return isCustUpdated;
    }

    //method to delete existing customer
    public static boolean deleteCustomer(Customer cust)
    {
        boolean isCustDeleted=false;
        Connection conn = TravelExpertsDB.getConnection();

        String sql="Delete from Customers where CustomerId=?";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cust.getCustomerId());

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0 )
            {
                isCustDeleted = true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return isCustDeleted;
    }
}
