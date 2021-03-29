package dao;

import model.Ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Mara
 *
 * TicketDAO : database access (for table Ticket)
 *
 * Create, retrieve, delete, update object Ticket in the database
 *
 * Get all the records from the database
 */

public class TicketDAO extends AbstractDAO<Ticket> {

    private Statement TicketStatement;

    public TicketDAO(Connection conn) {
        try {
            this.TicketStatement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CREATE
    public void createTicket(Ticket ticket) {
        create(ticket);
    }

    // RETRIEVE
    public ArrayList<Ticket> getTicketsList() throws SQLException {
        ResultSet rs = TicketStatement.executeQuery("SELECT * FROM untold.ticket");

        ArrayList<Ticket> TicketList = new ArrayList<Ticket>();
        while (rs.next()) {
            Ticket newTic = new Ticket(rs.getInt("idticket"), rs.getString("name"), rs.getInt("places"), rs.getString("concert"));
            TicketList.add(newTic);
        }
        return TicketList;
    }

    public ArrayList<Ticket> searchTickets(String concert) throws SQLException {

        ArrayList<Ticket> myTic = new ArrayList<Ticket>();

        ArrayList<Ticket> TicketList = getTicketsList();
        for (Ticket current : TicketList) {
            if (current.getConcert().equals(concert)) {
                myTic.add(current);
            }
        }
        return myTic;
    }

    public Ticket searchTicketsId(int id) throws SQLException {

        Ticket myTic = null;

        ArrayList<Ticket> TicketList = getTicketsList();
        for (Ticket current : TicketList) {
            if (current.getId() == id) {
                myTic = current;
                break;
            }
        }
        return myTic;
    }

    // DELETE
    public void deleteTicket(Ticket ticket) {
        delete(ticket);
    }

    // UPDATE
    public void updateTicket(Ticket ticket) {
        update(ticket);
    }
}
