package personal_finance_manager;

//region Import Files
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
//endregion

public class Report_form implements ActionListener {

    //region Data Members
    JFrame jFrame;
    JLabel ReportHeading;
    JPanel panel;
    Connection con;
    JButton exportButton;
    //endregion

    public Report_form(int userId){
        connector();

        //region Body Style Section
        jFrame = new JFrame();
        jFrame.setTitle("Personal Report");
        jFrame.setBounds(550,120,750,650);
        try {
            Image icon = ImageIO.read(new File("personal_finance_manager/3x3Logo.png"));
            jFrame.setIconImage(icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //endregion

        //region Heading Section
        panel = new JPanel();
        panel.setBounds(0, 0, 750, 100);
        panel.setBackground(new Color(30, 66, 101));
        ReportHeading = new JLabel("Report");
        ReportHeading.setForeground(Color.WHITE);
        ReportHeading.setFont(new Font("Arial", Font.BOLD, 40));
        ReportHeading.setBounds(285, 10, 200, 100);
        panel.setLayout(null);
        //endregion

        //region ChartPanel Body
        ChartPanel chartPanel = new ChartPanel(generateExpenseChart(userId));
        chartPanel.setBounds(90, 120, 550, 400);
        jFrame.add(chartPanel);
        //endregion

        //region Export Button
        exportButton = new JButton("Export as PDF");
        exportButton.setFont(new Font("Arial", Font.BOLD, 20));
        exportButton.setForeground(Color.WHITE);
        exportButton.setBackground(new Color(30, 66, 101));
        exportButton.setBounds(260, 540, 230, 50);
        exportButton.setFocusable(false);
        //endregion

        //region Add to Frame Section
        jFrame.add(exportButton);
        jFrame.add(panel);
        panel.add(ReportHeading);
        exportButton.addActionListener(this);
        jFrame.setResizable(false);
        jFrame.setLayout(null);
        jFrame.setVisible(true);
        //endregion

        //region ActionListener Method
        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    if (con != null && !con.isClosed()) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });
        //endregion

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
    private JFreeChart generateExpenseChart(int userId) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try (PreparedStatement pst = con.prepareStatement(
                "SELECT category, SUM(amount) AS total FROM transaction_new WHERE user_id = ? AND type = 'expense' GROUP BY category")) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dataset.setValue(rs.getString("category"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ChartFactory.createPieChart("User Expense Breakdown", dataset, true, true, false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exportButton){
         JOptionPane.showMessageDialog(null,"Generating Report.....");
        }
    }

    //endregion
}