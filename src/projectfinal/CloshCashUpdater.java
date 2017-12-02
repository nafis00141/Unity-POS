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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nafis
 */
public class CloshCashUpdater {
    
    
    String conString ="jdbc:mysql://localhost:3306/nafis";
    String username ="root";
    String passward ="";
    
    /*public boolean searchemp(){
        
        String sql = "SELECT * FROM  `cash`  WHERE  `reason` ='employee payment'"; 
        
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
    }*/
    
    
    public boolean search(String a){
        
        String sql = "SELECT * FROM  `cash`  WHERE  `reason` ='"+a+"'"; 
        
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
    
    
    public float employeemoney(){
        
        float mon = 0;
        
        String sql = "SELECT * FROM  `employeepay`";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            
            
            while(rs.next()){
                
                String money = rs.getString(3);
                
                mon = mon + Float.parseFloat(money);
                
                
            }
            
            
            
            return mon;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return mon;
        
    }
    
    
    public float slodmoney(){
        
        float mon = 0;
        
        String sql = "SELECT * FROM  `sold`";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            
            
            while(rs.next()){
                
                String qua = rs.getString(3);
                
                String money = rs.getString(4);
                
                mon = mon + Float.parseFloat(money)*Integer.parseInt(qua);
                
                
            }
            
            
            //System.out.println(mon);
            return mon;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return mon;
        
    }
    
    public float buymoney(){
        
        float mon = 0;
        
        String sql = "SELECT * FROM  `sold`";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            
            
            while(rs.next()){
                
                String qua = rs.getString(3);
                
                String money = rs.getString(7);
                
                mon = mon + Float.parseFloat(money)*Integer.parseInt(qua);
                
                
            }
            
            
            //System.out.println(mon);
            return mon;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return mon;
        
    }
    
    
    
    
    public void update(String reason,float money){
        
        
        Date dNow = new Date( );
                    
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd");
        String date = ""+ft.format(dNow);
        
        String sql = "UPDATE `cash` SET `money`='"+money+"',`date`='"+date+"' WHERE `reason` ='"+reason+"'"; 
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    public void add(String cinout,String reason,float money){
        
        Date dNow = new Date( );
                    
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd");
        String date = ""+ft.format(dNow);
        
        String sql = "INSERT INTO `cash`(`id`, `cashinout`, `reason`, `money`, `date`) VALUES (NULL,'"+cinout+"','"+reason+"','"+money+"','"+date+"')"; 
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
 
        
    }
    
    
    public DefaultTableModel getData(){
        
        DefaultTableModel dm = new DefaultTableModel();
        
        dm.addColumn("Date");
        dm.addColumn("Cash In/Out");
        dm.addColumn("Reason");
        dm.addColumn("Money");
        
        
        String sql = "SELECT * FROM `cash`";
        
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            while(rs.next()){
               
                String d = rs.getString(5);
                String c = rs.getString(2);
                String r = rs.getString(3);
                String m = rs.getString(4);
                
                dm.addRow(new String[]{d,c,r,m});
                
            }
            
            return dm;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    
    public boolean drop(){
        
        Date dNow = new Date( );
                    
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
    
        String date= ""+ft.format(dNow);
        
        String sql1 = "DELETE FROM employeepay"; 
        String sql2 = "DELETE FROM sold";
        String sql3 = "DELETE FROM soldtime";
        String sql4 = "DELETE FROM cash";
        String sql5 = "UPDATE sellno SET number='0'";
        String sql6 = "DELETE FROM `todaysell`";
        String sql7 = "ALTER TABLE employeepay AUTO_INCREMENT = 1";
        String sql8 = "ALTER TABLE sold AUTO_INCREMENT = 1";
        String sql9 = "ALTER TABLE soldtime AUTO_INCREMENT = 1";
        String sql10 = "ALTER TABLE cash AUTO_INCREMENT = 1";
        String sql11 = "ALTER TABLE todaysell AUTO_INCREMENT = 1";
        String adddate = "INSERT INTO `todaysell`(`no`, `name`, `quantity`, `price`, `buyprice`, `type`, `date`) VALUES (NULL,'a','0','0','0','NULL','"+date+"')";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s1 =(PreparedStatement) con.prepareStatement(sql1);
            PreparedStatement s2 =(PreparedStatement) con.prepareStatement(sql2);
            PreparedStatement s3 =(PreparedStatement) con.prepareStatement(sql3);
            PreparedStatement s4 =(PreparedStatement) con.prepareStatement(sql4);
            PreparedStatement s5 =(PreparedStatement) con.prepareStatement(sql5);
            PreparedStatement s6 =(PreparedStatement) con.prepareStatement(sql6);
            PreparedStatement s7 =(PreparedStatement) con.prepareStatement(sql7);
            PreparedStatement s8 =(PreparedStatement) con.prepareStatement(sql8);
            PreparedStatement s9 =(PreparedStatement) con.prepareStatement(sql9);
            PreparedStatement s10 =(PreparedStatement) con.prepareStatement(sql10);
            PreparedStatement s11 =(PreparedStatement) con.prepareStatement(sql11);
            PreparedStatement s =(PreparedStatement) con.prepareStatement(adddate);
            
            
            
            s1.execute(sql1);
            s2.execute(sql2);
            s3.execute(sql3);
            s4.execute(sql4);
            s5.execute(sql5);
            s6.execute(sql6);
            s7.execute(sql7);
            s8.execute(sql8);
            s9.execute(sql9);
            s10.execute(sql10);
            s11.execute(sql11);
            s.execute(adddate);
            
            return true;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    
        return false;
    }
    
    
    public String getStartDate(){
        
        String sql = "SELECT * FROM `soldtime`";
        String d="";
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            if(rs.next()){
               
                d = rs.getString(6);
                //d.substring(11, d.length());
                d=d.substring(0, 10);
                
                
            }
            
            return d;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return d;
    }
    
    
}
