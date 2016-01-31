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

    public static Admin aLoadAdmin() throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        
        Admin admin = new Admin();
        
        ResultSet rset;
        
        String rq = "select count(group_name) from users_groups";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        
        if(rset.next()) admin.grups = new Grup[rset.getInt(1)];
        
        rq = "select group_name from users_groups";
        rset = stmt
            .executeQuery(rq);
        
        for(int i = 0; rset.next();i++){
            admin.grups[i] = new Grup();
            admin.grups[i].setName(rset.getString(1));
        }
        
        //int dq=1;
        for(int j=0; j<admin.grups.length; j++)
        {
            rq = "select count(login) from user_table where group_id = "
                    + "(select id from users_groups where group_name = '"+admin.grups[j].getName()+"')";
            System.out.println(rq);
            rset = stmt
                .executeQuery(rq);

            int res =0;
            if(rset.next())
                res = rset.getInt(1);
            if(res>0){ 
                admin.grups[j].users = new User[res];

                rq = "select * from user_table where group_id = "
                        + "(select id from users_groups where group_name = '"+admin.grups[j].getName()+"')";
                System.out.println(rq);
            
                rset = stmt
                .executeQuery(rq);

                for(int i=0; rset.next(); i++)
                {
                    admin.grups[j].users[i] = new User();
                    admin.grups[j].users[i].setLogin(rset.getString(1));
                    admin.grups[j].users[i].setName(rset.getString(7));
                    admin.grups[j].users[i].setPhone(rset.getString(5));
                    admin.grups[j].users[i].setEmail(rset.getString(6));
                    admin.grups[j].users[i].setGrup(admin.grups[j].getName());
                }
                }else
            { //admin.grups[j].users = new User[1];
            }
        }
        EndSQLConection();
        return admin;
    }

    public static void updateGroupName(String tmpGroupName, String newGroupName) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        String rq = "update users_groups set group_name = '"+newGroupName+"' where group_name = '"+tmpGroupName+"'";
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        EndSQLConection();
    }

    public static void addGroup(String groupName) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        String rq = "insert into users_groups values ('"+groupName+"', (SELECT MAX(id) FROM users_groups)+1)";
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        EndSQLConection();
    }

    public static void deleteGroup(String text) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        String rq = "delete from users_groups where users_groups.group_name = '"+text+"'";
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        EndSQLConection();
    }

    public static void updateUserInfo(User newUser)  throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        String rq = "update user_table set login = '"+newUser.getLogin()
                +"', group_id = (select id from users_groups where group_name = '"+newUser.getGrup()
                +"'), phone = '"+newUser.getPhone()
                +"', email = '"+newUser.getEmail()
                +"', name = '"+newUser.getName()+"' where login = '"+newUser.tmp+"'";
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        EndSQLConection();
    }

    public static void addUser(User newUser)   throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        
        String rq = "insert into user_table values('"+newUser.getLogin()+
                "', '"+MD5.getString(newUser.getPassword())+
                "', ((SELECT MAX(id) FROM user_table)+1), "
                + "(select id from users_groups where group_name = '"
                +newUser.getGrup()+"'), '"+newUser.getPhone()+"', '"+
                newUser.getEmail()+"', '"+newUser.getName()+"', '"+
                newUser.getPermissionLevel()+"')";
        
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        
        EndSQLConection();
    }

    public static void deleteUser(String login)   throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        String rq = "delete from user_table where login = '"+login+"'";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        EndSQLConection();
    }

    public static void updateCource(Course cou)   throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        String rq = "update course set course_name = '"+cou.getName()+"' where course_name = '"+cou.tmp+"'";
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        EndSQLConection();
    }

    public static void addCourse(Course cou) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        
        String MaxId = "(SELECT MAX(id) FROM course)";
        System.out.println(MaxId);
        ResultSet rset = stmt
            .executeQuery(MaxId);
        
        if(rset.next())
            if(rset.getString(1)== null)
                MaxId = "1";
            
        String rq = "insert into course values('"+cou.getName()+"', "+MaxId+"+1)";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        EndSQLConection();
    }

    public static void deleteCourse(String courseName)   throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        String rq = "delete from course where course_name = '"+courseName+"'";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        EndSQLConection();
    }

    public static void setPass(String text, String pass)  throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
