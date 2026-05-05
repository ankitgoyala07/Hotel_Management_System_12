/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
/**
 *
 * @author i3
 */
public class MySqlConnection implements Database {
@Override
    public Connection Openconnection(){
        try{
            String password = "1234";
            String username = "root";
            String database = "shop";
               Connection connection;

            connection = DriverManager.getConnection(

                    "jdbc:mysql://localhost:3306/" + database, username, password

            );
            return connection;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
   

    @Override
    public void closeConnection(Connection conn) {
        try{

            if(conn != null && !conn.isClosed() ){

                conn.close();

                System.out.println("Connection close");

            }

            

        }catch(Exception e){

            System.out.println(e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
           try{

           Statement stmp = conn.createStatement();

           ResultSet result = stmp.executeQuery(query);

           return result;

       

       }catch (Exception e){

           System.out.println(e);

           return null;

       }
    }

    @Override
    public int executeUpdate(Connection conn, String query) {
         try{

          Statement stmp = conn.createStatement();

          int result = stmp.executeUpdate(query);

          return result;

          

      }catch(Exception e){

          System.out.println(e);

          return -1;
      }
    }   
}

