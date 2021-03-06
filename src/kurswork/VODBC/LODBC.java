/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurswork.VODBC;

import kurswork.Course;
import kurswork.Laba;
import kurswork.MainTransferDate;
import kurswork.User;
import kurswork.vlasovlib.MD5;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import kurswork.Admin;
import kurswork.Grup;

import oracle.jdbc.pool.*;
/**
 *
 * @author Vlasov
 */
public class LODBC {
     
    private static OracleDataSource ds;
    private static Statement stmt;
    private static Connection conn;
    private static void StartSQLConection() throws ClassNotFoundException,
      SQLException{
        ds = new OracleDataSource();
        ds.setDriverType("thin");
        ds.setServerName("localhost");
        ds.setPortNumber(1521);
        ds.setDatabaseName("neworcl"); // sid
        ds.setUser("BATMAN");
        ds.setPassword("ROBIN");
        
        conn = ds.getConnection();
        stmt = conn.createStatement();
    }
    //topin212
    public static int aGetLabaNumber(String course) throws ClassNotFoundException, SQLException{
        StartSQLConection();
        int result;
        ResultSet rset;
        
        rset = stmt.executeQuery("select * from (select course_name, count(distinct lab_id) from labs inner join course on course.ID = labs.COURSE_ID group by course_name) where course_name = '"+course+"'");
        
        if(rset.next()){
            result = Integer.parseInt(rset.getString(2), 10);
        }
        else{
            throw new SQLException("There are no labs on this course");
        }
        EndSQLConection();
        return result;
    }
    //topin212
    public static String[] aGetLabaNames(String course, int size) throws ClassNotFoundException, SQLException{
        StartSQLConection();
        String[] result;
        ResultSet rset;
        
        result = new String[size];
        
        rset = stmt.executeQuery("select distinct course_name,  laba_name from labs inner join course on course.ID = labs.COURSE_ID where course_name = '"+course+"'");
        
        for(int i = 0;rset.next();i++){
            result[i] = rset.getString(2);
        }
        EndSQLConection();
        return result;
    }
    //topin212
    public static int aGetNumberofStudentsInGroup(String group) throws ClassNotFoundException, SQLException{
        StartSQLConection();
        int result;
        ResultSet rset;
        
        rset = stmt.executeQuery("select count(name) from (select name, grup from user_table inner join users_grups on user_table.GRUP_ID = users_grups.ID) where grup = '"+group+"'");
        
        if(rset.next()){
            result = Integer.parseInt(rset.getString(1), 10);
        }
        else{
            throw new SQLException("There are no labs on this course");
        }
        EndSQLConection();
        return result;
    }
    //topin212
    public static User[] aGetStudentsOfGroup(String group, int size) throws ClassNotFoundException, SQLException{
        StartSQLConection();
        User[] result = null;
        ResultSet rset;

        rset = stmt.executeQuery("select distinct course_name,  laba_name from labs inner join course on course.ID = labs.COURSE_ID where course_name = '"+group+"'");
        /*
            select name from (select name, grup from user_table
            inner join users_grups on user_table.GRUP_ID = users_grups.ID)
            where grup = 'km-21'
        
        */
        for(int i = 0;rset.next();i++){
           result[i] = new User(rset.getString(1));
        }
        EndSQLConection();
        return result;
    }
    //topin212
    public static String[] aGetAllStudentsPoints(String course, String group, int labaNumber) throws ClassNotFoundException, SQLException{
        StartSQLConection();
        String[] result = new String[labaNumber];
        ResultSet rset;

        rset = stmt.executeQuery("select point from (select LABa_name, name, point, course_name, GRUP from user_table inner join (select LABa_name, user_id, point, course_id from user_lab inner join labs on user_lab.LAB_ID = labs.LAB_ID)tmp on user_table.USER_ID = tmp.user_id inner join COURSE_FIXED on tmp.course_id = COURSE_FIXED.ID inner join users_grups on user_table.GRUP_ID = users_grups.ID) where grup = '"+group+"' AND course_name = '"+course+"'");

        for(int i = 0;rset.next();i++){
           result[i] = rset.getString(1);
        }
        EndSQLConection();
        return result;
    }
    //topin212
    //<-vot tut dobavil
    public static boolean auUserExists(String user){
        try {
            StartSQLConection();
            ResultSet rset;
        
            rset = stmt.executeQuery("select login from user_login_tablev where login = '"+user+"'");
            if(rset.next()){
                EndSQLConection();
                return true;
            }
            else{
                EndSQLConection();
                return false;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LODBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LODBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    //Topin212
    //well, you asked for comments :)
    public static Course[] aLoadCourses() throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        ResultSet rset2;
        Course[] courses;
        
        
        
        //Initialize Course[] and grup[] inside it
        //----------------------------
        rset = stmt
            .executeQuery("select count(distinct COURSE_NAME)from BATMAN.COURSE");
        
        if(rset.next()){
            courses = new Course[Integer.parseInt(rset.getString(1), 10)];
        }
        else
            courses = new Course[5];
        
        rset = stmt
                .executeQuery("select course_name, grups from (select course_id, count(*) as grups from BATMAN.COURSE_GRUP group by course_id)tmp inner join course on course_id = course.id");
        
        for (int i = 1; rset.next(); i++) {
            courses[i-1] = new Course(rset.getString(1));
            courses[i-1].stringGrups = new String[Integer.parseInt(rset.getString(2), 10)];
        }
        //-----------------------------
        
        
        //now we have Course_name part filled in, we need to fill the group names in
        //rset = stmt
        //    .executeQuery("select course_name, grup from COURSE_GRUP inner join users_grups on course_grup.grup_id = users_grups.id inner join course on course_grup.course_id = course.id");
        
        //there's a thing, that between course_iterator change, we do not save information, the iteration passes and we 
        //rset.next();
        
        //The idea is kinda better. I am going to use a number of sql requests in order to get the info and not to fuck up on filling my structure
        
        
        for (int i = 0; i<courses.length; i++) {
            rset2 = stmt
                    .executeQuery("select GRUP from (select GRUP, COURSE_NAME from COURSE_GRUP inner join course on course_grup.COURSE_ID = course.id inner join users_grups on grup_id = users_grups.id) where COURSE_NAME = '"+courses[i].getName()+"'");
            for(int j = 0; rset2.next(); j++){
                //hotFix, suggest add a string constructor
                courses[i].stringGrups[j] = rset2.getString(1);
            }
        }
        EndSQLConection();
        return courses;
    }
    //endTopin212
//    ResultSet rset = stmt
//            .executeQuery("select PASSWORD from USER_LOGIN_TABLE where login = 'ussser'");
//        if (rset.next())
//          System.out.println(rset.getString(1));
    
    private static void EndSQLConection() throws ClassNotFoundException,
      SQLException{
        stmt.close();
        conn.close();
    }
    
}