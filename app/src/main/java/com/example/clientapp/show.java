package com.example.clientapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class show extends AppCompatActivity {

    final Activity activity = this;


    TextView red_text;
    TextView green_text;
    TextView yellow_text;

    String redValue;
    String greenValue;
    String yellowValue;
    String latitudeValue;
    String longitudeValue;

    String red;
    String green;
    String yellow;

    ImageView image;
    TextView congrat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        red_text = findViewById(R.id.text_red);
        green_text = findViewById(R.id.text_green);
        yellow_text = findViewById(R.id.text_yellow);

        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        redValue = bundle.getString("textred");
         greenValue = bundle.getString("textgreen");
         yellowValue = bundle.getString("textyellow");
         latitudeValue = bundle.getString("user_latitude");
         longitudeValue = bundle.getString("user_longitude");


        red_text.setText(redValue);
        green_text.setText(greenValue);
        yellow_text.setText(yellowValue);


    }

    public void check_product(View view) {

        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode, data);
        if (result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "you cancelled the scanning ",Toast.LENGTH_SHORT).show();

            }else{
                image = findViewById(R.id.status_image);
                congrat = findViewById(R.id.congratulation);
                String str = result.getContents();
                try {
                    JSONObject jObj = new JSONObject(str);
                    red = jObj.getString("red");
                    green = jObj.getString("green");
                    yellow = jObj.getString("yellow");

                    Toast.makeText(show.this, "red value  = " + red +"\n"+"green value = " + green+"\n"+"yellow value = " + yellow, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(show.this, " inside catch ", Toast.LENGTH_SHORT).show();
                }

                congrat.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);

                if (red != null && green != null && yellow != null) {
                    if (red.equals(redValue) && green.equals(greenValue) && yellow.equals(yellowValue)) {

                        image.setBackgroundResource(R.drawable.right);
                        congrat.setText("congratulation");
                    } else {

                        image.setBackgroundResource(R.drawable.wrong);
                        congrat.setText("the box is wrong");
                    }
                }else{
                    Toast.makeText(show.this, "the qr code has wrong data ", Toast.LENGTH_SHORT).show();

                }

            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
