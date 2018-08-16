package com.asek.dbconnection;

public class Main {

    public static void main(String[] args) {

        //Connection to the DB object
        DBConnection db = new DBConnection();

        //Create array of first names and last names
        String[] firstnames = {"Kennith", "Ermelinda", "Matilde", "Adrian", "Wai",
                               "Aurelio", "Stephen", "Ouida", "In", "Catharine"};

        String[] lastnames = {"Bonilla", "Fitzgerald", "Beasley", "Riley", "Perez", "Dunham",
                              "Schmitt", "Duarte", "Wheeler", "Valazquez"};

        //ADDING 10 DBUser's to the users table
        for(int i=0; i < 10; i++) {
            int uid = i+1;
            DBUser user = new DBUser(uid, firstnames[i], lastnames[i]);

            db.addNewUser(user);
        }

        //FINDING user based on their ID and return their first and last name
        db.findUserWithId(5);

        //UPDATING the db entry with uid=10
        db.updateFirstAndLastName(10, "Jimbo", "Baggins");

        //DISPLAYING a list of the data in the user table
        db.getAllUserInfo();

        //DELETING usr based on their uid
        db.deleteUser(5);

        //DISPLAYING data in the user table, NOTE: uid=5 has been removed
        db.getAllUserInfo();

    }

}
