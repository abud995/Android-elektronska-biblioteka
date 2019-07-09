package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements KnjigaFragment.OnFragmentInteractionListener {

    private Button pretraga,history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_menu);


            pretraga = findViewById(R.id.buttonSearch);
            pretraga.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainMenu.this, Pretraga.class));
                }
            });

            history = findViewById(R.id.buttonHistory);
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainMenu.this, History.class));
                }
            });


            KnjigaFragment ff = KnjigaFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.portraitFrame, ff);
            ft.commit();
        }
        else {
            setContentView(R.layout.activity_main_menu_landscape);

            pretraga = findViewById(R.id.buttonSearchLandScape);
            pretraga.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainMenu.this, Pretraga.class));
                }
            });

            history = findViewById(R.id.buttonHistoryLandScape);
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainMenu.this, History.class));
                }
            });

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main_menu);
            getSupportFragmentManager().beginTransaction().add(R.id.portraitFrame, KnjigaFragment.newInstance()).commit();
        }
        else
            setContentView(R.layout.activity_main_menu_landscape);
    }

    @Override
    public void izabranaKnjiga(String knjiga) {

        IzabranaKnjigaFragment pf = (IzabranaKnjigaFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailedFragment);

        if(pf != null){
            pf.zamenaKnjige(knjiga);

        }else{
            IzabranaKnjigaFragment pf1 = IzabranaKnjigaFragment.newInstance(knjiga);

            FragmentTransaction pt = getSupportFragmentManager().beginTransaction();
            pt.replace(R.id.portraitFrame, pf1);
            pt.addToBackStack(null);
            pt.commit();

        }


    }

}
