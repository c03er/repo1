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
import java.sql.Date;
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
public final class AddStockFinal extends javax.swing.JFrame {

    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    /**
     * Creates new form AddStock
     */
    public AddStockFinal(HomeFinal homeFinal) {
        initComponents();
        
        this.homeFinal = homeFinal;
        this.homeFinal2 = homeFinal;
        
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
        
        try {
         
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
        
        fillBrandComboBoxAddStock();
        loadBrandTableAddStock();
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

    
    private final void loadBrandTableAddStock(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "SELECT brand.bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box', brand.quantityWarehouse as 'Warehouse Stock' FROM brand order by brand.name, brand.weight, brand.price, brand.unitsPerBox";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            brandTableAddStock.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            brandTableAddStock.setRowHeight(30);
            
            TableColumnModel columnModel = brandTableAddStock.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);
           
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00018):Cannot load brand table", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
      
    }
    
    private final void loadBrandsSearchTableAddStock(String category, String value){
 
        String sqlStatement1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        
        try {
            
            if(category.equals("Brand Name")){
                sqlStatement1 = "SELECT brand.bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box', brand.quantityWarehouse as 'Warehouse Stock' FROM brand WHERE brand.name LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            else if(category.equals("Brand Weight")){
                sqlStatement1 = "SELECT brand.bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box', brand.quantityWarehouse as 'Warehouse Stock' FROM brand WHERE brand.weight LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            else if(category.equals("Wholesale Price")){
                sqlStatement1 = "SELECT brand.bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box', brand.quantityWarehouse as 'Warehouse Stock' FROM brand WHERE brand.price LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            else if(category.equals("Retail Price")){
                sqlStatement1 = "SELECT brand.bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box', brand.quantityWarehouse as 'Warehouse Stock' FROM brand WHERE brand.retailPrice LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            else if(category.equals("Warehouse Stock")){
                sqlStatement1 = "SELECT brand.bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box', brand.quantityWarehouse as 'Warehouse Stock' FROM brand WHERE brand.quantityWarehouse LIKE '%"+value+"%' ORDER BY brand.name, brand.weight";
            }
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            brandTableAddStock.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            brandTableAddStock.setRowHeight(30);
            
            TableColumnModel columnModel = brandTableAddStock.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
     
        } catch (SQLException ex) {
            Logger.getLogger(AddStockFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00019):Cannot load all brands table for searching", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
      
    }
    
    
    private final boolean isAddedStock(String brand, Float quantity, Date aDate){

        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            
            //get brand id
            final int brandID = Integer.parseInt(brand);
          
            //check whether a particular stock exist
            final String sqlStatement1 = "SELECT * FROM stock WHERE quantity=? AND arrivalDate=? AND  bID=?";
            
            ps1 = connection.prepareStatement(sqlStatement1);
            
            ps1.setFloat(1,quantity);
            ps1.setDate(2, aDate);
            ps1.setInt(3, brandID);
          
            rs1 = ps1.executeQuery();
            
            if(rs1.next()){
                
                ps1.close();
                rs1.close();
                return true;
            }
            else {
                ps1.close();
                rs1.close();
                return false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AddStockFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00020):IsAddedStock operation failed", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            return false;
        }
      
    }
    
