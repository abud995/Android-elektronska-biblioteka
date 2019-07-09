package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

public class Pretraga extends AppCompatActivity {

    private Spinner searchByGenre;
    private ArrayList<String> genres = new ArrayList<>();
    private LinearLayout searchResults = null;
    private TextView message = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga);

        message = findViewById(R.id.noResult);

        searchByGenre = findViewById(R.id.searchByGenre);
        genres.add("All genres");
        genres.add("fantasy");
        genres.add("sci-fi");
        genres.add("horror");
        ArrayAdapter genreAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genres);
        searchByGenre.setAdapter(genreAdapter);

        Button search = findViewById(R.id.buttonSearchDetailed);
        search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View view) {
                final String authorBook = ((EditText)findViewById(R.id.searchByAuthor)).getText().toString();
                final String titleBook = ((EditText)findViewById(R.id.searchByTitle)).getText().toString();
                final String genreBook = searchByGenre.getSelectedItem().toString();

                message.setText("");

                searchResults = findViewById(R.id.searchResults);
                searchResults.removeAllViews();

                API.getJSON("http://10.0.2.2:5000/knjige", new ReadDataHandler(){
                    @Override
                    public void handleMessage(Message msg) {
                        String odgovor = getJson();
                        try {
                            JSONArray array = new JSONArray(odgovor);
                            //odgovor spakujemo u JSONArray, i onda parsiramo da bismo dobili linked list
                            ArrayList<Knjiga> knjige = Knjiga.parseJSONArray(array);

                            for (Knjiga k : knjige) {
                                if (genreBook.equals("All genres") && k.getAuthor().contains(authorBook) && k.getTitle().contains(titleBook))
                                    ispisiRezultate(k);
                                else if(genreBook.equals("All genres") && authorBook.isEmpty() && titleBook.isEmpty())
                                    ispisiRezultate(k);
                                else if (genreBook.equals(k.getGenre()) && authorBook.isEmpty() && titleBook.isEmpty())
                                    ispisiRezultate(k);
                                else if (genreBook.equals(k.getGenre()) && k.getAuthor().contains(authorBook)  && k.getTitle().contains(titleBook))
                                    ispisiRezultate(k);
                            }
                            if (searchResults.getChildCount() == 0) {
                                message.setText("No search results");
                            }
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void ispisiRezultate(Knjiga k){
        LinearLayout item = (LinearLayout)getLayoutInflater().inflate(R.layout.knjiga, null);

        ImageView icon = item.findViewById(R.id.icon);
        if (k.getGenre().equals("fantasy"))
            icon.setImageResource(R.drawable.crown);
        else if(k.getGenre().equals("sci-fi"))
            icon.setImageResource(R.drawable.ufo);
        else if(k.getGenre().equals("horror"))
            icon.setImageResource(R.drawable.skeleton);
        TextView title = (TextView) item.findViewById(R.id.displayTitle);
        title.setKeyListener(null);
        title.setText("Title : " + k.getTitle());
        TextView author = (TextView) item.findViewById(R.id.displayAuthor);
        author.setKeyListener(null);
        author.setText("Author : " +k.getAuthor());
        TextView genre = (TextView) item.findViewById(R.id.displayGenre);
        genre.setKeyListener(null);
        genre.setText("Genre : " +k.getGenre());
        TextView availability = (TextView) item.findViewById(R.id.displayAvailability);
        availability.setKeyListener(null);
        availability.setText("Availability : "+k.getAvailability());

        item.setTag(k.getId());

        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        searchResults.addView(item);
    }


}
