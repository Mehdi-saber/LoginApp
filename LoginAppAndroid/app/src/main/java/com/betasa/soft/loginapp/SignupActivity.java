package com.betasa.soft.loginapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.signUpbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                String username=((EditText)findViewById(R.id.signupUsernameET)).getText().toString();
                String password=((EditText)findViewById(R.id.signupPasswordET)).getText().toString();
                TextView tv=(TextView)findViewById(R.id.signuoLogTV);
                if( MySqlConnection.funcUser(username,password,"signup"))  {
                    tv.setText("Signup State : Success!");
                    tv.setTextColor(Color.GREEN);
                }
                else {
                    tv.setText("Signup State : Problem!");
                    tv.setTextColor(Color.RED);
                }
            }

        });
        findViewById(R.id.signinTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent newI=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(newI);
            }

        });

    }
}
