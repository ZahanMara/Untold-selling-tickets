package model;


public class Concert {

    private int idconcert;
    private String artist;
    private String genre;
    private String title;
    private String concertDate;
    private String concertTime;
    private int tickets; // max number of tickets for the concert

    public Concert(String artist, String genre, String title, String date, String time, int tickets) {
        this.artist = artist;
        this.genre = genre;
        this.title = title;
        this.concertDate = date;
        this.concertTime = time;
        this.tickets = tickets;
    }

    public Concert(int id, String artist, String genre, String title, String date, String time, int tickets) {
        this.idconcert = id;
        this.artist = artist;
        this.genre = genre;
        this.title = title;
        this.concertDate = date;
        this.concertTime = time;
        this.tickets = tickets;
    }

    public int getId() {
        return idconcert;
    }

    public void setId(int id) {
        this.idconcert = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return concertDate;
    }

    public void setDate(String date) {
        this.concertDate = date;
    }

    public String getTime() {
        return concertTime;
    }

    public void setTime(String time) {
        this.concertTime = time;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }
}
