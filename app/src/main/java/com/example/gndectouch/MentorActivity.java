package com.example.gndectouch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class MentorActivity extends AppCompatActivity {
    String Appid = "application-0-kdmkx";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
        Animation an= AnimationUtils.loadAnimation(this,R.anim.translating);TextView eve=findViewById(R.id.eve);
        eve.startAnimation(an);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Home");
        // Get the intent
        Intent intent = getIntent();
        Realm.init(this);
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

        App app = new App(new AppConfiguration.Builder(Appid).build());



        //searching
        EditText search_me=findViewById(R.id.search_me);
        Button search_btn=findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = search_me.getText().toString();

// Construct the regex query string
                String regexQuery = ".*" + Pattern.quote(searchText)+".*";

                LinearLayout  linearLayout=findViewById(R.id.outputsearch);
                linearLayout.removeAllViews();

                Document document = new Document("name", new Document("$regex",regexQuery));

                Document mentor_name = new Document("mentor", name); // Replace 123 with the actual rollnumber

// Combine both queries using the $and operator
                List<Document> andQuery = Arrays.asList(document, mentor_name);
                Document finalQuery = new Document("$and", andQuery);

                User user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("GNDECdb");
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Mentee");
                // Toast.makeText(alumniView.this, "stap2 hello ", Toast.LENGTH_SHORT).show();
                RealmResultTask<MongoCursor<Document>> searchlist= mongoCollection.find(finalQuery).iterator();



                searchlist.getAsync(task ->
                {
                    if (task.isSuccess()) {
                        runOnUiThread(new Runnable() {
                            @SuppressLint("NewApi")
                            @Override
                            public void run() {
                                LinearLayout linearLayout = findViewById(R.id.outputsearch);
                                linearLayout.removeAllViews();
                                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                                    View child = linearLayout.getChildAt(i);
                                    linearLayout.removeView(child);
                                }
                                MongoCursor<Document> resu = task.get();
                                while (resu.hasNext()) {
                                    Document curDoc = resu.next();
                                    if (curDoc.getString("email") != null) {
                                        LinearLayout itemLayout = new LinearLayout(MentorActivity.this);

                                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT
                                        );
                                        int margin = 16; // Define your margin value here
                                        layoutParams.setMargins(margin, margin, margin, margin); // left, top, right, bottom
                                        itemLayout.setLayoutParams(layoutParams);

                                        itemLayout.setOutlineSpotShadowColor(getResources().getColor(android.R.color.black));
                                        itemLayout.setOrientation(LinearLayout.VERTICAL);


                                        TextView nameTextView = new TextView(MentorActivity.this);
                                        nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));

                                        nameTextView.setTextColor(getResources().getColor(android.R.color.black));
                                        nameTextView.setText("NAME    " + curDoc.getString("name"));


                                        TextView emailTextView = new TextView(MentorActivity.this);
                                        emailTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));
                                        emailTextView.setTextColor(getResources().getColor(android.R.color.black));
                                        emailTextView.setText("EMAIL  " + curDoc.getString("email"));


                                        TextView mentorView = new TextView(MentorActivity.this);
                                        mentorView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));
                                        mentorView.setTextColor(getResources().getColor(android.R.color.black));
                                        mentorView.setText("MENTOR  " + curDoc.getString("mentor"));

                                        TextView phoneTextView = new TextView(MentorActivity.this);
                                        phoneTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));
                                        phoneTextView.setTextColor(getResources().getColor(android.R.color.black));
                                        phoneTextView.setText("PHONE  " + curDoc.getString("phone"));


                                        // Add TextViews to the item's layout
                                        itemLayout.addView(nameTextView);
                                        itemLayout.addView(emailTextView);
                                        itemLayout.addView(phoneTextView);
                                        itemLayout.addView(mentorView);
                                        itemLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                                        linearLayout.setOutlineSpotShadowColor(getResources().getColor(android.R.color.black));

                                        linearLayout.addView(itemLayout);

                                        // Toast.makeText(alumniView.this, curDoc.getString("email"), Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }

                        });
                    };
                });




            }
        });








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


        app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> resulting) {


                Document document = new Document("event", "event");
                User user = app.currentUser();
                MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
                MongoDatabase mongoDatabase = mongoClient.getDatabase("GNDECdb");
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Events");

                RealmResultTask<MongoCursor<Document>> eventList = mongoCollection.find(document).iterator();

                eventList.getAsync(task -> {
                    //Toast.makeText(this, "yeah lo 1", Toast.LENGTH_SHORT).show();

                    if (task.isSuccess()) {

                        //   Toast.makeText(alumniView.this, "chk v 1", Toast.LENGTH_SHORT).show();
                        LinearLayout linearLayout = findViewById(R.id.linearlay1);

                        MongoCursor<Document> resu = task.get();
                        while (resu.hasNext()) {
                            // Toast.makeText(alumniView.this, "chk v 2", Toast.LENGTH_SHORT).show();
                            Document curDoc = resu.next();
                            if (curDoc.getString("name") != null) {
                                //Toast.makeText(alumniView.this, "chk v end", Toast.LENGTH_SHORT).show();
                                LinearLayout itemLayout = new LinearLayout(MentorActivity.this);
                                LinearLayout itemLayout2 = new LinearLayout(MentorActivity.this);

                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                int margin = 7; // Define your margin value here
                                layoutParams.setMargins(10, 10, 4, 5); // left, top, right, bottom
                                itemLayout.setLayoutParams(layoutParams);

                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                // Define your margin value here
                                layoutParams2.setMargins(4,2,4,2);
                                itemLayout2.setLayoutParams(layoutParams2);


                                itemLayout.setOrientation(LinearLayout.VERTICAL);
                                itemLayout2.setOrientation(LinearLayout.HORIZONTAL);
                                itemLayout.setBackgroundColor(getResources().getColor(R.color.grey)); // Change the color as needed
                                // Change the color as needed

                                linearLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                                TextView nameTextView = new TextView(MentorActivity.this);
                                nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                TextView eventdude = new TextView(MentorActivity.this);
                                eventdude.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));

                                nameTextView.setTextColor(getResources().getColor(android.R.color.black));
                                nameTextView.setText(curDoc.getString("name"));

                                TextView emailTextView = new TextView(MentorActivity.this);
                                emailTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                emailTextView.setTextColor(getResources().getColor(android.R.color.black));
                                emailTextView.setText(curDoc.getString("descroption"));

                                TextView phoneTextView = new TextView(MentorActivity.this);
                                phoneTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                phoneTextView.setTextColor(getResources().getColor(android.R.color.black));
                                phoneTextView.setText(curDoc.getString("name"));

                                // Add TextViews to the item's layout

                                itemLayout2.setGravity(1);
                                itemLayout.setGravity(1);

                                itemLayout.addView(nameTextView);
                                itemLayout.addView(emailTextView);

                                itemLayout.addView(phoneTextView);

                                linearLayout.addView(itemLayout);
                                linearLayout.addView(itemLayout2);


                            }


                        }

                    }
                });
            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate=getMenuInflater();
        inflate.inflate(R.menu.mentormenu,menu);



        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.events) {



            Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.logout) {
            Intent i=new Intent(MentorActivity.this,MainActivity.class);
            startActivity(i);
finish();

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


        return super.onOptionsItemSelected(item);
    }
    private void uploadData(){

    }
}