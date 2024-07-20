import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User {
    private final String userName;
    private final String password;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getPassword(){
        return this.password;
    }

}
class MyFrame extends JFrame implements ActionListener {
    JPanel inputPanel, mainPanel, buttonPanel, topPanel;
    JLabel usernameLabel, passwordLabel;
    JTextField nameField, passwordField;
    JButton signIn;
    User user1 = new User("kalana","1234");

    MyFrame(){

        // First Panel
        inputPanel = new JPanel(new GridLayout(4,2));


        usernameLabel = new JLabel("User Name:- ");
        passwordLabel = new JLabel("Password:- ");
        nameField = new JTextField();
        passwordField = new JPasswordField();

        // Add username password fields to first panel
        inputPanel.add(usernameLabel);
        inputPanel.add(nameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Create a button
        signIn = new JButton("SIGNING");
        signIn.addActionListener(this);
        //build a button
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(signIn);

        //top main panel
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel);

        //MainPanel
        mainPanel = new JPanel(new GridLayout(2,0));
        mainPanel.add(topPanel,BorderLayout.SOUTH);
        mainPanel.add(buttonPanel);


        this.add(mainPanel);


    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == signIn){

            String name = nameField.getText();
            String password = passwordField.getText();

            // access the username and password
            if((user1.getUserName().equals(name)) && (user1.getPassword() .equals(password))){
                JOptionPane.showConfirmDialog(null,"Logging Success","LOGIN",JOptionPane.CLOSED_OPTION);
                this.dispose();

                // Create a new frame
                GUI g = new GUI();
                g.setTitle("Westminster Shopping Centre");
                g.setSize(720, 480);
                g.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(null,"Logging Failed","LOGIN",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.setVisible(true);
        frame.setTitle("LOGIN");
        frame.setSize(720,480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}