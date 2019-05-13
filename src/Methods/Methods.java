/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import Database_Management.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javax.swing.table.TableColumnModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author pasindu
 */
public class Methods {
    
    private static Connection connection = null;
    private static PreparedStatement ps = null;
    private static String sqlStatement = null;
    
    private static void methodsGetConnection(){
        try {
            //obtain a new database connection;
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static boolean isFloat(String inputString){
        
        try{
            Float.parseFloat(inputString);
        }
        catch(NumberFormatException e){
            return false;
        }
        catch(NullPointerException e){
            return false;
        }
        
        return true;
    }
    
    
    public static boolean isInt(String inputString){
        
        try{
            Integer.parseInt(inputString);
        }
        catch(NumberFormatException e){
            return false;
        }
        catch(NullPointerException e){
            return false;
        }
        
        return true;
    }
    
    public static boolean isBrandAdded(String brandName, float brandWeight){
        
        methodsGetConnection();
        
        sqlStatement = "SELECT * FROM brand WHERE name = ? AND weight = ?";
        
        try {
            ps = connection.prepareStatement(sqlStatement);
            
            ps.setString(1, brandName);
            ps.setFloat(2, brandWeight);
            
            ResultSet rs1 = ps.executeQuery();
            
            if(rs1.next()){
                return true;
            }
            else{
                return false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
}
