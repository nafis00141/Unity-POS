/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectfinal;



import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
//import java.awt.event.KeyEvent;
import com.sun.glass.events.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.KeyListener;

import java.io.FileOutputStream;

import java.io.File;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.jar.Attributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nafis
 */
public class SellPage extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    
    public static int idd;
    
    public SellPage(int i) {
        initComponents();
        retreve();
        choice();
        
        idd = i;
        
        //so that int choice have a valu;
        new Thread(){
            
            public void run(){
                while(true){
                    Date dNow = new Date( );
                    
                    SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd hh:mm:ss a");
                    String time = ""+ft.format(dNow);
                    
                    jLabel4.setText(time);
                    
                }
            }
            
        }.start();
        jTextField3.setText(sellno+1+"");//to show sell number
        
        String a = "sellno"+jTextField3.getText();
        new SellTable().createtable(a);
        retreve("sellno"+jTextField3.getText());
        //System.out.println(a);
        
        
    }
    
    
    
    
    public void dropprevious(){
        
        int num = Integer.parseInt(jTextField3.getText());
        String ab = "sellno"+num;
                
        String sql1 = "SELECT * FROM information_schema.tables WHERE table_schema = 'nafis' AND table_name = '"+ab+"' LIMIT 1";
        
        try{
            Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nafis", "root", "");
            
            Statement s =(Statement) con.prepareStatement(sql1);
            
            ResultSet rs =s.executeQuery(sql1);
            
            if(rs.next()){
                if(new SellTable().dropTable(ab)){
                
                System.out.println("droped table "+ab);
                }
                else{
                JOptionPane.showMessageDialog(null, "problem cancelling");
                }
                          
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    public void retreve(String a){
       DefaultTableModel dm = new SellTable().getData(a);
       
        jTable2.setModel(dm);
        
        
    }
    
    
    
    
    public float total(String a){
        String conString ="jdbc:mysql://localhost:3306/nafis";
        String username ="root";
        String passward ="";
        float total=0;
        
        String sql = "SELECT price FROM "+a;
        
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            while(rs.next()){
                
                String price = rs.getString(1);
                
                total = total+Float.parseFloat(price);
                
                //System.out.println(total);
                
            }
            
            return total;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return total;
        
    }
    
    
    
    int choice;  ///choice for combo box
    
    
    int sellno= new SellTable().getSellNumber();//to get sell number
    
    int item_to_sell=-1;
    
   
    
    public void choice(){
        choice = jComboBox1.getSelectedIndex();
        
        
    }
    
    public void retreve(){
        DefaultTableModel dm = new StockUpdater().getData();
        
        jTable1.setModel(dm);
        
    }
    
    
    public boolean UpdateForUpdatingStock(String name,String price,String quantity){
        
        String conString ="jdbc:mysql://localhost:3306/nafis";
        String username ="root";
        String passward ="";
        
        String sql="UPDATE product SET stock=stock-"+quantity+" WHERE name='"+name+"' AND price='"+price+"'";
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            s.execute(sql);
            
            System.out.println("updated");
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return false;
    }
    
    
    ///search in the sell table
    public boolean SearchExist(String name,String price,String a){
        String conString ="jdbc:mysql://localhost:3306/nafis";
        String username ="root";
        String passward ="";
        
        String sql1 = "SELECT * FROM "+a+" WHERE name ='"+name+"'AND perprice='"+price+"'";
        
        
       
        String st;
        
        try{
            
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.createStatement();           
            
            
            
            //s.setString(1,name);
            
            ResultSet rs = s.executeQuery(sql1);
            
            
            
            while(rs.next()){
                
                return true;
 
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    //get sell quantity
    public int getQua(String name,String price,String a){
        
        String conString ="jdbc:mysql://localhost:3306/nafis";
        String username ="root";
        String passward ="";
        
        String sql1 = "SELECT * FROM "+a+" WHERE name ='"+name+"'AND perprice='"+price+"'";
        
        
       
        String st;
        int qua=0;
        try{
            
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.createStatement();           
            
            
            
            //s.setString(1,name);
            
            ResultSet rs = s.executeQuery(sql1);
            
            
            
            
            while(rs.next()){
                
                String quantity = rs.getString(3);
                
                qua=Integer.parseInt(quantity);
                
                return qua;
 
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return qua;
    }
    

    ///search in the product to update stock
    public boolean SearchForUpdate(String name,String price){
        String conString ="jdbc:mysql://localhost:3306/nafis";
        String username ="root";
        String passward ="";
        
        String sql1 = "SELECT * FROM product WHERE name =?";
        
        
        ////+" AND price ="+price+"";
        String st;
        try{
            
            
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql1);           
            
            
            
            s.setString(1,name);
            
            ResultSet rs = s.executeQuery();
            
            
            
            while(rs.next()){
                
                String p = rs.getString(3);
                
                
                int sto = Integer.parseInt(p);
                sto=sto-1;
                System.out.println(sto);
                
                st=sto+"";
                
                if(UpdateForUpdatingStock(name,price,"1")){
                    System.out.println("Success in update");
                }
 
            }
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean CreatingSell(String a){
        String conString ="jdbc:mysql://localhost:3306/nafis";
        String username ="root";
        String passward ="";
        
        String sql = "SELECT * FROM "+a;
        
        //int st;
        
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            Statement s =(Statement) con.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery(sql);
            
            while(rs.next()){
                
                String name = rs.getString(2);
                String price = rs.getString(4);
                String sold_price=rs.getString(4);
                String qua = rs.getString(3);
                
                
                
                ////updating product table
                if(UpdateForUpdatingStock(name,price,qua)){
                    System.out.println("Success in update");
                }
                
                
            }
            
            return true;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UNITY POS");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        )
        {public boolean isCellEditable(int row, int column){return false;}}
    );
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTable1MouseClicked(evt);
        }
    });
    jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            jTable1KeyPressed(evt);
        }
    });
    jScrollPane1.setViewportView(jTable1);

    jTable2.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {}
        },
        new String [] {

        }
    )
    {public boolean isCellEditable(int row, int column){return false;}}
    );
    jScrollPane2.setViewportView(jTable2);

    jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jLabel1.setBackground(new java.awt.Color(47, 102, 240));
    jLabel1.setFont(new java.awt.Font("Gisha", 0, 14)); // NOI18N
    jLabel1.setText("Main");

    jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_1_7021342772.png"))); // NOI18N
    jButton3.setText("Admin Panel");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });

    jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_20_6149163376.png"))); // NOI18N
    jButton1.setText("Close Cash");
    jButton1.setMaximumSize(new java.awt.Dimension(121, 50));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
            .addContainerGap())
    );

    jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
    jLabel2.setText("Administration");

    jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_5_8018731009.png"))); // NOI18N
    jButton4.setText("Stock");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
        }
    });

    jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_1_4933382701.png"))); // NOI18N
    jButton12.setText("Customers");
    jButton12.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton12ActionPerformed(evt);
        }
    });

    jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_13_2492534618.png"))); // NOI18N
    jButton5.setText("Sales Chart");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });

    jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_3_6522933915.png"))); // NOI18N
    jButton13.setText("Employee");
    jButton13.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton13ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jLabel2)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
            .addContainerGap())
    );

    jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
    jLabel3.setText("System");

    jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_1_9752744011.png"))); // NOI18N
    jButton8.setText("About");
    jButton8.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton8ActionPerformed(evt);
        }
    });

    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_9_3136674843.png"))); // NOI18N
    jButton2.setText("Change Pass");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });

    jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_7_6996753666.png"))); // NOI18N
    jButton6.setText("Log Out");
    jButton6.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton6ActionPerformed(evt);
        }
    });

    jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_5_7554107267.png"))); // NOI18N
    jButton15.setText("BarCode Gen");
    jButton15.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton15ActionPerformed(evt);
        }
    });

    jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectfinal/imageedit_1_6304402057.png"))); // NOI18N
    jButton16.setText("Calculator");
    jButton16.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton16ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jButton15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jButton2)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
            .addGap(54, 54, 54))
    );

    jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jLabel7.setText("Sell no.");

    jTextField3.setEditable(false);
    jTextField3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField3ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(16, Short.MAX_VALUE))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel5.setText("Search");

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Type", "Price", "Weight", "Barcode" }));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBox1ActionPerformed(evt);
        }
    });

    jTextField1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField1ActionPerformed(evt);
        }
    });
    jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            jTextField1KeyReleased(evt);
        }
    });

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTextField1)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jTextField2.setEditable(false);
    jTextField2.setFont(new java.awt.Font("Digital-7", 1, 24)); // NOI18N
    jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    jTextField2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField2ActionPerformed(evt);
        }
    });

    jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel6.setText("Total");

    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel6))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel6Layout.setVerticalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel6)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(30, 30, 30))
    );

    jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jButton9.setText("Remove");
    jButton9.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton9ActionPerformed(evt);
        }
    });

    jButton10.setText("Cancel");
    jButton10.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton10ActionPerformed(evt);
        }
    });

    jButton11.setText("Sell");
    jButton11.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton11ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton9)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(31, 31, 31))
    );
    jPanel7Layout.setVerticalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jLabel8.setText("Card ID");

    jTextField4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField4ActionPerformed(evt);
        }
    });
    jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            jTextField4KeyPressed(evt);
        }
        public void keyReleased(java.awt.event.KeyEvent evt) {
            jTextField4KeyReleased(evt);
        }
    });

    jLabel9.setText("Membership");

    jTextField5.setEditable(false);

    jLabel10.setText("Discount ");

    jTextField6.setEditable(false);
    jTextField6.setHorizontalAlignment(javax.swing.JTextField.LEFT);

    jLabel11.setText("%");

    jButton14.setText("Discount");
    jButton14.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton14ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
    jPanel8.setLayout(jPanel8Layout);
    jPanel8Layout.setHorizontalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addComponent(jLabel10)
                    .addGap(31, 31, 31)))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton14)))
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
    );
    jPanel8Layout.setVerticalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel8)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

    jButton17.setText("Retreve");
    jButton17.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton17ActionPerformed(evt);
        }
    });

    jLabel14.setText("Vat");

    jTextField7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

    jLabel15.setText("%");

    jButton18.setText("Vat");
    jButton18.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton18ActionPerformed(evt);
        }
    });

    jLabel16.setText("Discount ");

    jLabel17.setText("%");

    jButton19.setText("Discount");
    jButton19.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton19ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jSeparator1)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton17, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addContainerGap())))
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(jLabel14)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel15)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jButton18)
                                            .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel16)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGap(0, 0, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(0, 0, Short.MAX_VALUE)
                                                    .addComponent(jButton19)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addComponent(jScrollPane1)))))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(18, 18, 18)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator3)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel14)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15)
                                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel17))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton19)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(9, 9, 9)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))))
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       Stock st =  new Stock(idd,this);
       st.setVisible(true);
       
       
       st.jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                DefaultTableModel dm = new StockUpdater().getData();
        
                jTable1.setModel(dm);
                
            }
       });
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        choice();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        DefaultTableModel dm = new StockUpdater().search(choice,jTextField1.getText());
        jTable1.setModel(dm);
        
        
        
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try{
                String qua = jTable1.getValueAt(0,2).toString();
        
        
        
        if(Integer.parseInt(qua)==0){
            JOptionPane.showMessageDialog(null, "Out Of Stock", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(Integer.parseInt(qua)<=5){
            JOptionPane.showMessageDialog(null, "Stock is Low", "Warning", JOptionPane.INFORMATION_MESSAGE);
            String name = jTable1.getValueAt(0,1).toString();
            float price = Float.parseFloat(jTable1.getValueAt(0,3).toString());
            float buy_price = Float.parseFloat(jTable1.getValueAt(0,6).toString());
            String type = jTable1.getValueAt(0,4).toString();
        
        
        
            
            if(SearchExist(name, price+"","sellno"+jTextField3.getText())){
            
            int sellquantity = getQua(name, price+"","sellno"+jTextField3.getText());
                
                if(sellquantity < Integer.parseInt(qua)){/// checking if sell quantity gets bigger than product quntity
                    new SellTable().update("sellno"+jTextField3.getText(),name, price);
                    retreve("sellno"+jTextField3.getText());
                    jTextField2.setText(total("sellno"+jTextField3.getText())+" shs");
                    
                }
                else{
                    JOptionPane.showMessageDialog(null,"no stock left");
                    int n = JOptionPane.showConfirmDialog(null,"Would you like update Stock?","",JOptionPane.YES_NO_OPTION);
            
                    System.out.println("hoho "+n);
                    
                    if(n==0){
                        new updateStock().setVisible(true);
                    }
                
                }
            }
            ////before experimenting
            else if(new SellTable().add("sellno"+jTextField3.getText(),name, price," 2%",buy_price,type)){
                item_to_sell++;
                retreve("sellno"+jTextField3.getText());
                jTextField2.setText(total("sellno"+jTextField3.getText())+" shs");
                 System.out.println(item_to_sell);
            
             }
             else{
                JOptionPane.showMessageDialog(null, "problem");
                }
          }
        else{
            String name = jTable1.getValueAt(0,1).toString();
             float price = Float.parseFloat(jTable1.getValueAt(0,3).toString());
            float buy_price = Float.parseFloat(jTable1.getValueAt(0,6).toString());
            String type = jTable1.getValueAt(0,4).toString();
        
            
            if(SearchExist(name, price+"","sellno"+jTextField3.getText())){
            
                int sellquantity = getQua(name, price+"","sellno"+jTextField3.getText());
                
                if(sellquantity < Integer.parseInt(qua)){/// checking if sell quantity gets bigger than product quntity
                    new SellTable().update("sellno"+jTextField3.getText(),name, price);
                    retreve("sellno"+jTextField3.getText());
                    jTextField2.setText(total("sellno"+jTextField3.getText())+" shs");
                    
                }
                else{
                    JOptionPane.showMessageDialog(null,"no stock left");
                    int n = JOptionPane.showConfirmDialog(null,"Would you like update Stock?","",JOptionPane.YES_NO_OPTION);
            
                    System.out.println("hoho "+n);
                    
                    if(n==0){
                        new updateStock().setVisible(true);
                    }
                
                }
            }
        ////before experimenting
        else if(new SellTable().add("sellno"+jTextField3.getText(),name, price," 2%",buy_price,type)){
            item_to_sell++;
            retreve("sellno"+jTextField3.getText());
            jTextField2.setText(total("sellno"+jTextField3.getText())+" shs");
            System.out.println(item_to_sell);
            
        }
        else{
            JOptionPane.showMessageDialog(null, "problem");
        }
            
            
        }
            jTextField1.setText("");
            }catch(Exception e){
                
            }
            
        }
        
        
        
        
        if(jTextField1.getText().equalsIgnoreCase("")){
            retreve();
        }
        
        
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
        
        String qua = jTable1.getValueAt(jTable1.getSelectedRow(),2).toString();
        
        
        
        if(Integer.parseInt(qua)==0){
            JOptionPane.showMessageDialog(null, "Out Of Stock", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(Integer.parseInt(qua)<=5){
            JOptionPane.showMessageDialog(null, "Stock is Low", "Warning", JOptionPane.INFORMATION_MESSAGE);
            String name = jTable1.getValueAt(jTable1.getSelectedRow(),1).toString();
            float price = Float.parseFloat(jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
            float buy_price = Float.parseFloat(jTable1.getValueAt(jTable1.getSelectedRow(),6).toString());
            String type = jTable1.getValueAt(jTable1.getSelectedRow(),4).toString();
        
            
            if(SearchExist(name, price+"","sellno"+jTextField3.getText())){
                
                int sellquantity = getQua(name, price+"","sellno"+jTextField3.getText());
                
                if(sellquantity < Integer.parseInt(qua)){/// checking if sell quantity gets bigger than product quntity
                    new SellTable().update("sellno"+jTextField3.getText(),name, price);
                    retreve("sellno"+jTextField3.getText());
                    jTextField2.setText(total("sellno"+jTextField3.getText())+"");
                    
                }
                else{
                    //JOptionPane.showMessageDialog(null,"no stock left");
                    int n = JOptionPane.showConfirmDialog(null,"Would you like update Stock?","",JOptionPane.YES_NO_OPTION);
            
                    System.out.println("hoho "+n);
                    
                    if(n==0){
                        new updateStock().setVisible(true);
                    }
                    
                }
            }
            ////before experimenting
            else if(new SellTable().add("sellno"+jTextField3.getText(),name, price," 2%",buy_price,type)){
                item_to_sell++;
                retreve("sellno"+jTextField3.getText());
                jTextField2.setText(total("sellno"+jTextField3.getText())+"");
                 System.out.println(item_to_sell);
            
             }
             else{
                JOptionPane.showMessageDialog(null, "problem");
                }
        }
        else{
            String name = jTable1.getValueAt(jTable1.getSelectedRow(),1).toString();
             float price = Float.parseFloat(jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
            float buy_price = Float.parseFloat(jTable1.getValueAt(jTable1.getSelectedRow(),6).toString());
            String type = jTable1.getValueAt(jTable1.getSelectedRow(),4).toString();
        
           
            if(SearchExist(name, price+"","sellno"+jTextField3.getText())){
            
                int sellquantity = getQua(name, price+"","sellno"+jTextField3.getText());
                
                if(sellquantity < Integer.parseInt(qua)){/// checking if sell quantity gets bigger than product quntity
                    new SellTable().update("sellno"+jTextField3.getText(),name, price);
                    retreve("sellno"+jTextField3.getText());
                    jTextField2.setText(total("sellno"+jTextField3.getText())+"");
                    
                }
                else{
                    JOptionPane.showMessageDialog(null,"no stock left");
                    int n = JOptionPane.showConfirmDialog(null,"Would you like update Stock?","",JOptionPane.YES_NO_OPTION);
            
                    System.out.println("hoho "+n);
                    
                    if(n==0){
                        new updateStock().setVisible(true);
                    }
                
                }
            }
            ////before experimenting
            else if(new SellTable().add("sellno"+jTextField3.getText(),name, price," 2%",buy_price,type)){
                item_to_sell++;
                retreve("sellno"+jTextField3.getText());
                jTextField2.setText(total("sellno"+jTextField3.getText())+"");
                System.out.println(item_to_sell);

            }
            else{
                JOptionPane.showMessageDialog(null, "problem");
            }
        }
        
        
        
        
        
        
        
        
        
        
   
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        //For enter key to work to select from table
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            String name = jTable1.getValueAt(jTable1.getSelectedRow(),1).toString();
            float price = Float.parseFloat(jTable1.getValueAt(jTable1.getSelectedRow(),3).toString());
            float buy_price = Float.parseFloat(jTable1.getValueAt(jTable1.getSelectedRow(),6).toString());
            String type = jTable1.getValueAt(jTable1.getSelectedRow(),4).toString();
            
            //new SellTable().createtable("sellno"+jTextField3.getText());
            /*if(new SellTable().update("sellno"+jTextField3.getText(),name, price)){
            retreve("sellno"+jTextField3.getText());
            jTextField2.setText(total("sellno"+jTextField3.getText())+"tk");
            
            
            }*/
            if(SearchExist(name, price+"","sellno"+jTextField3.getText())){
                
                new SellTable().update("sellno"+jTextField3.getText(),name, price);
                retreve("sellno"+jTextField3.getText());
                jTextField2.setText(total("sellno"+jTextField3.getText())+" shs");
                
            }
            ////before experimenting
            else if(new SellTable().add("sellno"+jTextField3.getText(),name, price," 2%",buy_price,type)){
                item_to_sell++;
                retreve("sellno"+jTextField3.getText());
                jTextField2.setText(total("sellno"+jTextField3.getText())+" shs");
                System.out.println(item_to_sell);
                
            }
            else{
                JOptionPane.showMessageDialog(null, "problem");
            }
        }
            
        
            
            
        
            
    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        
        
            try{
                int index = jTable2.getSelectedRow();
                String id =jTable2.getValueAt(index,0).toString();
                String qua =jTable2.getValueAt(index,3).toString();
                String perprice =jTable2.getValueAt(index,2).toString();
                if(Integer.parseInt(qua)==1){
                    if(new SellTable().delete(id,"sellno"+jTextField3.getText())){
                    item_to_sell--;
                    retreve("sellno"+jTextField3.getText());
                    jTextField2.setText(total("sellno"+jTextField3.getText())+" shs");
                    }
                }
                else if(Integer.parseInt(qua)!=1){
                    if(new SellTable().updateSell("sellno"+jTextField3.getText(),id,perprice)){
                        item_to_sell--;
                        retreve("sellno"+jTextField3.getText());
                        jTextField2.setText(total("sellno"+jTextField3.getText())+" shs");
                    }
                    
                }
            
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Select first to remove");
            }
            

        //String[] option = {"YES","NO"};
        
        //int ans = JOptionPane.showOptionDialog(null, "Sure To Delete?", "DELETED", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
        
        /*if(ans==0){
            int index = jTable2.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
            for(int col =0;col<=5;col++){
                model.moveRow(ABORT, col, col);
            }
            
            
            
       // }/*/
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if(new SellTable().dropTable("sellno"+jTextField3.getText())){
            String a = "sellno"+jTextField3.getText();
            new SellTable().createtable(a);
            retreve(a);
            jTextField2.setText(total("sellno"+jTextField3.getText())+"");
            JOptionPane.showMessageDialog(null, "Sell Canceled");
        }
        else{
            JOptionPane.showMessageDialog(null, "problem cancelling");
        }

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        
        
        CashType c = new CashType();
        c.setVisible(true);
        
        c.jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String receive = c.jTextField1.getText();
                if(receive.equalsIgnoreCase("")){
                    JOptionPane.showMessageDialog(null,"Enter Amount");
                }
                else if(!receive.matches("[0-9]+")){
                    JOptionPane.showMessageDialog(null,"Enter Valid Amount");
                    c.jTextField1.setText("");
                }
                else{
                    float rec = Float.parseFloat(receive);
                    //create sell
        String b = "sellno"+jTextField3.getText();
        
        if(CreatingSell(b)){
            System.out.println("Success is final");
        }
        
        
        ///code for updatng solddtime
        
        
        if(new SoldTimeUpdater().getToday(b)){
            System.out.println("soldtime updated");
        }
        
        ////code for updating sold
        
        
        String sq = "SELECT * FROM "+b;
        
        try{
            
            Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nafis", "root", "");
            Statement s = (Statement) con.prepareStatement(sq);
        
            
            ResultSet rs =s.executeQuery(sq);
            
            while(rs.next()){
                String name = rs.getString(2);
                String price = rs.getString(4);
                String buy_price = rs.getString(7);
                String type = rs.getString(8);
                String qua = rs.getString(3);
                
                
                if(new SellTable().SearchExistItem(name, price+"","sold")){
            
                    new SellTable().updateItem("sold",name,Float.parseFloat(price),qua);
            
            
            
                }
        
                else if(new SellTable().addItem("sold",name, Float.parseFloat(price)," 2%",Float.parseFloat(buy_price),type,qua)){
                    
                    
                    
                    
                }
                else{
                    JOptionPane.showMessageDialog(null, "problem");
                }
            }
            
        }catch(Exception e){
            
        }
        
        
        
        

        //code for invoice
        
        double a=0;
        
        int length;
        String sql = "SELECT * FROM "+b;
        
        String sqlnew = "SELECT count(*) FROM "+b;
        
        
        File theDir = new File("C:\\Recept");

        // if the directory does not exist, create it
        if (!theDir.exists()) {
        //System.out.println("creating directory: " + directoryName);
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            } 
            catch(SecurityException se){
            //handle it
            }
        }
            
        
        try {
            
            Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nafis", "root", "");


            Statement ss = (Statement) con.prepareStatement(sqlnew);

            Statement s = (Statement) con.prepareStatement(sql);

            ResultSet rs =s.executeQuery(sql);

            ResultSet rss =ss.executeQuery(sqlnew);

            System.out.println("RING here   888888");


            int tou = 8;

            if(rss.next()){

                String sp = rss.getString(1);

                tou = Integer.parseInt(sp);


            }

            System.out.println("RING here   9999");


            Document document = new Document();


            /*int siz = 380;

            if(tou>10 && tou<=20) siz = 700;
            else if(tou>20 && tou<=30) siz =  1000;
            else if(tou>30 && tou<=40) siz =  1300;
            else if(tou>40 && tou<=50) siz =  1600;
            else if(tou>50 && tou<=60) siz =  1900;
            else if(tou>60 && tou<=70) siz =  2200;
            else if(tou>70 && tou<=80) siz =  2500;
            else if(tou>80 && tou<=90) siz =  2800;
            else if(tou>90 && tou<=10) siz =  3100;*/

            int siz = 20*tou;

            siz += 320; 


            System.out.println("siz   "+siz);

            Rectangle test = new Rectangle(223,siz);
            document.setPageSize(test);
            PdfWriter.getInstance(document,new FileOutputStream("C:\\Recept\\"+b+".pdf"));

            System.out.println("HEREEE    ");
            document.open();
            document.addCreationDate();
            document.add(new Paragraph("                        INVOICE",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            //document.add(new Paragraph("--------------------------------------------------------",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            document.add(new Paragraph("          "+new Date().toString(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            document.add(new Paragraph("                      INVOICE NO:"+jTextField3.getText(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            document.add(new Paragraph("                 INVOICE BY: " ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            document.add(new Paragraph("Product                 Qty          Price         Total",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.UNDERLINE,BaseColor.BLACK)));
            //document.add(new Paragraph("--------------------------------------------------------",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));

            System.out.println("HEREEE    ");

            while(rs.next()){
                String name = rs.getString(2);
                String qua = rs.getString(3);
                String rate = rs.getString(4);
                String amount = rs.getString(6);
                a = a+ Double.parseDouble(amount);
                if(name.length()<=25){
                    length = name.length();
                }
                else{
                    length = 25;
                }
                
                double amo = Double.parseDouble(rate);


                rate = String.format("%,.2f", amo);
                
                amo = Double.parseDouble(amount);
                
                amount = String.format("%,.2f", amo);
                
                document.add(new Paragraph(name.substring(0,length),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
                document.add(new Paragraph("                             "+qua+"           "+rate+"          "+amount+"",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
                //document.add(new Paragraph("                                        ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
                //t.addCell(rate);
                //t.addCell(amount);

            }

            System.out.println("HEREEE    now");

            //document.add(new Paragraph("                                   "));
            //document.add(new Paragraph("                                   "));
            String am = String.format("%,.2f", a);
            document.add(new Paragraph("--------------------------------------------------------",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            document.add(new Paragraph("                                         Total = "+am+" shs",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));

            //document.add(new Paragraph("                                   "));
            //document.add(new Paragraph("                                   "));
            if(jTextField6.getText().equalsIgnoreCase("")==false){
                float dis = Float.parseFloat(jTextField6.getText());
                a=a-((a*dis)/100);
                am = String.format("%,.2f", a);
                document.add(new Paragraph("Customer ID ="+jTextField4.getText()+" After Discount = "+am+" shs",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            }
            if(jTextField8.getText().equalsIgnoreCase("")==false){
                float dis = Float.parseFloat(jTextField8.getText());
                a=a-((a*dis)/100);
                am = String.format("%,.2f", a);
                document.add(new Paragraph("Discount ="+jTextField8.getText()+"% After Discount = "+am+" shs",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            }

            if(jTextField7.getText().equalsIgnoreCase("")==false){
                double vat = Float.parseFloat(jTextField7.getText());
                double v = (a*vat)/100;
                a=a+((a*vat)/100);
                am = String.format("%,.2f", v);
                document.add(new Paragraph("Vat ="+jTextField7.getText()+"%       Vat = "+am+" shs",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));

            }
            am = String.format("%,.2f", a);
            document.add(new Paragraph("                              Net Amount = "+am+" shs",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            am = String.format("%,.2f", rec);
            document.add(new Paragraph("                       Receive Amount = "+am+" shs",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            am = String.format("%,.2f", (rec-a));
            document.add(new Paragraph("                      Returned Amount = "+am+" shs   ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));

            //document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("         Goods Once Sold Can not be Return",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            document.add(new Paragraph("--------------------------------------------------------",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            document.add(new Paragraph("             Unity Point Of Sales System",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));



            document.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"problem in memoo");
        }
        
         ///////////////////////////
         //to open pdf invoice
        try {
           

            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"C:\\Recept\\"+b+".pdf");
        } catch (IOException ex) {
            Logger.getLogger(SellPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        ////update todaySell
        
        Date dNow = new Date( );
                    
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
    
        String date= ""+ft.format(dNow);
        
        
        
        if(new updatingTodaySell().checkdate(date)){
            
            System.out.println("updating");
            String read="SELECT * FROM "+b;
            
            try{
            
                Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nafis", "root", "");
            
                Statement s =(Statement) con.prepareStatement(read);
            
                ResultSet rs =s.executeQuery(read);
                
                while(rs.next()){
                    
                    
                String name = rs.getString(2);
                String price = rs.getString(4);
                String buy_price = rs.getString(7);
                String type = rs.getString(8);
                String qua = rs.getString(3);
                
                if(new updatingTodaySell().searchtodaysell(name, buy_price)){
                    
                    System.out.println(qua);
                    new updatingTodaySell().update(name, qua, buy_price, date);
                    System.out.println("update done");
                }
                else{
                    
                    new updatingTodaySell().add(name, qua, price, buy_price, type, date);
                    
                }
                
                
                }
            
            }catch(Exception e){
                e.printStackTrace();
            }
            
            
            
        }else{
            
            String sqldrop="DELETE FROM `todaysell` WHERE 1";
        
            try{
            
                Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nafis", "root", "");
            
                Statement s =(Statement) con.prepareStatement(sqldrop);
            
                s.execute(sqldrop);
            
            
            }catch(Exception e){
                e.printStackTrace();
            }
            
            String adddate = "INSERT INTO `todaysell`(`no`, `name`, `quantity`, `price`, `buyprice`, `type`, `date`) VALUES (NULL,'a','0','0','0','NULL','"+date+"')";
            
            
            
            try{
            
                Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nafis", "root", "");
            
                Statement s =(Statement) con.prepareStatement(adddate);
            
                s.execute(adddate);
            
            
            }catch(Exception e){
                e.printStackTrace();
            }
            
            
            
            String read="SELECT * FROM "+b;
            
            try{
            
                Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nafis", "root", "");
            
                Statement s =(Statement) con.prepareStatement(read);
            
                ResultSet rs =s.executeQuery(read);
                
                while(rs.next()){
                    
                    
                String name = rs.getString(2);
                String price = rs.getString(4);
                String buy_price = rs.getString(7);
                String type = rs.getString(8);
                String qua = rs.getString(3);
                
                new updatingTodaySell().add(name, qua, price, buy_price, type, date);
                }
            
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
        
        
        
        
        
        //droping previous sell
        int num = Integer.parseInt(jTextField3.getText());
        String ab = "sellno"+num;
                
        String sql1 = "SELECT * FROM information_schema.tables WHERE table_schema = 'nafis' AND table_name = '"+ab+"' LIMIT 1";
        
        try{
            Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nafis", "root", "");
            
            Statement s =(Statement) con.prepareStatement(sql1);
            
            ResultSet rs =s.executeQuery(sql1);
            
            if(rs.next()){
                if(new SellTable().dropTable(ab)){
                
                System.out.println("droped table "+ab);
                }
                else{
                JOptionPane.showMessageDialog(null, "problem cancelling");
                }
                          
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        
        //To update the number of sellno in db
        
        new SellTable().updateSellNumber();
        sellno= new SellTable().getSellNumber();
        //To show sellno in frame
        jTextField3.setText(sellno+1+"");
        
        new SellTable().createtable("sellno"+jTextField3.getText());
        retreve();
        
        
        retreve("sellno"+jTextField3.getText());
        
        
        //
        
        ///clear discount
        jLabel12.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField2.setText("");
        
        
        
        c.dispose();
                    
                }
                
                
                
        
            
        }

            private void matches(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            
        
        
                });

        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        new SellTable().dropTable("sellno"+jTextField3.getText());
    }//GEN-LAST:event_formWindowClosing

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        Customers cu = new Customers();
        cu.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        Employee e = new Employee();
        e.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        ///Searching customer card
        String conString ="jdbc:mysql://localhost:3306/nafis";
        String username ="root";
        String passward ="";
        
        
        String sql ="SELECT * FROM customer WHERE Card =?";
        
        try{
            Connection con= (Connection) DriverManager.getConnection(conString, username, passward);
            
            PreparedStatement s =(PreparedStatement) con.prepareStatement(sql);
            
            s.setString(1,jTextField4.getText());
            
            ResultSet rs = s.executeQuery();
            
            if(rs.next()){
                
                String mem = rs.getString("Membership Type");
                jTextField5.setText(mem);
                
                String dis = rs.getString("Discount");
                jTextField6.setText(dis);
                
            }
            else{
                jTextField5.setText("");
                jTextField6.setText("");
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        
        
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        
        if(jTextField4.getText().equalsIgnoreCase("")==false && jTextField6.getText().equalsIgnoreCase("")==false){
            float a = total("sellno"+jTextField3.getText());
            float b = (a * Float.parseFloat(jTextField6.getText()))/100;
        
            a = a - b;
        
            jTextField2.setText(a+"");
            jLabel12.setText("After Discount");
        }else if(jTextField6.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Enter Valid Card Number");
        }
        else{
            JOptionPane.showMessageDialog(null, "Enter Card Number First");
        }
        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        
        new SellTable().dropTable("sellno"+jTextField3.getText());
        new LogIn().setVisible(true);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        ChangePass c = new ChangePass();
        c.setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ManagerPanel mp = new ManagerPanel();
        mp.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        CalJFrame cal = new CalJFrame();
        cal.setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        SalesChart s = new SalesChart();
        s.setVisible(true);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       CloseCash cc = new CloseCash(idd);
       cc.setVisible(true);
       cc.jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                String[] option = {"YES","NO"};
                int ans = JOptionPane.showOptionDialog(null, "Are you sure?", "Cash Closed", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
        
                if(ans==0){

                    //drop all previous data
                    if(new CloshCashUpdater().drop()){
                    cc.retreve();
                    cc.jTextField3.setText("");
                    cc.jTextField4.setText("");
                    cc.jTextField5.setText("");
                    dispose();
                    dropprevious();
                    SellPage a = new SellPage(idd);
                    a.setVisible(true);
                    cc.dispose();
                    
                    cc.setVisible(true);
                    }
            
                }
                else{
                    JOptionPane.showMessageDialog(null,"Canceled");
                }
                
            }
       });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        new Generate().setVisible(true);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
      
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
         
    }//GEN-LAST:event_formKeyReleased

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        jTextField1ActionPerformed(evt);
    }
});
jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyReleased(java.awt.event.KeyEvent evt) {
        jTextField1KeyReleased(evt);
    }
});
    }//GEN-LAST:event_formKeyTyped

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        about a = new about();
        a.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        retreve();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        float t = Float.parseFloat(jTextField7.getText());
        float total =total("sellno"+jTextField3.getText());
        total = total + (total*t)/100;
        jTextField2.setText(total+" shs");
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        if(jTextField8.getText().equalsIgnoreCase("")==false){
            float a = total("sellno"+jTextField3.getText());
            float b = (a * Float.parseFloat(jTextField8.getText()))/100;
        
            a = a - b;
        
            jTextField2.setText(a+"");
            jLabel12.setText("After Discount");
        }
        else{
            JOptionPane.showMessageDialog(null, "Enter Discount First");
        }
    }//GEN-LAST:event_jButton19ActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SellPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SellPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SellPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SellPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new SellPage(idd).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    public javax.swing.JButton jButton12;
    public javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    public static javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    public javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
