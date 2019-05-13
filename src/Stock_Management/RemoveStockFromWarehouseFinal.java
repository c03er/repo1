/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stock_Management;


import Database_Management.DBConnection;
import Methods.Methods;
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
public final class RemoveStockFromWarehouseFinal extends javax.swing.JFrame {
    
    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    /**
     * Creates new form DailySales
     */
    public RemoveStockFromWarehouseFinal(HomeFinal homeFinal) {
        initComponents();
        
        this.homeFinal = homeFinal;
        this.homeFinal2 = homeFinal;
        
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
        
        try {
            //create objects
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
        
        loadStockTableRemoveStockFromWarehouseFinal();
        fillBrandComboBoxRemoveStockFromWarehouseFinal();
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
    
    private final void fillBrandComboBoxRemoveStockFromWarehouseFinal(){
        
        PreparedStatement ps1 = null;
        ResultSet rs1 =  null;
        
        try {
            final String sqlStatement1 = "SELECT * FROM brand";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery(sqlStatement1);
            
            //remove all available items 
            selectBrandComboBoxRemoveStockFromWarehouse.removeAllItems();
            selectBrandComboBoxRemoveStockFromWarehouse.addItem("Select brand");
            
            while(rs1.next()){
                
                selectBrandComboBoxRemoveStockFromWarehouse.addItem(rs1.getInt("bID") + " - " + rs1.getString("name") );
            }
            
            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00027):Cannot load all brands combo box", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    
    }
    
    private final void loadStockTableRemoveStockFromWarehouseFinal(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement2 = "SELECT brand.bID as 'Brand ID', brand.name as 'Brand Name', brand.weight as 'Brand Weight', brand.quantityWarehouse as 'Warehouse Stock', brand.quantityVehicle as 'Vehicle Stock', unitsPerBox as 'Units Per Box' from brand order by brand.name, brand.weight";
            ps1 = connection.prepareStatement(sqlStatement2);
            rs1 = ps1.executeQuery();
            
            stockTableRemoveStockFromWarehouse.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            stockTableRemoveStockFromWarehouse.setRowHeight(30);
            
            TableColumnModel columnModel = stockTableRemoveStockFromWarehouse.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
          
            
            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(RemoveStockFromWarehouseFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00028):Stock table cannot be loaded", JOptionPane.ERROR_MESSAGE);
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
        jLabel4 = new javax.swing.JLabel();
        selectBrandComboBoxRemoveStockFromWarehouse = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        quantityTextFieldRemoveStockFromWarehouse = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        stockTableRemoveStockFromWarehouse = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "REMOVE STOCK FROM WAREHOUSE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 768));

        jLabel4.setText("SELECT BRAND");

        selectBrandComboBoxRemoveStockFromWarehouse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select brand" }));

        jLabel2.setText("QUANTITY");

        jButton1.setText("REMOVE STOCK FROM WAREHOUSE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("EXIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        stockTableRemoveStockFromWarehouse.setModel(new javax.swing.table.DefaultTableModel(
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
        stockTableRemoveStockFromWarehouse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockTableRemoveStockFromWarehouseMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                stockTableRemoveStockFromWarehouseMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(stockTableRemoveStockFromWarehouse);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(40, 40, 40)
                                    .addComponent(quantityTextFieldRemoveStockFromWarehouse))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(selectBrandComboBoxRemoveStockFromWarehouse, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(selectBrandComboBoxRemoveStockFromWarehouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(quantityTextFieldRemoveStockFromWarehouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        homeFinal.loadTable();
        homeFinal.enable(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void stockTableRemoveStockFromWarehouseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockTableRemoveStockFromWarehouseMouseClicked
        final int selectedRow = stockTableRemoveStockFromWarehouse.getSelectedRow();
        
        final String brandID =  stockTableRemoveStockFromWarehouse.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) stockTableRemoveStockFromWarehouse.getValueAt(selectedRow, 1).toString();
        
        final String fullBrandName = brandID + " - " + brandName;
       
        selectBrandComboBoxRemoveStockFromWarehouse.setSelectedItem(fullBrandName);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_stockTableRemoveStockFromWarehouseMouseClicked

    private void stockTableRemoveStockFromWarehouseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockTableRemoveStockFromWarehouseMousePressed
        final int selectedRow = stockTableRemoveStockFromWarehouse.getSelectedRow();
        
        final String brandID =  stockTableRemoveStockFromWarehouse.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) stockTableRemoveStockFromWarehouse.getValueAt(selectedRow, 1).toString();
        
        final String fullBrandName = brandID + " - " + brandName;
       
        selectBrandComboBoxRemoveStockFromWarehouse.setSelectedItem(fullBrandName);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_stockTableRemoveStockFromWarehouseMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final String getBrandText = selectBrandComboBoxRemoveStockFromWarehouse.getSelectedItem().toString();
        String selectedBrand = null;
        
        if(getBrandText.equals("Select brand")){
            selectedBrand = "Select brand";
        }
        else{
            final String[] split = getBrandText.split(" - ");
            selectedBrand = split[0];
        }
        
        
        if(selectedBrand.equals("Select brand")){
            final JOptionPane newOptionPane = new JOptionPane("Please select a brand", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            selectBrandComboBoxRemoveStockFromWarehouse.requestFocus();
        }
        else if(!Methods.isInt(quantityTextFieldRemoveStockFromWarehouse.getText().toString())){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for quantity", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            quantityTextFieldRemoveStockFromWarehouse.requestFocus();
        }
        else{

            final int proceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(proceed == 0){
                
                final int brandID = Integer.parseInt(selectedBrand);

                final int quantityInt = Integer.parseInt(quantityTextFieldRemoveStockFromWarehouse.getText().toString());
                
                int quantityFromDatabaseWarehouse = 0;
                
                final String sqlStatement1 = "SELECT * FROM brand WHERE bID=?";
                
                PreparedStatement ps1 = null;
                PreparedStatement ps2 = null;
                PreparedStatement ps3 = null;
                ResultSet rs1 = null;
                
                try {
                    ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setInt(1, brandID);
                    rs1 = ps1.executeQuery();
                    
                    while(rs1.next()){
                        
                        quantityFromDatabaseWarehouse = rs1.getInt("quantityWarehouse");
                        
                    }
                    ps1.close();
                    rs1.close();
                    
                    if(quantityInt > quantityFromDatabaseWarehouse){
                        final JOptionPane newOptionPane = new JOptionPane("Not enough stock. Please check again", JOptionPane.ERROR_MESSAGE);
                        final JDialog newDialog = newOptionPane.createDialog("Warning");
                        newDialog.setAlwaysOnTop(true);
                        newDialog.setVisible(true);
                    }
                    else{
                        quantityFromDatabaseWarehouse -= quantityInt;
                    
                        final String sqlStatement2 = "UPDATE brand SET quantityWarehouse=? WHERE bID=?";
                        ps2 = connection.prepareStatement(sqlStatement2);
                        ps2.setInt(1, quantityFromDatabaseWarehouse);
                        ps2.setInt(2, brandID);

                        ps2.executeUpdate();
                        
                        ps2.close();

                        final String sqlStatement3 = "INSERT INTO stock(quantity, bID, type) VALUES(?,?,?)";
                        ps3 = connection.prepareStatement(sqlStatement3);

                        ps3.setInt(1, quantityInt);
                        ps3.setInt(2, brandID);
                        ps3.setString(3, "Removed from warehouse");
                        
                        ps3.executeUpdate();
                        
                        ps3.close();

                        loadStockTableRemoveStockFromWarehouseFinal();
                        quantityTextFieldRemoveStockFromWarehouse.setText(null);
                        selectBrandComboBoxRemoveStockFromWarehouse.setSelectedItem("Select brand");
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(RemoveStockFromWarehouseFinal.class.getName()).log(Level.SEVERE, null, ex);
                        final JOptionPane newOptionPane = new JOptionPane("Error(00029):Remove stock from warehouse operation failed", JOptionPane.ERROR_MESSAGE);
                        final JDialog newDialog = newOptionPane.createDialog("Warning");
                        newDialog.setAlwaysOnTop(true);
                        newDialog.setVisible(true);
                }
                
            }

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
            java.util.logging.Logger.getLogger(RemoveStockFromWarehouseFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RemoveStockFromWarehouseFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RemoveStockFromWarehouseFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RemoveStockFromWarehouseFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new RemoveStockFromWarehouseFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField quantityTextFieldRemoveStockFromWarehouse;
    private javax.swing.JComboBox<String> selectBrandComboBoxRemoveStockFromWarehouse;
    private javax.swing.JTable stockTableRemoveStockFromWarehouse;
    // End of variables declaration//GEN-END:variables
}
