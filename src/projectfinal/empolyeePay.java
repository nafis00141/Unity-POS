/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectfinal;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Nafis
 */
public class empolyeePay {
    
    String conString ="jdbc:mysql://localhost:3306/nafis";
    String username ="root";
    String passward ="";
    
    public boolean search(String a){
        
        String sql = "SELECT * FROM  `employeepay`  WHERE  `empid` ='"+a+"'"; 
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            if(rs.next()){
                return true;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    public void update(String id,String money){
        
        String sql = "UPDATE `employeepay` SET `money`='"+money+"' WHERE `empid`='"+id+"'"; 
        String sql1 = "UPDATE `employee` SET `hourwork`='0' WHERE `id`='"+id+"'";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            PreparedStatement p =(PreparedStatement) con.prepareStatement(sql1);
            
            s.execute(sql);
            p.execute(sql1);
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void add(String id,String money){
            
            System.out.println(id);
            
            System.out.println(money);
            String sql = "INSERT INTO `employeepay`(`no`, `empid`, `money`) VALUES (NULL,'"+id+"','"+money+"')";
            String sql1 = "UPDATE `employee` SET `hourwork`='0' WHERE `id`='"+id+"'";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            PreparedStatement p =(PreparedStatement) con.prepareStatement(sql1);
            
            s.execute(sql);
            p.execute(sql1);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }    
        
    }
    
}
