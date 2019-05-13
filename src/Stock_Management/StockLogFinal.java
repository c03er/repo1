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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import mb.HomeFinal;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author pasindu
 */
public final class StockLogFinal extends javax.swing.JFrame {

    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    /**
     * Creates new form StockSummary
     */
    public StockLogFinal(HomeFinal homeFinal) {
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
        
        loadStockTableStockLog();
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
        
        homeFinal.loadTable();
        homeFinal.enable(true);
        this.dispose();
    }
    
    private final void loadStockTableStockLog(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "SELECT Date as 'Date', brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price',  stock.quantity as 'Quantity', type as 'Type', billNumber as 'Bill Number' from brand, stock WHERE brand.bid=stock.bid order by Date, brand.name, brand.weight, stock.quantity";
            ps1= connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            stockTableStockLog.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            stockTableStockLog.setRowHeight(30);

            TableColumnModel columnModel = stockTableStockLog.getColumnModel();
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(StockLogFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00039):Stock table cannot be loaded", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        
    }
    
    private final void loadStockSearchTableStockLog(String category, String value){
 
        String sqlStatement1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
    
            if(category.equals("Date")){
                sqlStatement1 = "SELECT Date as 'Date', brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price', stock.quantity as 'Quantity', type as 'Type', billNumber as 'Bill Number' from brand, stock WHERE brand.bid=stock.bid and stock.Date like '%"+value+"%' order by Date, brand.name, brand.weight, stock.quantity";
            }
            else if(category.equals("Brand Name")){
                sqlStatement1 = "SELECT Date as 'Date', brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price', stock.quantity as 'Quantity', type as 'Type', billNumber as 'Bill Number' from brand, stock WHERE brand.bid=stock.bid and brand.name like '%"+value+"%' order by Date, brand.name, brand.weight, stock.quantity";
            }
            else if(category.equals("Brand Weight")){
                sqlStatement1 = "SELECT Date as 'Date', brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price', stock.quantity as 'Quantity', type as 'Type', billNumber as 'Bill Number' from brand, stock WHERE brand.bid=stock.bid and brand.weight like '%"+value+"%' order by Date, brand.name, brand.weight, stock.quantity";
            }
            else if(category.equals("Quantity")){
                sqlStatement1 = "SELECT Date as 'Date', brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price', stock.quantity as 'Quantity', type as 'Type', billNumber as 'Bill Number' from brand, stock WHERE brand.bid=stock.bid and stock.quantity like '%"+value+"%' order by Date, brand.name, brand.weight, stock.quantity";
            }
            else if(category.equals("Type")){
                sqlStatement1 = "SELECT Date as 'Date', brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price', stock.quantity as 'Quantity', type as 'Type', billNumber as 'Bill Number' from brand, stock WHERE brand.bid=stock.bid and stock.type like '%"+value+"%' order by Date, brand.name, brand.weight, stock.quantity";
            }
            else if(category.equals("Bill Number")){
                sqlStatement1 = "SELECT Date as 'Date', brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price', stock.quantity as 'Quantity', type as 'Type', billNumber as 'Bill Number' from brand, stock WHERE brand.bid=stock.bid and stock.billNumber like '%"+value+"%' order by Date, brand.name, brand.weight, stock.quantity";
            }
            else if(category.equals("Price")){
                sqlStatement1 = "SELECT Date as 'Date', brand.name as 'Brand Name', brand.weight as 'Brand Weight', stock.price as 'Price', stock.quantity as 'Quantity', type as 'Type', billNumber as 'Bill Number' from brand, stock WHERE brand.bid=stock.bid and stock.price like '%"+value+"%' order by Date, brand.name, brand.weight, stock.quantity";
            }
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            stockTableStockLog.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            stockTableStockLog.setRowHeight(30);
            
            TableColumnModel columnModel = stockTableStockLog.getColumnModel();
            
            ps1.close();
            rs1.close();
            
     
        } catch (SQLException ex) {
            Logger.getLogger(StockLogFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00040):Stock table cannot be loaded for searching", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        stockTableStockLog = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        categoryComboBoxStockLog = new javax.swing.JComboBox<>();
        StockLogSearchTextField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "STOCK LOG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 768));

        stockTableStockLog.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(stockTableStockLog);

        jLabel3.setText("CATEGORY");

        categoryComboBoxStockLog.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date", "Brand Name", "Brand Weight", "Price", "Quantity", "Type", "Bill Number", " " }));

        jButton3.setText("SEARCH");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("EXIT");
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(categoryComboBoxStockLog, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(StockLogSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(0, 199, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(categoryComboBoxStockLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(StockLogSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        final String value = StockLogSearchTextField.getText().toString();
        final String selectedCategory = categoryComboBoxStockLog.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadStockTableStockLog();
        }
        else{
            loadStockSearchTableStockLog(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        homeFinal.loadTable();
        homeFinal.enable(true);
        this.dispose();
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
            java.util.logging.Logger.getLogger(StockLogFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StockLogFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StockLogFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StockLogFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StockLogFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField StockLogSearchTextField;
    private javax.swing.JComboBox<String> categoryComboBoxStockLog;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable stockTableStockLog;
    // End of variables declaration//GEN-END:variables
}
