package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminUI extends JFrame{

    public AdminUI() {

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 500, 350);
        this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(Color.lightGray);

        JLabel title = new JLabel("ADMIN");
        title.setFont(new Font("Arial", Font.PLAIN, 28));
        title.setBounds(205, 30, 300, 70);
        getContentPane().add(title);

        JLabel lab = new JLabel("What exactly do you want to operate on?");
        lab.setFont(new Font("Arial", Font.PLAIN, 20));
        lab.setBounds(70, 120, 500, 35);
        getContentPane().add(lab);

        JButton cashB = new JButton("Cashiers");
        cashB.setBounds(70, 190, 150, 40);
        getContentPane().add(cashB);


        cashB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerformCashierUI cash = null;
                try {
                    cash = new PerformCashierUI();
                    setVisible(false);
                    cash.setVisible(true);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        JButton concertsB = new JButton("Concerts");
        concertsB.setBounds(280, 190, 150, 40);
        getContentPane().add(concertsB);

        concertsB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerformConcertUI con = null;
                try {
                    con = new PerformConcertUI();
                    setVisible(false);
                    con.setVisible(true);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
