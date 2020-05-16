package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

import java.security.Key;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {


    EditText name;
    EditText pass;
    EditText phoneNumber;
    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign Up");

        name = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber.setOnKeyListener(this);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.constrainLayout);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } else {
                    Log.i("not opened", "NOt opend");
                }
            }
        });
        Log.i("came back", "CAME BACK after on click");
    }

    private void alertDisplayer(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }


    public void onClick(View view) {
        try {
            ParseUser user = new ParseUser();
            String n = name.getText().toString();
            String p = pass.getText().toString();
            String phone = phoneNumber.getText().toString();


            if (TextUtils.isEmpty(n)) {
                name.setError("username cannot be empty");
                count++;
                if (count > 10) {
                    alertDisplayer("Error", "Enter the data");
                }
                Log.i("count", Integer.toString(count));
                if (TextUtils.isEmpty(p))
                    pass.setError("password cannot be empty");
                //count++;
                return;
            }

            if (TextUtils.isEmpty(p)) {
                pass.setError("password cannot be empty");
                return;
            }

            if (view.getId() == R.id.constrainLayout) {
                Log.i("tocked", "Touched");
            }

            //  if(view ==R.id.layout )
// Set the user's username and password, which can be obtained by a forms
            if (n != null && p != null) {
                user.setUsername(n);
                user.setPassword(p);
                //  user.getParseObject("user").put("phone", phone);

                user.put("phone", phone);

                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            alertDisplayer("congrats", "Worked Successfully");
                            Toast.makeText(MainActivity.this, "Sign up is successful", Toast.LENGTH_LONG).show();

                        } else {
                            ParseUser.logOut();
                            Toast.makeText(MainActivity.this, "user name already exists", Toast.LENGTH_LONG).show();
                        }
                    }

                });
            } else {
                Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
            onClick(v);
        return false;
    }


}
