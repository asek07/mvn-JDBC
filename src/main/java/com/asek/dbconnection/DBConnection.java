package com.asek.dbconnection;

import java.sql.*;

public class DBConnection {

    public Connection getConnection() {

        Connection con = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:33306/testdb?useSSL=false&serverTimezone=UTC", "testuser",
                    "password");
            // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    //RETURN ALL USER INFORMATION FROM DB (first & last name only)
    public void getAllUserInfo() {

        Connection con = getConnection();

        try {
            Statement st = con.createStatement();
            ResultSet rset = st.executeQuery("SELECT * FROM users");
            int iteration = 0;

            while(rset.next()) {
                if(iteration == 0) {
                    System.out.println("Displaying all data in db:");
                }
                String user = rset.getString("uid");
                String firstname = rset.getString("firstname");
                String lastname = rset.getString("lastname");

                System.out.println(user + " = " + firstname.substring(0,1).toUpperCase() + firstname.substring(1)
                        + " " + lastname.substring(0,1).toUpperCase() + lastname.substring(1));
                iteration++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ADD A NEW DB USER
    public void addNewUser(DBUser u) {

        Connection con = getConnection();

        try {
           PreparedStatement ps =  con.prepareStatement("insert into users values(?,?,?)");
           ps.setInt(1, u.getId());
           ps.setString(2, u.getFirstname());
           ps.setString(3, u.getLastname());

           int i = ps.executeUpdate();

           if(i != 0) {
               System.out.println(u.getFirstname() + " was inserted successfully.");
           }
           else {
               System.out.println("Could not insert properly.");
           }
        }
        catch (Exception e) {
            System.out.println("User may have been added to db already, there may be a duplicate");
            e.printStackTrace();
        }
    }

    //LOOKUP A USER BASED ON THEIR UNIQUE ID
    public void findUserWithId(int id) {
        Connection con = getConnection();

        try {

            PreparedStatement ps = con.prepareStatement("SELECT firstname, lastname from users where uid=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                //Formatting strings
                String firstname =  rs.getString("firstname").substring(0,1).toUpperCase()
                        + rs.getString("firstname").substring(1);
                String lastname =  rs.getString("lastname").substring(0,1).toUpperCase()
                        + rs.getString("lastname").substring(1);

                System.out.println("User found!");
                System.out.println("Full Name: " + firstname + " " + lastname);
            }
            else {
                System.out.println("User with ID=" + id + " cannot be found in DB.");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //DELETE A USER FROM DB BASED ON UID
    public void deleteUser(int id) {
        Connection con = getConnection();

        try {

            PreparedStatement ps = con.prepareStatement("DELETE from users where uid=?");
            ps.setInt(1, id);
            int i = ps.executeUpdate();

            if(i != 0) {
                System.out.println("Delete success.");
            }
            else {
                System.out.println("Delete failed.");
            }
        }
        catch(Exception e) {
            System.out.println("User may not exist in the DB.");
            e.printStackTrace();
        }
    }

    //UPDATE USERS FIRST/LAST NAME BASED ON UID
    public void updateFirstAndLastName(int id, String newFirstname, String newLastname) {

        Connection con = getConnection();

        try {
            PreparedStatement ps = con.prepareStatement("UPDATE users set firstname=?, lastname=? WHERE uid=?");
            ps.setString(1, newFirstname);
            ps.setString(2, newLastname);
            ps.setInt(3, id);

            int i = ps.executeUpdate();

            if(i != 0) {
                System.out.println("Updated username successfully.");
            }
            else {
                System.out.println("Could not update username.");
            }
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
    }


}
