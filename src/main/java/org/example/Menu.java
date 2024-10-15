package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Methods Group Project - Group 8");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10)); // 4x2 grid layout for 8 buttons // CHANGE THIS IS THE TEXT IN THE BUTTONS IS TOO LONG ETC

        // Create 8 buttons and add them to the panel !!CHANGING THIS DOESEN'T AFFECT THE UI LAYOUT SEE THE COMMENT ABOVE FROM GRIDLAYOUT!!
        for (int i = 1; i <= 8; i++) {
            JButton button = new JButton("Option " + i);
            int optionNumber = i; // Captures the options / Button number for which button the user has pressed
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Option " + optionNumber + " selected!");
                }
            });
            panel.add(button);
        }

        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
}
