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
public final class BillSummaryFinal extends javax.swing.JFrame {
    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    private DailySalesFinal dailySalesFinal = null;
    public static DailySalesFinal dailySalesFinal2 = null;
    public static HomeFinal homeFinal2 = null;
    private int discountPercentage = 0;
    private float payment = 0;
    private float totalPayment = 0;
    private float confirmedDiscount = 0;
    private float discount = 0;
    /**
     * Creates new form SalesSummary
     */
    public BillSummaryFinal(HomeFinal homeFinal, DailySalesFinal dailySalesFinal) {
        initComponents();
        
        this.homeFinal = homeFinal;
        this.homeFinal2 = homeFinal;
        this.dailySalesFinal = dailySalesFinal;
        this.dailySalesFinal2 = dailySalesFinal;
        confirmedDiscount = 0;
        
        setDiscountPercentage(0);
        
        
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
        
        billSummaryTable();
        loadFreeIssuesTable();
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
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        
        boolean isNotSaved = saveChangesBtn.isEnabled();
        
        if(isNotSaved)
        {
            final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to discard this bill? If do, all the bill progress will be lost");
        
            if(doProceed == JOptionPane.YES_OPTION){
                try {
                    final String sqlStatement1 = "drop table if exists solds";
                    final String sqlStatement2 = "drop table if exists freeIssue";
                    final String sqlStatement3 = "create table solds(bID int not null, quantity int not null, price float, constraint fk_solds_brand foreign key(bID) references brand(bID))";
                    final String sqlStatement4 = "create table freeIssue(bID int not null, quantity int not null, price float, constraint fk_freeIssue_Brand foreign key(bID) references brand(bID))";
                    final String sqlStatement5 = "drop table if exists brandCopy";
                    final String sqlStatement6 = "create table brandCopy as select * from brand";
                    ps1 = connection.prepareStatement(sqlStatement1);
                    ps2 = connection.prepareStatement(sqlStatement2);
                    ps3 = connection.prepareStatement(sqlStatement3);
                    ps4 = connection.prepareStatement(sqlStatement4);
                    ps5 = connection.prepareStatement(sqlStatement5);
                    ps6 = connection.prepareStatement(sqlStatement6);

                    ps1.executeUpdate();
                    ps2.executeUpdate();
                    ps3.executeUpdate();
                    ps4.executeUpdate();
                    ps5.executeUpdate();
                    ps6.executeUpdate();

                    ps1.close();
                    ps2.close();
                    ps3.close();
                    ps4.close();
                    ps5.close();
                    ps6.close();

                    dailySalesFinal.emptyArrayLists();

                    dailySalesFinal.setVisible(true);
                    dailySalesFinal.loadTables();
                    this.dispose();

                } catch (SQLException ex) {
                    Logger.getLogger(BillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00049):Close operation failed", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
                
            }
        }
        else{
            try {
                final String sqlStatement1 = "drop table if exists solds";
                final String sqlStatement2 = "drop table if exists freeIssue";
                final String sqlStatement3 = "create table solds(bID int not null, quantity int not null, price float, constraint fk_solds_brand foreign key(bID) references brand(bID))";
                final String sqlStatement4 = "create table freeIssue(bID int not null, quantity int not null, price float, constraint fk_freeIssue_Brand foreign key(bID) references brand(bID))";
                ps1 = connection.prepareStatement(sqlStatement1);
                ps2 = connection.prepareStatement(sqlStatement2);
                ps3 = connection.prepareStatement(sqlStatement3);
                ps4 = connection.prepareStatement(sqlStatement4);
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();

                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();

                dailySalesFinal.emptyArrayLists();
                dailySalesFinal.setVisible(true);
                this.dispose();

                } catch (SQLException ex) {
                    Logger.getLogger(BillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00050):Close operation failed", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
                
        }
        
    }
    
    public final void loadTextFields(){
        loadPaymentTextField();
        loadDiscountTextField();
        loadTotalPaymentTextField();
        
    }
    
    private final void loadPaymentTextField(){
        paymentTextField.setText(String.valueOf(payment));
    }
    
    private final void loadDiscountTextField(){
        
        if(discountPercentage <= 0){
            this.discount = 0;
        }
        else{
            discount = (float) (payment * (float)discountPercentage / 100.00);
        }
        discountTextField.setText(String.valueOf(discount));
    }
    
    private final void loadTotalPaymentTextField(){
        
        totalPayment = payment - discount;
        totalPaymentTextField.setText(String.valueOf(totalPayment));
    }
    
    private final void billSummaryTable(){
 
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        ResultSet rs1 = null;
        ResultSet rs4 = null;
        
        
        
        try {
            final String sqlStatement1 = "SELECT brand.name as 'Brand Name', brand.weight as 'Brand Weight', solds.price as 'Price', SUM(solds.quantity) as 'Sold Quantity', (SUM(solds.quantity) * solds.price) as 'Total Payment' from brand, solds WHERE brand.bID = solds.bID group by solds.bID, solds.price order by brand.name, brand.weight, solds.price";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            salesSummaryTable.setModel(DbUtils.resultSetToTableModel(rs1));
            //change row height
            salesSummaryTable.setRowHeight(30);
            TableColumnModel columnModel = salesSummaryTable.getColumnModel();
            
            final String sqlStatement2 = "drop view if exists soldsView";
            final String sqlStatement3 = "create view soldsView as SELECT brand.name as 'Brand_Name', brand.weight as 'Brand_Weight', solds.price as 'Brand_Price', SUM(solds.quantity) as 'Sold_Quantity', (SUM(solds.quantity) * solds.price) as 'Total_Payment' from brand, solds WHERE brand.bID = solds.bID group by solds.bID, solds.price order by brand.name, brand.weight, solds.price";
            
            
            ps2 = connection.prepareStatement(sqlStatement2);
            ps2.executeUpdate();
            
            ps3 = connection.prepareStatement(sqlStatement3);
            ps3.executeUpdate();
            
            final String sqlStatement5 = "SELECT SUM(Total_Payment) as 'TotalPayment' from soldsView";
            ps4 = connection.prepareStatement(sqlStatement5);
            
            rs4 = ps4.executeQuery();
             
            while(rs4.next()){
                payment = rs4.getFloat("TotalPayment");
            }
            
            loadTextFields();
            
            //drop the view
            final String sqlStatement6 = "drop view if exists soldsView";
            ps5 = connection.prepareStatement(sqlStatement6);
            ps5.executeUpdate();
            
            
            
            
            ps1.close();
            rs1.close();
            ps2.close();
            ps3.close();
            ps4.close();
            rs4.close();
            ps5.close();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(BillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00051):Cannot load bill summary table", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        
    }
    
    private final void loadFreeIssuesTable(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "SELECT brand.name as 'Brand Name', brand.weight as 'Brand Weight', freeIssue.price as 'Price', SUM(freeIssue.quantity) as 'Sold Quantity', (SUM(freeIssue.quantity) * freeIssue.price) as 'Total Payment' from brand, freeIssue WHERE brand.bID = freeIssue.bID group by freeIssue.bID, freeIssue.price order by brand.name, brand.weight, freeIssue.price";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            freeIssuesTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            freeIssuesTable.setRowHeight(30);
            
            TableColumnModel columnModel = freeIssuesTable.getColumnModel();
            
            ps1.close();
            rs1.close();
      
        } catch (SQLException ex) {
            Logger.getLogger(BillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error:(00052):Cannot load free issues summary table", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        
    }
    
    public final void setDiscountPercentage(int percentage){
        this.discountPercentage = percentage;
    }
    
    public final int getDiscountPercentage(){
        return this.discountPercentage;
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
        printBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        salesSummaryTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        totalPaymentTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        paymentTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        discountTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        freeIssuesTable = new javax.swing.JTable();
        addMoreSalesBtn = new javax.swing.JButton();
        saveChangesBtn = new javax.swing.JButton();
        addNextBillBtn = new javax.swing.JButton();
        viewDailySummaryBtn = new javax.swing.JButton();
        addDiscountBtn = new javax.swing.JButton();
        removeDiscountBtn = new javax.swing.JButton();
        confirmDiscountBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "BILL SUMMARY", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 768));

        discardBillBtn.setText("DISCARD BILL");
        discardBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discardBillBtnActionPerformed(evt);
            }
        });

