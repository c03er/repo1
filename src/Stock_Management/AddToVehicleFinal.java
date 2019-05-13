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
public final class AddToVehicleFinal extends javax.swing.JFrame {
    
    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    /**
     * Creates new form DailySales
     */
    public AddToVehicleFinal(HomeFinal homeFinal) {
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
        
        loadStockTableAddToVehicleFinal();
        fillBrandComboBoxAddToVehicleFinal();
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
        
        try {
            final String sqlStatement1 = "drop table if exists addVehicle";
            ps1 = connection.prepareStatement(sqlStatement1);
            ps1.executeUpdate();
            
            ps1.close();
            
            homeFinal.loadTable();
            homeFinal.enable(true);
            this.dispose();
            
        } catch (SQLException ex) {
            Logger.getLogger(AddToVehicleFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00030):Drop table \"addVehicle\" operation failed", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
       
    }
    
    private final void fillBrandComboBoxAddToVehicleFinal(){
        
        PreparedStatement ps1 = null;
        ResultSet rs1 =  null;
        
        try {
            final String sqlStatement1 = "SELECT * FROM brand";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery(sqlStatement1);
            
            //remove all available items 
            selectBrandComboBoxAddToVehicle.removeAllItems();
            selectBrandComboBoxAddToVehicle.addItem("Select brand");
            
            while(rs1.next()){
                
                selectBrandComboBoxAddToVehicle.addItem(rs1.getInt("bID") + " - " + rs1.getString("name") );
            }
            
            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00031):All brands combo box cannot be loaded", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        
    }
    
    private final void loadStockTableAddToVehicleFinal(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "SELECT brand.bID as 'Brand ID', brand.name as 'Brand Name', brand.weight as 'Brand Weight', brand.price as 'Wholesale Price', brand.quantityWarehouse as 'Warehouse Stock', brand.quantityVehicle as 'Vehicle Stock', unitsPerBox as 'Units Per Box' from brand order by brand.name, brand.weight";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            stockTableAddToVehicle.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            stockTableAddToVehicle.setRowHeight(30);
            
            TableColumnModel columnModel = stockTableAddToVehicle.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);

            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(AddToVehicleFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00032): Stock table cannot be loaded", JOptionPane.ERROR_MESSAGE);
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
        selectBrandComboBoxAddToVehicle = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        quantityTextFieldAddToVehicle = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        stockTableAddToVehicle = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "ADD STOCK TO VEHICLE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 768));

        jLabel4.setText("SELECT BRAND");

        selectBrandComboBoxAddToVehicle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select brand" }));

        jLabel2.setText("QUANTITY");

        jButton1.setText("ADD MORE STOCK TO VEHICLE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("GET VEHICLE STOCK SUMMARY");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("EXIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        stockTableAddToVehicle.setModel(new javax.swing.table.DefaultTableModel(
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
        stockTableAddToVehicle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockTableAddToVehicleMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                stockTableAddToVehicleMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(stockTableAddToVehicle);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(quantityTextFieldAddToVehicle))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(selectBrandComboBoxAddToVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(selectBrandComboBoxAddToVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityTextFieldAddToVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        PreparedStatement ps1 = null;
        
        try {
            final String sqlStatement1 = "drop table if exists addVehicle";
            ps1 = connection.prepareStatement(sqlStatement1);
            ps1.executeUpdate();
            
            ps1.close();
            
            homeFinal.loadTable();
            homeFinal.enable(true);
            this.dispose();
            
        } catch (SQLException ex) {
            Logger.getLogger(AddToVehicleFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00033):Drop table \"addVehicle\" operation failed", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void stockTableAddToVehicleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockTableAddToVehicleMouseClicked
        final int selectedRow = stockTableAddToVehicle.getSelectedRow();
        
        final String brandID =  stockTableAddToVehicle.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) stockTableAddToVehicle.getValueAt(selectedRow, 1).toString();
        
        final String fullBrandName = brandID + " - " + brandName;
       
        selectBrandComboBoxAddToVehicle.setSelectedItem(fullBrandName);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_stockTableAddToVehicleMouseClicked

    private void stockTableAddToVehicleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockTableAddToVehicleMousePressed
        final int selectedRow = stockTableAddToVehicle.getSelectedRow();
        
        final String brandID =  stockTableAddToVehicle.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) stockTableAddToVehicle.getValueAt(selectedRow, 1).toString();
        
        final String fullBrandName = brandID + " - " + brandName;
       
        selectBrandComboBoxAddToVehicle.setSelectedItem(fullBrandName);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_stockTableAddToVehicleMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final String getBrandText = selectBrandComboBoxAddToVehicle.getSelectedItem().toString();
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
            selectBrandComboBoxAddToVehicle.requestFocus();
        }
        else if(!Methods.isInt(quantityTextFieldAddToVehicle.getText().toString())){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for quantity", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            quantityTextFieldAddToVehicle.requestFocus();
        }
        else{

            final int proceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(proceed == 0){

                final int brandID = Integer.parseInt(selectedBrand);

                final int quantityInt = Integer.parseInt(quantityTextFieldAddToVehicle.getText().toString());
                
                int quantityFromDatabaseWarehouse = 0;
                int quantityFromDatabaseVehicle = 0;
                
                final String sqlStatement1 = "SELECT * FROM brand WHERE bID=?";
                
                PreparedStatement ps1 = null;
                PreparedStatement ps2 = null;
                PreparedStatement ps3 = null;
                PreparedStatement ps4 = null;
                PreparedStatement ps5 = null;
                PreparedStatement ps6 = null;
                PreparedStatement ps7 = null;
                ResultSet rs1 = null;
                ResultSet rs6 = null;
                
                try {
                    ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setInt(1, brandID);
                    rs1 = ps1.executeQuery();
                    
                    while(rs1.next()){
                        
                        quantityFromDatabaseWarehouse = rs1.getInt("quantityWarehouse");
                        quantityFromDatabaseVehicle = rs1.getInt("quantityVehicle");
                        
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
                        ps3.setString(3, "Added To Vehicle");
                        
                        ps3.executeUpdate();
                        
                        ps3.close();

                        final String sqlStatement4 = "INSERT INTO addVehicle(bID, quantity) VALUES(?,?)";
                        ps4 = connection.prepareStatement(sqlStatement4);

                        ps4.setInt(1, brandID);
                        ps4.setInt(2, quantityInt);
                        ps4.executeUpdate();
                        
                        ps4.close();
                        
                        final String sqlStatement5 = "UPDATE brand SET quantityVehicle=? WHERE bID=?";
                        quantityFromDatabaseVehicle += quantityInt;
                        
                        ps5 = connection.prepareStatement(sqlStatement5);
                        ps5.setInt(1, quantityFromDatabaseVehicle);
                        ps5.setInt(2, brandID);
                        
                        ps5.executeUpdate();
                        
                        ps5.close();
                        
                        boolean isDailySalesNotOpen = (homeFinal.getDailySalesBtn().isEnabled());
                        
                        if(!isDailySalesNotOpen){
                            
                            int warehouseQuantityBrandCopy = 0;
                            int vehicleQuantityBrandCopy = 0;
                            
                            final String sqlStatement6 = "select * from brandCopy where bID=?";
                            ps6 = connection.prepareStatement(sqlStatement6);
                            ps6.setInt(1, brandID);
                            rs6 = ps6.executeQuery();
                            
                            while(rs6.next()){
                                
                                warehouseQuantityBrandCopy = rs6.getInt("quantityWarehouse");
                                vehicleQuantityBrandCopy = rs6.getInt("quantityVehicle");
                            }
                            
                            
                            warehouseQuantityBrandCopy -= quantityInt;
                            vehicleQuantityBrandCopy += quantityInt;
                            
                            final String sqlStatement7 = "update brandCopy set quantityWarehouse=?, quantityVehicle=? where bID=?";
                            ps7 = connection.prepareStatement(sqlStatement7);
                            ps7.setInt(1, warehouseQuantityBrandCopy);
                            ps7.setInt(2, vehicleQuantityBrandCopy);
                            ps7.setInt(3, brandID);
                            
                            ps7.executeUpdate();
                            
                            ps6.close();
                            ps7.close();
                            rs6.close();
                         
                        }


                        loadStockTableAddToVehicleFinal();
                        quantityTextFieldAddToVehicle.setText(null);
                        selectBrandComboBoxAddToVehicle.setSelectedItem("Select brand");
                    }
                    
                    
                } catch (SQLException ex) {
                    
                        Logger.getLogger(AddToVehicleFinal.class.getName()).log(Level.SEVERE, null, ex);
                        final JOptionPane newOptionPane = new JOptionPane("Error(00034):Add to vehicle opeartion failed", JOptionPane.ERROR_MESSAGE);
                        final JDialog newDialog = newOptionPane.createDialog("Warning");
                        newDialog.setAlwaysOnTop(true);
                        newDialog.setVisible(true);
                }
                
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        final VehicleSummaryFinal vehicleSummaryFinal = new VehicleSummaryFinal(homeFinal);
        vehicleSummaryFinal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(AddToVehicleFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddToVehicleFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddToVehicleFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddToVehicleFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddToVehicleFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField quantityTextFieldAddToVehicle;
    private javax.swing.JComboBox<String> selectBrandComboBoxAddToVehicle;
    private javax.swing.JTable stockTableAddToVehicle;
    // End of variables declaration//GEN-END:variables
}
