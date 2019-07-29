package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    FirebaseAuth mAuth;


    private ProgressBar progressBar;


    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    public String Latitude;
    public String Longitude;
    public order orders;

    Button sign_up ;
    Button log_in ;
    Button get_loc;
    EditText name;
    EditText email;
    EditText pas;
    TextView location;
    public String name_text;
    public String email_text;
    public String pas_text;
    public String location_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_up = (Button)findViewById(R.id.sign);
        log_in = (Button)findViewById(R.id.login);
        get_loc = (Button)findViewById(R.id.get_location);

        name = (EditText)findViewById(R.id.user_name);
        email = (EditText)findViewById(R.id.emailAddress);
        pas = (EditText)findViewById(R.id.password);
        location = (TextView) findViewById(R.id.location);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);



        mAuth = FirebaseAuth.getInstance();




    }


    @Override
    protected void onStart() {
        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
            startActivity(new Intent(MainActivity.this, navigation.class));
            finish();


        }
        super.onStart();


    }


    public  void getLocation (View view){

        Intent i = new Intent(MainActivity.this,map.class);
        startActivityForResult(i, SECOND_ACTIVITY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == 2) {

                Latitude = data.getStringExtra("Lat");
                Longitude = data.getStringExtra("Lon");
                Toast.makeText(MainActivity.this, "latitude is " + Latitude + "\n" + "Longitude is" + Longitude, Toast.LENGTH_LONG).show();
                location.setText(getString(R.string.location_success));
                location.setTextSize(15);
            }

        }

    }

    public  void signUp(View view){

         name_text = name.getText().toString().trim();
         email_text = email.getText().toString().trim();
         pas_text = pas.getText().toString().trim();
         location_text = location.getText().toString().trim();


        if (name_text.isEmpty()) {
            name.setError(getString(R.string.input_error_name));
            name.requestFocus();
            return;
        }

        if (email_text.isEmpty()) {
            email.setError(getString(R.string.input_error_email));
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_text).matches()) {
            email.setError(getString(R.string.input_error_email_invalid));
            email.requestFocus();
            return;
        }
        if (pas_text.isEmpty()) {
            pas.setError(getString(R.string.input_error_password));
            pas.requestFocus();
            return;
        }

        if (pas_text.length() < 5) {
            pas.setError(getString(R.string.input_error_password_length));
            pas.requestFocus();
            return;
        }
        if (location_text.isEmpty()) {
            location.setError(getString(R.string.input_error_location));
            location.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email_text, pas_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(
                                    name_text,
                                    email_text,
                                    pas_text,
                                    Latitude,
                                    Longitude

                            );

                            FirebaseDatabase.getInstance().getReference("clients")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(MainActivity.this,navigation.class);
                                        startActivity(i);

                                    } else {

                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

    public void login(View view) {

        Intent i = new Intent(MainActivity.this,login.class);
        startActivity(i);


    }


}
