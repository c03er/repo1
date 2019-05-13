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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import mb.HomeFinal;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author pasindu
 */
public final class DailySalesFinal extends javax.swing.JFrame {
    
    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    private ButtonGroup radioButtonGroup = null;
    private float totalDiscount = 0;
    
    private ArrayList brandIDList = new ArrayList<>();
    private ArrayList quantityList = new ArrayList<>();
    private ArrayList tableNameList = new ArrayList<>();
    private ArrayList billNumberList = new ArrayList<>();
    private ArrayList priceList = new ArrayList<>();
    /**
     * Creates new form DailySales
     */
    public DailySalesFinal(HomeFinal homeFinal) {
        initComponents();
        
        this.homeFinal = homeFinal;
        this.homeFinal2 = homeFinal;
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
        
        //add the two radio buttons to a radio button group
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(this.wholesaleRadioButton);
        radioButtonGroup.add(this.retailPriceRadioButton);
        
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
        
        loadStockTableDailySales();
        fillBrandComboBoxDailySales();
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
    
    public final void loadTables(){
        loadStockTableDailySales();
        fillBrandComboBoxDailySales();
    }
    
    public final void setTotalDiscount(float discount){
        this.totalDiscount += discount;
    }
    
    public final float getTotalDiscount(){
        return this.totalDiscount;
    }
    
    public final void emptyArrayLists(){
        this.brandIDList.clear();
        this.quantityList.clear();
        this.tableNameList.clear();
        this.billNumberList.clear();
        this.priceList.clear();
    }
    
    public final ArrayList getBrandIDList(){
        return this.brandIDList;
    }
    
    public final ArrayList getQuantityList(){
        return this.quantityList;
    }
    
    public final ArrayList getTableNameList(){
        return this.tableNameList;
    }
    public final ArrayList getBillNumberList(){
        return this.billNumberList;
    }
    public final ArrayList getPriceList(){
        return this.priceList;
    }
    
    public final void closeApplication(){
        final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to exit? All the unsaved progress will be lost");
        
        if(doProceed == JOptionPane.YES_OPTION){
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
            PreparedStatement ps4 = null;
            PreparedStatement ps5 = null;
            
            try {
            
                final String sqlStatement1 = "drop table if exists soldsTotal";
                final String sqlStatement2 = "drop table if exists freeIssueTotal";
                final String sqlStatement3 = "drop table if exists solds";
                final String sqlStatement4 = "drop table if exists freeIssue";
                final String sqlStatement5 =  "drop table if exists brandCopy";
                ps1 = connection.prepareStatement(sqlStatement1);
                ps2 = connection.prepareStatement(sqlStatement2);
                ps3 = connection.prepareStatement(sqlStatement3);
                ps4 = connection.prepareStatement(sqlStatement4);
                ps5 = connection.prepareStatement(sqlStatement5);
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();
                ps5.executeUpdate();

                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();
                ps5.close();

                homeFinal.loadTable();
                homeFinal.getDailySalesBtn().setEnabled(true);
                homeFinal.getAddBrandBtn().setEnabled(true);
                homeFinal.getRemoveBrandBtn().setEnabled(true);
                homeFinal.getRemoveStockFromWarehouseBtn().setEnabled(true);
                homeFinal.getRemoveStockFromVehicleBtn().setEnabled(true);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(DailySalesFinal.class.getName()).log(Level.SEVERE, null, ex);
                final JOptionPane newOptionPane = new JOptionPane("Error(00043):Operation failed", JOptionPane.ERROR_MESSAGE);
                final JDialog newDialog = newOptionPane.createDialog("Warning");
                newDialog.setAlwaysOnTop(true);
                newDialog.setVisible(true);
            }
            
        }
    }
    
    public final void closeApplicationWithoutPrompt(){
        
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
            PreparedStatement ps4 = null;
            PreparedStatement ps5 = null;
            
            try {
            
                final String sqlStatement1 = "drop table if exists soldsTotal";
                final String sqlStatement2 = "drop table if exists freeIssueTotal";
                final String sqlStatement3 = "drop table if exists solds";
                final String sqlStatement4 = "drop table if exists freeIssue";
                final String sqlStatement5 = "drop table if exists brandCopy";
                ps1 = connection.prepareStatement(sqlStatement1);
                ps2 = connection.prepareStatement(sqlStatement2);
                ps3 = connection.prepareStatement(sqlStatement3);
                ps4 = connection.prepareStatement(sqlStatement4);
                ps5 = connection.prepareStatement(sqlStatement5);
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();
                ps5.executeUpdate();

                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();
                ps5.close();

                homeFinal.loadTable();
                homeFinal.getDailySalesBtn().setEnabled(true);
                homeFinal.getAddBrandBtn().setEnabled(true);
                homeFinal.getRemoveBrandBtn().setEnabled(true);
                homeFinal.getRemoveStockFromWarehouseBtn().setEnabled(true);
                homeFinal.getRemoveStockFromVehicleBtn().setEnabled(true);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(DailySalesFinal.class.getName()).log(Level.SEVERE, null, ex);
                final JOptionPane newOptionPane = new JOptionPane("Error(00044):Operation failed", JOptionPane.ERROR_MESSAGE);
                final JDialog newDialog = newOptionPane.createDialog("Warning");
                newDialog.setAlwaysOnTop(true);
                newDialog.setVisible(true);
            }
            
    }
    
    private final void fillBrandComboBoxDailySales(){
        
        PreparedStatement ps1 = null;
        ResultSet rs1 =  null;
        
        try {
            final String sqlStatement1 = "SELECT * FROM brand";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery(sqlStatement1);
            
            //remove all available items 
            selectBrandComboBoxDailySales.removeAllItems();
            selectBrandComboBoxDailySales.addItem("Select brand");
            
            while(rs1.next()){
                
                selectBrandComboBoxDailySales.addItem(rs1.getInt("bID") + " - " + rs1.getString("name") );
            }
            
            ps1.close();
            rs1.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00045):All brands combo box cannot be loaded", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        
    }
    
    private final void loadStockTableDailySales(){
        
        
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            
            final String sqlStatement1 = "SELECT brandCopy.bID as 'Brand ID', brandCopy.name as 'Brand Name', brandCopy.weight as 'Brand Weight', brandCopy.price as 'Wholesale Price', brandCopy.retailPrice as 'Retail Price', brandCopy.quantityWarehouse as 'Warehouse Stock', brandCopy.quantityVehicle as 'Vehicle Stock', unitsPerBox as 'Units Per Box' from brandCopy order by brandCopy.name, brandCopy.weight, brandCopy.price, brandCopy.retailPrice";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            stockTableDailySales.setModel(DbUtils.resultSetToTableModel(rs1));
           
            
            //change row height
            stockTableDailySales.setRowHeight(30);
            
            TableColumnModel columnModel = stockTableDailySales.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);
            columnModel.getColumn(7).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
           
        } catch (SQLException ex) {
            Logger.getLogger(DailySalesFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00046):Stock table cannot be loaded", JOptionPane.ERROR_MESSAGE);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        selectBrandComboBoxDailySales = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        quantityTextFieldDailySales = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        stockTableDailySales = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        billNumberTextFieldDailySales = new javax.swing.JTextField();
        freeIssueCheckBox = new javax.swing.JCheckBox();
        jButton4 = new javax.swing.JButton();
        wholesaleRadioButton = new javax.swing.JRadioButton();
        retailPriceRadioButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "DAILY SUMMARY", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 768));

        jLabel4.setText("SELECT BRAND");

        selectBrandComboBoxDailySales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select brand" }));

        jLabel2.setText("SOLD QUANTITY");

        jButton1.setText("ADD MORE SALES");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("GET BILL SUMMARY");
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

        stockTableDailySales.setModel(new javax.swing.table.DefaultTableModel(
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
        stockTableDailySales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockTableDailySalesMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                stockTableDailySalesMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(stockTableDailySales);

        jLabel6.setText("BILL NUMBER");

        freeIssueCheckBox.setText("FREE ISSUE");

        jButton4.setText("REFRESH ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        wholesaleRadioButton.setSelected(true);
        wholesaleRadioButton.setText("WHOLESALE PRICE");

        retailPriceRadioButton.setText("RETAIL PRICE");
        retailPriceRadioButton.setActionCommand("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(selectBrandComboBoxDailySales, 0, 287, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(quantityTextFieldDailySales, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                                            .addComponent(billNumberTextFieldDailySales))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(freeIssueCheckBox)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(wholesaleRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(retailPriceRadioButton)))))
                        .addGap(0, 303, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(selectBrandComboBoxDailySales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(freeIssueCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(quantityTextFieldDailySales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wholesaleRadioButton)
                    .addComponent(retailPriceRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(billNumberTextFieldDailySales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to exit? All the progress will be lost");
        
        if(doProceed == JOptionPane.YES_OPTION){
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
            PreparedStatement ps4 = null;
            PreparedStatement ps5 = null;
            
            try {
            
                final String sqlStatement1 = "drop table if exists soldsTotal";
                final String sqlStatement2 = "drop table if exists freeIssueTotal";
                final String sqlStatement3 = "drop table if exists solds";
                final String sqlStatement4 = "drop table if exists freeIssue";
                final String sqlStatement5 = "drop table if exists brandCopy";
                ps1 = connection.prepareStatement(sqlStatement1);
                ps2 = connection.prepareStatement(sqlStatement2);
                ps3 = connection.prepareStatement(sqlStatement3);
                ps4 = connection.prepareStatement(sqlStatement4);
                ps5 = connection.prepareStatement(sqlStatement5);
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();
                ps5.executeUpdate();

                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();
                ps5.close();

                homeFinal.loadTable();
                homeFinal.getDailySalesBtn().setEnabled(true);
                homeFinal.getAddBrandBtn().setEnabled(true);
                homeFinal.getRemoveBrandBtn().setEnabled(true);
                homeFinal.getRemoveStockFromWarehouseBtn().setEnabled(true);
                homeFinal.getRemoveStockFromVehicleBtn().setEnabled(true);
                this.dispose();
                
            } catch (SQLException ex) {
                Logger.getLogger(DailySalesFinal.class.getName()).log(Level.SEVERE, null, ex);
                final JOptionPane newOptionPane = new JOptionPane("Error(00047):Operation failed", JOptionPane.ERROR_MESSAGE);
                final JDialog newDialog = newOptionPane.createDialog("Warning");
                newDialog.setAlwaysOnTop(true);
                newDialog.setVisible(true);
            }
            
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void stockTableDailySalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockTableDailySalesMouseClicked
        final int selectedRow = stockTableDailySales.getSelectedRow();
        
        final String brandID =  stockTableDailySales.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) stockTableDailySales.getValueAt(selectedRow, 1).toString();
        
        final String fullBrandName = brandID + " - " + brandName;
       
        selectBrandComboBoxDailySales.setSelectedItem(fullBrandName);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_stockTableDailySalesMouseClicked

    private void stockTableDailySalesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockTableDailySalesMousePressed
        final int selectedRow = stockTableDailySales.getSelectedRow();
        
        final String brandID =  stockTableDailySales.getValueAt(selectedRow, 0).toString();
        final String brandName = (String) stockTableDailySales.getValueAt(selectedRow, 1).toString();
        
        final String fullBrandName = brandID + " - " + brandName;
       
        selectBrandComboBoxDailySales.setSelectedItem(fullBrandName);
        System.out.println("Brand ID : " + brandID);
        System.out.println("Brand Name: " + brandName);
    }//GEN-LAST:event_stockTableDailySalesMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final String getBrandText = selectBrandComboBoxDailySales.getSelectedItem().toString();
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
            final JOptionPane newOptionPane = new JOptionPane("Please select a brand", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            selectBrandComboBoxDailySales.requestFocus();
        }
        else if(!Methods.isInt(quantityTextFieldDailySales.getText().toString())){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for sold quantity", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            quantityTextFieldDailySales.requestFocus();
        }
        else if(billNumberTextFieldDailySales.getText().toString().isEmpty()){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid bill number", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            billNumberTextFieldDailySales.setText(null);
        }
        else{

            final int proceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(proceed == 0){
                
                int quantityFromDatabase= 0;
                float wholesalePrice = 0;
                float retailPrice = 0;
                float currentPrice = 0;
                
                PreparedStatement ps1 = null;
                PreparedStatement ps2 = null;
                PreparedStatement ps3 = null;
                ResultSet rs1 = null;

                final int brandID = Integer.parseInt(selectedBrand);
                
                final int quantityInt = Integer.parseInt(quantityTextFieldDailySales.getText().toString());
                
                final String billNumber = billNumberTextFieldDailySales.getText().toString();
                
                final String sqlStatement1 = "SELECT * FROM brandCopy WHERE bID=?";
                
                try {
                    ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setInt(1, brandID);
                    rs1 = ps1.executeQuery();
                    
                    while(rs1.next()){
                        
                        quantityFromDatabase = rs1.getInt("quantityVehicle");
                        wholesalePrice = rs1.getFloat("price");
                        retailPrice = rs1.getFloat("retailPrice");
                        
                    }
                    
                    ps1.close();
                    rs1.close();
                    
                    if(quantityInt > quantityFromDatabase){
                        final JOptionPane newOptionPane = new JOptionPane("Not enough stock. Please check again", JOptionPane.ERROR_MESSAGE);
                        final JDialog newDialog = newOptionPane.createDialog("Warning");
                        newDialog.setAlwaysOnTop(true);
                        newDialog.setVisible(true);
                    }
                    else{
                        
                        quantityFromDatabase -= quantityInt;
                        final String sqlStatement2 = "UPDATE brandCopy SET quantityVehicle=? WHERE bID=?";
                        ps2 = connection.prepareStatement(sqlStatement2);
                        ps2.setInt(1, quantityFromDatabase);
                        ps2.setInt(2, brandID);
                        ps2.executeUpdate();
                        ps2.close();
                        
                        //add data to arraylists
                        this.brandIDList.add(brandID);
                        this.quantityList.add(quantityInt);
                        this.billNumberList.add(billNumber);
                        
                        if(wholesaleRadioButton.isSelected() && retailPriceRadioButton.isSelected() == false){
                            this.priceList.add(wholesalePrice);
                            currentPrice = wholesalePrice;
                        }
                        else{
                            this.priceList.add(retailPrice);
                            currentPrice = retailPrice;
                        }
                        
                        boolean isFreeIssue = freeIssueCheckBox.isSelected();
                        
                        if(isFreeIssue){
                            
                            this.tableNameList.add("Free Issue");
                            final String sqlStatement3 = "INSERT INTO freeIssue(bID, quantity, price) VALUES(?,?,?)";
                            ps3 = connection.prepareStatement(sqlStatement3);

                            ps3.setInt(1, brandID);
                            ps3.setInt(2, quantityInt);
                            ps3.setFloat(3, currentPrice);
                            ps3.executeUpdate();
                        
                            ps3.close();
                        }
                        else{
                            
                            this.tableNameList.add("Solds");
                            final String sqlStatement3 = "INSERT INTO solds(bID, quantity, price) VALUES(?,?,?)";
                            ps3 = connection.prepareStatement(sqlStatement3);

                            ps3.setInt(1, brandID);
                            ps3.setInt(2, quantityInt);
                            ps3.setFloat(3, currentPrice);
                            ps3.executeUpdate();

                            ps3.close();
                        }
                        

                        loadStockTableDailySales();
                        quantityTextFieldDailySales.setText(null);
                        selectBrandComboBoxDailySales.setSelectedItem("Select brand");
                        freeIssueCheckBox.setSelected(false);
                    }
                    
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(DailySalesFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00048):Add more sales operation failed", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                    
                }
               
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        final BillSummaryFinal billSummaryFinal = new BillSummaryFinal(homeFinal, this);
        billSummaryFinal.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        loadStockTableDailySales();
        fillBrandComboBoxDailySales();
        
        
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
            java.util.logging.Logger.getLogger(DailySalesFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DailySalesFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DailySalesFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DailySalesFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DailySalesFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField billNumberTextFieldDailySales;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox freeIssueCheckBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField quantityTextFieldDailySales;
    private javax.swing.JRadioButton retailPriceRadioButton;
    private javax.swing.JComboBox<String> selectBrandComboBoxDailySales;
    private javax.swing.JTable stockTableDailySales;
    private javax.swing.JRadioButton wholesaleRadioButton;
    // End of variables declaration//GEN-END:variables
}
