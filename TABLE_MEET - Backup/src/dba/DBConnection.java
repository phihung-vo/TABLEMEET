package dba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection table_meetConnection(){
        Connection cn = null;
        String username = "sa";
        String password = "1123";
        String url="jdbc:sqlserver://localhost:1433;databaseName=TABLE_MEET";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(url, username, password);
            if(cn!=null){
                System.out.println("Connect SQL Successfully");
            }
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        return cn;
    }
}