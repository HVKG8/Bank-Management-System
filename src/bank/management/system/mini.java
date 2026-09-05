package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class mini extends JFrame implements ActionListener {

    String pin;
    JButton button;

    mini(String pin) {

        this.pin = pin;

        getContentPane().setBackground(new Color(255, 204, 204));

        setSize(400, 600);
        setLocation(20, 20);
        setLayout(null);

        JLabel label1 = new JLabel();
        label1.setBounds(20, 140, 350, 250);
        add(label1);

        JLabel label2 = new JLabel("Harz A.V");
        label2.setFont(new Font("System", Font.BOLD, 15));
        label2.setBounds(150, 20, 200, 20);
        add(label2);

        JLabel label3 = new JLabel();
        label3.setBounds(20, 80, 350, 20);
        add(label3);

        JLabel label4 = new JLabel();
        label4.setBounds(20, 400, 350, 20);
        add(label4);

        // Display Card Number
        try {

            Conn c = new Conn();

            ResultSet resultSet = c.statement.executeQuery(
                    "select * from login where pin = '" + pin + "'"
            );

            while (resultSet.next()) {

                String cardNumber = resultSet.getString("card_number");

                label3.setText(
                        "Card Number: "
                        + cardNumber.substring(0, 4)
                        + "XXXXXXXX"
                        + cardNumber.substring(12)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Display Transactions and Calculate Balance
        try {

            int balance = 0;

            Conn c = new Conn();

            ResultSet resultSet = c.statement.executeQuery(
                    "select * from bank where pin = '" + pin + "'"
            );

            String statement = "<html>";

            while (resultSet.next()) {

                statement +=
                        resultSet.getString("date")
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + resultSet.getString("type")
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + resultSet.getString("amount")
                        + "<br><br>";

                if (resultSet.getString("type").equals("Deposit")) {

                    balance += Integer.parseInt(
                            resultSet.getString("amount")
                    );

                } else {

                    balance -= Integer.parseInt(
                            resultSet.getString("amount")
                    );
                }
            }

            statement += "</html>";

            label1.setText(statement);

            label4.setText(
                    "Your Total Balance is Rs: " + balance
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Exit Button
        button = new JButton("Exit");
        button.setBounds(20, 500, 100, 25);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        setVisible(false);
    }

    public static void main(String[] args) {

        new mini("");
    }
}