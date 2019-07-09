package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    private LinearLayout searchResultsHistory = null;
    private TextView message = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        message = findViewById(R.id.noResultHistory);


        Button search = findViewById(R.id.buttonSearchHistory);
        search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View view) {

                final String userPINsearch = ((EditText)findViewById(R.id.searchByPIN)).getText().toString();


                message.setText("");

                searchResultsHistory = findViewById(R.id.searchResultsHistory);
                searchResultsHistory.removeAllViews();

                API.getJSON("http://10.0.2.2:5000/renthistory", new ReadDataHandler(){
                    @Override
                    public void handleMessage(Message msg) {
                        String odgovor = getJson();
                        try {
                            JSONArray array = new JSONArray(odgovor);
                            //odgovor spakujemo u JSONArray, i onda parsiramo da bismo dobili linked list
                            ArrayList<RentHistory> history = RentHistory.parseJSONArray(array);

                            for (RentHistory rh : history) {

                                String heshovan = rh.md5(userPINsearch);


                                if (rh.getUserPIN().equals(heshovan))
                                    ispisiRezultate(rh);

                            }
                            if (searchResultsHistory.getChildCount() == 0) {
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

    private void ispisiRezultate(RentHistory rh){
        LinearLayout item = (LinearLayout)getLayoutInflater().inflate(R.layout.renthistory, null);

        TextView rentedbooks = (TextView) item.findViewById(R.id.displayRentedbooks);
        rentedbooks.setKeyListener(null);
        rentedbooks.setText("Books rented by user : " + rh.getRentedbooks());

        TextView currentlyrenting = (TextView) item.findViewById(R.id.displayCurrentlyrenting);
        currentlyrenting.setKeyListener(null);

        if(rh.getCurrentlyrenting().length()==0) {

            currentlyrenting.setText("The user is not renting any books at the moment");
        }
        else {

            currentlyrenting.setText("The user is currently renting : " + rh.getCurrentlyrenting());
        }
        item.setTag(rh.getId());

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

        searchResultsHistory.addView(item);
    }
}
