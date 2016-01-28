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
import kurswork.Admin;
import kurswork.Grup;

import oracle.jdbc.pool.*;
/**
 *
 * @author Vlasov
 */
public class LODBC {
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
