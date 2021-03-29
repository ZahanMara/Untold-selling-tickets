package bll;

import connection.Connector;
import dao.UserDAO;
import model.Users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserBLL {


    public boolean checkCredentials(String username, char[] password) throws SQLException {

        boolean found = false;

        Connection con = Connector.getConnection();
        UserDAO dao = new UserDAO(con);

        ArrayList<Users> users = dao.getUsersList();
        for(Users u : users) {
            if(u.getUsername().equals(username)) {
                    if(isPasswordCorrect(password, u)) {
                        found = true;
                        break;
                    }
            }
        }
        return found;
    }

    private static boolean isPasswordCorrect(char[] input, Users us) {
        boolean isCorrect = true;
        char[] correctPassword = us.getPassword().toCharArray();

        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals (input, correctPassword);
        }

        return isCorrect;
    }

    public String guessWho(String username) throws SQLException {

        String type = null;

        Connection con = Connector.getConnection();
        UserDAO dao = new UserDAO(con);

        ArrayList<Users> users = dao.getUsersList();
        for(Users u : users) {
            if(u.getUsername().equals(username)) {
                type = u.getType();
                break;
            }
        }
        return type;
    }

}
