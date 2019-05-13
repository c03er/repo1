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
public class StockSummaryFinal extends javax.swing.JFrame {

    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    /**
     * Creates new form StockSummary
     */
    public StockSummaryFinal(HomeFinal homeFinal) {
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
        
        loadStockTableStockSummary();
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
    
    private final void loadStockTableStockSummary(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "SELECT brand.name as 'Brand Name', brand.weight as 'Brand Weight', brand.price as 'Wholesale Price', brand.retailPrice as 'Retail Price', brand.quantityWarehouse as 'Warehouse Stock', brand.quantityVehicle as 'Vehicle Stock' from brand order by brand.name, brand.weight";
            ps1= connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            stockTableStockSummary.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            stockTableStockSummary.setRowHeight(30);

            TableColumnModel columnModel = stockTableStockSummary.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(StockSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00041):Stock table cannot be loaded", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
      
    }
    
    private final void loadStockSearchTableStockSummary(String category, String value){
 
        String sqlStatement1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
    
            if(category.equals("Brand Name")){
                sqlStatement1 = "SELECT brand.name as 'Brand Name', brand.weight as 'Brand Weight(g)', brand.price as 'Wholesale Price', brand.retailPrice as 'Retail Price', brand.quantityWarehouse as 'Warehouse Stock', brand.quantityVehicle as 'Vehicle Stock' from brand where brand.name LIKE '%"+value+"%' order by brand.name, brand.weight";
            }
            else if(category.equals("Brand Weight")){
                sqlStatement1 = "SELECT brand.name as 'Brand Name', brand.weight as 'Brand Weight(g)', brand.price as 'Wholesale Price', brand.retailPrice as 'Retail Price', brand.quantityWarehouse as 'Warehouse Stock', brand.quantityVehicle as 'Vehicle Stock' from brand where brand.weight LIKE '%"+value+"%' order by brand.name, brand.weight";
            }
            else if(category.equals("Wholesale Price")){
                sqlStatement1 = "SELECT brand.name as 'Brand Name', brand.weight as 'Brand Weight(g)', brand.price as 'Wholesale Price', brand.retailPrice as 'Retail Price', brand.quantityWarehouse as 'Warehouse Stock', brand.quantityVehicle as 'Vehicle Stock' from brand where brand.price LIKE '%"+value+"%' order by brand.name, brand.weight";
            }
            else if(category.equals("Retail Price")){
                sqlStatement1 = "SELECT brand.name as 'Brand Name', brand.weight as 'Brand Weight(g)', brand.price as 'Wholesale Price', brand.retailPrice as 'Retail Price', brand.quantityWarehouse as 'Warehouse Stock', brand.quantityVehicle as 'Vehicle Stock' from brand where brand.retailPrice LIKE '%"+value+"%' order by brand.name, brand.weight";
            }
            else if(category.equals("Warehouse Stock")){
                sqlStatement1 = "SELECT brand.name as 'Brand Name', brand.weight as 'Brand Weight(g)', brand.price as 'Wholesale Price', brand.retailPrice as 'Retail Price',  brand.quantityWarehouse as 'Warehouse Stock', brand.quantityVehicle as 'Vehicle Stock' from brand where brand.quantityWarehouse LIKE '%"+value+"%' order by brand.name, brand.weight";
            }
            else if(category.equals("Vehicle Stock")){
                sqlStatement1 = "SELECT brand.name as 'Brand Name', brand.weight as 'Brand Weight(g)', brand.price as 'Wholesale Price', brand.retailPrice as 'Retail Price', brand.quantityWarehouse as 'Warehouse Stock', brand.quantityVehicle as 'Vehicle Stock' from brand where brand.quantityVehicle LIKE '%"+value+"%' order by brand.name, brand.weight";
            }
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            stockTableStockSummary.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            stockTableStockSummary.setRowHeight(30);
            
            TableColumnModel columnModel = stockTableStockSummary.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
            
     
        } catch (SQLException ex) {
            Logger.getLogger(StockSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00042):Stock table cannot be loaded for searching", JOptionPane.ERROR_MESSAGE);
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
        stockTableStockSummary = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        categoryComboBoxStockSummary = new javax.swing.JComboBox<>();
        StockSummarySearchTextField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "STOCK SUMMARY", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 768));

        stockTableStockSummary.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(stockTableStockSummary);

        jLabel3.setText("CATEGORY");

        categoryComboBoxStockSummary.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brand Name", "Brand Weight", "Wholesale Price", "Retail Price", "Warehouse Stock", "Vehicle Stock" }));

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

        jButton2.setText("CLEAR WAREHOUSE STOCK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("CLEAR VEHICLE STOCK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(categoryComboBoxStockSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(StockSummarySearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4)))
                        .addGap(0, 234, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(categoryComboBoxStockSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(StockSummarySearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton3)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        final String value = StockSummarySearchTextField.getText().toString();
        final String selectedCategory = categoryComboBoxStockSummary.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadStockTableStockSummary();
        }
        else{
            loadStockSearchTableStockSummary(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        homeFinal.loadTable();
        homeFinal.enable(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?\n Warehouse stock for each brand will be reset to 0");
        
        if(doProceed == 0){
            PreparedStatement ps1 = null;
            
            final String sqlStatement1 = "update brand set quantityWarehouse=? where bID is not null";
            
            try {
                ps1 = connection.prepareStatement(sqlStatement1);
                ps1.setInt(1, 0);
                
                ps1.executeUpdate();
                loadStockTableStockSummary();
                JOptionPane.showMessageDialog(null, "Warehouse stock cleared");
                
            } catch (SQLException ex) {
                Logger.getLogger(StockSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
                final JOptionPane newOptionPane = new JOptionPane("Error(00071):Unable to clear warehouse stock", JOptionPane.ERROR_MESSAGE);
                final JDialog newDialog = newOptionPane.createDialog("Warning");
                newDialog.setAlwaysOnTop(true);
                newDialog.setVisible(true);
                
            }
            
        }
         
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?\n Vehicle stock for each brand will be reset to 0");
        
        if(doProceed == 0){
            PreparedStatement ps1 = null;
            
            final String sqlStatement1 = "update brand set quantityVehicle=? where bID is not null";
            
            try {
                ps1 = connection.prepareStatement(sqlStatement1);
                ps1.setInt(1, 0);
                
                ps1.executeUpdate();
                loadStockTableStockSummary();
                JOptionPane.showMessageDialog(null, "Vehicle stock cleared");
                
            } catch (SQLException ex) {
                Logger.getLogger(StockSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
                final JOptionPane newOptionPane = new JOptionPane("Error(00072):Unable to clear vehicle stock", JOptionPane.ERROR_MESSAGE);
                final JDialog newDialog = newOptionPane.createDialog("Warning");
                newDialog.setAlwaysOnTop(true);
                newDialog.setVisible(true);
                
            }
            
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(StockSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StockSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StockSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StockSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StockSummaryFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField StockSummarySearchTextField;
    private javax.swing.JComboBox<String> categoryComboBoxStockSummary;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable stockTableStockSummary;
    // End of variables declaration//GEN-END:variables
}
