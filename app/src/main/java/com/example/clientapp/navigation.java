package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  custom_adapter orderAdapter;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String uid;


    String user_name ="Mu";
    String user_email="mu@gmail.com";
    String user_latitude = "30.4578965";
    String user_longitude = "31.4578359";
    String result="";

    TextView name;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
            user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            uid  = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("clients");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user_name =  dataSnapshot.child(uid).child("name").getValue(String.class);
                    user_email =  dataSnapshot.child(uid).child("email").getValue(String.class);
                    user_latitude = dataSnapshot.child(uid).child("latitude").getValue(String.class);
                    user_longitude =  dataSnapshot.child(uid).child("longitude").getValue(String.class);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
/*

*/
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mMessageDatabaseReference = mFirebaseDatabase.getReference().child("orders");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ArrayList<order> orders = new ArrayList<order>();


        orderAdapter = new custom_adapter(this, orders);
        final ListView listView = findViewById(R.id.navi_list_view);
        listView.setAdapter(orderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                TextView viewred = view.findViewById(R.id.red);
                TextView viewgreen = view.findViewById(R.id.green);
                TextView viewyellow = view.findViewById(R.id.yellow);
                String textred = viewred.getText().toString();
                String textgreen = viewgreen.getText().toString();
                String textyellow = viewyellow.getText().toString();


                Intent intent = new Intent(navigation.this, show.class);

                intent.putExtra("textred", textred);
                intent.putExtra("textgreen", textgreen);
                intent.putExtra("textyellow", textyellow);
                intent.putExtra("user_latitude", user_latitude);
                intent.putExtra("user_longitude", user_longitude);

                startActivity(intent);
            }
        });

        ChildEventListener mChildEvnetListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                order order = dataSnapshot.getValue(order.class);
                assert order != null;
                assert order.getClient() != null;
                assert order.getClient().getemail() != null;
                if(order.getClient().getemail().equals(user_email)) {
                        orderAdapter.add(order);

                }





            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mMessageDatabaseReference.addChildEventListener(mChildEvnetListener);


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                 result = data.getStringExtra("editTextValue");
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        name = (TextView) findViewById(R.id.name_show);
        email = (TextView) findViewById(R.id.email_show);

        name.setText(user_name);
        email.setText(user_email);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_order) {
            // Handle making order

            Intent i = new Intent(navigation.this,client_1.class);
            i.putExtra("user_name", user_name);
            i.putExtra("user_email", user_email);
            i.putExtra("user_latitude", user_latitude);
            i.putExtra("user_longitude", user_longitude);
            startActivity(i);

        } else if (id == R.id.nav_recent) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this,MainActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


}
