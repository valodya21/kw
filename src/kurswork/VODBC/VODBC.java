/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurswork.VODBC;

//import java.lang.reflect.InvocationTargetException;
import kurswork.Course;
import kurswork.Laba;
import kurswork.MainTransferDate;
import kurswork.User;
import kurswork.vlasovlib.MD5;

import java.sql.*;
import kurswork.Admin;
import kurswork.Grup;

import oracle.jdbc.pool.*;

/**
 *
 * @author Vlasov
 */
public class VODBC {

    static Course course[] = new Course[2];
    
    public static void TestUserInit(){
        
        course[0] = new Course("Matan");
        for(int i=0; i<8; i++){
            course[0].addLab(new Laba("Laba "+(i), "none"));
            course[0].labs[i].setResult("not checked yet");
        }
        
        course[1] = new Course("C programing");
        for(int i=0; i<3; i++){
            course[1].addLab(new Laba("Labka "+(i+1), "none"));
            course[1].labs[i].setResult("not checked yet");
        }
    }

    public static Admin aLoadAdmin() throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        
        Admin admin = new Admin();
        
        ResultSet rset;
        
        rset = stmt
            .executeQuery("select count(grup) from users_grups");
        
        if(rset.next()) admin.grups = new Grup[rset.getInt(1)];
        
        rset = stmt
            .executeQuery("select grup from users_grups");
        
        for(int i = 0; rset.next();i++){
            admin.grups[i] = new Grup();
            admin.grups[i].setName(rset.getString(1));
        }
        
