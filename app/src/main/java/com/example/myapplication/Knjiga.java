package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Knjiga {

    private String id, author,title,availability,genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Knjiga(String id, String author, String title, String availability, String genre) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.availability = availability;
        this.genre = genre;
    }

    public Knjiga(){

    }

    public static Knjiga parseJSON(JSONObject object){
        Knjiga knjiga = new Knjiga();

        try{
            if(object.has("id")){
                knjiga.setId(object.getString("id"));
            }

            if(object.has("author")){
                knjiga.setAuthor(object.getString("author"));
            }

            if(object.has("title")){
                knjiga.setTitle(object.getString("title"));
            }

            if(object.has("availability")){
                knjiga.setAvailability(object.getString("availability"));
            }

            if(object.has("genre")){
                knjiga.setGenre(object.getString("genre"));
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return knjiga;
    }

    public static ArrayList<Knjiga> parseJSONArray(JSONArray array){
        ArrayList<Knjiga> list = new ArrayList<>();
        try{
            for(int i = 0; i < array.length(); i++){
                Knjiga knjiga = parseJSON(array.getJSONObject(i));
                list.add(knjiga);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }


}