//        ResultSet rset;
//        
//        System.out.println();
//        rset = stmt
//            .executeQuery("insert into course values('"+cou.getName()+"', (SELECT MAX(id) FROM course)+1)");
//        
//            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        //"update user_login_table set password = '"+
//     
        
        EndSQLConection();
    }

    public static void addLab(Laba lab)  throws ClassNotFoundException,
      SQLException{
        StartSQLConection();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        String rq = "insert into labs values('"
                + lab.getName()+"', '"
                + lab.getDeadline()+"', '"
                + lab.getDescription()+ "', "
                + "(select id from course where course_name = '"+lab.getCourse()+"'), "
                + "(select max(lab_id) from labs)+1)";
        System.out.println(rq);
        ResultSet rset = stmt
                .executeQuery(rq);
        
        EndSQLConection();
    }

    public static void updateLab(Laba lab, Laba labBackup)  throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
//        "Update user_table set phone = '"+user.tmp+"' where login = '"+user.getLogin()+"'"
        String rq = "Update labs set "
                + "Lab_name = '"+lab.getName()+"', "
                + "deadline = '"+lab.getDeadline()+"', "
                + "description = '"+lab.getDescription()+"', "
                + "course_id = (select id from course where course_name = '"+lab.getCourse()+"') "
                + "where lab_name = '"+labBackup.getName()+"' and "
                + "deadline = '"+labBackup.getDeadline()+"'and "
                + "course_id = (select id from course where course_name = '"+labBackup.getCourse()+"') ";
        
        System.out.println(rq);
        ResultSet rset = stmt
                .executeQuery(rq);
        EndSQLConection();
    }

    public static void deleteLab(String labName, String labCourse) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        String rq = "delete from labs where lab_name = '"+labName+"' and "
                + "course_id = (select id from course where course_name = '"+labCourse+"')";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        EndSQLConection();
    }

    public static String[] getGroupInCourse(String name) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        String rq = "select count(*) from users_groups where id in"
                + "(select group_id from course_group where course_id = "
                + "(select id from course where course_name = '"+name+"'))";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        
        String []answer = null;
        if(rset.next()){
            int count = rset.getInt(1);
            if(count != 0){
                answer = new String[count];

                rq = "select * from users_groups where id in"
                    + "(select group_id from course_group where course_id = "
                    + "(select id from course where course_name = '"+name+"'))";
                System.out.println(rq);
                rset = stmt
                    .executeQuery(rq);
                for(int i=0; rset.next(); i++)
                    answer[i] = rset.getString(1);
            }
        }
        
        EndSQLConection();
        return answer;
    }

    public static String[] getGroupNotInCourse(String name) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        ResultSet rset;
        String rq = "select count(*) from users_groups where id not in"
                + "(select group_id from course_group where course_id = "
                + "(select id from course where course_name = '"+name+"'))";
        System.out.println(rq);
        rset = stmt
            .executeQuery(rq);
        
        String []answer = null;
        if(rset.next()){
            int count = rset.getInt(1);
            if(count != 0){
                answer = new String[count];

                rq = "select * from users_groups where id not in"
                    + "(select group_id from course_group where course_id = "
                    + "(select id from course where course_name = '"+name+"'))";
                System.out.println(rq);
                rset = stmt
                    .executeQuery(rq);
                for(int i=0; rset.next(); i++)
                    answer[i] = rset.getString(1);
            }
        }
        
        EndSQLConection();
        return answer;
    }

    public static void addGroupToCourse(String group, String Course) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        String rq = "insert into course_group values("
                + "(select id from users_groups where group_name = '"+group+"'),"
                + "(select id from course where course_name = '"+Course+"'))";
        System.out.println(rq);
        ResultSet rset = stmt
                .executeQuery(rq);
        
        EndSQLConection();
    }
    
    public static void delGroupFromCourse(String group, String Course) throws ClassNotFoundException,
      SQLException{
        StartSQLConection();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        String rq = "delete from course_group where "
                + "group_id = (select id from users_groups where group_name = '"+group+"') and "
                + "course_id = (select id from course where course_name = '"+Course+"')";
        System.out.println(rq);
        ResultSet rset = stmt
                .executeQuery(rq);
        
        EndSQLConection();
    }
    
    public VODBC(){
        
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
    
    private static void StartSQLConection__() throws ClassNotFoundException,
      SQLException{
        ds = new OracleDataSource();
        ds.setDriverType("thin");
        ds.setServerName("localhost");
        ds.setPortNumber(1521);
        ds.setDatabaseName("neworcl"); // sid
        ds.setUser("BATMAN");
        ds.setPassword("ROBIN");
        
        conn = ds.getConnection();
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        conn.setAutoCommit(false);
        //Savepoint save1 = conn.setSavepoint();
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
        
        String rq = "select PASSWORD from USER_TABLE where login = '"+user.getLogin()+"'";
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        if(rset.next()){
            String pass = rset.getString(1);
            String testPass  = MD5.getString(user.getPassword());
            if(testPass.equals(pass)){
                EndSQLConection();
                return true;
            }
        }
        EndSQLConection();
        return false;
    }

    public String getUserPermision(User user)throws ClassNotFoundException,
      SQLException{
        
        String answer = "ERROR";
        if(this.userExistValid(user))
        {
            StartSQLConection__();
            
            String rq = "select group_name from users_groups where id = (select group_id from user_table where login = '"+user.getLogin()+"')";
            System.out.println(rq);
            ResultSet rset = stmt
                .executeQuery(rq);
            if(rset.next())
                answer = rset.getString(1);
            EndSQLConection();
        }
        return answer;
    }
    
    public static boolean updatePhoneNumber(User user)throws ClassNotFoundException,
      SQLException{ 
        StartSQLConection();
        System.out.println();
        String rq = "Update user_table set phone = '"+user.tmp+"' where login = '"+user.getLogin()+"'";
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        EndSQLConection();
        return true;
    }
    
    public static boolean updateEmail(User user)throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        String rq = "Update user_table set email = '"+user.tmp+"' where login = '"+user.getLogin()+"'";
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        EndSQLConection();
        return true;
    }
    
    public static Course getCourseLabs(Course crs)throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        
        String rq = "select LAB_NAME,deadline,description from LABS where COURSE_ID = (select distinct ID from COURSE where COURSE_NAME ='"+crs.getName()+"')";
        System.out.println(rq);
        ResultSet rset = stmt
              .executeQuery(rq);
        
        for(int i=0; rset.next(); i++){
             crs.addLab(new Laba(rset.getString(1),rset.getString(2)));
             crs.labs[i].setDescription(rset.getString(3));
         }
        rset.close();
