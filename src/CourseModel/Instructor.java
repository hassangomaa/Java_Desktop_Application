/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseModel;

import NewAdminFrames.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author egypt
 */
public class Instructor extends person {
    private static Function f = new Function(true);
    public Instructor(){}
    
    public static void ReadAllInstructor(JTable TABLEIinfo){
        DATABASE DB = new DATABASE();
        try {
            DefaultTableModel model = (DefaultTableModel) TABLEIinfo.getModel();
            Statement stat = DB.CreateConnection("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/courses", "root", "");
            ResultSet rs = DB.SelectAll("instractor_information",stat);
            while(rs.next()){
                Object obj [] = {
                    rs.getString("FULL_NAME") ,
                    rs.getString("AGE") ,
                    rs.getString("PHONE") ,
                    rs.getString("HOMEPHONE") ,
                    rs.getString("ADDRESS") ,
                    rs.getString("COURS") ,
                };
                model.addRow(obj);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NewAdminFrames.Instructor.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void InsertInstractor( JTable TABLEIinfo , JComboBox COURSENAMETXT , JTextField fNAMETXT , JTextField mNAMETXT , JTextField  lNAMETXT,
        JTextField AGETXT , JTextField PHONETXT , JTextField HOMEPHONETXT ,JTextField ADDRESSTXT ){
        DefaultTableModel model = (DefaultTableModel) TABLEIinfo.getModel();
        DATABASE DB = new DATABASE();
        if( ADDRESSTXT.getText().compareTo("") == 0    ||
            HOMEPHONETXT.getText().compareTo("") == 0  ||
            PHONETXT.getText().compareTo("") == 0      ||
            AGETXT.getText().compareTo("") == 0        ||
            lNAMETXT.getText().compareTo("") == 0      ||
            fNAMETXT.getText().compareTo("") == 0      ||
            mNAMETXT.getText().compareTo("") == 0
        ){JOptionPane.showMessageDialog(null,"The faild cant be Empty");}
        else{
            String fnm = fNAMETXT.getText();
            String mnm = mNAMETXT.getText();
            String lnm = lNAMETXT.getText();
            String FullName = fnm + " " + mnm + " " + lnm ;
            String age = AGETXT.getText();
            String phn = PHONETXT.getText();
            String hphn = HOMEPHONETXT.getText();
            String Adress = ADDRESSTXT.getText();
            String course = (String) COURSENAMETXT.getSelectedItem();
            Object obj[] = {FullName , age , phn , hphn , Adress , course};
            model.addRow(obj);
            try{
                java.sql.Statement stat = DB.CreateConnection("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/courses", "root", "");
                
                if( DB.SupInsertIntoInstractor(stat, FullName, age, phn, hphn, Adress, course) == 1 ){
                    JOptionPane.showMessageDialog(null, "Instructor Information Is Inserted");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Instructor Information Isn't Inserted");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Rejester.class.getName()).log(Level.SEVERE, null, ex);
            }
            JTextField []jt = { fNAMETXT , mNAMETXT , lNAMETXT,
             AGETXT , PHONETXT , HOMEPHONETXT , ADDRESSTXT };
            
            f.CLEAR(jt);
        }
    }
    
    public static void updateInstractor(JTable TABLEIinfo , JTextField NEWVALUETXT ) throws ClassNotFoundException{
        DATABASE DB = new DATABASE();
        DefaultTableModel model = (DefaultTableModel) TABLEIinfo.getModel() ;
        if(NEWVALUETXT.getText().compareTo("") == 0){
            JOptionPane.showMessageDialog(null,"The faild cant be Empty");
        }
        else{
            try {
                int CurRow = TABLEIinfo.getSelectedRow()
                , CurCol = TABLEIinfo.getSelectedColumn() , x = 0 ;
                java.sql.Statement stat = DB.CreateConnection("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/courses", "root", "");
                if(CurCol == 0){
                    x = DB.UpdateWhere(stat, "students_information", "FULL_NAME", NEWVALUETXT.getText(), "PHONE",TABLEIinfo.getValueAt(CurRow, 2)+"" );
                }
                else if(CurCol == 1){
                    x = DB.UpdateWhere(stat, "students_information", "AGE", NEWVALUETXT.getText(), "FULL_NAME",TABLEIinfo.getValueAt(CurRow, 0)+"" );
                }
                else if(CurCol == 2){
                    x = DB.UpdateWhere(stat, "students_information", "PHONE", NEWVALUETXT.getText(), "FULL_NAME",TABLEIinfo.getValueAt(CurRow, 0)+"" );
                }
                else if(CurCol == 3){
                    x = DB.UpdateWhere(stat, "students_information", "HOMEPHONE", NEWVALUETXT.getText(), "FULL_NAME",TABLEIinfo.getValueAt(CurRow, 0)+"" );
                }
                else if(CurCol == 4){
                    x = DB.UpdateWhere(stat, "students_information", "ADDRESS", NEWVALUETXT.getText(), "FULL_NAME",TABLEIinfo.getValueAt(CurRow, 0)+"" );
                }
                else if(CurCol == 5){
                    x = DB.UpdateWhere(stat, "students_information", "COURS", NEWVALUETXT.getText(), "FULL_NAME",TABLEIinfo.getValueAt(CurRow, 0)+"" );
                }
                if(x != 0){
                    model.setValueAt( NEWVALUETXT.getText()  , CurRow , CurCol );
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewAdminFrames.Instructor.class.getName()).log(Level.SEVERE, null, ex);
            }
            NEWVALUETXT.setText("");
        }
    }
    
    public static void removeInstructor(JTable TABLEIinfo){
        DATABASE DB = new DATABASE();
        DefaultTableModel model = (DefaultTableModel) TABLEIinfo.getModel();
        
        int CurrentRow = -1 ;
        CurrentRow = TABLEIinfo.getSelectedRow();
        if(CurrentRow == -1){
            JOptionPane.showMessageDialog(null, "Choose one row to delete it");
        }
        else{
            try{
                java.sql.Statement st = DB.CreateConnection("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/courses", "root", "");
                String VALUE = (String) TABLEIinfo.getValueAt(CurrentRow, 0) ;
                if(DB.deleteWhere(st, "instractor_information" , "FULL_NAME" , VALUE) == 1 )
                    model.removeRow(CurrentRow);
            }
            catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Rejester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void retreveAllInstructor(JTable TABLEIinfo , JComboBox COURSENAMETXT){
        DATABASE DB = new DATABASE();
        try {
            DefaultTableModel model = (DefaultTableModel) TABLEIinfo.getModel();
            java.sql.Statement stat = DB.CreateConnection("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/courses", "root", "");
            ResultSet rs = DB.SelectAll("instractor_information", stat);
            while(rs.next()){
                Object obj[] = {
                    rs.getString("FULL_NAME") ,
                    rs.getString("AGE") ,
                    rs.getString("PHONE") ,
                    rs.getString("HOMEPHONE") ,
                    rs.getString("ADDRESS") ,
                    rs.getString("COURS") 
                };
                model.addRow(obj);
            }
            /*====================combobox==================*/
            int numberOfRows = DB.Count("courses_information", stat) ;
            rs = DB.SelectAll("courses_information", stat);
            while(rs.next()){
                COURSENAMETXT.addItem(rs.getString("CourseName"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NewAdminFrames.Instructor.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
