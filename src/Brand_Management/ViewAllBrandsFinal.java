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
public final class ViewAllBrandsFinal extends javax.swing.JFrame {

    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    /**
     * Creates new form ViewAllBrands
     */
    public ViewAllBrandsFinal(HomeFinal homeFinal) {
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
        
        loadBrandTableViewAllBrands();
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
    
    private final void loadBrandsSearchTableViewAllBrands(String category, String value){
 
        String sqlStatement1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
      
            if(category.equals("Brand ID")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.bID LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price, brand.retailPrice";
            }
            else if(category.equals("Brand Name")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.name LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price, brand.retailPrice";
            }
            else if(category.equals("Brand Weight")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.weight LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price, brand.retailPrice";
            }
            else if(category.equals("Wholesale Price")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.price LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price, brand.retailPrice";
            }
            else if(category.equals("Retail Price")){
                sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand WHERE brand.retailPrice LIKE '%"+value+"%' ORDER BY brand.name, brand.weight, brand.price, brand.retailPrice";
            }
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            brandTableViewAllBrand.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            brandTableViewAllBrand.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = brandTableViewAllBrand.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
     
        } catch (SQLException ex) {
            Logger.getLogger(RemoveBrandFinal.class.getName()).log(Level.SEVERE, null, ex);
            final JOptionPane newOptionPane = new JOptionPane("Error(00016):Cannot load all brands table for searching", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
       
    }
    
    
    private final void loadBrandTableViewAllBrands(){
 
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "SELECT bID as 'Brand ID', brand.name as 'Brand Name', weight as 'Weight(g)', price as 'Wholesale Price', brand.retailPrice as 'Retail Price', unitsPerBox as 'Units Per Box' FROM brand order by brand.name, brand.weight, brand.price, brand.retailPrice";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            brandTableViewAllBrand.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            brandTableViewAllBrand.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = brandTableViewAllBrand.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
                  
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("Error(00017):Cannot load all brands table", JOptionPane.ERROR_MESSAGE);
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

        viewAllBrandsPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        categoryComboBoxViewAllBrands = new javax.swing.JComboBox<>();
        ViewAllBrandsSearchTextField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        brandTableViewAllBrand = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        viewAllBrandsPanel.setBackground(new java.awt.Color(244, 244, 244));
        viewAllBrandsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "VIEW ALL BRANDS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        viewAllBrandsPanel.setPreferredSize(new java.awt.Dimension(1360, 768));

        jLabel3.setText("CATEGORY");

        categoryComboBoxViewAllBrands.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brand ID", "Brand Name", "Brand Weight", "Wholesale Price", "Retail Price", " " }));

        jButton3.setText("SEARCH");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        brandTableViewAllBrand.setModel(new javax.swing.table.DefaultTableModel(
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
        brandTableViewAllBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brandTableViewAllBrandMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                brandTableViewAllBrandMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                brandTableViewAllBrandMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(brandTableViewAllBrand);

        jButton1.setText("EXIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout viewAllBrandsPanelLayout = new javax.swing.GroupLayout(viewAllBrandsPanel);
        viewAllBrandsPanel.setLayout(viewAllBrandsPanelLayout);
        viewAllBrandsPanelLayout.setHorizontalGroup(
            viewAllBrandsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewAllBrandsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewAllBrandsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(viewAllBrandsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(categoryComboBoxViewAllBrands, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ViewAllBrandsSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(0, 126, Short.MAX_VALUE)))
                .addContainerGap())
        );
        viewAllBrandsPanelLayout.setVerticalGroup(
            viewAllBrandsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewAllBrandsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(viewAllBrandsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categoryComboBoxViewAllBrands, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ViewAllBrandsSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewAllBrandsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewAllBrandsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        final String value = ViewAllBrandsSearchTextField.getText().toString();
        final String selectedCategory = categoryComboBoxViewAllBrands.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadBrandTableViewAllBrands();
        }
        else{
            loadBrandsSearchTableViewAllBrands(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void brandTableViewAllBrandMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableViewAllBrandMouseClicked
        
    }//GEN-LAST:event_brandTableViewAllBrandMouseClicked

    private void brandTableViewAllBrandMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableViewAllBrandMouseEntered

    }//GEN-LAST:event_brandTableViewAllBrandMouseEntered

    private void brandTableViewAllBrandMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brandTableViewAllBrandMousePressed
        
    }//GEN-LAST:event_brandTableViewAllBrandMousePressed

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
            java.util.logging.Logger.getLogger(ViewAllBrandsFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewAllBrandsFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewAllBrandsFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewAllBrandsFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewAllBrandsFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ViewAllBrandsSearchTextField;
    private javax.swing.JTable brandTableViewAllBrand;
    private javax.swing.JComboBox<String> categoryComboBoxViewAllBrands;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel viewAllBrandsPanel;
    // End of variables declaration//GEN-END:variables
}
