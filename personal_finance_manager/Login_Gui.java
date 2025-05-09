package personal_finance_manager;

//region All Import Files.
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

public class Login_Gui  implements ActionListener {


             //region Member declaration region.
              JFrame jframe;
              JLabel jLabel,loginHeading,usernameTxt,passwordTxt,Sign_text;
              JTextField usernameBox;
              JPasswordField passwordBox;
              Color color;
              JButton loginbutton,signButton;
              Connection con;
              ResultSet rs;
              //endregion

          public Login_Gui(){

              try {
                  connector();
                  jframe = new JFrame();
                  jLabel = new JLabel();

                  //region Body style
                  Image icon_logo = ImageIO.read(new File("personal_finance_manager/3x3Logo.png"));
                  color = new Color(245, 245, 245);
                  jframe.getContentPane().setBackground(color);
                  jframe.setIconImage(icon_logo);
                  jframe.setTitle("Personal Finance Manager");
                  jframe.setBounds(500, 150, 750, 500);
                  jLabel.setIcon(new ImageIcon("personal_finance_manager/3x3Logo.png"));
                  jLabel.setBounds(1, 60, 300, 300);
                  //endregion

                  //region login-Heading
                  loginHeading = new JLabel("Login");
                  loginHeading.setBounds(500, 50, 200, 50);
                  loginHeading.setFont(new Font("Serif", Font.BOLD, 33));
                  loginHeading.setForeground(new Color(30, 66, 101));
                  //endregion

                  //region username Title
                  usernameTxt = new JLabel("Username");
                  usernameTxt.setBounds(310, 120, 120, 40);
                  usernameTxt.setFont(new Font("Serif", Font.BOLD, 25));
                  usernameTxt.setForeground(Color.black);
                  //endregion

                  //region password Title
                  passwordTxt = new JLabel("Password");
                  passwordTxt.setBounds(310, 180, 120, 40);
                  passwordTxt.setFont(new Font("Serif", Font.BOLD, 25));
                  passwordTxt.setForeground(Color.black);
                  //endregion

                  //region username Box
                  usernameBox = new JTextField();
                  usernameBox.setBounds(450, 130, 220, 30);
                  usernameBox.setBackground(new Color(230, 230, 230));
                  //endregion

                  //region Password Box
                  passwordBox = new JPasswordField();
                  passwordBox.setBounds(450, 190, 220, 30);
                  passwordBox.setBackground(new Color(230, 230, 230));
                  //endregion

                  //region Login button
                  loginbutton = new JButton("Login");
                  loginbutton.setFont(new Font("Arial", Font.BOLD, 13));
                  loginbutton.setBounds(500, 280, 120, 30);
                  loginbutton.setBackground(new Color(30, 66, 101));
                  loginbutton.setForeground(Color.white);
                  //endregion

                  //region sign-up Text
                  Sign_text = new JLabel("Don't have Account ?");
                  Sign_text.setBounds(310, 230, 230, 40);
                  Sign_text.setFont(new Font("Arial", Font.BOLD, 13));
                  Sign_text.setForeground(Color.black);
                  //endregion

                  //region Sign-in Button
                  signButton = new JButton("Sign Up");
                  signButton.setForeground(Color.black);
                  signButton.setBackground(new Color(245, 245, 245));
                  signButton.setBounds(440, 240, 80, 20);
                  signButton.setFont(new Font("Arial", Font.BOLD, 12));
                  signButton.setBorder(new LineBorder(new Color(230, 230, 230)));
                  //endregion

                  //region adding to frame
                  jframe.add(signButton);
                  jframe.add(Sign_text);
                  jframe.add(loginbutton);
                  jframe.add(jLabel);
                  jframe.add(loginHeading);
                  jframe.add(usernameTxt);
                  jframe.add(passwordTxt);
                  jframe.add(usernameBox);
                  jframe.add(passwordBox);
                  loginbutton.addActionListener(this);
                  signButton.addActionListener(this);
                  jframe.setLayout(null);
                  jframe.setResizable(false);
                  jframe.setVisible(true);
                  jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                  //endregion
              } catch (RuntimeException e) {
                  throw new RuntimeException(e);
              }
                  catch (Exception e){
                  e.printStackTrace();
              }
          }
              //region All Class Methods
    private void getUserId(){
              try {
                  String name,pass;
                  name=usernameBox.getText();
                  pass=passwordBox.getText();
                  PreparedStatement getUserid = con.prepareStatement("SELECT user_id FROM usernew WHERE username = ? AND password = ?;");
                  getUserid.setString(1, name);
                  getUserid.setString(2, pass);
                  ResultSet rs = getUserid.executeQuery();
                  int userId ;
                  if (rs.next()) {
                      userId = rs.getInt("user_id");
                      DashBoard_Gui objNew = new DashBoard_Gui(userId,name);
                      jframe.dispose();
                  }
              } catch (SQLException | IOException e) {
                  throw new RuntimeException(e);
              }
          }
    private void connector(){
              try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  con = DriverManager.getConnection
                          ("jdbc:mysql://localhost:3306/user_data1?useTimezone=true&serverTimezone=UTC",
                                  "root","Vedant1#");
              }
              catch(Exception e){
                  e.printStackTrace();
              }
    }
    private void infoCheck(){
              try {
                  String name,pass;
                  name=usernameBox.getText();
                  pass=passwordBox.getText();
                  String login="SELECT * FROM usernew WHERE username = ? AND password = ?;";
                  PreparedStatement preparedStatement=con.prepareStatement(login);
                  preparedStatement.setString(1,name);
                  preparedStatement.setString(2,pass);

                  rs = preparedStatement.executeQuery();
                  if (rs.next()){
                      JOptionPane.showMessageDialog(jframe,"Login Successful.");
                      getUserId();
                  }
                  else{
                      JOptionPane.showMessageDialog(jframe,"Login Failed,Please enter the Correct username and password !");
                  }
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }
    }
    //endregion

            //region ActionListener Methods
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
          if (e.getSource()==loginbutton){
               infoCheck();
          }
          else if (e.getSource()==signButton){
              Sign_up_Gui obj_sign_up = new Sign_up_Gui();
               jframe.dispose();
          }
        }
        catch (RuntimeException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    //endregion
}
