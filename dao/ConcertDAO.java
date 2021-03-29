package dao;

import model.Concert;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Mara
 *
 * ConcertDAO : database access (for table Concert)
 *
 * Create, retrieve, delete, update object Concert in the database
 *
 * Get all the records from the database
 */

public class ConcertDAO extends AbstractDAO<Concert> {

    private Statement ConcertStatement;

    public ConcertDAO(Connection conn) {
        try {
            this.ConcertStatement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CREATE
    public void createNewConcert(Concert concert) {
        create(concert);
    }

    // RETRIEVE
    public ArrayList<Concert> getConcertsList() throws SQLException {
        ResultSet rs = ConcertStatement.executeQuery("SELECT * FROM untold.concert");

        ArrayList<Concert> ConcertList = new ArrayList<Concert>();
        while (rs.next()) {
            Concert newCon = new Concert(rs.getInt("idconcert"), rs.getString("artist"), rs.getString("genre"), rs.getString("title"), rs.getString("concertDate"), rs.getString("concertTime"), rs.getInt("tickets"));
            ConcertList.add(newCon);
        }
        return ConcertList;
    }

    public Concert searchConcert(String artist) throws SQLException {
        Concert myCon = null;

        ArrayList<Concert> ConcertList = getConcertsList();
        for (Concert current : ConcertList) {
            if(current.getArtist().equals(artist)) {
                myCon = current;
                break;
            }
        }
        return myCon;
    }

    public Concert searchConcertId(int id) throws SQLException {
        Concert myCon = null;

        ArrayList<Concert> ConcertList = getConcertsList();
        for (Concert current : ConcertList) {
            if(current.getId() == id) {
                myCon = current;
                break;
            }
        }
        return myCon;
    }

    // UPDATE
    public void updateConcert(Concert concert) {
        update(concert);
    }

    // DELETE
    public void deleteConcert(Concert concert) {
        delete(concert);
    }
}
