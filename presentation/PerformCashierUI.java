package presentation;

import bll.AdminBLL;

import model.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PerformCashierUI extends JFrame {

    public PerformCashierUI() throws SQLException {

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 1000, 450);
        this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(Color.lightGray);

        JLabel title = new JLabel("CASHIERS");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setBounds(20, 0, 300, 77);
        getContentPane().add(title);

        String[] collumns = {"ID", "Username", "Password"};

        DefaultTableModel myModel = new DefaultTableModel();
        myModel.setColumnIdentifiers(collumns);

        AdminBLL bll = new AdminBLL();
        ArrayList<Users> cashiers = bll.getCashiersList();

        Object[] obj = new Object[3];

        for(Users c : cashiers) {
            obj[0] = c.getId();
            obj[1] = c.getUsername();
            obj[2] = c.getPassword();

            myModel.addRow(obj);
        }

        JTable myTable = new JTable(myModel);
        JScrollPane myScrollPane = new JScrollPane();
        myScrollPane.setBounds(20, 80, 500, 300);
        myScrollPane.setViewportView(myTable);
        myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(myScrollPane);


        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");

        username.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        username.setBounds(600, 80, 100, 30);
        password.setBounds(780, 80, 100, 30);
        getContentPane().add(username);
        getContentPane().add(password);

        JTextField userTf = new JTextField();
        JTextField passTf = new JTextField();

        userTf.setBounds(600, 108, 150, 30);
        passTf.setBounds(780, 108, 150, 30);
        getContentPane().add(userTf);
        getContentPane().add(passTf);


        //************ CREATE

        JButton createB = new JButton("Create");
        createB.setBounds(600, 150, 80, 30);
        getContentPane().add(createB);

        createB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String user = userTf.getText();
                String pass = passTf.getText();
                if(user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Both fields must be completed!", "", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        String toShow = bll.createCashier(user, pass);
                        updateTable(bll);
                        JOptionPane.showMessageDialog(new JFrame(), toShow, "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });


        //********** DELETE

        JButton deleteB = new JButton("Delete");
        deleteB.setBounds(600, 230, 90, 30);
        JLabel deleteLb = new JLabel("ID cashier");
        deleteLb.setBounds(700, 200, 100, 30);
        JTextField deleteTf = new JTextField();
        deleteTf.setBounds(700, 230, 60, 30);

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
                        String toShow = bll.deleteCashier(id);
                        updateTable(bll);
                        JOptionPane.showMessageDialog(new JFrame(), toShow, "", JOptionPane.INFORMATION_MESSAGE);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else JOptionPane.showMessageDialog(new JFrame(), "Please introduce a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        //************ UPDATE

        JButton retrieveB = new JButton("Retrieve");
        retrieveB.setBounds(600, 300, 90, 30);
        JLabel retrieveLb = new JLabel("ID cashier");
        retrieveLb.setBounds(700, 270, 100, 30);
        JTextField retrieveTf = new JTextField();
        retrieveTf.setBounds(700, 300, 60, 30);
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
                        Users cash = bll.retrieveCashier(id);
                        if(cash != null) {
                            userTf.setText(cash.getUsername());
                            passTf.setText(cash.getPassword());
                        }
                        else JOptionPane.showMessageDialog(new JFrame(), "There's no cashier with this ID", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Please introduce a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        JButton updateB = new JButton("Update");
        updateB.setBounds(780, 150, 80, 30);
        getContentPane().add(updateB);

        updateB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_str = retrieveTf.getText();

                if(isStringInt(id_str)) {

                    int id = Integer.parseInt(id_str);
                    String user = userTf.getText();
                    String pass = passTf.getText();

                    if(user.isEmpty() || pass.isEmpty()) {
                        JOptionPane.showMessageDialog(new JFrame(), "Both fields must be completed!", "", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        try {
                            String toShow = bll.updateCashier(new Users(id, user, pass, "cashier"));
                            updateTable(bll);
                            JOptionPane.showMessageDialog(new JFrame(), toShow, "", JOptionPane.INFORMATION_MESSAGE);

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
                else JOptionPane.showMessageDialog(new JFrame(), "Please introduce a cashier ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        //********* BACK

        JButton backB = new JButton("Back");
        backB.setBounds(780, 350, 150, 30);
        getContentPane().add(backB);

        backB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                AdminUI adm = new AdminUI();
                adm.setVisible(true);
            }
        });
    }

    private void updateTable(AdminBLL bll) throws SQLException {

        String[] collumns = {"ID", "Username", "Password"};

        DefaultTableModel myModel = new DefaultTableModel();
        myModel.setColumnIdentifiers(collumns);

        ArrayList<Users> cashiers = bll.getCashiersList();

        Object[] obj = new Object[3];

        for(Users c : cashiers) {
            obj[0] = c.getId();
            obj[1] = c.getUsername();
            obj[2] = c.getPassword();

            myModel.addRow(obj);
        }

        JTable myTable = new JTable(myModel);
        JScrollPane myScrollPane = new JScrollPane();
        myScrollPane.setBounds(20, 80, 500, 400);
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