//        rset = stmt
//              .executeQuery("select POINT, LAB_ID as lab from user_lab where user_id = (select ID from user_table where LOGIN = '"+tmpUserName+"')  AND LAB_ID in (select LAB_ID from LABS where COURSE_ID = (select distinct ID from COURSE where COURSE_NAME = '"+crs.getName()+"'))");
//        for(int i=0; rset.next(); i++){
//            crs.labs[i].setResult(rset.getString(1));
//        }
        EndSQLConection();
        return crs;
    }
    
    public static Course[] getCourse(String group)throws ClassNotFoundException,
      SQLException{
        StartSQLConection();
        Course crs[] = null;
        
        String rq = "select course_name from course where id in (select course_id from course_group where group_id = (select id from users_groups where group_name ='"+group+"'))";
        System.out.println(rq);
        ResultSet rset = stmt
              .executeQuery(rq);
        
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
            String rq2 = "select GROUP from USERS_GROUPS where ID in"
                    + "(select GROUP_ID from COURSE_GROUP where COURSE_ID = (select ID from COURSE where COURSE_NAME = '"+crs[0].getName()+"'))";
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
        
        String rq = "select * from user_table left join users_groups on user_table.group_id = users_groups.id";             
        System.out.println(rq);
        ResultSet rset = stmt
            .executeQuery(rq);
        if(rset.next())
        {
            date.user.setLogin(rset.getString(1));
            date.user.setPhone(rset.getString(5));
            date.user.setEmail(rset.getString(6));
            date.user.setName(rset.getString(7));
            date.user.setPermissionLevel(rset.getString(8));
            date.user.setGrup(rset.getString(9));
        }
        
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
                Laba lab = null;
                if(rset2.getString(1) != null){
                    lab = new Laba();
                    lab.setDeadline(rset2.getString(2));
                    lab.setName(rset2.getString(1));
                    lab.setDescription(rset2.getString(3));
                }
                crs[i].addLab(lab);
            }
            
            String rq3 = "select GROUP_name from users_groups where id in (select group_id from course_group where course_id = (select id from course where course_name = '"+crs[i].getName()+"'))"; 
            Statement stmt3 = conn.createStatement();
            ResultSet rset3 = stmt3.executeQuery(rq3);
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
