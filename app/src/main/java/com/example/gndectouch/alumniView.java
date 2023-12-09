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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import org.bson.Document;

import java.io.File;
import java.util.regex.Pattern;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;




public class alumniView extends AppCompatActivity {

    File myFile;
    private PopupWindow popupWindow;
    TextView mentorname;
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    String Appid = "application-0-kdmkx";
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private ActivityResultLauncher<Intent> filePickerLauncher1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Animation an= AnimationUtils.loadAnimation(this,R.anim.translating);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_view);
        App app = new App(new AppConfiguration.Builder(Appid).build());
        TextView eve=findViewById(R.id.eve);
        eve.setVisibility(View.VISIBLE);
        eve.setText("MENTOR LIST");
eve.startAnimation(an);
        LinearLayout addevent=findViewById(R.id.addevent);
        addevent.setVisibility(View.GONE);


        //searching area
        //get input by user
        EditText search=findViewById(R.id.search);
        String s=search.getText().toString();

        Button searchbtn=findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Document document = new Document("name", search.getText().toString().trim());
                try {
                    String searchText = search.getText().toString();
                    String regexQuery = ".*" + Pattern.quote(searchText)+".*";

                    document = new Document("name", new Document("$regex", regexQuery).append("$options", "i"));

                }
                catch (Exception e)
                {
                    Toast.makeText(alumniView.this, "hie "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
               // Bson regexFilter = Filters.regex("name",".*"+search.getText().toString().trim()+".*", "i");

                TextView eve=findViewById(R.id.eve);
                eve.setVisibility(View.VISIBLE);
                eve.setText("STUDENT LIST");
                eve.startAnimation(an);
LinearLayout  linearLayout=findViewById(R.id.linearlay);
linearLayout.removeAllViews();


                //Document document = (Document) Filters.regex("name", "^"+search.getText().toString().trim(),"i");

                User user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("GNDECdb");
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Mentee");
                // Toast.makeText(alumniView.this, "stap2 hello ", Toast.LENGTH_SHORT).show();
                RealmResultTask<MongoCursor<Document>> searchlist= mongoCollection.find(document).iterator();



                searchlist.getAsync(task ->
                {
                    if (task.isSuccess()) {
                        runOnUiThread(new Runnable() {
                            @SuppressLint("NewApi")
                            @Override
                            public void run() {
                                LinearLayout linearLayout = findViewById(R.id.linearlay);
                                linearLayout.removeAllViews();
                                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                                    View child = linearLayout.getChildAt(i);
                                    linearLayout.removeView(child);
                                }
                                MongoCursor<Document> resu = task.get();
                                while (resu.hasNext()) {
                                    Document curDoc = resu.next();
                                    if (curDoc.getString("email") != null) {
                                        LinearLayout itemLayout = new LinearLayout(alumniView.this);

                                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT
                                        );
                                        int margin = 16; // Define your margin value here
                                        layoutParams.setMargins(margin, margin, margin, margin); // left, top, right, bottom
                                        itemLayout.setLayoutParams(layoutParams);

                                        itemLayout.setOutlineSpotShadowColor(getResources().getColor(android.R.color.black));
                                        itemLayout.setOrientation(LinearLayout.VERTICAL);


                                        TextView nameTextView = new TextView(alumniView.this);
                                        nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));

                                        nameTextView.setTextColor(getResources().getColor(android.R.color.black));
                                        nameTextView.setText("NAME    "+curDoc.getString("name"));


                                        TextView emailTextView = new TextView(alumniView.this);
                                        emailTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));
                                        emailTextView.setTextColor(getResources().getColor(android.R.color.black));
                                        emailTextView.setText("EMAIL  "+curDoc.getString("email"));


                                        TextView mentorView = new TextView(alumniView.this);
                                       mentorView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));
                                        mentorView.setTextColor(getResources().getColor(android.R.color.black));
                                        mentorView.setText("MENTOR  "+curDoc.getString("mentor"));

                                        TextView phoneTextView = new TextView(alumniView.this);
                                        phoneTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));
                                        phoneTextView.setTextColor(getResources().getColor(android.R.color.black));
                                        phoneTextView.setText("PHONE  "+curDoc.getString("phone"));


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
                    }
                });

            }
        });












        app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> resulting) {
                LinearLayout daa=findViewById(R.id.detailaboutactivity);
                daa.setVisibility(View.VISIBLE);

                // Toast.makeText(alumniView.this, "stap1", Toast.LENGTH_SHORT).show();
                //showing mentor data
                Document document = new Document("occ", "mentor");
                LinearLayout linear = findViewById(R.id.linearlay);
                User user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("GNDECdb");
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Mentor");
                // Toast.makeText(alumniView.this, "stap2 hello ", Toast.LENGTH_SHORT).show();
                RealmResultTask<MongoCursor<Document>> mentorlist = mongoCollection.find(document).iterator();
                mentorlist.getAsync(task ->
                {
                    if (task.isSuccess()) {
                        runOnUiThread(new Runnable() {
                            @SuppressLint("NewApi")
                            @Override
                            public void run() {
                                LinearLayout linearLayout = findViewById(R.id.linearlay);
                                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                                    View child = linearLayout.getChildAt(i);
                                    linearLayout.removeView(child);
                                }
                                MongoCursor<Document> resu = task.get();
                                while (resu.hasNext()) {
                                    Document curDoc = resu.next();
                                    if (curDoc.getString("email") != null) {
                                        LinearLayout itemLayout = new LinearLayout(alumniView.this);

                                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        );
                                        int margin = 16; // Define your margin value here
                                        layoutParams.setMargins(margin, margin, margin, margin); // left, top, right, bottom
                                        itemLayout.setLayoutParams(layoutParams);

                                       itemLayout.setOutlineSpotShadowColor(getResources().getColor(android.R.color.black));
                                        itemLayout.setOrientation(LinearLayout.VERTICAL);


                                        TextView nameTextView = new TextView(alumniView.this);
                                        nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));

                                        nameTextView.setTextColor(getResources().getColor(android.R.color.black));
                                        nameTextView.setText(curDoc.getString("name"));

                                        TextView emailTextView = new TextView(alumniView.this);
                                        emailTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));
                                        emailTextView.setTextColor(getResources().getColor(android.R.color.black));
                                        emailTextView.setText(curDoc.getString("email"));

                                        TextView phoneTextView = new TextView(alumniView.this);
                                        phoneTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        ));
                                        phoneTextView.setTextColor(getResources().getColor(android.R.color.black));
                                        phoneTextView.setText(curDoc.getString("phone"));


                                        // Add TextViews to the item's layout
                                        itemLayout.addView(nameTextView);
                                        itemLayout.addView(emailTextView);
                                        itemLayout.addView(phoneTextView);
                                        itemLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                                        linearLayout.setOutlineSpotShadowColor(getResources().getColor(android.R.color.black));

                                        linearLayout.addView(itemLayout);

                                        // Toast.makeText(alumniView.this, curDoc.getString("email"), Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }

                        });
                    }
                });

            }
        });

        //add events

        Button addEvent=findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                LinearLayout daa=findViewById(R.id.detailaboutactivity);
                daa.setVisibility(View.VISIBLE);
                FrameLayout frag=findViewById(R.id.frag);
                frag.setVisibility(View.GONE);
                app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
                    @Override
                    public void onResult(App.Result<User> resulting) {

                        TextView eve=findViewById(R.id.eve);
                        eve.setText("EVENT  LIST");
                        eve.startAnimation(an);
                       eve.setVisibility(View.GONE);
                        LinearLayout addevent=findViewById(R.id.addevent);
                        addevent.setVisibility(View.VISIBLE);
                        // Toast.makeText(alumniView.this, "stap1", Toast.LENGTH_SHORT).show();

                        User user = app.currentUser();
                        EditText eventname = findViewById(R.id.eventname);
                        EditText eventdes = findViewById(R.id.eventdes);


                        MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
                        MongoDatabase mongoDatabase = mongoClient.getDatabase("GNDECdb");
                        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Events");
                        mongoCollection.insertOne(new Document("name", eventname.getText().toString()).append("descroption", eventdes.getText().toString()).append("event", "event")).getAsync(
                                ret -> {
                                    if (ret.isSuccess()) {
                                        eventname.setText("");
                                        eventdes.setText("");
                                        Toast.makeText(alumniView.this, "Event Added", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle the case where event insertion fails.
                                        Throwable error = ret.getError();
                                        if (error != null) {
                                            // You can log or display the error message.
                                            String errorMessage = error.getLocalizedMessage();
                                            Toast.makeText(alumniView.this, "Event Insertion Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                        );
                    };

                });

            }
        });

        //toolbar options and thier working
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Home");


















     //   Realm.init(this);

//        real work
//        first connect with data base and check which login belong to faculity,mentor or mentee?

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
            LinearLayout daa=findViewById(R.id.detailaboutactivity);
            daa.setVisibility(View.VISIBLE);
            FrameLayout frag=findViewById(R.id.frag);
            frag.setVisibility(View.GONE);
            TextView eve=findViewById(R.id.eve);
            eve.setText("EVENT  LIST");
            eve.setVisibility(View.VISIBLE);
            LinearLayout addevent=findViewById(R.id.addevent);
            addevent.setVisibility(View.VISIBLE);
            loadEventsFromDatabase();

            Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.chat) {
            LinearLayout daa=findViewById(R.id.detailaboutactivity);
            daa.setVisibility(View.GONE);
            FrameLayout frag=findViewById(R.id.frag);
            frag.setVisibility(View.VISIBLE);
            TextView eve=findViewById(R.id.eve);
            eve.setText("CHATS HERE");
            eve.setVisibility(View.VISIBLE);
            LinearLayout addevent=findViewById(R.id.addevent);
            addevent.setVisibility(View.GONE);
            LinearLayout linear = findViewById(R.id.linearlay);
            linear.removeAllViews();
            Toast.makeText(this, "Chats", Toast.LENGTH_SHORT).show();
            MessegeFragment fragment = new MessegeFragment();
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frag, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            // Handle item 2 click
            return true;
        }
        else if (id == R.id.Home) {
            LinearLayout daa=findViewById(R.id.detailaboutactivity);
            daa.setVisibility(View.VISIBLE);
            FrameLayout frag=findViewById(R.id.frag);
            frag.setVisibility(View.GONE);
            TextView eve=findViewById(R.id.eve);
            eve.setText("MENTOR  LIST");
            eve.setVisibility(View.VISIBLE);
            LinearLayout addevent=findViewById(R.id.addevent);
            addevent.setVisibility(View.GONE);
            App app = new App(new AppConfiguration.Builder(Appid).build());

            eve.setText("MENTOR LIST");
            ImageView img=findViewById(R.id.evei);
            img.setVisibility(View.GONE);
            app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
                @Override
                public void onResult(App.Result<User> resulting) {

                    FrameLayout frag=findViewById(R.id.frag);
                    frag.setVisibility(View.GONE);
                    // Toast.makeText(alumniView.this, "stap1", Toast.LENGTH_SHORT).show();
                    //showing mentor data
                    Document document = new Document("occ", "mentor");
                    LinearLayout linear = findViewById(R.id.linearlay);
                    linear.removeAllViews();
                    User user = app.currentUser();
                    mongoClient = user.getMongoClient("mongodb-atlas");
                    mongoDatabase = mongoClient.getDatabase("GNDECdb");
                    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Mentor");
                    // Toast.makeText(alumniView.this, "stap2 hello ", Toast.LENGTH_SHORT).show();
                    RealmResultTask<MongoCursor<Document>> mentorlist = mongoCollection.find(document).iterator();
                    mentorlist.getAsync(task ->
                    {
                        if (task.isSuccess()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LinearLayout linearLayout = findViewById(R.id.linearlay);
                                    for (int i = 0; i < linearLayout.getChildCount(); i++) {
                                        View child = linearLayout.getChildAt(i);
                                        linearLayout.removeView(child);
                                    }
                                    MongoCursor<Document> resu = task.get();
                                    while (resu.hasNext()) {
                                        Document curDoc = resu.next();
                                        if (curDoc.getString("email") != null) {
                                            LinearLayout itemLayout = new LinearLayout(alumniView.this);

                                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                            );
                                            int margin = 16; // Define your margin value here
                                            layoutParams.setMargins(margin, margin, margin, margin); // left, top, right, bottom
                                            itemLayout.setLayoutParams(layoutParams);



                                            itemLayout.setOrientation(LinearLayout.VERTICAL);


                                            TextView nameTextView = new TextView(alumniView.this);
                                            nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                            ));

                                            nameTextView.setTextColor(getResources().getColor(android.R.color.black));
                                            nameTextView.setText(curDoc.getString("name"));

                                            TextView emailTextView = new TextView(alumniView.this);
                                            emailTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                            ));
                                            emailTextView.setTextColor(getResources().getColor(android.R.color.black));
                                            emailTextView.setText(curDoc.getString("email"));

                                            TextView phoneTextView = new TextView(alumniView.this);
                                            phoneTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                            ));
                                            phoneTextView.setTextColor(getResources().getColor(android.R.color.black));
                                            phoneTextView.setText(curDoc.getString("phone"));


                                            // Add TextViews to the item's layout
                                            itemLayout.addView(nameTextView);
                                            itemLayout.addView(emailTextView);
                                            itemLayout.addView(phoneTextView);

                                            itemLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                                            linearLayout.addView(itemLayout);

                                            // Toast.makeText(alumniView.this, curDoc.getString("email"), Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }

                            });
                        }
                    });

                }
            });
            // Handle item 2 click

            return true;
        }
        else if (id == R.id.upload) {
            FrameLayout frag=findViewById(R.id.frag);
            frag.setVisibility(View.VISIBLE);
            LinearLayout daa=findViewById(R.id.detailaboutactivity);
            daa.setVisibility(View.GONE);
            TextView eve=findViewById(R.id.eve);

            eve.setVisibility(View.GONE);
            LinearLayout addevent=findViewById(R.id.addevent);
            addevent.setVisibility(View.GONE);
            LinearLayout linear = findViewById(R.id.linearlay);
            linear.removeAllViews();
            Toast.makeText(this, "Chats", Toast.LENGTH_SHORT).show();
            UploadData fragment = new UploadData();
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frag, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            // Handle item 2 click
            return true;
        }
        else if(id==R.id.logout)
        {
            Intent i=new Intent(alumniView.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadEventsFromDatabase() {
        LinearLayout daa=findViewById(R.id.detailaboutactivity);
        daa.setVisibility(View.VISIBLE);
        App app = new App(new AppConfiguration.Builder(Appid).build());
        Document document = new Document("event", "event");
        User user = app.currentUser();
        MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
        TextView eve=findViewById(R.id.eve);
        eve.setText("EVENTS");
        ImageView img=findViewById(R.id.evei);
        img.setVisibility(View.VISIBLE);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("GNDECdb");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Events");

        RealmResultTask<MongoCursor<Document>> eventList = mongoCollection.find(document).iterator();

        eventList.getAsync(task -> {
            //Toast.makeText(this, "yeah lo 1", Toast.LENGTH_SHORT).show();

            if (task.isSuccess()) {

                     //   Toast.makeText(alumniView.this, "chk v 1", Toast.LENGTH_SHORT).show();
                        LinearLayout linearLayout = findViewById(R.id.linearlay);
                        linearLayout.removeAllViews();
                        MongoCursor<Document> resu = task.get();
                        while (resu.hasNext()) {
                            // Toast.makeText(alumniView.this, "chk v 2", Toast.LENGTH_SHORT).show();
                            Document curDoc = resu.next();
                            if (curDoc.getString("name") != null) {
                                //Toast.makeText(alumniView.this, "chk v end", Toast.LENGTH_SHORT).show();
                                LinearLayout itemLayout = new LinearLayout(alumniView.this);
                                LinearLayout itemLayout2 = new LinearLayout(alumniView.this);

                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                    int margin = 16; // Define your margin value here
                                    layoutParams.setMargins(margin, margin, margin, margin); // left, top, right, bottom
                                    itemLayout.setLayoutParams(layoutParams);

                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                // Define your margin value here
                                layoutParams2.setMargins(2,2,2,2); // left, top, right, bottom
                                itemLayout2.setLayoutParams(layoutParams2);


                                    itemLayout.setOrientation(LinearLayout.VERTICAL);
                                itemLayout2.setOrientation(LinearLayout.HORIZONTAL);
                                    itemLayout.setBackgroundColor(getResources().getColor(android.R.color.background_light)); // Change the color as needed


                                EditText nameTextView = new EditText(alumniView.this);
                                    nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    ));

                                    nameTextView.setTextColor(getResources().getColor(android.R.color.black));
                                    nameTextView.setText(curDoc.getString("name"));

                                    EditText emailTextView = new EditText(alumniView.this);
                                    emailTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    ));
                                    emailTextView.setTextColor(getResources().getColor(android.R.color.black));
                                    emailTextView.setText(curDoc.getString("descroption"));

                                    EditText phoneTextView = new EditText(alumniView.this);
                                    phoneTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    ));
                                    phoneTextView.setTextColor(getResources().getColor(android.R.color.black));
                                    phoneTextView.setText(curDoc.getString("name"));

                                    // Add TextViews to the item's layout

                                Button updateEvent=new Button(alumniView.this);
                                Button AddEvent=new Button(alumniView.this);
                                AddEvent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                                AddEvent.setText("Add Text");
                                updateEvent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                                updateEvent.setText("Update ");
                                Document doc=new Document("_id",curDoc.getObjectId("_id"));
                                updateEvent.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

//                                        Toast.makeText(alumniView.this, "I will add ur event id"+curDoc.getObjectId("_id").toHexString(), Toast.LENGTH_SHORT).show();
                                        RealmResultTask<Document> update=mongoCollection.findOneAndUpdate(doc,new Document("name",nameTextView.getText().toString()).append("descroption",emailTextView.getText().toString() ).append("event","event"));
                                       update.getAsync(updated->{
                                           if(updated.isSuccess()){
                                               Toast.makeText(alumniView.this, "Event name updated", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                    }
                                });


                                Button DeleteEvent=new Button(alumniView.this);
                                DeleteEvent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                                DeleteEvent.setText("Delete Event");
                                DeleteEvent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Toast.makeText(alumniView.this, "I will delete ur event"+curDoc.getObjectId("_id").toHexString(), Toast.LENGTH_SHORT).show();
                                        RealmResultTask<Document> delete=mongoCollection.findOneAndDelete(doc);
                                        delete.getAsync(updated->{
                                            if(updated.isSuccess()){
                                                Toast.makeText(alumniView.this, "Event id Deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });

                                itemLayout2.setGravity(1);
                                itemLayout.setGravity(1);

                                itemLayout.addView(nameTextView);
                                    itemLayout.addView(emailTextView);
                                    itemLayout.addView(phoneTextView);
                                    itemLayout2.addView(updateEvent);
                                    itemLayout2.addView(DeleteEvent);

                            linearLayout.addView(itemLayout);
                            linearLayout.addView(itemLayout2);


                            }

                        }

            }
        });

    }






}