    private final void fillBrandComboBoxAddStock(){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "SELECT * FROM brand";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery(sqlStatement1);
            
            //remove all available items 
            selectBrandComboBoxAddStock.removeAllItems();
            selectBrandComboBoxAddStock.addItem("Select brand");
            
            while(rs1.next()){
                
                selectBrandComboBoxAddStock.addItem(rs1.getInt("bID") + " - " + rs1.getString("name") );
            }
            
            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00021):All brands combo box cannot be loaded", JOptionPane.ERROR_MESSAGE);
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
        jLabel2 = new javax.swing.JLabel();
        quantityTextFieldAddStock = new javax.swing.JTextField();
        selectBrandComboBoxAddStock = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        brandTableAddStock = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        categoryComboBoxAddStock = new javax.swing.JComboBox<>();
        AddStockSearchTextField = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        billNumberTextFieldAddStock = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "ADD NEW STOCK", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 768));

        jLabel2.setText("QUANTITY");

        quantityTextFieldAddStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityTextFieldAddStockActionPerformed(evt);
            }
        });

        selectBrandComboBoxAddStock.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("SELECT BRAND");

        jButton1.setText("EXIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("CLEAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("ADD STOCK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        brandTableAddStock.setModel(new javax.swing.table.DefaultTableModel(
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
        brandTableAddStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brandTableAddStockMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                brandTableAddStockMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                brandTableAddStockMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(brandTableAddStock);

        jLabel3.setText("CATEGORY");

        categoryComboBoxAddStock.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brand Name", "Brand Weight", "Wholesale Price", "Retail Price", "Warehouse Stock" }));

        AddStockSearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStockSearchTextFieldActionPerformed(evt);
            }
        });

        jButton4.setText("SEARCH");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel6.setText("BILL NUMBER");

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
                                .addComponent(categoryComboBoxAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(AddStockSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel6))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(selectBrandComboBoxAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(quantityTextFieldAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(billNumberTextFieldAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)))
                        .addGap(0, 229, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectBrandComboBoxAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(quantityTextFieldAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(billNumberTextFieldAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(categoryComboBoxAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(AddStockSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        homeFinal.loadTable();
        homeFinal.enable(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        selectBrandComboBoxAddStock.setSelectedItem("Select brand");
        quantityTextFieldAddStock.setText(null);
        billNumberTextFieldAddStock.setText(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        final String getBrandText = selectBrandComboBoxAddStock.getSelectedItem().toString();
        System.out.println("Get brand text: " + getBrandText);
        
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
        }
        else if(!Methods.isInt(quantityTextFieldAddStock.getText().toString())){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for quantity", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            quantityTextFieldAddStock.setText(null);
        }
        else if(billNumberTextFieldAddStock.getText().toString().isEmpty()){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid bill number", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            billNumberTextFieldAddStock.setText(null);
        }
        else{
                
            final int proceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
        
            if(proceed == 0){
                
                PreparedStatement ps1 = null;
                PreparedStatement ps2 = null;
                PreparedStatement ps3 = null;
                PreparedStatement ps4 = null;
                PreparedStatement ps5 = null;
                ResultSet rs2 = null;
                ResultSet rs4 = null;
                int quantity = 0;
                  
                try {
                    final String sqlStatement1 = "INSERT INTO stock(quantity, bID, type, billNumber) VALUES(?,?,?,?)";
                    ps1 = connection.prepareStatement(sqlStatement1);

                    final String billNumber = billNumberTextFieldAddStock.getText().toString();
                    final int quantityInt = Integer.parseInt(quantityTextFieldAddStock.getText().toString());
                    final int brandID = Integer.parseInt(selectedBrand);
                    
                    ps1.setInt(1, quantityInt);
                    ps1.setInt(2, brandID);
                    ps1.setString(3, "Arrived");
                    ps1.setString(4, billNumber);
                  
                    ps1.executeUpdate();
                    
                    ps1.close();
                    
                    final String sqlStatement2 = "SELECT * FROM brand WHERE bID=?";
                    ps2 = connection.prepareStatement(sqlStatement2);
                    ps2.setInt(1, brandID);
                    rs2 = ps2.executeQuery();
                 
                    while(rs2.next()){
                        quantity = rs2.getInt("quantityWarehouse");
                    }
                    
                    ps2.close();
                    rs2.close();
                    
                    quantity = quantity + quantityInt;
                    
                    
                    final String sqlStatement3 = "UPDATE brand SET quantityWarehouse=? WHERE bID=?";
                    ps3 = connection.prepareStatement(sqlStatement3);
                    ps3.setInt(1, quantity);
                    ps3.setInt(2, brandID);
                    
                    ps3.executeUpdate();
                    ps3.close();
                    
                    //update the brandCopy table if exists
                    boolean isDailySalesNotOpen = homeFinal.getDailySalesBtn().isEnabled();
                    
                    if(!isDailySalesNotOpen){
                        
                        int warehouseQuantityBrandCopy = 0;
                        
                        final String sqlStatement4 = "select * from brandCopy where bID=?";
                        ps4 = connection.prepareStatement(sqlStatement4);
                        ps4.setInt(1, brandID);
                        rs4 = ps4.executeQuery();
                        
                        while(rs4.next()){
                            warehouseQuantityBrandCopy = rs4.getInt("quantityWarehouse");
                        }
                        
                        warehouseQuantityBrandCopy += quantityInt;
                        
                        final String sqlStatement5 = "update brandCopy set quantityWarehouse=? where bID=?";
                        ps5 = connection.prepareStatement(sqlStatement5);
                        ps5.setInt(1, warehouseQuantityBrandCopy);
                        ps5.setInt(2, brandID);
                        
                        ps5.executeUpdate();
                        
                        ps4.close();
                        ps5.close();
                        rs4.close();
                        
                        
                    }
                    
                    JOptionPane.showMessageDialog(null, "Insertion Successful");
                    
                    homeFinal.loadTable();
                    homeFinal.enable(true);
                    this.dispose();


                } catch (SQLException ex) {
                    Logger.getLogger(AddStockFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00022):Stock insertion unsuccessful", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
                
            }
            
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void brandTableAddStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableAddStockMouseClicked
        final int selectedRow = brandTableAddStock.getSelectedRow();

        final String brandID =  brandTableAddStock.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) brandTableAddStock.getValueAt(selectedRow, 1).toString();

        final String fullBrandName = brandID + " - " + brandName;

        selectBrandComboBoxAddStock.setSelectedItem(fullBrandName);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_brandTableAddStockMouseClicked

    private void brandTableAddStockMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableAddStockMouseEntered

    }//GEN-LAST:event_brandTableAddStockMouseEntered

    private void brandTableAddStockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableAddStockMousePressed
        final int selectedRow = brandTableAddStock.getSelectedRow();

        final String brandID =  brandTableAddStock.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) brandTableAddStock.getValueAt(selectedRow, 1).toString();

        final String fullBrandName = brandID + " - " + brandName;

        selectBrandComboBoxAddStock.setSelectedItem(fullBrandName);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_brandTableAddStockMousePressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        final String value = AddStockSearchTextField.getText().toString();
        final String selectedCategory = categoryComboBoxAddStock.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadBrandTableAddStock();
        }
        else{
            loadBrandsSearchTableAddStock(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void quantityTextFieldAddStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityTextFieldAddStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityTextFieldAddStockActionPerformed

    private void AddStockSearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStockSearchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddStockSearchTextFieldActionPerformed

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
            java.util.logging.Logger.getLogger(AddStockFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddStockFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddStockFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddStockFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddStockFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddStockSearchTextField;
    private javax.swing.JTextField billNumberTextFieldAddStock;
    private javax.swing.JTable brandTableAddStock;
    private javax.swing.JComboBox<String> categoryComboBoxAddStock;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField quantityTextFieldAddStock;
    private javax.swing.JComboBox<String> selectBrandComboBoxAddStock;
    // End of variables declaration//GEN-END:variables
}
