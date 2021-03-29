package presentation;

import bll.CashierBLL;
import model.Ticket;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;


public class CashierUI extends JFrame{

    public CashierUI() throws SQLException {

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 550);
        this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(Color.lightGray);

        JLabel title = new JLabel("CASHIER");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setBounds(400, 0, 300, 77);
        getContentPane().add(title);


        String[] collumns = { "ID", "Name", "Places", "Concert"};

        DefaultTableModel myModel = new DefaultTableModel();
        myModel.setColumnIdentifiers(collumns);



        CashierBLL bll = new CashierBLL();
        ArrayList<Ticket> tickets = bll.getTicketsList();

        Object[] obj = new Object[4];

        for (Ticket t : tickets) {
            obj[0] = t.getId();
            obj[1] = t.getName();
            obj[2] = t.getPlaces();
            obj[3] = t.getConcert();

            myModel.addRow(obj);
        }

        JTable myTable = new JTable(myModel);
        JScrollPane myScrollPane = new JScrollPane();
        myScrollPane.setBounds(20, 80, 400, 400);
        myScrollPane.setViewportView(myTable);
        myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(myScrollPane);



        JLabel name = new JLabel("Name");
        JLabel places = new JLabel("Places");
        JLabel concert = new JLabel("Concert");

        name.setFont(new Font("Arial", Font.PLAIN, 15));
        places.setFont(new Font("Arial", Font.PLAIN, 15));
        concert.setFont(new Font("Arial", Font.PLAIN, 15));

        name.setBounds(480, 80, 40, 30);
        places.setBounds(620, 80, 50, 30);
        concert.setBounds(760, 80, 80, 30);
        getContentPane().add(name);
        getContentPane().add(places);
        getContentPane().add(concert);


        JTextField nameTf = new JTextField();
        JTextField placesTf = new JTextField();
        JTextField concertTf = new JTextField();
        nameTf.setBounds(480, 108, 100, 30);
        placesTf.setBounds(620, 108, 100, 30);
        concertTf.setBounds(760, 108, 100, 30);
        getContentPane().add(nameTf);
        getContentPane().add(placesTf);
        getContentPane().add(concertTf);



        // *************** CREATE

        JButton createB = new JButton("Create");
        createB.setBounds(580, 155, 80, 30);
        getContentPane().add(createB);

        createB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTf.getText();
                String places = placesTf.getText();
                String concert = concertTf.getText();

                if(isStringInt(places)) {
                    int nr_places = Integer.parseInt(places);

                    if(nr_places < 1)
                        JOptionPane.showMessageDialog(new JFrame(), "Please enter a number of places greater or equal to 1", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        try {
                            String toShow = bll.createTicket(name, nr_places, concert);
                            updateTable(bll);
                            JOptionPane.showMessageDialog(new JFrame(), toShow, "", JOptionPane.INFORMATION_MESSAGE);

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Places must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });




        // ******* DELETE

        JButton deleteB = new JButton("Delete");
        deleteB.setBounds(480, 240, 130, 30);
        JLabel deleteLb = new JLabel("ID Ticket");
        deleteLb.setBounds(620, 210, 100, 30);
        JTextField deleteTf = new JTextField();
        deleteTf.setBounds(620, 240, 100, 30);
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
                        String toShow = bll.deleteTicket(id);
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




        //********** SHOW TICKETS FOR A CERTAIN CONCERT

        JButton showB = new JButton("Show tickets for:");
        showB.setBounds(480, 300, 130, 30);
        JLabel showLb = new JLabel("Artist");
        showLb.setBounds(620, 270, 100, 30);
        JTextField showTf = new JTextField();
        showTf.setBounds(620, 300, 100, 30);
        getContentPane().add(showB);
        getContentPane().add(showLb);
        getContentPane().add(showTf);

        showB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String artist = showTf.getText();

                try {
                    ArrayList<Ticket> tickets = bll.retrieveTicket(artist);
                    showTickets(bll, artist);
                    if(tickets.isEmpty())
                        JOptionPane.showMessageDialog(new JFrame(), "No such tickets", "", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });


        //******** SHOW ALL

        JButton showAllB = new JButton("Show all tickets");
        showAllB.setBounds(480, 350, 240, 30);
        getContentPane().add(showAllB);

        showAllB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateTable(bll);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });



        //********* UPDATE

        JButton retrieveB = new JButton("Retrieve");
        retrieveB.setBounds(480, 410, 130, 30);
        JLabel retrieveLb = new JLabel("ID ticket");
        retrieveLb.setBounds(620, 380, 100, 30);
        JTextField retrieveTf = new JTextField();
        retrieveTf.setBounds(620, 410, 100, 30);
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
                        Ticket tic = bll.retrieveTicketId(id);
                        if(tic != null) {
                            nameTf.setText(tic.getName());
                            placesTf.setText(Integer.toString(tic.getPlaces()));
                            concertTf.setText(tic.getConcert());
                        }
                        else JOptionPane.showMessageDialog(new JFrame(), "There is no such ticket", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Please introduce a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton updateB = new JButton("Update");
        updateB.setBounds(680, 155, 80, 30);
        getContentPane().add(updateB);

        updateB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_str = retrieveTf.getText();

                if(isStringInt(id_str)) {
                    int id = Integer.parseInt(id_str);
                    String name = nameTf.getText();
                    String places_str = placesTf.getText();
                    int places = Integer.parseInt(places_str);
                    String concert = concertTf.getText();

                    try {
                        String toShow = bll.updateTicket(new Ticket(id, name, places, concert));
                        updateTable(bll);
                        JOptionPane.showMessageDialog(new JFrame(), toShow, "", JOptionPane.INFORMATION_MESSAGE);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else JOptionPane.showMessageDialog(new JFrame(), "Please introduce a ticket ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }


    private void updateTable(CashierBLL bll) throws SQLException {
        String[] collumns = { "ID", "Name", "Places", "Concert"};
        DefaultTableModel myModel = new DefaultTableModel();
        myModel.setColumnIdentifiers(collumns);

        Object[] obj = new Object[4];

        ArrayList<Ticket> tickets = bll.getTicketsList();
        for (Ticket t : tickets) {
            obj[0] = t.getId();
            obj[1] = t.getName();
            obj[2] = t.getPlaces();
            obj[3] = t.getConcert();

            myModel.addRow(obj);
        }

        JTable myTable = new JTable(myModel);
        JScrollPane myScrollPane = new JScrollPane();
        myScrollPane.setBounds(20, 80, 400, 400);
        myScrollPane.setViewportView(myTable);
        myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(myScrollPane);
    }

    // show ticket for a specific concert
    private void showTickets(CashierBLL bll, String concert) throws SQLException {
        String[] collumns = { "ID", "Name", "Places", "Concert"};
        DefaultTableModel myModel = new DefaultTableModel();
        myModel.setColumnIdentifiers(collumns);

        Object[] obj = new Object[4];

        ArrayList<Ticket> tickets = bll.retrieveTicket(concert);
        for (Ticket t : tickets) {
            obj[0] = t.getId();
            obj[1] = t.getName();
            obj[2] = t.getPlaces();
            obj[3] = t.getConcert();

            myModel.addRow(obj);
        }

        JTable myTable = new JTable(myModel);
        JScrollPane myScrollPane = new JScrollPane();
        myScrollPane.setBounds(20, 80, 400, 400);
        myScrollPane.setViewportView(myTable);
        myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(myScrollPane);
    }

    private boolean isStringInt(String s)
    { try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        { return false; }
    }
}
