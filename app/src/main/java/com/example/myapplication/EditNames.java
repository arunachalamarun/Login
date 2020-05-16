package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.R.layout.*;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.R.*;
import static android.R.layout.*;

public class EditNames extends AppCompatActivity {
    ListView mainFunc;
    ArrayList<String> menu = new ArrayList<>();
    EditText alertEdit;
    Editable value;
    boolean changedScore = false;
    final int[] finalScore = new int[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_names);
        mainFunc = findViewById(R.id.allDetails);

        int pid = android.os.Process.myPid();
        String whiteList = "logcat -P '" + pid + "'";

        EditNames dn = new EditNames();
        menu.add("change name");
        menu.add("change password");
        menu.add("change phone number");
        menu.add("View Balance");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, simple_list_item_1, menu);
        mainFunc.setAdapter(adapter);

        mainFunc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position != 3) {
                    alert(position);
                } else {
                    view();
                }
            }
        });

    }

    public void view() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("score");
        query.whereEqualTo("username", "Arun");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (changedScore == false) {
                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {
                            if (object.getInt("score") > 30) {
                                int score = object.getInt("score") + 10;
                                object.put("score", score);
                                object.saveInBackground();
                                changedScore = true;
                                Log.i("Your final score is ", String.valueOf(object.getInt("score")));
                                finalScore[0] = object.getInt("score");
                                Toast.makeText(EditNames.this, "Your Updated Score is " + Integer.toString(finalScore[0]), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(EditNames.this, "Your Updated Score is " + Integer.toString(finalScore[0]), Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void changeInServer(int n, final String name, String whichValue) {


        Log.i("Which Value", whichValue);
        ParseUser pUser = ParseUser.getCurrentUser();

        String objId = pUser.getObjectId();
        pUser.put(whichValue, name);
        pUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(EditNames.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });



      /*  ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("score");
        query.getInBackground(objId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                //if (e == null && object != null) {
                object.put("username", "aksj");
                object.saveInBackground();
                Toast.makeText(EditNames.this, "changed", Toast.LENGTH_SHORT).show();

                // }
            }
        });*/


        Log.i("change in server", "name is " + name + "object ID is " + Login.objectId);
    }

    public void alert(final int n) {
        String[] message = {"enter the username", "enter the password", "enter the phone number"};
        alertEdit = new EditText(this);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(getApplicationContext());
        alert.setMessage(message[n]);
        alert.setTitle("Confirm!!!!");
        alert.setView(edittext);
        final String[] whichValue = message[n].split(" ");
        alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value

                value = edittext.getText();
                boolean isPrinted = true;
                if (value.toString().isEmpty()) {
                    isPrinted = false;
                }
                while (isPrinted == false) {
                    Log.i("entered", "cheanghed");
                    Toast.makeText(EditNames.this, "The  " + whichValue[2] + " is empty", Toast.LENGTH_LONG).show();
                    isPrinted = true;
                    alert(n);


                }
                changeInServer(n, value.toString(), whichValue[2]);


            }

        }).setNegativeButton("No Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }


}





