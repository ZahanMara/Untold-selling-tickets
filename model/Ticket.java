package model;

public class Ticket {

    private int idticket;
    private String name;
    private int places; // number of tickets
    private String concert;

    public Ticket(String name, int places, String concert) {
        this.name = name;
        this.places = places;
        this.concert = concert;
    }

    public Ticket(int id, String name, int places, String concert) {
        this.idticket = id;
        this.name = name;
        this.places = places;
        this.concert = concert;
    }


    public int getId() {
        return idticket;
    }

    public void setId(int id) {
        this.idticket = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public String getConcert() {
        return concert;
    }

    public void setConcert(String show) {
        this.concert = show;
    }
}
