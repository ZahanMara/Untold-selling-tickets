package bll;

import connection.Connector;
import dao.ConcertDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Concert;
import model.Ticket;
import model.Users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Zahan Mara
 *
 * AdminBLL class manages the operations made on cashiers and concerts by admin
 *
 */

public class AdminBLL {

        Connection con = Connector.getConnection();
        UserDAO dao = new UserDAO(con);
        ConcertDAO dao1 = new ConcertDAO(con);
        TicketDAO dao2 = new TicketDAO(con);


        /***  CRUD ON CASHIERS ***/


        public String createCashier(String username, String password) {
            dao.createCashier(new Users(username, password, "cashier"));
            return "Cashier created successfully!";
        }

        public String deleteCashier(int id) throws SQLException {
            String returning = "";

            Users toDelete = dao.searchCashier(id);
            if(toDelete != null) {
                dao.deleteCashier(toDelete);
                returning = "Cashier deleted successfully!";
            }
            else returning = "There's no cashier with this ID";

            return returning;
        }

        public String updateCashier(Users cashier) throws SQLException {
            String returning = "";

            Users c = dao.searchCashier(cashier.getId());
            if(c != null) {
                dao.updateCashier(cashier);
                returning = "Cashier updated successfully!";
            }
            else returning = "There's no cashier with this ID";

            return returning;

        }

        public Users retrieveCashier(int id) throws SQLException {
            return dao.searchCashier(id);
       }

        public ArrayList<Users> getCashiersList() throws SQLException {
            return dao.getCashiersList();
        }



       /*** CRUD ON CONCERTS ***/



       public String createConcert(String artist, String genre, String title, String date, String time, int tickets) {

           dao1.createNewConcert(new Concert(artist, genre, title, date, time, tickets));
           return "Concert created successfully!";
       }

       public String deleteConcert(int id) throws SQLException {
           String returning = "";

           Concert toDelete = dao1.searchConcertId(id);

           if(toDelete != null) {

               // delete all the tickets to that concert
               ArrayList<Ticket> tickets = dao2.searchTickets(toDelete.getArtist());
               if (!tickets.isEmpty()) {
                   for (Ticket t : tickets)
                       dao2.deleteTicket(t);
               }

               dao1.deleteConcert(toDelete);
               returning = "Concert deleted successfully!";
           }
           else returning = "There's no such concert";

           return returning;
       }

       public String updateConcert(Concert concert) throws SQLException {
           String returning = "";

           Concert con = dao1.searchConcertId(concert.getId());
           if(con != null) {
               dao1.updateConcert(concert);
               returning = "Concert updated successfully!";
           }
           else returning = "There is no concert with this ID";

           return returning;
       }

       public Concert retrieveConcert(int id) throws SQLException {

           return dao1.searchConcertId(id);
       }

       public ArrayList<Concert> getConcertsList() throws SQLException {
           return dao1.getConcertsList();
       }

    public String printTickets(String artist) throws SQLException {
           String returning = "";

        ArrayList<Ticket> tickets = dao2.searchTickets(artist);

        if(!tickets.isEmpty()) {
            returning = "All the tickets sold for " + artist + " were printed in a .csv file";
            String name = "tickets_" + artist + ".csv";

            try (PrintWriter writer = new PrintWriter(new File(name))) {
                StringBuilder sb = new StringBuilder();
                sb.append("idticket");
                sb.append(',');
                sb.append("Name");
                sb.append(',');
                sb.append("Places");
                sb.append('\n');

                for (Ticket t : tickets) {
                    sb.append(t.getId());
                    sb.append(',');
                    sb.append(t.getName());
                    sb.append(',');
                    sb.append(t.getPlaces());
                    sb.append('\n');
                }

                writer.write(sb.toString());

                System.out.println("Done!");

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        else returning = "There are no tickets sold for this concert";

        return returning;
    }
}

