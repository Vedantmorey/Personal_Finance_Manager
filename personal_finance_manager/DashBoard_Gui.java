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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
//endregion



public class DashBoard_Gui implements ActionListener {

    //region Data Members Declaration
    static JFrame jFrame;
    JLabel jLabel,heading, CurrentB_Text,TotalIncome_Text,TotalExpense_Text,currentData,incomeData,expensedata,greetUser;
    JPanel CurrentB_Panel,TotalIncome_Panel, TotalExpense_Panel,headingPanel;
    JButton TransactionButton,ViewReportButton,BudgetButton;
    Connection con;
    ResultSet rs;
    Integer user_id;
    String user_name;
    //endregion

    public DashBoard_Gui(Integer userId,String name) throws IOException {

        this.user_id=userId;
        this.user_name=name;
        jFrame = new JFrame();
        connector();
        displayExpenseBarChart();

        //region logo region
        Image logoImage = ImageIO.read(new File("personal_finance_manager/3x3Logo.png"));
        jFrame.setIconImage(logoImage);
        //endregion

        //region Heading Panel
        headingPanel = new JPanel();
        headingPanel.setBounds(0, 0, 999, 150);
        headingPanel.setBackground(new Color(30, 66, 101));
        headingPanel.setLayout(null);
        //endregion

        //region DashBoard Logo
        jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon("personal_finance_manager/150pxPng.png"));
        jLabel.setBounds(50,22,150,125);
        jLabel.setBorder(new LineBorder(new Color(211, 211, 211, 0)));
        //endregion

        //region Heading region

        heading = new JLabel();
        heading.setText("Personal Finance Manager");
        heading.setForeground(Color.white);
        heading.setFont(new Font("Arial",Font.PLAIN,40));
        heading.setBounds(280,60,485,50);
        //endregion

        //region Greeting Users.
        greetUser = new JLabel("<html>Welcome, <font color='rgb(30, 66, 101)'>" + user_name + "</font></html>");
        greetUser.setForeground(Color.black);
        greetUser.setFont(new Font("Arial", Font.PLAIN, 25));
        greetUser.setBounds(70, 190, 400, 30);
        //endregion

        //region Current Balance Panel
        CurrentB_Panel = new JPanel();
        CurrentB_Panel.setBounds(90, 245, 260, 100);
        CurrentB_Panel.setBorder(new LineBorder(Color.gray,2,false));
        CurrentB_Panel.setBackground(Color.WHITE);
        CurrentB_Panel.setLayout(null);

        CurrentB_Text = new JLabel("Current Balance");
        CurrentB_Text.setBounds(60, 20, 150, 30);
        CurrentB_Text.setFont(new Font("Arial", Font.PLAIN, 18));

        currentData = new JLabel("₹0.00");
        currentData.setFont(new Font("Arial", Font.BOLD, 20));
        currentData.setForeground(new Color(52, 152, 219));
        currentData.setBounds(70, 55, 150, 30);
        //endregion

        //region Total Income Panel
       TotalIncome_Panel = new JPanel();
       TotalIncome_Panel.setBounds(380, 245, 260, 100);
       TotalIncome_Panel.setBorder(new LineBorder(Color.gray,2,false));
       TotalIncome_Panel.setBackground(Color.WHITE);
       TotalIncome_Panel.setLayout(null);

       TotalIncome_Text = new JLabel("Total Income");
       TotalIncome_Text.setBounds(60, 20, 150, 30);
       TotalIncome_Text.setFont(new Font("Arial", Font.PLAIN, 18));

       incomeData = new JLabel("₹0.00");
       incomeData.setBounds(70, 55, 150, 30);
       incomeData.setForeground(new Color (46, 204, 113));
       incomeData.setFont(new Font("Arial",Font.BOLD,20));

        //endregion

        //region Total Expenses Panel
       TotalExpense_Panel = new JPanel();
       TotalExpense_Panel.setBounds(670, 245, 260, 100);
       TotalExpense_Panel.setBorder(new LineBorder(Color.gray,2,false));
       TotalExpense_Panel.setBackground(Color.WHITE);
       TotalExpense_Panel.setLayout(null);


       TotalExpense_Text = new JLabel("Total Expenses");
       TotalExpense_Text.setFont(new Font("Arial", Font.BOLD, 18));
       TotalExpense_Text.setBounds(60, 20, 150, 30);

       expensedata = new JLabel("₹0.00");
       expensedata.setBounds(70, 55, 150, 30);
       expensedata.setForeground(new Color (231, 76, 60));
       expensedata.setFont(new Font("Arial",Font.BOLD,20));
       //endregion


        //region Transaction Button
        ImageIcon PlusIcon = new ImageIcon("personal_finance_manager/AddIcon.png");
        TransactionButton = new JButton("  Add Transaction ",PlusIcon);
        TransactionButton.setBounds(710, 400, 220, 50);
        TransactionButton.setFocusable(false);
        TransactionButton.setFont(new Font("Arial", Font.PLAIN, 17));
        TransactionButton.setForeground(Color.WHITE);
        TransactionButton.setBackground(new Color(30, 66, 101));
        Image resizedImage = PlusIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        TransactionButton.setIcon(resizedIcon);
        TransactionButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        //endregion

