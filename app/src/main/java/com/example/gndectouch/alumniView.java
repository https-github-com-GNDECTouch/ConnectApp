package com.example.gndectouch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    private PopupWindow popupWindow;
    TextView mentorname;
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    String Appid = "application-0-kdmkx";
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private ActivityResultLauncher<Intent> filePickerLauncher1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_view);
        LinearLayout whole = (LinearLayout) findViewById(R.id.linearlay);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Home");


        Button btn=findViewById(R.id.selectFileButton);
        Button btn1=findViewById(R.id.selectFileButton1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker1();
            }


        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }


        });


        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedFileUri = result.getData().getData();
                        if (selectedFileUri != null) {
                            displayFileContent(selectedFileUri);
                        }

                        // Handle the selected file URI (e.g., upload it to a server).
                        // For this example, we are not implementing the file upload.
                    }
                }
        );
        filePickerLauncher1 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedFileUri = result.getData().getData();
                        if (selectedFileUri != null) {
                            displayFileContent1(selectedFileUri);
                        }

                        // Handle the selected file URI (e.g., upload it to a server).
                        // For this example, we are not implementing the file upload.
                    }
                }
        );
     //   Realm.init(this);

//        real work
//        first connect with data base and check which login belong to faculity,mentor or mentee?

        App app = new App(new AppConfiguration.Builder(Appid).build());


        app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
                    @Override
                    public void onResult(App.Result<User> resulting) {


                       // Toast.makeText(alumniView.this, "stap1", Toast.LENGTH_SHORT).show();
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
                                                itemLayout.setBackgroundColor(getResources().getColor(android.R.color.background_light)); // Change the color as needed


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
            loadEventsFromDatabase();

            Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            // Handle item 2 click
            return true;
        }
        else if (id == R.id.chat) {
            Toast.makeText(this, "Chats", Toast.LENGTH_SHORT).show();
            // Handle item 2 click
            return true;
        }
        else if (id == R.id.Home) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            // Handle item 2 click

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadEventsFromDatabase() {
        App app = new App(new AppConfiguration.Builder(Appid).build());
        Document document = new Document("event", "event");
        User user = app.currentUser();
        MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("GNDECdb");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Events");

        RealmResultTask<MongoCursor<Document>> eventList = mongoCollection.find(document).iterator();

        eventList.getAsync(task -> {
            //Toast.makeText(this, "yeah lo 1", Toast.LENGTH_SHORT).show();

            if (task.isSuccess()) {
TextView cur=findViewById(R.id.cur);
cur.setText("Event List");
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
                                    emailTextView.setText(curDoc.getString("descroption"));

                                    TextView phoneTextView = new TextView(alumniView.this);
                                    phoneTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    ));
                                    phoneTextView.setTextColor(getResources().getColor(android.R.color.black));
                                    phoneTextView.setText(curDoc.getString("name"));

                                    // Add TextViews to the item's layout

                                Button addEvent=new Button(alumniView.this);
                                addEvent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                                addEvent.setText("Add Event");
                                addEvent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(alumniView.this, "I will add ur event id"+curDoc.getObjectId("_id").toHexString(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                                Button DeleteEvent=new Button(alumniView.this);
                                DeleteEvent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                                DeleteEvent.setText("Delete Event");
                                DeleteEvent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(alumniView.this, "I will delete ur event"+curDoc.getObjectId("_id").toHexString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                itemLayout2.setGravity(1);
                                itemLayout.setGravity(1);

                                itemLayout.addView(nameTextView);
                                    itemLayout.addView(emailTextView);
                                    itemLayout.addView(phoneTextView);
                                    itemLayout2.addView(addEvent);
                                    itemLayout2.addView(DeleteEvent);

                            linearLayout.addView(itemLayout);
                            linearLayout.addView(itemLayout2);

                            }

                        }

            }
        });

    }

    private void displayFileContent(Uri fileUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append('\n');
                }
                inputStream.close();
                TextView data=findViewById(R.id.csvdata);
                data.setText(content.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void displayFileContent1(Uri fileUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append('\n');
                }
                inputStream.close();
                TextView data=findViewById(R.id.csvdata1);
                data.setText(content.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        filePickerLauncher.launch(intent);
    }
    private void openFilePicker1() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        filePickerLauncher1.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Toast.makeText(alumniView.this, "HELLO MONIKA 1", Toast.LENGTH_SHORT).show();
                Uri selectedFileUri = data.getData();
                TextView csv=findViewById(R.id.csvdata);
                csv.setText(selectedFileUri.toString());
                // You can use the selected file URI to access the chosen file.
                // For example, you can open and read the file's contents.
            }
            else{
                Toast.makeText(alumniView.this, "Null data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
