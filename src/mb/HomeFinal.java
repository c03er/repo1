/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import Brand_Management.AddBrandFinal;
import Brand_Management.RemoveBrandFinal;
import Brand_Management.UpdateBrandFinal;
import Brand_Management.ViewAllBrandsFinal;
import Database_Management.DBConnection;
import Incident_Management.BackupFinal;
import Incident_Management.RecoverFinal;
import Stock_Management.AddStockFinal;
import Stock_Management.AddToVehicleFinal;
import Stock_Management.DailySalesFinal;
import Stock_Management.PreviousBillSummaryFinal;
import Stock_Management.RemoveStockFromVehicleFinal;
import Stock_Management.RemoveStockFromWarehouseFinal;
import Stock_Management.StockLogFinal;
import Stock_Management.StockSummaryFinal;
import java.awt.Button;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import net.proteanit.sql.DbUtils;
import java.lang.Cloneable;

/**
 *
 * @author pasindu
 */
public final class HomeFinal extends javax.swing.JFrame{

    private Connection connection = null;
    private Dimension dimension = null;
    /**
     * Creates new form HomeFinal
     */
    public HomeFinal() {
        initComponents();
        
        //start the window in the middle of the screen
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
        
        try {
            connection = (Connection) DBConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        //override the windows closing method
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
           closeApplication();
        }
        });
        
        
        loadBrandTableHomeFinal();
        loadStockTableHomeFinal();
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
    
    public final void loadTable(){
        
        loadBrandTableHomeFinal();
        loadStockTableHomeFinal();
    }
    
    private final void closeApplication(){
        
        //check if any windows are open before closing the application.
        final boolean isDailySalesNotOpen = dailySalesBtn.isEnabled();
        
        if(isDailySalesNotOpen){
            final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to exit?");
        
            if(doProceed == JOptionPane.YES_OPTION){
                this.dispose();
            }
        }
        else{
            final JOptionPane newOptionPane = new JOptionPane("Please close all the opened windows", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        
    }
    
    private final void loadStockTableHomeFinal(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select brand.name as 'Brand Name', brand.weight as 'Brand Weight', brand.quantityWarehouse as 'Available Quantity In Warehouse', brand.quantityVehicle as 'Available Quantity In Vehicle' from brand order by brand.name, brand.weight, brand.price";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            stockTableHomeFinal.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            stockTableHomeFinal.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = stockTableHomeFinal.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(5);
          
            
            ps1.close();
            rs1.close();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(HomeFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00001):Cannot load stock table", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
      
    }
    
    private final void loadBrandTableHomeFinal(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price' from brand order by brand.name, brand.weight, brand.price, brand.retailPrice";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            brandTableHomeFinal.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            brandTableHomeFinal.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = brandTableHomeFinal.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(10);
            columnModel.getColumn(2).setPreferredWidth(20);
            columnModel.getColumn(3).setPreferredWidth(30);
            
            ps1.close();
            rs1.close();
  
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00002):Cannot load brand table", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
       
    }
    
    public final JButton getDailySalesBtn(){
        
        return this.dailySalesBtn;
    }
    
    public final JButton getAddBrandBtn(){
        return this.addBrandBtn;
    }
    
    public final JButton getRemoveBrandBtn(){
        return this.removeBrandBtn;
    }
    
    public final JButton getRemoveStockFromWarehouseBtn(){
        return this.removeStockFromWarehouseBtn;
    }
    
    public final JButton getRemoveStockFromVehicleBtn(){
        return this.removeStockFromVehicleBtn;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        brandManagementPanel = new javax.swing.JPanel();
        addBrandBtn = new javax.swing.JButton();
        removeBrandBtn = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        brandTableHomeFinal = new javax.swing.JTable();
        stockManagementPanel = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        dailySalesBtn = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        stockTableHomeFinal = new javax.swing.JTable();
        removeStockFromWarehouseBtn = new javax.swing.JButton();
        removeStockFromVehicleBtn = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        incidentManagementPanel = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1330, 720));
        setMinimumSize(new java.awt.Dimension(1330, 720));
        setPreferredSize(new java.awt.Dimension(1330, 720));
        setResizable(false);

        backgroundPanel.setBackground(new java.awt.Color(244, 244, 244));
        backgroundPanel.setMaximumSize(new java.awt.Dimension(1330, 720));
        backgroundPanel.setMinimumSize(new java.awt.Dimension(1330, 720));
        backgroundPanel.setPreferredSize(new java.awt.Dimension(1330, 720));

        titlePanel.setBackground(new java.awt.Color(244, 244, 244));
        titlePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("MASTER BISCUITS");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addContainerGap(127, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(123, 123, 123))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        brandManagementPanel.setBackground(new java.awt.Color(244, 244, 244));
        brandManagementPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "BRAND MANAGEMENT"));

        addBrandBtn.setText("ADD BRAND");
        addBrandBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBrandBtnActionPerformed(evt);
            }
        });

        removeBrandBtn.setText("REMOVE BRAND");
        removeBrandBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBrandBtnActionPerformed(evt);
            }
        });

        jButton3.setText("UPDATE BRAND");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("VIEW ALL BRANDS");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        brandTableHomeFinal.setBackground(new java.awt.Color(244, 244, 244));
        brandTableHomeFinal.setModel(new javax.swing.table.DefaultTableModel(
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
        brandTableHomeFinal.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(brandTableHomeFinal);

        javax.swing.GroupLayout brandManagementPanelLayout = new javax.swing.GroupLayout(brandManagementPanel);
        brandManagementPanel.setLayout(brandManagementPanelLayout);
        brandManagementPanelLayout.setHorizontalGroup(
            brandManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(brandManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(brandManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(brandManagementPanelLayout.createSequentialGroup()
                        .addComponent(addBrandBtn)
                        .addGap(18, 18, 18)
                        .addComponent(removeBrandBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addGap(0, 21, Short.MAX_VALUE)))
                .addContainerGap())
        );
        brandManagementPanelLayout.setVerticalGroup(
            brandManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, brandManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addGroup(brandManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBrandBtn)
                    .addComponent(removeBrandBtn)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        stockManagementPanel.setBackground(new java.awt.Color(244, 244, 244));
        stockManagementPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "STOCK MANAGEMENT"));

        jButton5.setText("ADD NEW STOCK");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("ADD TO VEHICLE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        dailySalesBtn.setText("DAILY SALES");
        dailySalesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dailySalesBtnActionPerformed(evt);
            }
        });

        jButton8.setText("STOCK SUMMARY");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("STOCK LOG");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        stockTableHomeFinal.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(stockTableHomeFinal);

        removeStockFromWarehouseBtn.setText("REMOVE STOCK FROM WAREHOUSE");
        removeStockFromWarehouseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeStockFromWarehouseBtnActionPerformed(evt);
            }
        });

        removeStockFromVehicleBtn.setText("REMOVE STOCK FROM VEHICLE");
        removeStockFromVehicleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeStockFromVehicleBtnActionPerformed(evt);
            }
        });

        jButton7.setText("PREVIOUS BILL SUMMARY");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout stockManagementPanelLayout = new javax.swing.GroupLayout(stockManagementPanel);
        stockManagementPanel.setLayout(stockManagementPanelLayout);
        stockManagementPanelLayout.setHorizontalGroup(
            stockManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stockManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(stockManagementPanelLayout.createSequentialGroup()
                        .addGroup(stockManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(stockManagementPanelLayout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dailySalesBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9))
                            .addGroup(stockManagementPanelLayout.createSequentialGroup()
                                .addComponent(removeStockFromWarehouseBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeStockFromVehicleBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7)))
                        .addGap(0, 71, Short.MAX_VALUE)))
                .addContainerGap())
        );
        stockManagementPanelLayout.setVerticalGroup(
            stockManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(stockManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(dailySalesBtn)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stockManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeStockFromWarehouseBtn)
                    .addComponent(removeStockFromVehicleBtn)
                    .addComponent(jButton7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        incidentManagementPanel.setBackground(new java.awt.Color(244, 244, 244));
        incidentManagementPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "INCIDENT MANAGEMENT"));

        jButton10.setText("BACKUP");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("RECOVER");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout incidentManagementPanelLayout = new javax.swing.GroupLayout(incidentManagementPanel);
        incidentManagementPanel.setLayout(incidentManagementPanelLayout);
        incidentManagementPanelLayout.setHorizontalGroup(
            incidentManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(incidentManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        incidentManagementPanelLayout.setVerticalGroup(
            incidentManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(incidentManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(incidentManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(brandManagementPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(incidentManagementPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stockManagementPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGap(444, 444, 444)
                        .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addComponent(brandManagementPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(incidentManagementPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(stockManagementPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1345, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBrandBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBrandBtnActionPerformed

        final AddBrandFinal addBrandFinal = new AddBrandFinal(this);
        this.enable(false);
        addBrandFinal.setVisible(true);
         
    }//GEN-LAST:event_addBrandBtnActionPerformed

    private void removeBrandBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBrandBtnActionPerformed
        final RemoveBrandFinal removeBrandFinal = new RemoveBrandFinal(this);
        this.enable(false);
        removeBrandFinal.setVisible(true);
    }//GEN-LAST:event_removeBrandBtnActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        final UpdateBrandFinal updateBrandFinal = new UpdateBrandFinal(this);
        this.enable(false);
        updateBrandFinal.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        final ViewAllBrandsFinal viewAllBrandsFinal = new ViewAllBrandsFinal(this);
        this.enable(false);
        viewAllBrandsFinal.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        final AddStockFinal addStockFinal = new AddStockFinal(this);
        this.enable(false);
        addStockFinal.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            
            final String dropQuery = "drop table if exists addVehicle";
            final String createQuery = "create table addVehicle(bID int not null, quantity int not null, constraint fk_addVehicle_brand foreign key(bID) references brand(bID))";
            ps1 = connection.prepareStatement(dropQuery);
            ps2 = connection.prepareStatement(createQuery);
            
            ps1.executeUpdate();
            ps2.executeUpdate();
            
            ps1.close();
            ps2.close();
            
            final AddToVehicleFinal addToVehicleFinal = new AddToVehicleFinal(this);
            this.enable(false);
            addToVehicleFinal.setVisible(true);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(HomeFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00003):Cannot perform add to vehicle operation", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void dailySalesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailySalesBtnActionPerformed
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        PreparedStatement ps7 = null;
        PreparedStatement ps8 = null;
        PreparedStatement ps9 = null;
        PreparedStatement ps10 = null;
        
        try {
           
            final String sqlStatement1 = "drop table if exists solds";
            final String sqlStatement2 = "create table solds(bID int not null, quantity int not null, price float, constraint fk_solds_brand foreign key(bID) references brand(bID))";
            final String sqlStatement3 = "drop table if exists freeIssue";
            final String sqlStatement4 = "create table freeIssue(bID int not null, quantity int not null, price float, constraint fk_freeIssue_Brand foreign key(bID) references brand(bID))";
            final String sqlStatement5 = "drop table if exists soldsTotal";
            final String sqlStatement6 = "create table soldsTotal(bID int not null, quantity int not null, price float, constraint fk_soldsTotal_brand foreign key(bID) references brand(bID))";
            final String sqlStatement7 = "drop table if exists freeIssueTotal";
            final String sqlStatement8 = "create table freeIssueTotal(bID int not null, quantity int not null, price float, constraint fk_freeIssueTotal_Brand foreign key(bID) references brand(bID))";
            final String sqlStatement9 = "drop table if exists brandCopy";
            final String sqlStatement10 = "create table brandCopy as select * from brand";
            
            ps1 = connection.prepareStatement(sqlStatement1);
            ps2 = connection.prepareStatement(sqlStatement2);
            ps3 = connection.prepareStatement(sqlStatement3);
            ps4 = connection.prepareStatement(sqlStatement4);
            ps5 = connection.prepareStatement(sqlStatement5);
            ps6 = connection.prepareStatement(sqlStatement6);
            ps7 = connection.prepareStatement(sqlStatement7);
            ps8 = connection.prepareStatement(sqlStatement8);
            ps9 = connection.prepareStatement(sqlStatement9);
            ps10 = connection.prepareStatement(sqlStatement10);
            
            
            ps1.executeUpdate();
            ps2.executeUpdate();
            ps3.executeUpdate();
            ps4.executeUpdate();
            ps5.executeUpdate();
            ps6.executeUpdate();
            ps7.executeUpdate();
            ps8.executeUpdate();
            ps9.executeUpdate();
            ps10.executeUpdate();
            
            ps1.close();
            ps2.close();
            ps3.close();
            ps4.close();
            ps5.close();
            ps6.close();
            ps7.close();
            ps8.close();
            ps9.close();
            ps10.close();
            
            DailySalesFinal dailySalesFinal = new DailySalesFinal(this);
            this.dailySalesBtn.setEnabled(false);
            this.addBrandBtn.setEnabled(false);
            this.removeBrandBtn.setEnabled(false);
            this.removeStockFromWarehouseBtn.setEnabled(false);
            this.removeStockFromVehicleBtn.setEnabled(false);
            dailySalesFinal.setVisible(true);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(HomeFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00004):Cannot perform daily sales operation", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        
    }//GEN-LAST:event_dailySalesBtnActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        final StockLogFinal stockLogFinal = new StockLogFinal(this);
        this.enable(false);
        stockLogFinal.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        final StockSummaryFinal stockSummaryFinal = new StockSummaryFinal(this);
        this.enable(false);
        stockSummaryFinal.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        final BackupFinal backupFinal = new BackupFinal(this);
        this.enable(false);
        backupFinal.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        final RecoverFinal recoverFinal = new RecoverFinal(this);
        this.enable(false);
        recoverFinal.setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void removeStockFromWarehouseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeStockFromWarehouseBtnActionPerformed
        final RemoveStockFromWarehouseFinal removeStockFromWarehouseFinal = new RemoveStockFromWarehouseFinal(this);
        this.enable(false);
        removeStockFromWarehouseFinal.setVisible(true);
    }//GEN-LAST:event_removeStockFromWarehouseBtnActionPerformed

    private void removeStockFromVehicleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeStockFromVehicleBtnActionPerformed
        final RemoveStockFromVehicleFinal removeStockFromVehicleFinal = new RemoveStockFromVehicleFinal(this);
        this.enable(false);
        removeStockFromVehicleFinal.setVisible(true);
    }//GEN-LAST:event_removeStockFromVehicleBtnActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        final PreviousBillSummaryFinal previousBillSummmaryFinal = new PreviousBillSummaryFinal(this);
        this.enable(false);
        previousBillSummmaryFinal.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(HomeFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeFinal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBrandBtn;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JPanel brandManagementPanel;
    private javax.swing.JTable brandTableHomeFinal;
    private javax.swing.JButton dailySalesBtn;
    private javax.swing.JPanel incidentManagementPanel;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton removeBrandBtn;
    private javax.swing.JButton removeStockFromVehicleBtn;
    private javax.swing.JButton removeStockFromWarehouseBtn;
    private javax.swing.JPanel stockManagementPanel;
    private javax.swing.JTable stockTableHomeFinal;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables
}
