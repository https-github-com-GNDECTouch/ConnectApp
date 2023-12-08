package com.example.gndectouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.bson.Document;

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
        App app = new App(new AppConfiguration.Builder(Appid).build());

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