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




public class EmployeeUpdater {
    
    String conString ="jdbc:mysql://localhost:3306/nafis";
    String username ="root";
    String passward ="";
    
    public boolean add(String First_name,String Last_name,String Post,String salary,String pass){
        
        String sql="INSERT INTO `employee` (`First name`, `Last name`, `Post`, `Salary` , `password`) VALUES('"+First_name+"','"+Last_name+"','"+Post+"','"+salary+"','"+pass+"')";
        
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
    
    public DefaultTableModel getData(){
        
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("ID");
        dm.addColumn("First name");
        dm.addColumn("Last name");
        dm.addColumn("Post");
        dm.addColumn("salary");
        
        
        String sql = "SELECT * FROM employee";
        
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            while(rs.next()){
                String i = rs.getString(1);
                String f = rs.getString(2);
                if(f.equalsIgnoreCase("nafis")) continue;
                String l = rs.getString(3);
                String p = rs.getString(4);
                String sa = rs.getString(5);
                
                
                dm.addRow(new String[]{i,f,l,p,sa});
                
            }
            
            return dm;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    public boolean update(String id,String First_name,String Last_name,String Post,String salary){
        
        
        String sql="UPDATE  `employee` SET  `First name` =  '"+First_name+"',`Last name` =  '"+Last_name+"',`Post` =  '"+Post+"',`Salary` =  '"+salary+"' WHERE  `id` =  '"+id+"'";
        
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
        
        String sql="DELETE FROM employee WHERE id='"+id+"'";
        
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
        dm.addColumn("First name");
        dm.addColumn("Last name");
        dm.addColumn("Post");
        dm.addColumn("Salary");
        
        String sql=null;
        
        
        if(choice==0){
            
            sql="SELECT * FROM  `employee` WHERE  `First name`=?";
            
        }
        else if(choice==1){
            
            sql="SELECT * FROM `employee` WHERE `Last name`=?";
            
        }
        else if(choice==2){
            
            sql="SELECT * FROM `employee` WHERE `Post`=?";
            
        }
        else if(choice==3){
            
            sql="SELECT * FROM `employee` WHERE `Salary`=?";
            
        }
        else if(choice==4){
            
            sql="SELECT * FROM `employee` WHERE `id`=?";
            
        }
        
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            s.setString(1,text);
            
            ResultSet rs = s.executeQuery();
            
            while(rs.next()){
                String i = rs.getString(1);
                String f = rs.getString(2);
                if(f.equalsIgnoreCase("nafis")) continue;
                String l = rs.getString(3);
                String p = rs.getString(4);
                String sa = rs.getString(5);
                

               dm.addRow(new String[]{i,f,l,p,sa});
                
            }
            
            return dm;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        System.out.println("---");
        return null;
    }
    
    
}