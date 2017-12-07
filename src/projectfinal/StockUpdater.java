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
public class StockUpdater {
    
    String conString ="jdbc:mysql://localhost:3306/nafis";
    String username ="root";
    String passward ="";
    
    public boolean add(String name,int stock,float price,String type,String weight,String buy_price,String barcode,int di){
        
        String sql="INSERT INTO product(`id`, `name`, `stock`, `price`, `type`, `weight`, `buy price`, `barcode`) VALUES(NULL,'"+name+"','"+stock+"','"+price+"','"+type+"','"+weight+"','"+buy_price+"','"+barcode+"')";
        
        try{
            
            System.out.println("here add");
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            sql = "SELECT MAX(ID) from product";
            
            s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs = s.executeQuery(sql);
            
            if(rs.next()){
            
                String sp = rs.getString(1);

                System.out.println(sp);
                
                int id = Integer.parseInt( sp );
            
                sql = "INSERT INTO `stockhistory`(`eid`, `sid`, `Operation`, `Time`, `stock before update`, `stock after update`) VALUES ('"+di+"','"+id+"','inserted',NOW(),'0','"+stock+"')";


                s =(Statement) con.prepareStatement(sql);


                s.execute(sql);
            
            }
            else{
                System.out.println("You are fucked");
            }
            
            /**/
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public DefaultTableModel getData(){
        
        DefaultTableModel dm = new DefaultTableModel(){
             @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dm.addColumn("ID");
        dm.addColumn("Name");
        dm.addColumn("Stock");
        dm.addColumn("Price");
        dm.addColumn("Type");
        dm.addColumn("Weight");
        dm.addColumn("Buy Price");
        dm.addColumn("BarCode");
        
        String sql = "SELECT * FROM product";
        
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            while(rs.next()){
                String id = rs.getString(1);
                String name = rs.getString(2);
                String price = rs.getString(3);
                String stock = rs.getString(4);
                String type = rs.getString(5);
                String weight = rs.getString(6);
                String buy_price = rs.getString(7);
                String barcode = rs.getString(8);
                
                dm.addRow(new String[]{id,name,price,stock,type,weight,buy_price,barcode});
                
            }
            
            return dm;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    public boolean update(String id,String name,int stock,float price,String type,String weight,String buy_price,String barcode,int di){
        
        
        String sql = "select stock from `product` WHERE `id`='"+id+"'";
        int pre_stock = 0;
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            if(rs.next()){
                pre_stock = Integer.parseInt(rs.getString(1));
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
        
        
        sql="UPDATE `product` SET `name`='"+name+"',`stock`='"+stock+"',`price`='"+price+"',`type`='"+type+"',`weight`='"+weight+"',`buy price`='"+buy_price+"',`barcode`='"+barcode+"' WHERE `id`='"+id+"'";
        
        
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            sql = "INSERT INTO `stockhistory`(`eid`, `sid`, `Operation`, `Time`, `stock before update`, `stock after update`) VALUES ('"+di+"','"+id+"','updated',NOW(),'"+pre_stock+"','"+stock+"')";
            
            
            System.out.println(sql);
            
            s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
        
    }
    
    public boolean update(int iid,int stock,int di){
        
        
        String sql = "select stock from `product` WHERE `id`='"+iid+"'";
        int pre_stock = 0;
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            if(rs.next()){
                pre_stock = Integer.parseInt(rs.getString(1));
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
        
        
        sql="UPDATE `product` SET `stock`='"+stock+"' WHERE `id`='"+iid+"'";
        
        
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            sql = "INSERT INTO `stockhistory`(`eid`, `sid`, `Operation`, `Time`, `stock before update`, `stock after update`) VALUES ('"+di+"','"+iid+"','updated',NOW(),'"+pre_stock+"','"+stock+"')";
            
            
            System.out.println(sql);
            
            s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
        
    }
    
    
    public boolean delete(String id,int di){
        
        String sql="DELETE FROM product WHERE id='"+id+"'";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            sql = "INSERT INTO `stockhistory`(`eid`, `sid`, `Operation`, `Time`, `stock before update`, `stock after update`) VALUES ('"+di+"','"+id+"','deleted',NOW(),'0','0')";
            
            s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
        
    }
    
    public DefaultTableModel search(String name,String stock,String price,String type,String weight,String buy_price){
        
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("ID");
        dm.addColumn("Name");
        dm.addColumn("Stock");
        dm.addColumn("Price");
        dm.addColumn("Type");
        dm.addColumn("Weight");
        dm.addColumn("Buy Price");
        dm.addColumn("BarCode");
        
        String sql=null;
        String a=null;
        
        if(name.equalsIgnoreCase("")==false){
            
            
            a=name;
            
            sql="SELECT * FROM product WHERE name LIKE  '"+a+"%' ";
        }
        else if(stock.equalsIgnoreCase("")==false){
            
            sql="SELECT * FROM product WHERE stock=? ";
            a=stock;
        }
        else if(price.equalsIgnoreCase("")==false){
            
            sql="SELECT * FROM product WHERE price=? ";
            a=price;
        }
        else if(type.equalsIgnoreCase("")==false){
            
            sql="SELECT * FROM product WHERE type=? ";
            a=type;
        }
        else if(weight.equalsIgnoreCase("")==false){
            
            sql="SELECT * FROM product WHERE weight=? ";
            a=weight;
        }
        else if(buy_price.equalsIgnoreCase("")==false){
            
            sql="SELECT * FROM product WHERE 'buy price'=? ";
            a=buy_price;
        }
        
        
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            
            
            ResultSet rs;
            
            if(a.equalsIgnoreCase(name)){
                rs = s.executeQuery(sql);
            }
            else{
                s.setString(1,a);
                rs = s.executeQuery();
            }
            
            
            
            //ResultSet rs = 
            
            while(rs.next()){
                String i = rs.getString(1);
                String n = rs.getString(2);
                String p = rs.getString(3);
                String st = rs.getString(4);
                String t = rs.getString(5);
                String w = rs.getString(6);
                String b = rs.getString(7);
                String bc = rs.getString(8);
                
                dm.addRow(new String[]{i,n,p,st,t,w,b,bc});
                
            }
            
            return dm;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        return null;
    }
    
    
    
    public DefaultTableModel search(int choice,String text){
        
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("ID");
        dm.addColumn("Name");
        dm.addColumn("Stock");
        dm.addColumn("Price");
        dm.addColumn("Type");
        dm.addColumn("Weight");
        dm.addColumn("Buy Price");
        dm.addColumn("BarCode");
        
        String sql=null;
        
        
        if(choice==0){
            
            sql="SELECT * FROM product WHERE name LIKE  '"+text+"%'  ";
            
        }
        else if(choice==1){
            
            sql="SELECT * FROM product WHERE type LIKE '"+text+"%' ";
            
        }
        else if(choice==2){
            
            sql="SELECT * FROM product WHERE price=? ";
            
        }
        else if(choice==3){
            
            sql="SELECT * FROM product WHERE weight=? ";
            
        }
        else if(choice == 4){
            
            sql="SELECT * FROM product WHERE barcode=? ";
        }
        
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            ResultSet rs;
            
            if(choice==0 || choice == 1){
                rs = s.executeQuery(sql);
            }
            else{
                s.setString(1,text);
                rs = s.executeQuery();
            }
            
            while(rs.next()){
                String i = rs.getString(1);
                String n = rs.getString(2);
                String p = rs.getString(3);
                String st = rs.getString(4);
                String t = rs.getString(5);
                String w = rs.getString(6);
                String bp = rs.getString(7);
                String bc = rs.getString(8);
                
                dm.addRow(new String[]{i,n,p,st,t,w,bp,bc});
                
            }
            
            return dm;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        return null;
    }
    
    
    
    
}
