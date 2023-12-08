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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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

public class MenteeActivity extends AppCompatActivity {
    String Appid = "application-0-kdmkx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Home");
        // Get the intent
        Intent intent = getIntent();
        Animation an= AnimationUtils.loadAnimation(this,R.anim.translating);TextView eve=findViewById(R.id.eve);
        eve.startAnimation(an);
        Realm.init(this);
// Retrieve the data from the intent
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String idd = intent.getStringExtra("id");
        String phone = intent.getStringExtra("phone");
        String mentor = intent.getStringExtra("mentor");

// Find the TextView elements
        TextView n = findViewById(R.id.name);
        TextView e = findViewById(R.id.email);
        TextView i = findViewById(R.id.id);
        TextView p = findViewById(R.id.phone);
        TextView m=findViewById(R.id.mentor);

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
        if (mentor != null) {
            m.setText(mentor);
        }
        App app = new App(new AppConfiguration.Builder(Appid).build());

        app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> resulting) {


        // Create a reference to the GraphView
        GraphView graphView = findViewById(R.id.GraphView);

        // Create a series for the line chart
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });

        // Set a title for the graph view
        graphView.setTitle("My Graph View");

        // Set text color for the title
        graphView.setTitleColor(getResources().getColor(R.color.purple_200)); // Use your own color resource

        // Set title text size
        graphView.setTitleTextSize(18);

        // Add the data series to the graph view
        graphView.addSeries(series);


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
                                LinearLayout itemLayout = new LinearLayout(MenteeActivity.this);
                                LinearLayout itemLayout2 = new LinearLayout(MenteeActivity.this);

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
                                TextView nameTextView = new TextView(MenteeActivity.this);
                                nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                TextView eventdude = new TextView(MenteeActivity.this);
                                eventdude.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));

                                nameTextView.setTextColor(getResources().getColor(android.R.color.black));
                                nameTextView.setText(curDoc.getString("name"));

                                TextView emailTextView = new TextView(MenteeActivity.this);
                                emailTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                emailTextView.setTextColor(getResources().getColor(android.R.color.black));
                                emailTextView.setText(curDoc.getString("descroption"));

                                TextView phoneTextView = new TextView(MenteeActivity.this);
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
            Intent i=new Intent(MenteeActivity.this,MainActivity.class);
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