package com.spark.sql;

import java.sql.*;

public class Hive_JDBC {

    static {
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
             connection = DriverManager.getConnection("jdbc:hive2://hadoop102:10000");
             ps = connection.prepareStatement("select * from  student");
             rs  = ps.executeQuery();
             while(rs.next()){
                System.out.println(rs.getString("id"));
                System.out.println(rs.getString("name"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
