package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class RentHistory {

    private String id, userPIN,rentedbooks,currentlyrenting;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserPIN() {
        return userPIN;
    }

    public void setUserPIN(String userPIN) {
        this.userPIN = userPIN;
    }

    public String getRentedbooks() {
        return rentedbooks;
    }

    public void setRentedbooks(String rentedbooks) {
        this.rentedbooks = rentedbooks;
    }

    public String getCurrentlyrenting() {
        return currentlyrenting;
    }

    public void setCurrentlyrenting(String currentlyrenting) {
        this.currentlyrenting = currentlyrenting;
    }

    public RentHistory(String id, String userPIN, String rentedbooks, String currentlyrenting) {
        this.id = id;
        this.userPIN = userPIN;
        this.rentedbooks = rentedbooks;
        this.currentlyrenting = currentlyrenting;
    }

    public RentHistory(){

    }

    public static RentHistory parseJSON(JSONObject object){
        RentHistory history = new RentHistory();

        try{
            if(object.has("id")){
                history.setId(object.getString("id"));
            }

            if(object.has("userPIN")){
                history.setUserPIN(object.getString("userPIN"));
            }

            if(object.has("rentedbooks")){
                history.setRentedbooks(object.getString("rentedbooks"));
            }

            if(object.has("currentlyrenting")){
                history.setCurrentlyrenting(object.getString("currentlyrenting"));
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return history;
    }

    public static ArrayList<RentHistory> parseJSONArray(JSONArray array){
        ArrayList<RentHistory> list = new ArrayList<>();
        try{
            for(int i = 0; i < array.length(); i++){
                RentHistory history = parseJSON(array.getJSONObject(i));
                list.add(history);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
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



}