        printBtn.setText("PRINT");
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
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

        paymentTextField.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("-");

        jLabel4.setText("DISCOUNT");

        discountTextField.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("=");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paymentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(discountTextField)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
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
                    .addComponent(paymentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(discountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        addMoreSalesBtn.setText("ADD MORE SALES TO BILL");
        addMoreSalesBtn.setToolTipText("");
        addMoreSalesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMoreSalesBtnActionPerformed(evt);
            }
        });

        saveChangesBtn.setText("SAVE CHANGES");
        saveChangesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveChangesBtnActionPerformed(evt);
            }
        });

        addNextBillBtn.setText("ADD NEXT BILL");
        addNextBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNextBillBtnActionPerformed(evt);
            }
        });

        viewDailySummaryBtn.setText("VIEW DAILY SUMMARY");
        viewDailySummaryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDailySummaryBtnActionPerformed(evt);
            }
        });

        addDiscountBtn.setText("ADD DISCOUNT");
        addDiscountBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDiscountBtnActionPerformed(evt);
            }
        });

        removeDiscountBtn.setText("REMOVE DISCOUNT");
        removeDiscountBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeDiscountBtnActionPerformed(evt);
            }
        });

        confirmDiscountBtn.setText("CONFIRM DISCOUNT");
        confirmDiscountBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmDiscountBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(discardBillBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addMoreSalesBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saveChangesBtn)
                                .addGap(18, 18, 18)
                                .addComponent(addNextBillBtn)
                                .addGap(18, 18, 18)
                                .addComponent(viewDailySummaryBtn)
                                .addGap(18, 18, 18)
                                .addComponent(printBtn))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(addDiscountBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeDiscountBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(confirmDiscountBtn)))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                    .addComponent(printBtn)
                    .addComponent(addMoreSalesBtn)
                    .addComponent(saveChangesBtn)
                    .addComponent(addNextBillBtn)
                    .addComponent(viewDailySummaryBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addDiscountBtn)
                    .addComponent(removeDiscountBtn)
                    .addComponent(confirmDiscountBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void discardBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discardBillBtnActionPerformed
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        
        final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to discard this bill? If do, all the bill progress will be lost");
        
        if(doProceed == JOptionPane.YES_OPTION){
            try {
                final String sqlStatement1 = "drop table if exists solds";
                final String sqlStatement2 = "drop table if exists freeIssue";
                final String sqlStatement3 = "create table solds(bID int not null, quantity int not null, price float, constraint fk_solds_brand foreign key(bID) references brand(bID))";
                final String sqlStatement4 = "create table freeIssue(bID int not null, quantity int not null, price float,  constraint fk_freeIssue_Brand foreign key(bID) references brand(bID))";
                final String sqlStatement5 = "drop table if exists brandCopy";
                final String sqlStatement6 = "create table brandCopy as select * from brand";

                ps1 = connection.prepareStatement(sqlStatement1);
                ps2 = connection.prepareStatement(sqlStatement2);
                ps3 = connection.prepareStatement(sqlStatement3);
                ps4 = connection.prepareStatement(sqlStatement4);
                ps5 = connection.prepareStatement(sqlStatement5);
                ps6 = connection.prepareStatement(sqlStatement6);
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();
                ps5.executeUpdate();
                ps6.executeUpdate();

                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();
                ps5.close();
                ps6.close();

                dailySalesFinal.emptyArrayLists();
                dailySalesFinal.setVisible(true);
                dailySalesFinal.loadTables();
                this.dispose();

                } catch (SQLException ex) {
                    Logger.getLogger(BillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00053):Discard operation failed", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
        }
        
    }//GEN-LAST:event_discardBillBtnActionPerformed

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
       
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd<->HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("TimeStamp: " + timeStamp);
        final MessageFormat header1 = new MessageFormat("Bill Summary(Sales) - " + timeStamp);
        final MessageFormat header2 = new MessageFormat("Bill Summary(Free Issue) - " + timeStamp);
        final MessageFormat footer = new MessageFormat("Page");
        
        try{
            salesSummaryTable.print(JTable.PrintMode.FIT_WIDTH, header1, footer);
            freeIssuesTable.print(JTable.PrintMode.FIT_WIDTH, header2, footer);
        }
        catch(Exception e){
            e.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00054):Print operation failed", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }//GEN-LAST:event_printBtnActionPerformed

    private void addMoreSalesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMoreSalesBtnActionPerformed
        dailySalesFinal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_addMoreSalesBtnActionPerformed

    private void saveChangesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveChangesBtnActionPerformed
        final ArrayList brandIDArrayList = dailySalesFinal.getBrandIDList();
        final ArrayList quantityArrayList = dailySalesFinal.getQuantityList();
        final ArrayList tableNameArrayList = dailySalesFinal.getTableNameList();
        final ArrayList billNumberArrayList = dailySalesFinal.getBillNumberList();
        final ArrayList priceArrayList = dailySalesFinal.getPriceList();
        
        if(brandIDArrayList.size() != quantityArrayList.size()){
            final JOptionPane newOptionPane = new JOptionPane("Error(00055):Fatal exception occured. ArrayList sizes don't match", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else{
            
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
            PreparedStatement ps4 = null;
            PreparedStatement ps5 = null;
            PreparedStatement ps6 = null;
            PreparedStatement ps7 = null;
            PreparedStatement ps8 = null;
            ResultSet rs1 = null;
            String firstBillNumber = null;
            boolean exceptionOccured = false;
            
            //check if all the bill numbers match
            for(int i=0; i < brandIDArrayList.size(); i++){
                
                if(i == 0){
                    firstBillNumber = billNumberArrayList.get(i).toString();
                }
                
                if(!firstBillNumber.equals(billNumberArrayList.get(i))){
                    exceptionOccured = true;
                    break;
                }
                
            }
            
            if(exceptionOccured){
                
                final JOptionPane newOptionPane = new JOptionPane("Error(00068):Fata exception.Different bill numbers used in the same bill.\n Bill information cannot be saved", JOptionPane.ERROR_MESSAGE);
                final JDialog newDialog = newOptionPane.createDialog("Warning");
                newDialog.setAlwaysOnTop(true);
                newDialog.setVisible(true);
                
                discardBillBtn.setEnabled(true);
                addMoreSalesBtn.setEnabled(false);
                saveChangesBtn.setEnabled(true);
                addNextBillBtn.setEnabled(false);
                viewDailySummaryBtn.setEnabled(false);
                printBtn.setEnabled(false);
                addDiscountBtn.setEnabled(false);
                removeDiscountBtn.setEnabled(false);
                confirmDiscountBtn.setEnabled(false);
                
            }
            else{
                for(int i=0; i < brandIDArrayList.size(); i++){
                
                    int quantityFromDatabaseVehicle = 0;
                    final int brandID = (int) brandIDArrayList.get(i);
                    final int quantity = (int) quantityArrayList.get(i);
                    final String tableName = tableNameArrayList.get(i).toString();
                    final String billNumber = billNumberArrayList.get(i).toString();


                    final float price = (float) priceArrayList.get(i);

                    final String sqlStatement1 = "SELECT * FROM brand WHERE bID=?";
                    try {
                        ps1 = connection.prepareStatement(sqlStatement1);
                        ps1.setInt(1, brandID);

                        rs1 = ps1.executeQuery();

                        while(rs1.next()){
                            quantityFromDatabaseVehicle = rs1.getInt("quantityVehicle");
                        }

                        ps1.close();
                        rs1.close();

                        quantityFromDatabaseVehicle -= quantity;

                        //update the details of the brand table
                        final String sqlStatement2 = "UPDATE brand SET quantityVehicle=? WHERE bID=?";
                        ps2 = connection.prepareStatement(sqlStatement2);
                        ps2.setInt(1, quantityFromDatabaseVehicle);
                        ps2.setInt(2, brandID);

                        ps2.executeUpdate();

                        ps2.close();

                        //based on the type input inofrmation into either soldsTotal or freeIssueTotal and update stock log
                        if(tableName.equals("Free Issue"))
                        {
                            final String sqlStatement3 = "INSERT INTO freeIssueTotal(bID, quantity, price) VALUES(?,?,?)";
                            ps3 = connection.prepareStatement(sqlStatement3);
                            ps3.setInt(1, brandID);
                            ps3.setInt(2, quantity);
                            ps3.setFloat(3, price);
                            ps3.executeUpdate();

                            ps3.close();

                            final String sqlStatement4 = "INSERT INTO stock(quantity, bID, type, billNumber, price) VALUES(?,?,?,?,?)";
                            ps4 = connection.prepareStatement(sqlStatement4);
                            ps4.setInt(1, quantity);
                            ps4.setInt(2, brandID);
                            ps4.setString(3, "Free Issued");
                            ps4.setString(4, billNumber);
                            ps4.setFloat(5, price);

                            ps4.executeUpdate();
                            ps4.close();

                        }
                        else{
                            final String sqlStatement3 = "INSERT INTO soldsTotal(bID, quantity, price) VALUES(?,?,?)";
                            ps3 = connection.prepareStatement(sqlStatement3);
                            ps3.setInt(1, brandID);
                            ps3.setInt(2, quantity);
                            ps3.setFloat(3, price);
                            ps3.executeUpdate();

                            ps3.close();

                            final String sqlStatement4 = "INSERT INTO stock(quantity, bID, type, billNumber, price) VALUES(?,?,?,?,?)";
                            ps4 = connection.prepareStatement(sqlStatement4);
                            ps4.setInt(1, quantity);
                            ps4.setInt(2, brandID);
                            ps4.setString(3, "Sold");
                            ps4.setString(4, billNumber);
                            ps4.setFloat(5, price);

                            ps4.executeUpdate();
                            ps4.close();

                            //insert the bill number and the discount into discount table if a discount is confirmed
                        }

                    } catch (SQLException ex) {

                        Logger.getLogger(BillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
                        final JOptionPane newOptionPane = new JOptionPane("Error(00056):Save operation failed", JOptionPane.ERROR_MESSAGE);
                        final JDialog newDialog = newOptionPane.createDialog("Warning");
                        newDialog.setAlwaysOnTop(true);
                        newDialog.setVisible(true);
                    }
                }
            
                dailySalesFinal.emptyArrayLists();
                discardBillBtn.setEnabled(false);
                addMoreSalesBtn.setEnabled(false);
                saveChangesBtn.setEnabled(false);
                
                //delete the tables
                final String sqlStatement5 = "DROP TABLE IF EXISTS solds";
                final String sqlStatement6 = "DROP TABLE IF EXISTS freeIssue";
                try {
                    ps5 = connection.prepareStatement(sqlStatement5);
                    ps6 = connection.prepareStatement(sqlStatement6);
                    ps5.executeUpdate();
                    ps6.executeUpdate();

                    ps5.close();
                    ps6.close();
                    
                    //insert the billnumber and the discount into discount table if a discount has been confirmed
                    if(confirmDiscountBtn.isEnabled() == false && addDiscountBtn.isEnabled() == false && removeDiscountBtn.isEnabled() == false){
                    
                        final String sqlStatement7 = "insert into discount(billNumber, discount) values(?,?)";
                        ps7 = connection.prepareStatement(sqlStatement7);
                        ps7.setString(1, firstBillNumber);
                        ps7.setFloat(2, discount);
                        
                        dailySalesFinal.setTotalDiscount(this.confirmedDiscount);
                        
                        
                        
                        ps7.executeUpdate();
                        
                        ps7.close();
                        
                        //insert bill number, final payment and payment into payment table
                        float finalPayment = Float.parseFloat(totalPaymentTextField.getText().toString());
                        final String sqlStatement8 = "insert into payment(billNumber, payment, finalPayment) values(?,?,?)";
                        ps8 = connection.prepareStatement(sqlStatement8);
                        ps8.setString(1, firstBillNumber);
                        ps8.setFloat(2, payment);
                        ps8.setFloat(3, finalPayment);

                        ps8.executeUpdate();
                        ps8.close();
                    }
                    else{
                        
                        discountTextField.setText("0.00");
                        totalPaymentTextField.setText(paymentTextField.getText().toString());
                        //insert bill number, final payment and payment into payment table
                        final String sqlStatement8 = "insert into payment(billNumber, payment, finalPayment) values(?,?,?)";
                        ps8 = connection.prepareStatement(sqlStatement8);
                        ps8.setString(1, firstBillNumber);
                        ps8.setFloat(2, payment);
                        ps8.setFloat(3, payment);

                        ps8.executeUpdate();
                        ps8.close();
                    }
                    
                    
                    addDiscountBtn.setEnabled(false);
                    removeDiscountBtn.setEnabled(false);
                    confirmDiscountBtn.setEnabled(false);
                    
                } catch (SQLException ex) {
                    final JOptionPane newOptionPane = new JOptionPane("Error(00057):Tables cannot be dropped", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
            }
            
            
        }
    }//GEN-LAST:event_saveChangesBtnActionPerformed

    private void addNextBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNextBillBtnActionPerformed
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        
        boolean isNotSaved = saveChangesBtn.isEnabled();
        
        if(isNotSaved)
        {
            
            final JOptionPane newOptionPane = new JOptionPane("Please save the changes of current bill, before continuing to the next one", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else{
            try {
                final String sqlStatement1 = "drop table if exists solds";
                final String sqlStatement2 = "drop table if exists freeIssue";
                final String sqlStatement3 = "create table solds(bID int not null, quantity int not null, price float, constraint fk_solds_brand foreign key(bID) references brand(bID))";
                final String sqlStatement4 = "create table freeIssue(bID int not null, quantity int not null, price float, constraint fk_freeIssue_Brand foreign key(bID) references brand(bID))";
                ps1 = connection.prepareStatement(sqlStatement1);
                ps2 = connection.prepareStatement(sqlStatement2);
                ps3 = connection.prepareStatement(sqlStatement3);
                ps4 = connection.prepareStatement(sqlStatement4);
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();

                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();

                dailySalesFinal.emptyArrayLists();
                dailySalesFinal.setVisible(true);
                this.dispose();

                } catch (SQLException ex) {
                    Logger.getLogger(BillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00058):Operation cannot be performed", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
               
        }
    }//GEN-LAST:event_addNextBillBtnActionPerformed

    private void viewDailySummaryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDailySummaryBtnActionPerformed
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        
        boolean isNotSaved = saveChangesBtn.isEnabled();
        
        if(isNotSaved)
        {
            
            final JOptionPane newOptionPane = new JOptionPane("Please save the changes of current bill, before continuing to the daily summary", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else{
            try {
                final String sqlStatement1 = "drop table if exists solds";
                final String sqlStatement2 = "drop table if exists freeIssue";
                final String sqlStatement3 = "create table solds(bID int not null, quantity int not null, price float, constraint fk_solds_brand foreign key(bID) references brand(bID))";
                final String sqlStatement4 = "create table freeIssue(bID int not null, quantity int not null, price float, constraint fk_freeIssue_Brand foreign key(bID) references brand(bID))";
      
                ps1 = connection.prepareStatement(sqlStatement1);
                ps2 = connection.prepareStatement(sqlStatement2);
                ps3 = connection.prepareStatement(sqlStatement3);
                ps4 = connection.prepareStatement(sqlStatement4);
               
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();
            

                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();
          
                final SalesSummaryFinal salesSummaryFinal = new SalesSummaryFinal(homeFinal, dailySalesFinal);
                dailySalesFinal.emptyArrayLists();
                salesSummaryFinal.setVisible(true);
                this.dispose();

                } catch (SQLException ex) {
                    Logger.getLogger(BillSummaryFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00059):Operation failed", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
                
        }
    }//GEN-LAST:event_viewDailySummaryBtnActionPerformed

    private void addDiscountBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDiscountBtnActionPerformed
        final AddDiscountFinal addDiscountFinal = new AddDiscountFinal(this);
        addDiscountFinal.setVisible(true);
        this.enable(false);
    }//GEN-LAST:event_addDiscountBtnActionPerformed

    private void removeDiscountBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDiscountBtnActionPerformed
        this.discountPercentage = 0;
        loadTextFields();
    }//GEN-LAST:event_removeDiscountBtnActionPerformed

    private void confirmDiscountBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmDiscountBtnActionPerformed
      
        final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to confrim? If onfirmed, you would not be able to add any more sales to this bill");
        
        if(doProceed == JOptionPane.YES_OPTION){
        
            
            this.confirmedDiscount = discount;
            this.addDiscountBtn.setEnabled(false);
            this.removeDiscountBtn.setEnabled(false);
            this.confirmDiscountBtn.setEnabled(false);
            this.addMoreSalesBtn.setEnabled(false);
        }
        
        
        
        
    }//GEN-LAST:event_confirmDiscountBtnActionPerformed

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
            java.util.logging.Logger.getLogger(BillSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BillSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BillSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BillSummaryFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BillSummaryFinal(homeFinal2, dailySalesFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDiscountBtn;
    private javax.swing.JButton addMoreSalesBtn;
    private javax.swing.JButton addNextBillBtn;
    private javax.swing.JButton confirmDiscountBtn;
    private javax.swing.JButton discardBillBtn;
    private javax.swing.JTextField discountTextField;
    private javax.swing.JTable freeIssuesTable;
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
    private javax.swing.JTextField paymentTextField;
    private javax.swing.JButton printBtn;
    private javax.swing.JButton removeDiscountBtn;
    private javax.swing.JTable salesSummaryTable;
    private javax.swing.JButton saveChangesBtn;
    private javax.swing.JTextField totalPaymentTextField;
    private javax.swing.JButton viewDailySummaryBtn;
    // End of variables declaration//GEN-END:variables
}
