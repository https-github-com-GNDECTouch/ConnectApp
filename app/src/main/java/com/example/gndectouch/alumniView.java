package com.example.gndectouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import org.bson.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

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
    List<String[]> mentorlist;
    List<String[]> menteelist;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_view);
        App app = new App(new AppConfiguration.Builder(Appid).build());

        app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> resulting) {


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

                                        linearLayout.setOutlineSpotShadowColor(getResources().getColor(android.R.color.black));
                                        itemLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
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
                app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
                    @Override
                    public void onResult(App.Result<User> resulting) {


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









        Button alotmentor=findViewById(R.id.alotmentor);
        alotmentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePickerLauncher!=null && filePickerLauncher1!=null)
                {

                }
            }
        });









        Button btn=findViewById(R.id.selectFileButton);
        Button btn1=findViewById(R.id.selectFileButton1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  openFilePicker1();

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
            App app = new App(new AppConfiguration.Builder(Appid).build());

            app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
                @Override
                public void onResult(App.Result<User> resulting) {


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
            // Handle item 2 click

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
                String csvData = readCSVFile(selectedFileUri);

                // Append the data to the TextView
                TextView csvTextView = findViewById(R.id.csvdata);
                String currentText = csvTextView.getText().toString();
                csvTextView.setText(currentText + "\n" + csvData);

                // Write the data to the CSV file

                // You can use the selected file URI to access the chosen file.
                // For example, you can open and read the file's contents.
            }
            else{
                Toast.makeText(alumniView.this, "Null data", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String readCSVFile(Uri fileUri) {
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
                return content.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void writeDataToCSVFile(String data) {
        File dir = Environment.getExternalStorageDirectory(); // External storage directory
        File file = new File(dir, "mydata.csv");
        Toast.makeText(this, dir.toString(), Toast.LENGTH_SHORT).show();
        try {
            FileOutputStream fos = new FileOutputStream(file, true); // Set 'true' to append data
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
            pw.println(data); // Write the data to the file
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error writing to CSV file", Toast.LENGTH_SHORT).show();
        }
    }

}
