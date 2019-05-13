/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Brand_Management;

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
public final class UpdateBrandFinal extends javax.swing.JFrame {

    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    
    /**
     * Creates new form UpdateBrand
     */
    public UpdateBrandFinal(HomeFinal homeFinal) {
        initComponents();
        this.homeFinal = homeFinal;
        this.homeFinal2 = homeFinal;
        
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
        
        try {
            //create objects
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
        
        fillBrandComboBoxUpdateBrand();
        loadBrandTableUpdateBrand();
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
    
    private final void fillBrandComboBoxUpdateBrand(){
        
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "SELECT * FROM brand";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery(sqlStatement1);
            
            //remove all available items 
            selectBrandComboBoxUpdateBrand.removeAllItems();
            selectBrandComboBoxUpdateBrand.addItem("Select brand");
            
            while(rs1.next()){
                
                selectBrandComboBoxUpdateBrand.addItem(rs1.getInt("bID") + " - " + rs1.getString("name") );
            }
            
            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00011):Cannot load all brands combo box", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    
    private final void loadBrandTableUpdateBrand(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "SELECT bID AS 'Brand ID', brand.name AS 'Brand Name', weight AS 'Weight(g)', price AS 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand order by brand.name, brand.weight";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            brandTableUpdateBrand.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            brandTableUpdateBrand.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = brandTableUpdateBrand.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00012):Cannot load all brands table", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final int getBrandIDUpdateBrand(String brandName){
        
        
        int brandID = 0;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        final String sqlStatement1 = "SELECT bID FROM brand WHERE name = ?";
        
        try {
            ps1 = connection.prepareStatement(sqlStatement1);
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
            final JOptionPane newOptionPane = new JOptionPane("Error(00013):Brand id retrieval operation failed", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            return brandID;
        }
    
    }
    
    private final void loadBrandsSearchTableUpdateBrand(String category, String value){
         String sqlStatement1 = null;
         PreparedStatement ps1 = null;
         ResultSet rs1 = null;

        try {
     
            if(category.equals("Brand ID")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.bID LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            else if(category.equals("Brand Name")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.name LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            else if(category.equals("Brand Weight")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.weight LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            else if(category.equals("Wholesale Price")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.price LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            else if(category.equals("Retail Price")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.retailPrice LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            brandTableUpdateBrand.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            brandTableUpdateBrand.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = brandTableUpdateBrand.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            
            rs1.close();
            ps1.close();
            
     
        } catch (SQLException ex) {
            Logger.getLogger(RemoveBrandFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00014):Cannot load all brands table for searching", JOptionPane.ERROR_MESSAGE);
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

        backgroundJPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        selectBrandComboBoxUpdateBrand = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        categoryComboBoxUpdateBrand = new javax.swing.JComboBox<>();
        updateBrandSearchTextField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        wholeSalePriceTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        brandTableUpdateBrand = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        unitsPerBoxTextiFieldUpdateBrand = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        retailPriceTextFieldUpdateBrand = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        backgroundJPanel.setBackground(new java.awt.Color(244, 244, 244));
        backgroundJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "UPDATE EXISTING BRAND", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        backgroundJPanel.setPreferredSize(new java.awt.Dimension(1360, 768));

        jLabel2.setText("SELECT BRAND");

        selectBrandComboBoxUpdateBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton2.setText("UPDATE BRAND");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("EXIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("CATEGORY");

        categoryComboBoxUpdateBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brand ID", "Brand Name", "Brand Weight", "Wholesale Price", "Retail Price" }));

        jButton3.setText("SEARCH");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("WHOLESALE PRICE");

        brandTableUpdateBrand.setModel(new javax.swing.table.DefaultTableModel(
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
        brandTableUpdateBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brandTableUpdateBrandMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                brandTableUpdateBrandMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                brandTableUpdateBrandMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(brandTableUpdateBrand);

        jLabel1.setText("UNITS PER BOX");

        jLabel5.setText("RETAIL PRICE");

        javax.swing.GroupLayout backgroundJPanelLayout = new javax.swing.GroupLayout(backgroundJPanel);
        backgroundJPanel.setLayout(backgroundJPanelLayout);
        backgroundJPanelLayout.setHorizontalGroup(
            backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(backgroundJPanelLayout.createSequentialGroup()
                        .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundJPanelLayout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2))
                            .addGroup(backgroundJPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(categoryComboBoxUpdateBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(updateBrandSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addGroup(backgroundJPanelLayout.createSequentialGroup()
                                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(selectBrandComboBoxUpdateBrand, 0, 185, Short.MAX_VALUE)
                                    .addComponent(wholeSalePriceTextField))
                                .addGap(82, 82, 82)
                                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel5))
                                .addGap(41, 41, 41)
                                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(unitsPerBoxTextiFieldUpdateBrand)
                                    .addComponent(retailPriceTextFieldUpdateBrand, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))))
                        .addGap(0, 94, Short.MAX_VALUE)))
                .addContainerGap())
        );
        backgroundJPanelLayout.setVerticalGroup(
            backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectBrandComboBoxUpdateBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(unitsPerBoxTextiFieldUpdateBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(wholeSalePriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(retailPriceTextFieldUpdateBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categoryComboBoxUpdateBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBrandSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //get the values in text fields
        final String getBrandText = selectBrandComboBoxUpdateBrand.getSelectedItem().toString();
        String selectedBrand = null;

        if(getBrandText.equals("Select brand")){
            selectedBrand = "Select brand";
        }
        else{
            final String[] split = getBrandText.split(" - ");
            selectedBrand = split[0];
        }

        //validate inputs
        if(selectedBrand.equals("Select brand")){
            final JOptionPane newOptionPane = new JOptionPane("Please select a brand to update", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            selectBrandComboBoxUpdateBrand.requestFocus();
        }
        else if(!Methods.isFloat(wholeSalePriceTextField.getText().toString())){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for the new unit price", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            wholeSalePriceTextField.requestFocus();
        }
        else if(!Methods.isFloat(retailPriceTextFieldUpdateBrand.getText().toString())){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for the new retail price", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            retailPriceTextFieldUpdateBrand.requestFocus();
        }
        else if(!Methods.isInt(unitsPerBoxTextiFieldUpdateBrand.getText().toString())){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for the units per box field", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            unitsPerBoxTextiFieldUpdateBrand.requestFocus();
        }
        else{

            final int proceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");

            if(proceed == 0){
               
                PreparedStatement ps1 = null;
                PreparedStatement ps2 = null;
                
                
                final int brandID = Integer.parseInt(selectedBrand);
                final float brandPrice = Float.parseFloat(wholeSalePriceTextField.getText().toString());
                final float retailPriceFloat = Float.parseFloat(retailPriceTextFieldUpdateBrand.getText().toString());
                final int unitsPerBoxInt = Integer.parseInt(unitsPerBoxTextiFieldUpdateBrand.getText().toString());

                //sql query
                final String sqlStatement1 = "UPDATE brand SET price = ?, unitsPerBox=?, retailPrice=? WHERE bID = ?";
                try {
                    ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setFloat(1, brandPrice);
                    ps1.setInt(2, unitsPerBoxInt);
                    ps1.setFloat(3, retailPriceFloat);
                    ps1.setInt(4, brandID);

                    ps1.executeUpdate();
                    
                    //if brandCopy table if exists
                    boolean isDailySalesNotOpen = homeFinal.getDailySalesBtn().isEnabled();
                    
                    if(!isDailySalesNotOpen){
                    
                        final String sqlStatement2 = "update brandCopy set price=?, unitsPerBox=?, retailPrice=? where bID=?";
                        ps2 = connection.prepareStatement(sqlStatement2);
                        ps2.setFloat(1, brandPrice);
                        ps2.setInt(2, unitsPerBoxInt);
                        ps2.setFloat(3, retailPriceFloat);
                        ps2.setInt(4, brandID);
                        
                        ps2.executeUpdate();
                        ps2.close();
                   
                    }
                    
                    JOptionPane.showMessageDialog(null, "Updation successful");
                    
                    ps1.close();
                   

                    fillBrandComboBoxUpdateBrand();
                    loadBrandTableUpdateBrand();

                } catch (SQLException ex) {
                    Logger.getLogger(RemoveBrandFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00015):Brand details updation operation failed", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
                
            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        homeFinal.loadTable();
        homeFinal.enable(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        final String value = updateBrandSearchTextField.getText().toString();
        final String selectedCategory = categoryComboBoxUpdateBrand.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadBrandTableUpdateBrand();
        }
        else{
            loadBrandsSearchTableUpdateBrand(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void brandTableUpdateBrandMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableUpdateBrandMouseClicked
        final int selectedRow = brandTableUpdateBrand.getSelectedRow();

        final String brandID =  brandTableUpdateBrand.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) brandTableUpdateBrand.getValueAt(selectedRow, 1).toString();
        final String brandPrice = brandTableUpdateBrand.getValueAt(selectedRow, 3).toString();
        final String retailPrice = brandTableUpdateBrand.getValueAt(selectedRow, 4).toString();
        final String unitsPerBox = brandTableUpdateBrand.getValueAt(selectedRow, 5).toString();

        final String fullBrandName = brandID + " - " + brandName;

        selectBrandComboBoxUpdateBrand.setSelectedItem(fullBrandName);
        wholeSalePriceTextField.setText(brandPrice);
        unitsPerBoxTextiFieldUpdateBrand.setText(unitsPerBox);
        retailPriceTextFieldUpdateBrand.setText(retailPrice);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_brandTableUpdateBrandMouseClicked

    private void brandTableUpdateBrandMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableUpdateBrandMouseEntered

    }//GEN-LAST:event_brandTableUpdateBrandMouseEntered

    private void brandTableUpdateBrandMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableUpdateBrandMousePressed
        final int selectedRow = brandTableUpdateBrand.getSelectedRow();

        final String brandID =  brandTableUpdateBrand.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) brandTableUpdateBrand.getValueAt(selectedRow, 1).toString();
        final String brandPrice = brandTableUpdateBrand.getValueAt(selectedRow, 3).toString();
        final String retailPrice = brandTableUpdateBrand.getValueAt(selectedRow, 4).toString();
        final String unitsPerBox = brandTableUpdateBrand.getValueAt(selectedRow, 5).toString();

        final String fullBrandName = brandID + " - " + brandName;

        selectBrandComboBoxUpdateBrand.setSelectedItem(fullBrandName);
        wholeSalePriceTextField.setText(brandPrice);
        unitsPerBoxTextiFieldUpdateBrand.setText(unitsPerBox);
        retailPriceTextFieldUpdateBrand.setText(retailPrice);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_brandTableUpdateBrandMousePressed

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
            java.util.logging.Logger.getLogger(UpdateBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateBrandFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundJPanel;
    private javax.swing.JTable brandTableUpdateBrand;
    private javax.swing.JComboBox<String> categoryComboBoxUpdateBrand;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField retailPriceTextFieldUpdateBrand;
    private javax.swing.JComboBox<String> selectBrandComboBoxUpdateBrand;
    private javax.swing.JTextField unitsPerBoxTextiFieldUpdateBrand;
    private javax.swing.JTextField updateBrandSearchTextField;
    private javax.swing.JTextField wholeSalePriceTextField;
    // End of variables declaration//GEN-END:variables
}
