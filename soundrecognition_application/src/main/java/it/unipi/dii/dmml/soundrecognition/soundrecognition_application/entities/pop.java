package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.entities;

// Java Program to create a popup and display
// it on a parent frame
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class pop extends JFrame implements ActionListener {
    // popup
    Popup p;

    // constructor
    pop()
    {
        // create a frame
        JFrame f = new JFrame("pop");

        // create a label
        JLabel l = new JLabel("This is a popup");

        f.setSize(400, 400);

        PopupFactory pf = new PopupFactory();

        // create a panel
        JPanel p2 = new JPanel();

        // set Background of panel
        p2.setBackground(Color.red);

        p2.add(l);

        // create a popup
        p = pf.getPopup(f, p2, 180, 100);

        // create a button
        JButton b = new JButton("click");

        // add action listener
        b.addActionListener(this);

        // create a panel
        JPanel p1 = new JPanel();

        p1.add(b);
        f.add(p1);
        f.show();
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        p.show();
    }
    // main class
    public static void main(String args[])
    {
        pop p = new pop();
    }
}

