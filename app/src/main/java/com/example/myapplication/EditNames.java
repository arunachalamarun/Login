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

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.R.*;
import static android.R.layout.*;

public class EditNames extends AppCompatActivity {
    ListView mainFunc;
    ArrayList<String> menu = new ArrayList<>();
    EditText alertEdit;
    Editable value;

    public class Details extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            super.onPostExecute(aVoid);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_names);
        mainFunc = findViewById(R.id.allDetails);

        int pid = android.os.Process.myPid();
        String whiteList = "logcat -P '" + pid + "'";
        try {
            Runtime.getRuntime().exec(whiteList).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditNames dn = new EditNames();
        menu.add("change name");
        menu.add("change password");
        menu.add("change phone number");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, simple_list_item_1, menu);
        mainFunc.setAdapter(adapter);

        mainFunc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                alert(position);
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





