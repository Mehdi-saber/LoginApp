package com.betasa.soft.loginapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str="Hello World"
                ,enc=MySqlConnection.encryptString(str)
                ,dec=MySqlConnection.encryptString(enc);

        Log.println(Log.INFO,"encrypt","Enc:");
        Log.println(Log.INFO,"encrypt",enc);
        Log.println(Log.INFO,"encrypt","Dec:");
        Log.println(Log.INFO,"encrypt",MySqlConnection.decryptString(enc));

        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                String username=((EditText)findViewById(R.id.etUsername)).getText().toString();
                String password=((EditText)findViewById(R.id.etPassword)).getText().toString();
                TextView tv=(TextView)findViewById(R.id.state);
                if( MySqlConnection.funcUser(username,password,"signin"))  {
                    tv.setText("Login State : Entered!");
                    tv.setTextColor(Color.GREEN);
                }
                else {
                    tv.setText("Login State : Wrong username or password!");
                    tv.setTextColor(Color.RED);
                }
            }

        });
        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent = new Intent( getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }

        });
    }
}
