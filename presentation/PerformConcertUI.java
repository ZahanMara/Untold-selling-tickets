package presentation;

import bll.AdminBLL;
import model.Concert;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PerformConcertUI extends JFrame {

    public PerformConcertUI() throws SQLException {

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 1200, 550);
        this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(Color.lightGray);

        JLabel title = new JLabel("CONCERTS");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setBounds(20, 0, 300, 77);
        getContentPane().add(title);

        String[] collumns = { "ID", "Artist", "Genre", "Title", "Date", "Time", "Tickets"};

        DefaultTableModel myModel = new DefaultTableModel();
        myModel.setColumnIdentifiers(collumns);

        AdminBLL bll = new AdminBLL();
        ArrayList<Concert> concerts = bll.getConcertsList();

        Object[] obj = new Object[7];

        for(Concert c : concerts) {
            obj[0] = c.getId();
            obj[1] = c.getArtist();
            obj[2] = c.getGenre();
            obj[3] = c.getTitle();
            obj[4] = c.getDate();
            obj[5] = c.getTime();
            obj[6] = c.getTickets();

            myModel.addRow(obj);
        }

        JTable myTable = new JTable(myModel);
        JScrollPane myScrollPane = new JScrollPane();
        myScrollPane.setBounds(20, 80, 600, 400);
        myScrollPane.setViewportView(myTable);
        myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(myScrollPane);

        JLabel artist = new JLabel("Artist");
        JLabel genre = new JLabel("Genre");
        JLabel concertTitle = new JLabel("Title");
        JLabel date = new JLabel("Date");
        JLabel time = new JLabel("Time");
        JLabel tickets = new JLabel("Tickets");

        artist.setFont(new Font("Arial", Font.PLAIN, 15));
        genre.setFont(new Font("Arial", Font.PLAIN, 15));
        concertTitle.setFont(new Font("Arial", Font.PLAIN, 15));
        date.setFont(new Font("Arial", Font.PLAIN, 15));
        time.setFont(new Font("Arial", Font.PLAIN, 15));
        tickets.setFont(new Font("Arial", Font.PLAIN, 15));

        artist.setBounds(700, 80, 40, 30);
        genre.setBounds(850, 80, 40, 30);
        concertTitle.setBounds(1000, 80, 40, 30);
        date.setBounds(700, 150, 40, 30);
        time.setBounds(850, 150, 40, 30);
        tickets.setBounds(1000, 150, 50, 30);

        getContentPane().add(artist);
        getContentPane().add(genre);
        getContentPane().add(concertTitle);
        getContentPane().add(date);
        getContentPane().add(time);
        getContentPane().add(tickets);

        JTextField artistTf = new JTextField();
        JTextField genreTf = new JTextField();
        JTextField titleTf = new JTextField();
        JTextField dateTf = new JTextField();
        JTextField timeTf = new JTextField();
        JTextField ticketsTf = new JTextField();

        artistTf.setBounds(700, 108, 130, 30);
        genreTf.setBounds(850, 108, 130, 30);
        titleTf.setBounds(1000, 108, 130, 30);
        dateTf.setBounds(700, 178, 130, 30);
        timeTf.setBounds(850, 178, 130, 30);
        ticketsTf.setBounds(1000, 178, 130, 30);

        getContentPane().add(artistTf);
        getContentPane().add(genreTf);
        getContentPane().add(titleTf);
        getContentPane().add(dateTf);
        getContentPane().add(timeTf);
        getContentPane().add(ticketsTf);


        //************* CREATE

        JButton createB = new JButton("Create");
        createB.setBounds(830, 230, 80, 30);
        getContentPane().add(createB);

        createB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String artist = artistTf.getText();
                String genre = genreTf.getText();
                String title = titleTf.getText();
                String date = dateTf.getText();
                String time = timeTf.getText();
                String tickets_str = ticketsTf.getText();

                if(checkFormat(date)) {
                    if(isStringInt(tickets_str)) {
                        int tick = Integer.parseInt(tickets_str);
                        if(tick < 1) {
                            JOptionPane.showMessageDialog(new JFrame(), "Tickets must be a positive number!", "", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            try {
                                String toShow = bll.createConcert(artist, genre, title, date, time, tick);
                                updateTable(bll);
                                JOptionPane.showMessageDialog(new JFrame(), toShow, "", JOptionPane.INFORMATION_MESSAGE);

                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                    else JOptionPane.showMessageDialog(new JFrame(), "Tickets must be a number!", "", JOptionPane.ERROR_MESSAGE);
                }
                else JOptionPane.showMessageDialog(new JFrame(), "Date format is incorrect!\n Please try yyyy-mm-dd", "", JOptionPane.ERROR_MESSAGE);

            }
        });


        //************* UPDATE

        JButton retrieveB = new JButton("Retrieve");
        retrieveB.setBounds(700, 360, 130, 30);
        JLabel retrieveLb = new JLabel("ID ticket");
        retrieveLb.setBounds(850, 330, 100, 30);
        JTextField retrieveTf = new JTextField();
        retrieveTf.setBounds(850, 360, 130, 30);
        getContentPane().add(retrieveB);
        getContentPane().add(retrieveLb);
        getContentPane().add(retrieveTf);

        retrieveB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_str = retrieveTf.getText();

                if(isStringInt(id_str)) {
                    int id = Integer.parseInt(id_str);
                    try {
                        Concert con = bll.retrieveConcert(id);
                        if(con != null) {
                            artistTf.setText(con.getArtist());
                            genreTf.setText(con.getGenre());
                            titleTf.setText(con.getTitle());
                            dateTf.setText(con.getDate());
                            timeTf.setText(con.getTime());
                            ticketsTf.setText(Integer.toString(con.getTickets()));
                        }
                        else JOptionPane.showMessageDialog(new JFrame(), "There's no concert with this ID", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Please introduce a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        JButton updateB = new JButton("Update");
        updateB.setBounds(920, 230, 80, 30);
        getContentPane().add(updateB);

        updateB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_str = retrieveTf.getText();

                if(isStringInt(id_str)) {
                    int id = Integer.parseInt(id_str);
                    String artist = artistTf.getText();
                    String genre = genreTf.getText();
                    String title = titleTf.getText();
                    String date = dateTf.getText();
                    String time = timeTf.getText();
                    String tickets_str = ticketsTf.getText();

                    if(isStringInt(tickets_str)) {

                        int tick = Integer.parseInt(tickets_str);

                        try {
                            String toShow = bll.updateConcert(new Concert(id, artist, genre, title, date, time, tick));
                            updateTable(bll);
                            JOptionPane.showMessageDialog(new JFrame(), toShow, "", JOptionPane.INFORMATION_MESSAGE);

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                    else JOptionPane.showMessageDialog(new JFrame(), "Tickets must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else JOptionPane.showMessageDialog(new JFrame(), "Please introduce a ticket ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        //************* DELETE

        JButton deleteB = new JButton("Delete");
        deleteB.setBounds(700, 300, 130, 30);
        JLabel deleteLb = new JLabel("ID Ticket");
        deleteLb.setBounds(850, 270, 100, 30);
        JTextField deleteTf = new JTextField();
        deleteTf.setBounds(850, 300, 130, 30);
        getContentPane().add(deleteB);
        getContentPane().add(deleteLb);
        getContentPane().add(deleteTf);

        deleteB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_str = deleteTf.getText();
                if(isStringInt(id_str)) {
                    int id = Integer.parseInt(id_str);

                    try {
                        String toShow = bll.deleteConcert(id);
                        updateTable(bll);
                        JOptionPane.showMessageDialog(new JFrame(), toShow, "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Please introduce a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        //******** BACK

        JButton backB = new JButton("Back");
        backB.setBounds(1000, 455, 130, 30);
        getContentPane().add(backB);

        backB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                AdminUI adm = new AdminUI();
                adm.setVisible(true);
            }
        });


        //****** PRINT ALL TICKETS FOR A SHOW

        JButton printB = new JButton("Print tickets");
        printB.setBounds(700, 420, 130, 30);
        JLabel printLb = new JLabel("Concert");
        printLb.setBounds(850, 390, 100, 30);
        JTextField printTf = new JTextField();
        printTf.setBounds(850, 420, 130, 30);

        getContentPane().add(printB);
        getContentPane().add(printLb);
        getContentPane().add(printTf);

        printB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String artist = printTf.getText();
                if(artist.isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Please introduce a concert in order to print the tickets", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        String toShow = bll.printTickets(artist);
                        JOptionPane.showMessageDialog(new JFrame(), toShow, "", JOptionPane.INFORMATION_MESSAGE);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }



    private void updateTable(AdminBLL bll) throws SQLException {
        String[] collumns = { "ID", "Artist", "Genre", "Title", "Date", "Time", "Tickets"};

        DefaultTableModel myModel = new DefaultTableModel();
        myModel.setColumnIdentifiers(collumns);

        ArrayList<Concert> concerts = bll.getConcertsList();

        Object[] obj = new Object[7];

        for(Concert c : concerts) {
            obj[0] = c.getId();
            obj[1] = c.getArtist();
            obj[2] = c.getGenre();
            obj[3] = c.getTitle();
            obj[4] = c.getDate();
            obj[5] = c.getTime();
            obj[6] = c.getTickets();

            myModel.addRow(obj);
        }

        JTable myTable = new JTable(myModel);
        JScrollPane myScrollPane = new JScrollPane();
        myScrollPane.setBounds(20, 80, 600, 400);
        myScrollPane.setViewportView(myTable);
        myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(myScrollPane);
    }

    private boolean checkFormat(String input) {
        boolean check = false;

        if (input.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
            check = true;
        return check;
    }

    private boolean isStringInt(String s)
    { try {
        Integer.parseInt(s);
        return true;
    } catch (NumberFormatException ex)
    { return false; }
    }
}
