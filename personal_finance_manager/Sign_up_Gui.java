package personal_finance_manager;

//region import files.
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;
//endregion

public class Sign_up_Gui implements ActionListener {

    //region Data Members
    JFrame jframe;
    JLabel jLabel, nameText, emailText, passwordText, titleHeading,loginText;
    JTextField textField, namebox, emailbox, passwordbox;
    Color color;
    Image icon_logo;
    JButton signButton,loginButton;
    Connection con;
    //endregion

    public Sign_up_Gui() throws IOException {

        jframe = new JFrame();
        jLabel = new JLabel();
        textField = new JTextField();

        connector();

        //region Body style
        icon_logo = ImageIO.read(new File("personal_finance_manager/3x3Logo.png"));
        color = new Color(255, 255, 255);
        jLabel.setIcon(new ImageIcon("personal_finance_manager/3x3Logo.png"));
        jLabel.setBounds(1, 80, 300, 300);
        jframe.getContentPane().setBackground(color);
        jframe.setIconImage(icon_logo);
        jframe.setTitle("Personal Finance Manager");
        jframe.setBounds(500, 150, 750, 500);
        jframe.setLayout(null);
        //endregion

        //region login-Heading
        titleHeading = new JLabel("Sign up");
        titleHeading.setBounds(510, 23, 180, 50);
        titleHeading.setFont(new Font("Serif", Font.BOLD, 35));
        titleHeading.setForeground(new Color(30, 66, 101));
        //endregion

        //region Name Text
        nameText = new JLabel("Username");
        nameText.setFont(new Font("Serif", Font.BOLD, 23));
        nameText.setBounds(320, 110, 180, 30);
        //endregion

        //region Password Text
        passwordText = new JLabel("Create Password");
        passwordText.setFont(new Font("Serif", Font.BOLD, 23));
        passwordText.setBounds(320, 180, 180, 30);
        //endregion

        //region Email Text
        emailText = new JLabel("Email");
        emailText.setFont(new Font("Serif", Font.BOLD, 23));
        emailText.setBounds(320, 250, 180, 30);
        //endregion

        //region Name Box
        namebox = new JTextField();
        namebox.setBounds(495, 110, 180, 30);
        namebox.setBackground(new Color(230, 230, 230));
        //endregion

        //region Password Box
        passwordbox = new JTextField();
        passwordbox.setBounds(495, 180, 180, 30);
        passwordbox.setBackground(new Color(230, 230, 230));
        //endregion

        //region Email box
        emailbox = new JTextField();
        emailbox.setBounds(495, 250, 180, 30);
        emailbox.setBackground(new Color(230, 230, 230));
        //endregion

        //region sign in Button
        signButton = new JButton("Sign up");
        signButton.setFont(new Font("Arial", Font.BOLD, 13));
        signButton.setBounds(530, 350, 100, 30);
        signButton.setBackground(new Color(30, 66, 101));
        signButton.setForeground(Color.white);
        //endregion

        //region Login page Connectivity
        loginText = new JLabel("Already have an Account ?");
        loginText.setFont(new Font("Arial", Font.BOLD,13));
        loginText.setBounds(320, 310, 180, 30);
        //endregion

        //region Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial",Font.BOLD,13));
        loginButton.setBounds(488, 315, 50, 20);
        loginButton.setForeground(Color.BLACK);
        loginButton.setBackground(new Color(255, 255, 255));
        loginButton.setBorder(new LineBorder(new Color(230, 230, 230)));
        //endregion

        //region ActionListener Region.
        signButton.addActionListener(this);
        loginButton.addActionListener(this);
        //endregion

        //region Adding to Frame.
        jframe.add(loginButton);
        jframe.add(loginText);
        jframe.add(signButton);
        jframe.add(titleHeading);
        jframe.add(jLabel);
        jframe.add(textField);
        jframe.add(namebox);
        jframe.add(emailbox);
        jframe.add(passwordbox);
        jframe.add(nameText);
        jframe.add(passwordText);
        jframe.add(emailText);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        //endregion
    }

       //region All Class Methods
    private void connector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/user_data1?useTimezone=true&serverTimezone=UTC",
                            "root", "Vedant1#");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void signup() {
        try {
            String name, pass, email;
            name = namebox.getText();
            pass = passwordbox.getText();
            email = emailbox.getText();
            String sql = "INSERT INTO usernew(username,password,email) VALUES (?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,pass);
            preparedStatement.setString(3,email);
          int n=preparedStatement.executeUpdate();
            if (n>0){
               JOptionPane.showMessageDialog(jframe, "SignUp SuccessFull.");
               Login_Gui obj_login_gui = new Login_Gui();
               jframe.dispose();
            }
            else {
                JOptionPane.showMessageDialog(jframe, "Please Enter the Correct Information.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void Checkup(){
         try {
             if (namebox.getText().trim().isEmpty() || passwordbox.getText().trim().isEmpty() || emailbox.getText().trim().isEmpty()) {
                 JOptionPane.showMessageDialog(jframe, "Please Enter the Correct Information Again.");
             }
             else {
                 signup();
             }
         } catch (RuntimeException e) {
             throw new RuntimeException(e);
         }
    }
    //endregion

       //region ActionListner Methods
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signButton) {
            try {
             Checkup();
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }

        }
        else if (e.getSource() == loginButton){
            {
             Login_Gui obj_login = new Login_Gui();
             jframe.dispose();
            }
        }
    }
    //endregion
}
