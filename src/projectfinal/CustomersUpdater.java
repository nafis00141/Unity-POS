/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectfinal;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nafis
 */
public class CustomersUpdater {
    
    
    String conString ="jdbc:mysql://localhost:3306/nafis";
    String username ="root";
    String passward ="";
    
    
    public DefaultTableModel getData(){
        
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("ID");
        dm.addColumn("First Name");
        dm.addColumn("Last Name");
        dm.addColumn("Membership Type");
        dm.addColumn("Discount");
        dm.addColumn("Card Number");
        
        
        String sql = "SELECT * FROM customer";
        
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            while(rs.next()){
                String id = rs.getString(1);
                String fName = rs.getString(2);
                String lName = rs.getString(3);
                String mType = rs.getString(4);
                String dis = rs.getString(5)+"%";
                String cNumber = rs.getString(6);
                
                
                dm.addRow(new String[]{id,fName,lName,mType,dis,cNumber});
                
            }
            
            return dm;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    
    
    public boolean add(String fname,String lname,String mtype,String dis,String card){
        
        String sql="INSERT INTO customer(`id`, `First name`, `Last name`, `Membership Type`, `discount`, `card`) VALUES(NULL,'"+fname+"','"+lname+"','"+mtype+"','"+dis+"','"+card+"')";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    
    public boolean update(String id,String fname,String lname,String mtype,String dis,String card){
        
        
        String sql="UPDATE `customer` SET `First name`='"+fname+"',`Last name`='"+lname+"',`Membership Type`='"+mtype+"',`discount`='"+dis+"',`card`='"+card+"' WHERE `id`='"+id+"'";
        
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
        
    }
    
    
    
    
    public boolean delete(String id){
        
        String sql="DELETE FROM customer WHERE id='"+id+"'";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
        
    }
    
    
    public DefaultTableModel search(int choice,String text){
        
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("ID");
        dm.addColumn("First Name");
        dm.addColumn("Last Name");
        dm.addColumn("Membership Type");
        dm.addColumn("Discount");
        dm.addColumn("Card Number");
        
        String sql=null;
        
        
        if(choice==0){
            System.out.println("0");
            sql="SELECT * FROM `customer` WHERE `Card`=?";
            
        }
        else if(choice==1){
            
            System.out.println("1");
            sql="SELECT * FROM `customer` WHERE `First name`=?";
            
        }
        else if(choice==2){
            
            System.out.println("2");
            sql="SELECT * FROM `customer` WHERE `Membership Type`=?";
            
        }
        
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            s.setString(1,text);
            
            ResultSet rs = s.executeQuery();
            
            while(rs.next()){
                String i = rs.getString(1);
                String f = rs.getString(2);
                String l = rs.getString(3);
                String mt = rs.getString(4);
                String ds = rs.getString(5);
                String c = rs.getString(5);
                

               dm.addRow(new String[]{i,f,l,mt,ds,c});
                
            }
            
            return dm;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        //System.out.println("---");
        return null;
    }
    
    
}
