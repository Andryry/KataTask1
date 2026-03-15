package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

   private final static String DB_URL = "jdbc:mysql://localhost:3306/test_db_one";
   private final static String USER = "jpauser";
   private final static String PWD = "jpapwd";

   public static Connection getNewConnection () throws SQLException {
       return DriverManager.getConnection(DB_URL,USER,PWD);

   }






}
