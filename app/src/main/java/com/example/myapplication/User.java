package com.example.myapplication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    public static final String TABLE_NAME="users";
    public static final String FIELD_USER_PIN="userPIN";
    public static final String FIELD_NAME="name";
    public static final String FIELD_SURNAME="surname";


    private String name,surname, username,userPIN;

    public User(String userPIN,String name, String surname, String username){
        this.userPIN = userPIN;
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public String getUserPIN() {
        return userPIN;
    }

    public void setUserPIN(String userPIN) {
        this.userPIN = userPIN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static ArrayList<User> getUsers(){

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("81dc9bdb52d04dc20036dbd8313ed055","Stevan","Stevic","Steva123")); // PIN kod : 1234
        users.add(new User("a01610228fe998f515a72dd730294d87","Petar","Petrovic","Pera123")); // PIN kod : 1212
        users.add(new User("1111","Marko","Markovic","Marko123")); // provera da li radi ako nije zapisan kao hesh (ne radi)

        return users;
    }


    public String md5(String s) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(s.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkLogIn(String username, String pin){

        ArrayList<User> users = getUsers();

        for(User u : users){

            String userUsername = u.getUsername();
            String userPIN = u.getUserPIN();
            String userHash = u.md5(pin);

            if(userUsername.equals(username) && userPIN.equals(userHash)){
                return true;
            }
        }
        return false;

    }




}
