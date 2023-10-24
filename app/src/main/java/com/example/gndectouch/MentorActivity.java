package com.example.gndectouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MentorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Home");
        // Get the intent
        Intent intent = getIntent();

// Retrieve the data from the intent
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String idd = intent.getStringExtra("id");
        String phone = intent.getStringExtra("phone");

// Find the TextView elements
        TextView n = findViewById(R.id.name);
        TextView e = findViewById(R.id.email);
        TextView i = findViewById(R.id.id);
        TextView p = findViewById(R.id.phone);

// Check if the intent extras are not null before setting the text
        if (name != null) {
            n.setText(name);
        }
        if (email != null) {
            e.setText(email);
        }
        if (idd != null) {
            i.setText(idd);
        }
        if (phone != null) {
            p.setText(phone);
        }




    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate=getMenuInflater();
        inflate.inflate(R.menu.menu,menu);



        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.events) {
            Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            // Handle item 2 click
            return true;
        }
        else if (id == R.id.chat) {
            Toast.makeText(this, "Chats", Toast.LENGTH_SHORT).show();


            return true;
        }
        else if (id == R.id.Home) {

            return true;
        }
        else if (id == R.id.upload) {
            Toast.makeText(this, "Upload", Toast.LENGTH_SHORT).show();
            // Handle item 2 click


            uploadData();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void uploadData(){

    }
}