        //region View Report
        ImageIcon View = new ImageIcon("personal_finance_manager/ViewReportIcon.png");
        ViewReportButton = new JButton("View Report",View);
        ViewReportButton.setFocusable(false);
        ViewReportButton.setBounds(710,500 , 220, 50);
        ViewReportButton.setFont(new Font("Arial", Font.PLAIN, 17));
        ViewReportButton.setForeground(Color.WHITE);
        ViewReportButton.setBackground(new Color(30, 66, 101));
        Image resizedImage1 = View.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon1 = new ImageIcon(resizedImage1);
        ViewReportButton.setIcon(resizedIcon1);
        ViewReportButton.setIconTextGap(40);
        ViewReportButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        //endregion

        //region Budget Planning
        ImageIcon calci = new ImageIcon("personal_finance_manager/calciIcon.png");
        BudgetButton = new JButton("  Budget Planning ",calci);
        BudgetButton.setBounds(710, 600, 220, 50);
        BudgetButton.setFocusable(false);
        BudgetButton.setFont(new Font("Arial", Font.PLAIN, 17));
        BudgetButton.setForeground(Color.WHITE);
        BudgetButton.setBackground(new Color(30, 66, 101));
        Image resizedImage2 = calci.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon2 = new ImageIcon(resizedImage2);
        BudgetButton.setIcon(resizedIcon2);
        BudgetButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        BudgetButton.addActionListener(this);
        //endregion

        //region Adding to frame.
        jFrame.add(jLabel);
        jFrame.add(TransactionButton);
        jFrame.add(ViewReportButton);
        jFrame.add(BudgetButton);
        jFrame.add(CurrentB_Panel);
        jFrame.add(TotalIncome_Panel);
        jFrame.add(TotalExpense_Panel);
        jFrame.add(heading);
        jFrame.add(TotalExpense_Text);
        jFrame.add(TotalIncome_Text);
        CurrentB_Panel.add(CurrentB_Text);
        CurrentB_Panel.add(currentData);
        TotalIncome_Panel.add(TotalIncome_Text);
        TotalIncome_Panel.add(incomeData);
        TotalExpense_Panel.add(TotalExpense_Text);
        TotalExpense_Panel.add(expensedata);
        jFrame.add(headingPanel);
        jFrame.add(greetUser);
        headingPanel.add(jLabel);
        headingPanel.add(heading);
        //endregion

        currentData();
        total_income();
        expense();

        //region Body Style
        jFrame.setTitle("Personal Finance Manager");
        jFrame.setBounds(360, 8, 1000, 800);
        jFrame.setBackground(new Color(245, 245, 245));
        jFrame.setResizable(false);
        TransactionButton.addActionListener(this);
        ViewReportButton.addActionListener(this);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(null);
        jFrame.setVisible(true);
        //endregion

    }

    //region Class Methods.
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
    void currentData(){
        try {
            String sql = "SELECT " +
                    "IFNULL(SUM(CASE WHEN type = 'Income' THEN amount ELSE 0 END), 0) - " +
                    "IFNULL(SUM(CASE WHEN type = 'Expense' THEN amount ELSE 0 END), 0) AS balance " +
                    "FROM transaction_new WHERE user_id = ?";
                    PreparedStatement getCurrentData = con.prepareStatement(sql);
                    getCurrentData.setInt(1, user_id);
            ResultSet CB = getCurrentData.executeQuery();
            double balance = 0;
            if (CB.next()) {
                balance = CB.getDouble("balance");
            }
            currentData.setText("₹" + balance);
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
    void total_income(){
        try{
        String sql = "SELECT SUM(amount) AS total_income FROM transaction_new WHERE type = 'Income' AND user_id = ?";
            PreparedStatement ttincome = con.prepareStatement(sql);
            ttincome.setInt(1, user_id);
            ResultSet rs = ttincome.executeQuery();
            if (rs.next()){
                double income = rs.getDouble("total_income");
                incomeData.setText("₹"+income);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void expense(){
        try {
            String sql = "SELECT SUM(amount) AS total_expense FROM transaction_new WHERE type = 'Expense' AND user_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            rs = preparedStatement.executeQuery();
            if (rs.next()){
                double expense = rs.getDouble("total_expense");
                expensedata.setText(String.format("₹%.2f", expense));
            }
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void RefreshGui(){
        currentData();
        total_income();
        expense();
        jFrame.revalidate();
        jFrame.repaint();
    }
    public void RefreshTheChart(){
        try {
            for (Component comp : jFrame.getContentPane().getComponents()) {
                if (comp instanceof ChartPanel) {
                    jFrame.remove(comp);
                }
            }
            displayExpenseBarChart();
        } catch (Exception e) {
            System.out.println("Error refreshing the chart: " + e.getMessage());
        }
    }
    public void displayExpenseBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            String sql = "SELECT category, SUM(amount) AS total FROM transaction_new WHERE user_id = ? AND type = 'Expense' GROUP BY category";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                double total = rs.getDouble("total");
                dataset.addValue(total, "Expenses", category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Expenses by Category",
                "Category",
                "Amount (₹)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBounds(70, 380, 600, 300);
        jFrame.add(chartPanel);
        jFrame.revalidate();
        jFrame.repaint();
    }
    //endregion

    //region ActionListener Method
    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == TransactionButton){
             Transaction_form obj_transaction = new Transaction_form(user_id,this);
         } else if (e.getSource() == ViewReportButton) {
             try{
                 Report_form obj_report = new Report_form(user_id);
             } catch (RuntimeException ex) {
                 throw new RuntimeException(ex);
             }
         } else if (e.getSource() == BudgetButton) {
             JOptionPane.showMessageDialog(null,"Feature Under Development...");
         }
    }
    //endregion

}
