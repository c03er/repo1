/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Brand_Management;

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
public final class RemoveBrandFinal extends javax.swing.JFrame {

    private Dimension dimension = null;
    private Connection connection = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    
    /**
     * Creates new form RemoveBrand
     */
    public RemoveBrandFinal(HomeFinal homeFinal) {
        
        initComponents();
        this.homeFinal = homeFinal;
        this.homeFinal2 = homeFinal;
        
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
        
        try {
            //obtain database connection
            connection = (Connection) DBConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
           closeApplication();
        }
        });
        
       
        fillBrandComboBoxRemoveBrand();
        loadBrandTableRemoveBrand();
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
    
    private final void fillBrandComboBoxRemoveBrand(){
        
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select * from brand";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery(sqlStatement1);
            
            //remove all available items 
            selectBrandComboBoxRemoveBrand.removeAllItems();
            selectBrandComboBoxRemoveBrand.addItem("Select brand");
            
            while(rs1.next()){
                
                selectBrandComboBoxRemoveBrand.addItem(rs1.getInt("bID") + " - " + rs1.getString("name") );
            }
            
            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("Error(00006):All brands combo box cannot be loaded", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
       
    }
    
    private final void loadBrandTableRemoveBrand(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale price', brand.retailPrice as 'Retail Price' from brand order by brand.name, brand.weight, brand.price";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            brandTableRemoveBrand.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            brandTableRemoveBrand.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = brandTableRemoveBrand.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(10);
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00007):Cannot load all brands table", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final int getBrandIDRemoveBrand(String brandName){
       
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        int brandID = 0;
        
        final String sqlStatement3 = "select bID from brand where name = ?";
        
        try {
            ps1 = connection.prepareStatement(sqlStatement3);
            ps1.setString(1, brandName);
            rs1 = ps1.executeQuery();
            
            while(rs1.next()){
                
                brandID = (int) rs1.getInt("bID");
            }
            
            ps1.close();
            rs1.close();
            
            return brandID;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00008):Brand id retrieval operation failed", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            return brandID;
        }
       
    }
    
    private final void loadBrandsSearchTableRemoveBrand(String category, String value){
 
        String sqlStatement1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            
            if(category.equals("Brand ID")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale price', brand.retailPrice as 'Retail Price' FROM brand WHERE brand.bID LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price";
            }
            else if(category.equals("Brand Name")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale price', brand.retailPrice as 'Retail Price' FROM brand WHERE brand.name LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price";
            }
            else if(category.equals("Brand Weight")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale price', brand.retailPrice as 'Retail Price' FROM brand WHERE brand.weight LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price";
            }
            else if(category.equals("Wholesale Price")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale price', brand.retailPrice as 'Retail Price' FROM brand WHERE brand.price LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price";
            }
            else if(category.equals("Retail Price")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale price', brand.retailPrice as 'Retail Price' FROM brand WHERE brand.retailPrice LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price";
            }
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            brandTableRemoveBrand.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            brandTableRemoveBrand.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = brandTableRemoveBrand.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(10);
            
            ps1.close();
            rs1.close();
     
        } catch (SQLException ex) {
            Logger.getLogger(RemoveBrandFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00009):Cannot load all brands table for searching", JOptionPane.ERROR_MESSAGE);
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

        removeBrandPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        selectBrandComboBoxRemoveBrand = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        brandTableRemoveBrand = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        categoryComboBoxRemoveBrand = new javax.swing.JComboBox<>();
        removeBrandSearchTextField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        removeBrandPanel.setBackground(new java.awt.Color(244, 244, 244));
        removeBrandPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "REMOVE BRAND", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        removeBrandPanel.setPreferredSize(new java.awt.Dimension(1360, 768));

        jLabel2.setText("SELECT BRAND");

        selectBrandComboBoxRemoveBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("EXIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("REMOVE BRAND");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        brandTableRemoveBrand.setModel(new javax.swing.table.DefaultTableModel(
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
        brandTableRemoveBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brandTableRemoveBrandMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                brandTableRemoveBrandMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                brandTableRemoveBrandMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(brandTableRemoveBrand);

        jLabel3.setText("CATEGORY");

        categoryComboBoxRemoveBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brand ID", "Brand Name", "Brand Weight", "Wholesale Price", "Retail Price" }));

        jButton3.setText("SEARCH");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout removeBrandPanelLayout = new javax.swing.GroupLayout(removeBrandPanel);
        removeBrandPanel.setLayout(removeBrandPanelLayout);
        removeBrandPanelLayout.setHorizontalGroup(
            removeBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeBrandPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(removeBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(removeBrandPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selectBrandComboBoxRemoveBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(removeBrandPanelLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(removeBrandPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(categoryComboBoxRemoveBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeBrandSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        removeBrandPanelLayout.setVerticalGroup(
            removeBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(removeBrandPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(removeBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectBrandComboBoxRemoveBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(removeBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(removeBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categoryComboBoxRemoveBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeBrandSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(removeBrandPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(removeBrandPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        homeFinal.loadTable();
        homeFinal.enable(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void brandTableRemoveBrandMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableRemoveBrandMouseClicked
        final int selectedRow = brandTableRemoveBrand.getSelectedRow();
        
        final String brandID =  brandTableRemoveBrand.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) brandTableRemoveBrand.getValueAt(selectedRow, 1).toString();
        
        final String fullBrandName = brandID + " - " + brandName;
       
        selectBrandComboBoxRemoveBrand.setSelectedItem(fullBrandName);
    }//GEN-LAST:event_brandTableRemoveBrandMouseClicked

    private void brandTableRemoveBrandMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableRemoveBrandMouseEntered
        
    }//GEN-LAST:event_brandTableRemoveBrandMouseEntered

    private void brandTableRemoveBrandMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableRemoveBrandMousePressed
        final int selectedRow = brandTableRemoveBrand.getSelectedRow();
        
        final String brandID =  brandTableRemoveBrand.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) brandTableRemoveBrand.getValueAt(selectedRow, 1).toString();
        
        final String fullBrandName = brandID + " - " + brandName;
       
        selectBrandComboBoxRemoveBrand.setSelectedItem(fullBrandName);
        
        
    }//GEN-LAST:event_brandTableRemoveBrandMousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        String selectedBrand = null;
        String[] split = null;
        
        //get the values in text fields
        final String getBrandText = selectBrandComboBoxRemoveBrand.getSelectedItem().toString();
        
        if(getBrandText.equals("Select brand")){
            selectedBrand = "Select brand";
        }
        else{
            split = getBrandText.split(" - ");
            selectedBrand = split[0];
        }

        //validate inputs
        if(selectedBrand.equals("Select brand")){
            final JOptionPane newOptionPane = new JOptionPane("Please select a brand to remove", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            selectBrandComboBoxRemoveBrand.requestFocus();
        }
        else{

            final int proceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(proceed == 0){
                
                final int brandID = Integer.parseInt(selectedBrand);

                //sql query
                final String sqlStatement1 = "DELETE FROM brand WHERE bID = ?";
                
                PreparedStatement ps1 = null;
                
                try {
                    ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setInt(1, brandID);


                    ps1.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Deletion successful");

                    ps1.close();
                   
                    fillBrandComboBoxRemoveBrand();
                    loadBrandTableRemoveBrand();

                } catch (SQLException ex) {
                    Logger.getLogger(RemoveBrandFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00010):Brand removal operation failed", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
              
            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        final String value = removeBrandSearchTextField.getText().toString();
        final String selectedCategory = categoryComboBoxRemoveBrand.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadBrandTableRemoveBrand();
        }
        else{
            loadBrandsSearchTableRemoveBrand(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(RemoveBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RemoveBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RemoveBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RemoveBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RemoveBrandFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable brandTableRemoveBrand;
    private javax.swing.JComboBox<String> categoryComboBoxRemoveBrand;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel removeBrandPanel;
    private javax.swing.JTextField removeBrandSearchTextField;
    private javax.swing.JComboBox<String> selectBrandComboBoxRemoveBrand;
    // End of variables declaration//GEN-END:variables
}
