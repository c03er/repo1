/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stock_Management;

import Database_Management.DBConnection;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import mb.HomeFinal;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author pasindu
 */
public final class PreviousBillSummaryFinal extends javax.swing.JFrame {
    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    /**
     * Creates new form SalesSummary
     */
    public PreviousBillSummaryFinal(HomeFinal homeFinal) {
        initComponents();
        
        this.homeFinal = homeFinal;
        this.homeFinal2 = homeFinal;
       
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
        
        try {
            
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
           closeApplication();
        }
        });
        
        
    }
    
    //make the class non cloneable
    @Override
    protected final CloneNotSupportedException clone() throws java.lang.CloneNotSupportedException{
        
        throw new java.lang.CloneNotSupportedException();
    }
    //make the class nonseriallizeable
    private final void writeObject(ObjectOutputStream out) throws java.io.IOException {
        throw new java.io.IOException("Object cannot be serialized");
    }
    
    
    //make the class nondeserializeable 
    private final void readObject(ObjectInputStream in)
        throws java.io.IOException {
        throw new java.io.IOException("Class cannot be deserialized");
    }
    
    private final void closeApplication(){
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {

            final String sqlStatement1 = "drop view if exists previousBillSummarySales";
            ps1 = connection.prepareStatement(sqlStatement1);
            ps1.executeUpdate();
            
            final String sqlStatement2 = "drop view if exists previousBillSummaryFreeIssue";
            ps2 = connection.prepareStatement(sqlStatement2);
            ps2.executeUpdate();
            
            homeFinal.loadTable();
            homeFinal.enable(true);
            this.dispose();
            
            ps1.close();
            ps2.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(PreviousBillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00072):Drop view operation failed", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final void billSummaryTable(String billNumber){
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        PreparedStatement ps7 = null;
        PreparedStatement ps8 = null;
        PreparedStatement ps9 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        
        float paymentFromStock = 0;
        float totalDiscount = 0;
        float totalPayment = 0;
        float finalTotalPayment = 0;
        
        try {
            
            final String sqlStatement1 = "drop view if exists previousBillSummarySales";
            ps1 = connection.prepareStatement(sqlStatement1);
            ps1.executeUpdate();
     
            final String sqlStatement2 = "drop view if exists previousBillSummaryFreeIssue";
            ps2 = connection.prepareStatement(sqlStatement2);
            ps2.executeUpdate();
            
            final String sqlStatement3 = "create view previousBillSummarySales as select brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price', sum(stock.quantity) as 'Quantity', (sum(stock.quantity) * stock.price) as 'Payment' from stock, brand where brand.bID = stock.bID and  billNumber = ? and type = ? group by stock.bID, stock.price order by brand.name, brand.weight";
            ps3 = connection.prepareStatement(sqlStatement3);
            ps3.setString(1, billNumber);
            ps3.setString(2, "Sold");
            
            ps3.executeUpdate();
            
            final String sqlStatement4 = "create view previousBillSummaryFreeIssue as select brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price', sum(stock.quantity) as 'Quantity' from stock, brand where stock.bID = brand.bID and billNumber = ?  and type = ? group by stock.bID, stock.price order by brand.name, brand.weight";
            ps4 = connection.prepareStatement(sqlStatement4);
            ps4.setString(1, billNumber);
            ps4.setString(2, "Free Issued");
            
            ps4.executeUpdate();
            
            
            final String sqlStatement5 = "select * from previousBillSummarySales";
            ps5 = connection.prepareStatement(sqlStatement5);
            rs5 = ps5.executeQuery();
            
            
            final String sqlStatement6 = "select * from previousBillSummaryFreeIssue";
            ps6 = connection.prepareStatement(sqlStatement6);
            rs6 = ps6.executeQuery();

            
            final String sqlStatement7 = "select sum(Payment) as 'Payment' from previousBillSummarySales";
            ps7 = connection.prepareStatement(sqlStatement7);
            rs7 = ps7.executeQuery();
            
            
            while(rs7.next()){
                paymentFromStock = rs7.getFloat("Payment");
            }
                        
            final String sqlStatement8 = "select sum(discount) as 'Total_Discount' from discount where billNumber=? group by billNumber";
            ps8 = connection.prepareStatement(sqlStatement8);
            ps8.setString(1, billNumber);
            rs8 = ps8.executeQuery();
            
           
            while(rs8.next()){
                totalDiscount = rs8.getFloat("Total_Discount");
            }
            
            final String sqlStatement9 = "select sum(payment) as 'Payment', sum(finalPayment) as 'Final_Payment' from payment where billNumber=? group by billNumber";
            ps9 = connection.prepareStatement(sqlStatement9);
            ps9.setString(1, billNumber);
            rs9 = ps9.executeQuery();
            
            
            while(rs9.next()){
                totalPayment = rs9.getFloat("Payment");
                finalTotalPayment = rs9.getFloat("Final_Payment");
            }
            
            System.out.println("Discount: " + totalDiscount);
            System.out.println("Payment from stock table: " + paymentFromStock);
            System.out.println("Payment from payment table: " + totalPayment);
            System.out.println("Final payment from payment table: " + finalTotalPayment);
        
            if(paymentFromStock != totalPayment || (finalTotalPayment != (totalPayment - totalDiscount))){
                final JOptionPane newOptionPane = new JOptionPane("Error(00070):Previos bill retrival process failed", JOptionPane.ERROR_MESSAGE);
                final JDialog newDialog = newOptionPane.createDialog("Warning");
                newDialog.setAlwaysOnTop(true);
                newDialog.setVisible(true);
                
                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();
                ps5.close();
                ps6.close();
                ps7.close();
                ps8.close();
                ps9.close();
                
                rs5.close();
                rs6.close();
                rs7.close();
                rs8.close();
                rs9.close();
                
                homeFinal.enable(true);
                this.dispose();
            }
            else{
                salesSummaryTable.setModel(DbUtils.resultSetToTableModel(rs5));
                //change row height
                salesSummaryTable.setRowHeight(30);

                freeIssuesTable.setModel(DbUtils.resultSetToTableModel(rs6));

                //change row height
                freeIssuesTable.setRowHeight(30);
               
                paymentTextFieldPreviousBillSummary.setText(String.valueOf(totalPayment));
                discountTextFieldPreviousBillSummary.setText(String.valueOf(totalDiscount));
                totalPaymentTextField.setText(String.valueOf(finalTotalPayment));
                
                 
                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();
                ps5.close();
                ps6.close();
                ps7.close();
                ps8.close();
                ps9.close();
                
                rs5.close();
                rs6.close();
                rs7.close();
                rs8.close();
                rs9.close();
            }
     
        } catch (SQLException ex) {
            Logger.getLogger(PreviousBillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        discardBillBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        salesSummaryTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        totalPaymentTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        paymentTextFieldPreviousBillSummary = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        discountTextFieldPreviousBillSummary = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        freeIssuesTable = new javax.swing.JTable();
        billNumberTextFieldPreviousBillSummaryFinal = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "BILL SUMMARY", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 768));

        discardBillBtn.setText("HOME");
        discardBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discardBillBtnActionPerformed(evt);
            }
        });

        jButton2.setText("PRINT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(244, 244, 244));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "SALES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        salesSummaryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(salesSummaryTable);

        jLabel1.setText("TOTAL PAYMENT");

        totalPaymentTextField.setEditable(false);

        jLabel2.setText("PAYMENT");

        paymentTextFieldPreviousBillSummary.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("-");

        jLabel4.setText("DISCOUNT");

        discountTextFieldPreviousBillSummary.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("=");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(paymentTextFieldPreviousBillSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(discountTextFieldPreviousBillSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(totalPaymentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(totalPaymentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(paymentTextFieldPreviousBillSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(discountTextFieldPreviousBillSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(244, 244, 244));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "FREE ISSUES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        freeIssuesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(freeIssuesTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jButton1.setText("GET BILL DETAILS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(billNumberTextFieldPreviousBillSummaryFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(discardBillBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(discardBillBtn)
                    .addComponent(jButton2)
                    .addComponent(billNumberTextFieldPreviousBillSummaryFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void discardBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discardBillBtnActionPerformed
    
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {

            final String sqlStatement1 = "drop view if exists previousBillSummarySales";
            ps1 = connection.prepareStatement(sqlStatement1);
            ps1.executeUpdate();
            
            final String sqlStatement2 = "drop view if exists previousBillSummaryFreeIssue";
            ps2 = connection.prepareStatement(sqlStatement2);
            ps2.executeUpdate();
            
            homeFinal.loadTable();
            homeFinal.enable(true);
            this.dispose();
            
            ps1.close();
            ps2.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(PreviousBillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00072):Drop view operation failed", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
       
    }//GEN-LAST:event_discardBillBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd<->HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("TimeStamp: " + timeStamp);
        final MessageFormat header = new MessageFormat("Bill Summary - " + timeStamp);
        final MessageFormat footer = new MessageFormat("Page");
        
        try{
            salesSummaryTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            freeIssuesTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        }
        catch(Exception e){
            e.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00054):Print operation failed", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(billNumberTextFieldPreviousBillSummaryFinal.getText().toString().isEmpty()){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid bill number", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else{
            final String billNumber = billNumberTextFieldPreviousBillSummaryFinal.getText().toString();
            billSummaryTable(billNumber);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PreviousBillSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PreviousBillSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PreviousBillSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PreviousBillSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PreviousBillSummaryFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField billNumberTextFieldPreviousBillSummaryFinal;
    private javax.swing.JButton discardBillBtn;
    private javax.swing.JTextField discountTextFieldPreviousBillSummary;
    private javax.swing.JTable freeIssuesTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField paymentTextFieldPreviousBillSummary;
    private javax.swing.JTable salesSummaryTable;
    private javax.swing.JTextField totalPaymentTextField;
    // End of variables declaration//GEN-END:variables
}
