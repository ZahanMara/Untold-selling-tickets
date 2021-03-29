package dao;

import model.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Mara
 *
 * UserDAO : database access (for table Users)
 *
 * Create, retrieve, delete, update object Users in the database
 *
 * Get all the records from the database
 */

public class UserDAO extends AbstractDAO<Users> {

    private Statement UserStatement;

    public UserDAO(Connection conn) {
        try {
            this.UserStatement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CREATE
    public void createCashier(Users user) {
        if(user.getType().equals("cashier"))
            create(user);
        else System.out.println("You can't create an admin!");
    }

    // RETRIEVE
    public ArrayList<Users> getCashiersList() throws SQLException {
        ResultSet rs = UserStatement.executeQuery("SELECT * FROM untold.users where type='cashier'");

        ArrayList<Users> CashierList = new ArrayList<Users>();
        while (rs.next()) {
            Users newCon = new Users(rs.getInt("iduser"), rs.getString("username"), rs.getString("password"), rs.getString("type"));
            CashierList.add(newCon);
        }
        return CashierList;
    }

    public Users searchCashier(int id) throws SQLException {
        Users myUser = null;

        ArrayList<Users> CashierList = getCashiersList();
        for (Users current : CashierList) {
            if(current.getId() == id) {
                myUser = current;
                break;
            }
        }
        return myUser;
    }

    // DELETE
    public void deleteCashier(Users user) {
        if(user.getType().equals("cashier"))
            delete(user);
        else System.out.println("You can't delete an admin!");
    }

    // UPDATE
    public void updateCashier(Users user) {
        if(user.getType().equals("cashier"))
            update(user);
        else System.out.println("You can't edit an admin!");
    }



    public ArrayList<Users> getUsersList() throws SQLException {
        ResultSet rs = UserStatement.executeQuery("SELECT * FROM untold.users");

        ArrayList<Users> UsersList = new ArrayList<Users>();
        while (rs.next()) {
            Users newCon = new Users(rs.getInt("iduser"), rs.getString("username"), rs.getString("password"), rs.getString("type"));
            UsersList.add(newCon);
        }
        return UsersList;
    }
}
