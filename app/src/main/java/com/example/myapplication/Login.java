package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;


import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class Login extends AppCompatActivity {

    EditText name;
    EditText password;
    static String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        /*ParseObject score = new ParseObject("score");

        score.put("score", 86);
        score.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Toast.makeText(getApplicationContext(), "score saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "score not saved", Toast.LENGTH_LONG).show();
                }
            }
        });*/


        name = (EditText) findViewById(R.id.nameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
    }

    private void alertDisplayer(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        //      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //    startActivity(intent);
                    }
                });
        // AlertDialog ok = builder.create();
        //ok.show();
    }

    public void OnLogin(View view) {

        final String na = name.getText().toString();
        String pass = password.getText().toString();

        ParseUser.logInInBackground(na, pass, new LogInCallback() {


            @Override
            public void done(ParseUser parseUser, com.parse.ParseException e) {

                if (parseUser != null) {
                    objectId = parseUser.getObjectId().toString();
                    alertDisplayer("Sucessful Login", "Welcome back  " + na + "!");
                    Intent in = new Intent(getApplicationContext(), EditNames.class);
                    in.putExtra("objectId", objectId);
                    Log.i("class name", ParseUser.class.getClass().toString());
                    Log.i("idofobject", objectId);
                    startActivity(in);
                } else {
                    ParseUser.logOut();
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void OnSignup(View view) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);

    }
}


