/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectfinal;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nafis
 */
public class CheckInOutUpdater {
    
    
    String conString ="jdbc:mysql://localhost:3306/nafis";
    String username ="root";
    String passward ="";
    
    public DefaultTableModel search(String id){
        
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("ID");
        dm.addColumn("First Name");
        dm.addColumn("Last Name");
        //dm.addColumn("Post");
        dm.addColumn("Per Hour");
        //dm.addColumn("Start Work");
        dm.addColumn("Last Work");
        dm.addColumn("Start Time");
        dm.addColumn("END Time");
        dm.addColumn("Hours Worked");
        
        
        
        String sql = "SELECT * FROM employee WHERE id="+id;
        
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            while(rs.next()){
                String i = rs.getString(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                //String p = rs.getString(4);
                String mone = rs.getString(5);
                //String sw = rs.getString(6);
                String lw = rs.getString(7);
                String st = rs.getString(8);
                String et = rs.getString(9);
                String hw = rs.getString(10);
                
                dm.addRow(new String[]{i,fname,lname,mone,lw,st,et,hw});
                
            }
            
            return dm;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    
}
