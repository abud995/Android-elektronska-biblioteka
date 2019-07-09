package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText inputUsername,inputPIN;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents(){

        inputUsername =(EditText)findViewById(R.id.inputUsername);
        inputPIN =(EditText)findViewById(R.id.inputPIN);
        login = (Button)findViewById(R.id.buttonLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = inputUsername.getText().toString();
                String pin = inputPIN.getText().toString();

                if(username.isEmpty() || pin.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Please enter username and PIN",Toast.LENGTH_SHORT).show();
                }

                else if (User.checkLogIn(username,pin)){

                    Intent loginIntent =new Intent(MainActivity.this,MainMenu.class);
                    MainActivity.this.startActivity(loginIntent);

                }

                else{
                    Toast.makeText(getApplicationContext(),"Incorrect Username or PIN",Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


}
