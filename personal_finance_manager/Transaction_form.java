package personal_finance_manager;

//region All Import Files.
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
//endregion

public class Transaction_form implements ActionListener {

    //region Data Member Declaration
    JFrame jFrame;
    Image logo;
    JLabel dateText,categoryText,typeText,amountText;
    Font Text_font;
    Button addbutton,deleteButton;
    JTextField datebox,amountbox;
    JComboBox typeBox,categoryBox;
    Connection con;
    Integer userid;
    DashBoard_Gui dashBoardGui;
    //endregion

    public Transaction_form(Integer user_id,DashBoard_Gui dashBoardGui) {
        try{
            this.userid=user_id;
            this.dashBoardGui=dashBoardGui;
            connector();

            //region Body style
        Text_font = new Font("Arial", Font.BOLD,23);
        jFrame = new JFrame();
        logo = ImageIO.read(new File("personal_finance_manager/3x3Logo.png"));
        jFrame.setIconImage(logo);
        jFrame.setTitle("Transaction");
        jFrame.setBounds(550,120,390,460);
           //  endregion

            //region Date Text
            dateText = new JLabel("Date ");
            dateText.setFont(Text_font);
            dateText.setBounds(25, 45, 100, 30);
            //endregion

            //region Type Text
            typeText = new JLabel("Type");
            typeText.setFont(Text_font);
            typeText.setBounds(25, 100, 100, 30);
            //endregion

            //region Category Text
            categoryText = new JLabel("Category ");
            categoryText.setFont(Text_font);
            categoryText.setBounds(25, 160, 130, 30);
            //endregion

            //region Amount Text
            amountText = new JLabel("Amount ");
            amountText.setFont(Text_font);
            amountText.setBounds(25, 220, 100, 30);
            //endregion

            //region Field Boxes
            datebox = new JTextField("2025/04/03");
            datebox.setBounds(200, 45, 150, 30);

            String []type_string={"Expense" , "Income"};
            typeBox = new JComboBox<>(type_string);
            typeBox.setFont(Text_font);
            typeBox.setBounds(200, 100, 150,30 );

            String []category_box ={"Food","Travel","Rent","Salary","Medicine","Other"};
            categoryBox = new JComboBox<>(category_box);
            categoryBox.setFont(Text_font);
            categoryBox.setBounds(200, 160, 150, 30);

            amountbox = new JTextField();
            amountbox.setBounds(200, 220, 150, 30);
            //endregion

            //region Add Button
            addbutton = new Button("Add");
            addbutton.setFont(Text_font);
            addbutton.setBounds(60, 300, 100, 30);
            addbutton.setFocusable(false);
            addbutton.setForeground(Color.WHITE);
            addbutton.setBackground(new Color(30, 66, 101));
            //endregion

            //region Clear Button
            deleteButton = new Button("Clear");
            deleteButton.setBounds(210, 300, 100, 30);
            deleteButton.setFont(Text_font);
            deleteButton.setFocusable(false);
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setBackground(new Color(30, 66, 101));
            //endregion

            //region Adding region
            addbutton.addActionListener(this);
            deleteButton.addActionListener(this);
            jFrame.add(deleteButton);
            jFrame.add(datebox);
            jFrame.add(typeBox);
            jFrame.add(categoryBox);
            jFrame.add(amountbox);
            jFrame.add(dateText);
            jFrame.add(amountText);
            jFrame.add(categoryText);
            jFrame.add(typeText);
            jFrame.add(addbutton);
            jFrame.setLayout(null);
            jFrame.setVisible(true);
            //endregion

        } catch(IOException Ex){
            Ex.printStackTrace();
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    //region Class Methods
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
    private void InsertData(){
        try {
            String date, amount, type, category;
            int id = userid;
            date = datebox.getText();
            amount = amountbox.getText();
            type = Objects.requireNonNull(typeBox.getSelectedItem()).toString();
            category = Objects.requireNonNull(categoryBox.getSelectedItem()).toString();
            String sql ="INSERT INTO transaction_new (user_id, date, type, category, amount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,date );
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, category);
            preparedStatement.setInt(5, Integer.parseInt(amount));
            int n = preparedStatement.executeUpdate();
            if (n>0){
                JOptionPane.showMessageDialog(jFrame, "Entry Stored SuccessFully.");
                if (dashBoardGui!=null){
                    dashBoardGui.RefreshGui();
                    dashBoardGui.RefreshTheChart();
                }
                jFrame.dispose();
            }
            else {
                JOptionPane.showMessageDialog(jFrame, "Data not Stored");
            }

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void ClearData(){
        datebox.setText("");
        typeBox.setSelectedIndex(0);
        categoryBox.setSelectedIndex(0);
        amountbox.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addbutton){
        InsertData();
        } else if (e.getSource() == deleteButton) {
            ClearData();
        }
    }
}
