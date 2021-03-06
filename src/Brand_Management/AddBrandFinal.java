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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import mb.HomeFinal;

/**
 *
 * @author pasindu
 */
public final class AddBrandFinal extends javax.swing.JFrame {

    private Connection connection = null;
    private Dimension dimension = null;
    private HomeFinal homeFinal = null;
    public static HomeFinal homeFinal2 = null;
    
    /**
     * Creates new form AddBrand
     */
    public AddBrandFinal(HomeFinal homeFinal) {
        
        initComponents();
        this.homeFinal = homeFinal;
        this.homeFinal2 = homeFinal;
            
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
        
        //obtain a new database connection;
        try {
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //override windowClosing method
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
           closeApplication();
        }
        });
        
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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addBrandPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        brandNameTextField = new javax.swing.JTextField();
        brandWeightTextField = new javax.swing.JTextField();
        wholesalePriceTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        unitsPerBoxTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        retailPriceTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));
        setResizable(false);

        addBrandPanel.setBackground(new java.awt.Color(244, 244, 244));
        addBrandPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "ADD BRAND", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        addBrandPanel.setPreferredSize(new java.awt.Dimension(1360, 768));

        jLabel1.setText("BRAND NAME");

        jLabel3.setText("BRAND WEIGHT");

        jLabel4.setText("WHOLESALE PRICE");

        brandNameTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        brandWeightTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        wholesalePriceTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setText("EXIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("CLEAR");
        jButton2.setToolTipText("");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("ADD BRAND");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("UNITS PER BOX");

        jLabel5.setText("RETAIL PRICE");

        javax.swing.GroupLayout addBrandPanelLayout = new javax.swing.GroupLayout(addBrandPanel);
        addBrandPanel.setLayout(addBrandPanelLayout);
        addBrandPanelLayout.setHorizontalGroup(
            addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBrandPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addBrandPanelLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addContainerGap(309, Short.MAX_VALUE))
                    .addGroup(addBrandPanelLayout.createSequentialGroup()
                        .addGroup(addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(33, 33, 33)
                        .addGroup(addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(unitsPerBoxTextField)
                            .addComponent(wholesalePriceTextField)
                            .addComponent(brandWeightTextField)
                            .addComponent(brandNameTextField)
                            .addComponent(retailPriceTextField))
                        .addGap(96, 96, 96))))
        );
        addBrandPanelLayout.setVerticalGroup(
            addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBrandPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(brandNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(brandWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wholesalePriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(11, 11, 11)
                .addGroup(addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(retailPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(unitsPerBoxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(addBrandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addBrandPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addBrandPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        homeFinal.loadTable();
        homeFinal.enable(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        final String brandName = brandNameTextField.getText().toString();
        final String brandWeight = brandWeightTextField.getText().toString();
        final String unitPrice = wholesalePriceTextField.getText().toString();
        final String retailPrice = retailPriceTextField.getText().toString();
        final String unitsPerBox = unitsPerBoxTextField.getText().toString();
        
        if(brandName.isEmpty() || brandWeight.isEmpty() || unitPrice.isEmpty() || unitsPerBox.isEmpty() || retailPrice.isEmpty()){
            
            final JOptionPane newOptionPane = new JOptionPane("Please fill all the fields before continuing", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(!Methods.isInt(brandWeight)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for weight", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            brandWeightTextField.setText(null);
            brandWeightTextField.requestFocus();
        }
        else if(!Methods.isInt(unitsPerBox)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for units per box field", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            unitsPerBoxTextField.setText(null);
            unitsPerBoxTextField.requestFocus();
        }
        else if(!Methods.isFloat(unitPrice)){
            
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for wholesale price", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            wholesalePriceTextField.setText(null);
            wholesalePriceTextField.requestFocus();
        }
        else if(!Methods.isFloat(retailPrice)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid number for retail price", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            retailPriceTextField.setText(null);
            retailPriceTextField.requestFocus();
        }
        else if(Methods.isBrandAdded(brandName, Float.parseFloat(brandWeight))){
            
            final JOptionPane newOptionPane = new JOptionPane("This particular brand is already added. Please check again", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else{
            final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(doProceed == 0){
                
                final int brandWeightInt = Integer.parseInt(brandWeight);
                final int unitsPerBoxInt = Integer.parseInt(unitsPerBox);
                final float unitPriceFloat = Float.parseFloat(unitPrice);
                final float retailPriceFloat = Float.parseFloat(retailPrice);
                
                final String sqlStatement1 = "insert into brand(name, weight, price, retailPrice, unitsPerBox) values(?,?,?,?,?)";
                
                PreparedStatement ps1 = null;
                
                try {
                    
                    ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setString(1, brandName);
                    ps1.setInt(2, brandWeightInt);
                    ps1.setFloat(3, unitPriceFloat);
                    ps1.setFloat(4, retailPriceFloat);
                    ps1.setInt(5, unitsPerBoxInt);
                    
                    ps1.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "New Brand Added Successfully");
                    
                    ps1.close();
                    
                    homeFinal.loadTable();
                    homeFinal.enable(true);
                    this.dispose();
                    
                } catch (SQLException ex) {

                    Logger.getLogger(AddBrandFinal.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("Error(00005):Add new brand operation failed.SQLException occured", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Warning");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
                
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        brandNameTextField.setText(null);
        brandWeightTextField.setText(null);
        wholesalePriceTextField.setText(null);
        retailPriceTextField.setText(null);
        unitsPerBoxTextField.setText(null);
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
            java.util.logging.Logger.getLogger(AddBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBrandFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddBrandFinal(homeFinal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addBrandPanel;
    private javax.swing.JTextField brandNameTextField;
    private javax.swing.JTextField brandWeightTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField retailPriceTextField;
    private javax.swing.JTextField unitsPerBoxTextField;
    private javax.swing.JTextField wholesalePriceTextField;
    // End of variables declaration//GEN-END:variables
}
