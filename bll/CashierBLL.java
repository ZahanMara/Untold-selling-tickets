package bll;

import connection.Connector;
import dao.ConcertDAO;
import dao.TicketDAO;
import model.Concert;
import model.Ticket;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Zahan Mara
 *
 * CashierBLL class manages the operations made on tickets by cashier
 *
 */

public class CashierBLL {

    Connection con = Connector.getConnection();
    TicketDAO dao = new TicketDAO(con);

    /*** OPERATIONS ON TICKETS ***/

    public String createTicket(String name, int places, String artist) throws SQLException {
        String returning = "";

        ConcertDAO conDao = new ConcertDAO(con);
        Concert concert = conDao.searchConcert(artist);

        if(concert != null) {

            int ticketsAvailable = concert.getTickets() - places;
            if (ticketsAvailable > 0) {
                dao.createTicket(new Ticket(name, places, artist));
                conDao.updateConcert(new Concert(concert.getId(), concert.getArtist(), concert.getGenre(), concert.getTitle(), concert.getDate(), concert.getTime(), ticketsAvailable));
                returning = "Ticket created successfully!";
            } else if (ticketsAvailable == 0) {
                dao.createTicket(new Ticket(name, places, artist));
                conDao.updateConcert(new Concert(concert.getId(), concert.getArtist(), concert.getGenre(), concert.getTitle(), concert.getDate(), concert.getTime(), ticketsAvailable));
                returning = "SOLD OUT for " + concert.getArtist();
            } else returning = "There are no more " + places + " places available.\n Try a smaller number";
        }
        else returning = "There's no such concert";

        return returning;
    }

    public String deleteTicket(int id) throws SQLException {

        String toShow = "";

        Ticket toDelete = dao.searchTicketsId(id);

        if(toDelete != null) {

            // update the number of available tickets to that concert
            ConcertDAO conDao = new ConcertDAO(con);
            Concert concert = conDao.searchConcert(toDelete.getConcert());
            int ticketsAvailable = concert.getTickets() + toDelete.getPlaces();
            conDao.updateConcert(new Concert(concert.getId(), concert.getArtist(), concert.getGenre(), concert.getTitle(), concert.getDate(), concert.getTime(), ticketsAvailable));

            dao.deleteTicket(toDelete);

            toShow = "Ticket deleted successfully!";
        }
        else toShow = "No such ticket";

        return toShow;
    }

    public String updateTicket(Ticket ticket) throws SQLException {

        String returning = "";

        Ticket old = dao.searchTicketsId(ticket.getId());

        if(old != null) {

            int oldNrTickets = old.getPlaces();
            dao.updateTicket(ticket);

            ConcertDAO conDao = new ConcertDAO(con);
            Concert concert = conDao.searchConcert(ticket.getConcert());
            int ticketsAvailable = concert.getTickets() + oldNrTickets - ticket.getPlaces();
            conDao.updateConcert(new Concert(concert.getId(), concert.getArtist(), concert.getGenre(), concert.getTitle(), concert.getDate(), concert.getTime(), ticketsAvailable));

            returning = "Ticket updated successfully!";
        }
        else returning = "No such ticket";

        return returning;
    }

    public ArrayList<Ticket> retrieveTicket(String concert) throws SQLException {

        return dao.searchTickets(concert);
    }

    public Ticket retrieveTicketId(int id) throws SQLException {
        return dao.searchTicketsId(id);
    }

    public ArrayList<Ticket> getTicketsList() throws SQLException {
        return dao.getTicketsList();
    }
}
