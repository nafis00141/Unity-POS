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

/**
 *
 * @author Nafis
 */
public class updatingTodaySell {
    
    
    String conString ="jdbc:mysql://localhost:3306/nafis";
    String username ="root";
    String passward ="";
    
    public void add(String name,String qua,String price,String bp,String type,String date){
        
        String sql="INSERT INTO `todaysell`(`no`, `name`, `quantity`, `price`, `buyprice`, `type`, `date`) VALUES (NULL,'"+name+"','"+qua+"','"+price+"','"+bp+"','"+type+"','"+date+"')";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    public void update(String name,String qua,String price,String date){
        
        
        String sql="UPDATE `todaysell` SET quantity=quantity+'"+qua+"',`date`='"+date+"' WHERE `buyprice`='"+price+"' AND `name`='"+name+"'";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            System.out.println("donee");
            s.execute(sql);
            System.out.println("ddd");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    public boolean checkdate(String date){
        
        String sql = "SELECT * FROM todaysell ORDER BY no ASC LIMIT 1";
        
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            String d= "";
            
            while(rs.next()){
                
                d = rs.getString(7);
            }
            
            System.out.println(d);
            System.out.println(date);
            if(date.equalsIgnoreCase(d)){
                System.out.println(d);
                return true;
            }
            else return false;
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
        
    }
    
    
    public boolean searchtodaysell(String name,String buyprice){
        
        String sql = "SELECT * FROM `todaysell` WHERE `name` ='"+name+"' AND `buyprice`='"+buyprice+"'";
        
        
       
        String st;
        
        try{
            
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.createStatement();           
            
            
            
            
            
            ResultSet rs = s.executeQuery(sql);
            
            
            
            while(rs.next()){
                
                return true;
 
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return false;
    }
    
    
    
    
    
}
