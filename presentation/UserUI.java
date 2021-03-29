package presentation;

import bll.UserBLL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;


public class UserUI extends JFrame {

    private JTextField user;
    private JPasswordField pass;


    public UserUI() {

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 500, 400);
        this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(Color.lightGray);

        JLabel title = new JLabel ("Enter login details");
        title.setFont(new Font("Arial", Font.PLAIN, 28));
        title.setBounds (130, 50, 300, 77);
        getContentPane().add(title);

        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        username.setBounds (55, 130, 100, 77);
        password.setBounds (55, 200, 100, 77);
        getContentPane().add(username);
        getContentPane().add(password);


        user = new JTextField();
        user.setBounds (170, 150, 230, 35);
        getContentPane().add(user);

        pass = new JPasswordField();
        pass.setBounds(170, 220, 230, 35);
        getContentPane().add(pass);

        JButton button = new JButton("OK");
        button.setFont(new Font("Arial", Font.PLAIN, 10));
        button.setBounds(350, 280, 50, 30);
        getContentPane().add(button);



        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = user.getText();
                char[] password = pass.getPassword();

                UserBLL bll = new UserBLL();
                try {
                    if(bll.checkCredentials(username, password)) {
                        if(bll.guessWho(username).equals("cashier")) {
                            CashierUI cash = new CashierUI();
                            setVisible(false);
                            cash.setVisible(true);
                        }
                        else if(bll.guessWho(username).equals("admin")) {
                            AdminUI adm = new AdminUI();
                            setVisible(false);
                            adm.setVisible(true);
                        }
                    }
                    else JOptionPane.showMessageDialog(new JFrame(), "Username or password incorrect", "Error", JOptionPane.ERROR_MESSAGE);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                /*AdminGUI adminPage = new AdminGUI(restaurant, name);
                adminPage.setVisible(true);*/
            }
        });


    }
}
