package com.example.clientapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class    client_1 extends AppCompatActivity {


    /*
       TextView greenValue = (TextView) findViewById(R.id.text_green);
       TextView orangeValue = (TextView) findViewById(R.id.text_orange);
       TextView yellowValue = (TextView) findViewById(R.id.text_yellow);
  */
    String user_name ="";
    String user_email="";
    String user_latitude = "";
    String user_longitude = "";

    static int code_scan = 102;
    public int RED_VALUE = 1;
    public int GREEN_VALUE = 1;
    public int YELLOW_VALUE = 1;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_name = bundle.getString("user_name");
        user_email = bundle.getString("user_email");
        user_latitude = bundle.getString("user_latitude");
        user_longitude = bundle.getString("user_longitude");


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("orders");


    }

    public void order(View view) {

        //design the shape of the message of the order
        final String message  = "Red number = " + RED_VALUE +"\n" + "Green number = " + GREEN_VALUE + "\n" +
                 "Yellow number = " + YELLOW_VALUE + "\n" +"user_email : " +  user_email +"\n"
                + "user_name : " +user_name +"\n"+ "user_latitude : " +user_latitude +"\n"+ "user_longitude : " +user_longitude ;


        // set dialog message
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Are you sure to order this : ");
        alertDialogBuilder.setMessage(message).setCancelable(false).setPositiveButton("Yes"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // if the yes button is clicked , start the new activity
                        /*

                        */

                        String red = Integer.toString(RED_VALUE);
                        String green = Integer.toString(GREEN_VALUE);
                        String yellow = Integer.toString(YELLOW_VALUE);
                        Client client = new Client(user_email,user_name,user_latitude,user_longitude);

                        order order = new order(red,green,yellow,client);
                        mMessageDatabaseReference.push().setValue(order);
                        finish();
                        /*
                        // create json object if needed
                        JSONObject jsonObj = new JSONObject();
                        try {
                            jsonObj.put("red",red);
                            jsonObj.put("green",green);
                            jsonObj.put("yellow",yellow);
                            jsonObj.put("latitude",user_latitude);
                            jsonObj.put("longitude",user_longitude);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String mes = jsonObj.toString();
                        */

                        //startActivity(i);
                    /*
                        Intent resultIntent = new Intent();
                        // TODO Add extras or a data URI to this intent as appropriate.
                        resultIntent.putExtra("order", message);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    */
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // if the no button is clicked ,  close the dialog message and do no thing
                        dialog.cancel();
                    }
                });

        // create alert message
        AlertDialog alertDialog = alertDialogBuilder.create();


        // show it
        alertDialog.show();

    }




    public void incrementRed(View view) {
        TextView redValue = (TextView) findViewById(R.id.text_red);
        int value = Integer.parseInt(redValue.getText().toString());
        value++;
        if(value > 10){
            value =10;
        }
        RED_VALUE = value;
        redValue.setText(""+value);
    }


    public void incrementGreen(View view) {
        TextView greenValue = (TextView) findViewById(R.id.text_green);
        int value = Integer.parseInt(greenValue.getText().toString());
        value++;
        if(value > 10){
            value =10;
        }
        GREEN_VALUE = value;
        greenValue.setText(""+value);
    }




    public void incrementYellow(View view) {
        TextView yellowValue = (TextView) findViewById(R.id.text_yellow);
        int value = Integer.parseInt(yellowValue.getText().toString());
        value++;
        if(value > 10){
            value =10;
        }
        YELLOW_VALUE = value;
        yellowValue.setText(""+value);
    }






    public void decrementRed(View view) {
        TextView redValue = (TextView) findViewById(R.id.text_red);
        int value = Integer.parseInt(redValue.getText().toString());
        value--;
        if(value < 0){
            value =0;
        }
        RED_VALUE = value;
        redValue.setText("" + value);
    }

    public void decrementGreen(View view) {
        TextView greenValue = (TextView) findViewById(R.id.text_green);
        int value = Integer.parseInt(greenValue.getText().toString());
        value--;
        if(value < 0){
            value =0;
        }
        GREEN_VALUE = value;
        greenValue.setText("" + value);
    }

    public void decremenYellow(View view) {
        TextView yellowValue = (TextView) findViewById(R.id.text_yellow);
        int value = Integer.parseInt(yellowValue .getText().toString());
        value--;
        if(value < 0){
            value =0;
        }
        YELLOW_VALUE = value;
        yellowValue .setText("" + value);
    }


}