        //int dq=1;
        for(int j=0; j<admin.grups.length; j++)
        {
        rset = stmt
            .executeQuery("select count(login) from (select * from (select USER_LOGIN_TABLE.LOGIN, user_table.name, user_table.phone, user_table.email, user_table.grup_id from BATMAN.USER_LOGIN_TABLE left join BATMAN.USER_TABLE on BATMAN.USER_LOGIN_TABLE.id = BATMAN.USER_TABLE.user_id) where grup_id = (select id from users_grups where grup = '"+admin.grups[j].getName()+"'))");
        
        int res =0;
        if(rset.next())
            res = rset.getInt(1);
        if(res>0){ 
                admin.grups[j].users = new User[res];
            
            rset = stmt
            .executeQuery("select * from (select USER_LOGIN_TABLE.LOGIN, user_table.name, user_table.phone, user_table.email, user_table.grup_id from BATMAN.USER_LOGIN_TABLE left join BATMAN.USER_TABLE on BATMAN.USER_LOGIN_TABLE.id = BATMAN.USER_TABLE.user_id) where grup_id = (select id from users_grups where grup = '"+admin.grups[j].getName()+"')");
        
            for(int i=0; rset.next(); i++)
            {
                admin.grups[j].users[i] = new User();
                admin.grups[j].users[i].setLogin(rset.getString(1));
                admin.grups[j].users[i].setName(rset.getString(2));
                admin.grups[j].users[i].setPhone(rset.getString(3));
                admin.grups[j].users[i].setEmail(rset.getString(4));
                admin.grups[j].users[i].setGrup(rset.getString(5));
            }
            }else{ //admin.grups[j].users = new User[1];
        }
        }
        EndSQLConection();
        return admin;
    }

    public static void updateGrupName(String tmpGrupName, String newGrupName) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        
        System.out.println("select id from users_grups where grup = '"+tmpGrupName+"'");
        ResultSet rset = stmt
            .executeQuery("select id from users_grups where grup = '"+tmpGrupName+"'");
        
        int id = -1;
        if(rset.next())
        {
            id = rset.getInt(1);
        }
        
        System.out.println("update users_grups set grup = '"+newGrupName+"' where id = '"+id+"'");
        rset = stmt
            .executeQuery("update USERS_GRUPS set GRUP = '"+newGrupName+"' where ID = '"+id+"'");
        EndSQLConection();
    }

    public static void addGrup(String text) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        //;
        System.out.println("insert into users_grups values ('"+text+"', (SELECT MAX(id) FROM users_grups)+1)");
        ResultSet rset = stmt
            .executeQuery("insert into users_grups values ('"+text+"', (SELECT MAX(id) FROM users_grups)+1)");
        EndSQLConection();
    }

    public static void deleteGrup(String text) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        //;
        System.out.println("delete from user_lab where user_lab.user_id in(select user_id from user_table where grup_id = (select id from users_grups where grup = '"+text+"'))");
        ResultSet rset = stmt
            .executeQuery("delete from user_lab where user_lab.user_id in(select user_id from user_table where grup_id = (select id from users_grups where grup = '"+text+"'))");
        
        System.out.println("delete from user_login_table where user_login_table.id in(select user_id from user_table where grup_id = (select id from users_grups where grup = '"+text+"'))");
        rset = stmt
            .executeQuery("delete from user_login_table where user_login_table.id in(select user_id from user_table where grup_id = (select id from users_grups where grup = '"+text+"'))");
        
        System.out.println("delete from user_table where user_table.grup_id = (select id from users_grups where grup = '"+text+"')");
        rset = stmt
            .executeQuery("delete from user_table where user_table.grup_id = (select id from users_grups where grup = '"+text+"')");

        System.out.println("delete from users_grups where users_grups.grup = '"+text+"'");
        rset = stmt
            .executeQuery("delete from users_grups where users_grups.grup = '"+text+"'");
        
        EndSQLConection();
        
    }

    public static void updateUserInfo(User newUser)  throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        
        rset = stmt
            .executeQuery("select ID, password from user_login_table where login = '"+newUser.tmp+"'");
        
        int id = -1;
        String pass = null;
        if(rset.next())
        {
            id = rset.getInt(1);
            pass = rset.getString(2);
            System.out.println(id);
        }
        
        System.out.println("update user_login_table set login = '"+newUser.getLogin()+"', permision = '"+newUser.getPermissionLevel()+"' where id = '"+id+"'");
        rset = stmt
            .executeQuery("update user_login_table set login = '"+newUser.getLogin()+"', permision = '"+newUser.getPermissionLevel()+"' where id = '"+id+"'");
  
        System.out.println("update user_table set grup_id = (select id from users_grups where grup = '"+newUser.getGrup()+"'), phone = '"+newUser.getPhone()+"', email = '"+newUser.getEmail()+"', name = '"+newUser.getName()+"' where user_id = (select id from user_login_table where login = '"+newUser.getLogin()+"')");
        rset = stmt
            .executeQuery("update user_table set grup_id = (select id from users_grups where grup = '"+newUser.getGrup()+"'), phone = '"+newUser.getPhone()+"', email = '"+newUser.getEmail()+"', name = '"+newUser.getName()+"' where user_id = (select id from user_login_table where login = '"+newUser.getLogin()+"')");

        EndSQLConection();
    }

    public static void addUser(User newUser)   throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        
        System.out.println("insert into user_login_table values('"+newUser.getLogin()+"', '"+MD5.getString(newUser.getPassword())+"', '"+newUser.getPermissionLevel()+"', (SELECT MAX(id) FROM user_login_table)+1)");
        rset = stmt
            .executeQuery("insert into user_login_table values('"+newUser.getLogin()+"', '"+MD5.getString(newUser.getPassword())+"', '"+newUser.getPermissionLevel()+"', (SELECT MAX(id) FROM user_login_table)+1)");
        
        System.out.println("insert into user_table values((select id from users_grups where grup = '"+newUser.getGrup()+"'), '"+newUser.getPhone()+"', '"+newUser.getEmail()+"', '"+newUser.getName()+"' ,(select id from user_login_table where login = '"+newUser.getLogin()+"'))");
        rset = stmt
            .executeQuery("insert into user_table values((select id from users_grups where grup = '"+newUser.getGrup()+"'), '"+newUser.getPhone()+"', '"+newUser.getEmail()+"', '"+newUser.getName()+"' ,(select id from user_login_table where login = '"+newUser.getLogin()+"'))");

        EndSQLConection();
    }

    public static void deleteUser(String login)   throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        
        System.out.println("select id from user_login_table where login = '"+login+"'");
        rset = stmt
            .executeQuery("select id from user_login_table where login = '"+login+"'");
        int id = -1;
        if(rset.next()){
            id = rset.getInt(1);
        }
        
        System.out.println("delete from user_lab where user_lab.user_id = '"+id+"'");
        rset = stmt
            .executeQuery("delete from user_lab where user_lab.user_id = '"+id+"'");
        
        System.out.println("delete from user_table where user_table.user_id = '"+id+"'");
        rset = stmt
            .executeQuery("delete from user_table where user_table.user_id = '"+id+"'");
        
        System.out.println("delete from user_login_table where user_login_table.id = '"+id+"'");
        rset = stmt
            .executeQuery("delete from user_login_table where user_login_table.id = '"+id+"'");
        
        EndSQLConection();
    }

    public static void updateCource(Course cou) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void addCourse(Course cou) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void deleteCourse(String text) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public VODBC(){
        this.TestUserInit();
    }
    
    public static void testSQLConection() throws ClassNotFoundException,
      SQLException{
        OracleDataSource ds = new OracleDataSource();
        ds.setDriverType("thin");
        ds.setServerName("localhost");
        ds.setPortNumber(1521);
        ds.setDatabaseName("neworcl"); // sid
        ds.setUser("BATMAN");
        ds.setPassword("ROBIN");
        
        Connection conn = ds.getConnection();

        Statement stmt = conn.createStatement();
        ResultSet rset = stmt
            .executeQuery("select PASSWORD from USER_LOGIN_TABLE where login = 'ussser'");
        if (rset.next())
          System.out.println(rset.getString(1));
        rset.close();
        stmt.close();
        conn.close();
    }
    
    private static String tmpUserName;
    
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
    
    private static void EndSQLConection() throws ClassNotFoundException,
      SQLException{
        stmt.close();
        conn.close();
    }
    
    public static boolean userExistValid(User user) throws ClassNotFoundException,
      SQLException{
        
        StartSQLConection();
        
        ResultSet rset = stmt
            .executeQuery("select PASSWORD from USER_LOGIN_TABLE where login = '"+user.getLogin()+"'");
        if(rset.next()){
            if(MD5.getString(user.getPassword()).equals(rset.getString(1)))
            return true;
        }
        EndSQLConection();
        return false;
    }

    public String getUserPermision(User user)throws ClassNotFoundException,
      SQLException{
        
        String answer = "ERROR";
        if(this.userExistValid(user))
        {
            StartSQLConection();
            ResultSet rset = stmt
                .executeQuery("select PERMISION from USER_LOGIN_TABLE where login = '"+user.getLogin()+"'");
            if(rset.next())
                answer = rset.getString(1);
            EndSQLConection();
        }
        return answer;
    }
    
    public static boolean updatePhoneNumber(User user)throws ClassNotFoundException,
      SQLException{ 
        StartSQLConection();
        ResultSet rset = stmt
            .executeQuery("Update user_table set phone = '"+user.tmp+"' where USER_ID = (select distinct ID from user_login_table where login = '"+user.getLogin()+"')");
        
        EndSQLConection();
        return true;
    }
    
    public static boolean updateEmail(User user)throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        System.out.println("Update user_table set email = '"+user.tmp+"' where user_id = (select distinct ID from user_login_table where login = '"+user.getLogin()+"')");
        ResultSet rset = stmt
            .executeQuery("Update user_table set email = '"+user.tmp+"' where user_id = (select distinct ID from user_login_table where login = '"+user.getLogin()+"')");
        
        EndSQLConection();
        return true;
    }
    
    public static Course getCourseLabs(Course crs)throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        
        ResultSet rset2;
        ResultSet rset = stmt
              .executeQuery("select LABA_NAME,deadline,description from LABS where COURSE_ID = (select distinct ID from COURSE where COURSE_NAME ='"+crs.getName()+"')");
        
        for(int i=0; rset.next(); i++){
             //rset2.next();
             crs.addLab(new Laba(rset.getString(1),rset.getString(2)));//rset2.getString(1)));
             //crs.labs[i].setResult(rset.getString(3));
             crs.labs[i].setDescription(rset.getString(3));
         }
        rset.close();
       
        rset = stmt
              .executeQuery("select POINT, LAB_ID as lab from user_lab where user_id = (select ID from user_login_table where LOGIN = '"+tmpUserName+"')  AND LAB_ID in (select LAB_ID from LABS where COURSE_ID = (select distinct ID from COURSE where COURSE_NAME = '"+crs.getName()+"'))");
        for(int i=0; rset.next(); i++){
            crs.labs[i].setResult(rset.getString(1));
        }
      
        EndSQLConection();
        
        return crs;
    }
    
    public static Course[] getCourse(String grup)throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        Course crs[] = null;
        
        String rq = "select course_name from course where id in (select course_id from course_grup where grup_id = (select id from users_grups where grup ='"+grup+"'))";
        System.out.println(rq);
        ResultSet rset = stmt
              .executeQuery(rq);
