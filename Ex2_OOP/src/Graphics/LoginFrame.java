package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginFrame implements Runnable{

    private long tz;
    private int scenario;
    private JComboBox liste1;




    @Override
    public synchronized void run() {

        //Creat a Window "Login" Of Size 500x400
        JFrame fen=new JFrame("Login");
        fen.setSize(500, 400);
        //If the window is close The Game Stop and the thrad is close the program stop running.
        fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creat a Pannel Color
        JPanel pan = new JPanel();
        //To Have My Pannelin Grey
        pan.setBackground( new Color(102, 118, 134) );
        fen.setContentPane(pan);

        // Detective automatic layout
        pan.setLayout(null);

        //Creat a bouton "Start Game"
        JButton b=new JButton("Start Game");
        //To add the button into my frame
        fen.add(b);
        //To define were exactly i want my button
        b.setBounds(200,250, 100,50);


        //Creat a zone to enter a text (My ID here )size 10
        JTextField tid = new JTextField(10);
        //To add the text zone into my frame
        fen.getContentPane().add(tid);
        //To define were exactly i want my text zone
        tid.setBounds(300, 100, 70, 20);

        //To get my text in the system
        JLabel id=new JLabel("Enter ID: ");
        fen.getContentPane().add(id);
        id.setBounds(100, 100, 70, 20);


        //Creat a arrray of String who's contains all the possibility of my Level Game that i will implement in a menu bar
        String[] scenarStrings = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};

        //Create the combo box, select item at index 23.
        JComboBox scenList = new JComboBox(scenarStrings);
        //To set the default index selected
        scenList.setSelectedIndex(0);
        scenList.addActionListener(this.liste1);
        fen.getContentPane().add(scenList);
        scenList.setBounds(300, 150, 100, 20);

        //Text that the User can know that he have to choos a level in the game
        JLabel scenar=new JLabel("Enter Scenario number: ");
        fen.getContentPane().add(scenar);
        scenar.setBounds(100, 150, 150, 20);

        //set button b as default button
        fen.getRootPane().setDefaultButton(b);
        // set focus toi b so we can press enter to push it
        b.requestFocus();
        b.addActionListener(this::actiongraph);
        fen.setVisible(true);
        //Add an Action important to the button Start Game , if the User press the button so the game can run :
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e){

                fen.setVisible(false);
                String idtext = tid.getText();
                //To get the ID i recieved that i could use after .
                id.setText("ID : "+idtext);
                setTz(Long.parseLong(idtext)); //update tz
                setScenario(Integer.parseInt(scenList.getSelectedItem().toString()));

            }
        });

    }

    public void actiongraph(ActionEvent e) {
        //DO SOMETHING
        //If the Button Strat Game are Pressed Start the Game using the ID and The level Recently uptadet
        Thread game = new Thread(new DijkstraAlgo(this.tz,this.scenario));
        //The game can start because all the informationi need are uploaded
        game.start();
    }

    //To get The ID
    public long getTz() {
        return tz;
    }
    //To Set The ID
    public void setTz(long tz) {
        this.tz = tz;
    }
    //To Get The Level
    public int getScenario() {
        return scenario;
    }
    //To Set The Level
    public void setScenario(int scenario) {
        this.scenario = scenario;
    }
}
