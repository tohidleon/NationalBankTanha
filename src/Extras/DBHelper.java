package Extras;

import DataStructure.Account;

import java.sql.*;
import java.util.ArrayList;

public class DBHelper {
    private Connection connection;
    private Statement statement;

    public DBHelper() {
        connect();

        creatTable();
        ObjToDB account = new ObjToDB();
        account.lastName = "hossein";
        account.name = "Shakeri";
        insertPerson(account);
        ArrayList<ObjToDB> list =getAllPerson();
        for (ObjToDB o : list
        ) {
            System.out.println(o.name.toString() );
        }
        close();
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:Data.db");
            statement = connection.createStatement();
            System.out.println("Connection Created");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //  close();
    }

    public void creatTable() {
        String tableSQL = "CREATE TABLE IF NOT EXISTS person (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,name TEXT , lastName TEXT);";
        try {
            statement.executeUpdate(tableSQL);
            System.out.println("Person table is created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //  close();
    }

    public void insertPerson(ObjToDB objToDB) {

        String insertSQL = "INSERT INTO person (name ) VALUES ('" +objToDB + "');";
        try {
            statement.executeUpdate(insertSQL);
            System.out.println("inserted");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        // close();
    }

    public ArrayList<ObjToDB> getAllPerson() {
        String getSql = "SELECT name , lastname FROM person;";
        ArrayList<ObjToDB> list = new ArrayList<DBHelper.ObjToDB>();
        try {
            ResultSet resultSet = statement.executeQuery(getSql);
            while (resultSet.next()) {
                ObjToDB objToDB = new ObjToDB();
                objToDB.name = resultSet.getString(1);
                objToDB.lastName = resultSet.getString(2);
                list.add(objToDB);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //  close();
        return list;

    }

    class ObjToDB {
        String name;
        String lastName;

        public String toString() {
            return "[" + name + "  " + lastName + "]";
        }
    }

    public void close() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new DBHelper();
    }
}