//              .executeQuery("select distinct COURSE.COURSE_NAME from COURSE where GRUP_ID = (select ID from USERS_GRUPS where GRUP = '"+grup+"')");
        
        for(int i=0; rset.next(); i++){
            if(crs == null){
                crs = new Course[1];
                crs[0] = new Course(rset.getString(1));
            }
            else{
                Course tmpcrs[] = new Course[crs.length];
                System.arraycopy(crs, 0, tmpcrs, 0, crs.length);
                crs = new Course[tmpcrs.length+1];
                System.arraycopy(tmpcrs, 0, crs, 0, tmpcrs.length);
                crs[i] = new Course(rset.getString(1));
            }
            crs[i]=getCourseLabs(crs[i]);
        }
        EndSQLConection();
        
        return crs;
    }
    
    
    public static Course[] getAllCourse()throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        Course crs[] = null;
        String rq = "select distinct COURSE.COURSE_NAME from COURSE";
        System.out.println();
        ResultSet rset = stmt
              .executeQuery(rq);
        
        if(rset.next()){
            crs = new Course[1];
            crs[0] = new Course(rset.getString(1));
            String rq2 = "select GRUP from USERS_GRUPS where ID in(select GRUP_ID from COURSE_GRUP where COURSE_ID = (select ID from COURSE where COURSE_NAME = '"+crs[0].getName()+"'))";
            System.out.println(rq2);
            ResultSet rset2 = stmt
            .executeQuery(rq2);
            for(int j=0; rset2.next(); j++){
                if(crs[0].stringGrups == null){
                    crs[0].stringGrups = new String[1];
                    crs[0].stringGrups[0] = rset2.getString(1);
                }
                else{
                    String tmpstr[] = new String[crs[0].stringGrups.length];
                    System.arraycopy(crs[0].stringGrups, 0, tmpstr, 0, crs[0].stringGrups.length);
                    crs[0].stringGrups = new String[tmpstr.length+1];
                    System.arraycopy(tmpstr, 0, crs[0].stringGrups, 0, tmpstr.length);
                    crs[0].stringGrups[j] = rset.getString(1);
                }
            }

            for(int i=0; rset.next(); i++){
                Course tmpcrs[] = new Course[crs.length];
                System.arraycopy(crs, 0, tmpcrs, 0, crs.length);
                crs = new Course[tmpcrs.length+1];
                System.arraycopy(tmpcrs, 0, crs, 0, tmpcrs.length);
                crs[i] = new Course(rset.getString(1));

                for(int j=0; rset2.next(); j++){
                    if(crs[i].stringGrups == null){
                        crs[i].stringGrups = new String[1];
                        crs[i].stringGrups[0] = rset2.getString(1);
                    }
                    else{
                        String tmpstr[] = new String[crs[i].stringGrups.length];
                        System.arraycopy(crs[i].stringGrups, 0, tmpstr, 0, crs[i].stringGrups.length);
                        crs[i].stringGrups = new String[tmpstr.length+1];
                        System.arraycopy(tmpstr, 0, crs[i].stringGrups, 0, tmpstr.length);
                        crs[i].stringGrups[j] = rset.getString(1);
                    }
                }
                crs[i] = getCourseLabs(crs[i]);
            }
        }
        EndSQLConection();
        return crs;
    }
    
    public MainTransferDate LoadUserDate(MainTransferDate date)throws ClassNotFoundException,
      SQLException{
        //TestUserInit();
        
        StartSQLConection();
        
        String rq = "select PERMISION from USER_LOGIN_TABLE where login = '"+date.user.getLogin()+"'";
        
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        if(rset.next())
            date.user.setPermissionLevel(rset.getString(1));
        else date.user.setPermissionLevel("");
        
        rq = "select GRUP from USERS_GRUPS where id = (select GRUP_ID from USER_TABLE where USER_ID = (select distinct ID from user_login_table where login = '"+date.user.getLogin()+"'))";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        if(rset.next())
            date.user.setGrup(rset.getString(1));
        else date.user.setGrup("");
        
        rq = "select NAME from USER_TABLE where USER_ID = (select distinct ID from user_login_table where login = '"+date.user.getLogin()+"')";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        if(rset.next())
            date.user.setName(rset.getString(1));
        else date.user.setName("");
        
        rq = "select PHONE from USER_TABLE where USER_ID = (select distinct ID from user_login_table where login = '"+date.user.getLogin()+"')";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        if(rset.next())
            date.user.setPhone(rset.getString(1));
        else date.user.setPhone("");
        
        rq = "select EMAIL from USER_TABLE where USER_ID = (select distinct ID from user_login_table where login = '"+date.user.getLogin()+"')";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        if(rset.next())
            date.user.setEmail(rset.getString(1));
        else date.user.setEmail("");
        
        EndSQLConection();
        tmpUserName = date.user.getLogin();
        date.user.course = getCourse(date.user.getGrup());
        
        EndSQLConection();
        return date;
    }
    
    public static Course[] getCourses()throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        
        Course crs[] = null;
        
        String rq = "select count(course_name) from course";
        //rq = "select course_name from course";
        System.out.println(rq);
        ResultSet rset = stmt.executeQuery(rq);
        rset = stmt.executeQuery(rq);
        
        if(rset.next()){
            crs = new Course[rset.getInt(1)];
        }else return null;
        
        rq = "select course_name from course";
        System.out.println(rq);
        rset = stmt.executeQuery(rq);
        
        for(int i=0; rset.next(); i++){
            crs[i] = new Course(rset.getString(1));
            
            String rq2 = "select * from labs where course_id = (select id from course where course_name = '"+crs[i].getName()+"')";
            System.out.println(rq2);
            Statement stmt2 = conn.createStatement();
            ResultSet rset2 = stmt2.executeQuery(rq2);
            while(rset2.next()){
            Laba lab = new Laba();
                lab.setDeadline(rset2.getString(2));
                lab.setName(rset2.getString(1));
                lab.setDescription(rset2.getString(3));
                crs[i].addLab(lab);
            }
            
            String rq3 = "select GRUP from users_grups where id in (select grup_id from course_grup where course_id = (select id from course where course_name = '"+crs[i].getName()+"'))"; 
            Statement stmt3 = conn.createStatement();
            ResultSet rset3 = stmt2.executeQuery(rq3);
            while(rset3.next()){
            Grup grup = new Grup();
                grup.setName(rset3.getString(1));
                crs[i].addGrup(grup);
            }
        }
        
        EndSQLConection();
        
        return crs;
    }
}
