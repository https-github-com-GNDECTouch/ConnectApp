package com.example.gndectouch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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
            // Handle item 1 click
            showPopupWindow(item.getActionView());
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
    private void showPopupWindow(View anchorView) {
          //LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {
             View popupView = inflater.inflate(R.layout.popup_layout, null);
//
//            popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
//
//            if (popupWindow != null) {
//                popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
//
//                Button closeButton = popupView.findViewById(R.id.closebutton);
//
//                if (closeButton != null) {
//                    closeButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            popupWindow.dismiss();
//                        }
//                    });
//                } else {
//                    // Handle the case where the closeButton view is not found.
//                }
//            } else {
//                // Handle the case where the popupWindow is not created.
//            }
        } else {
            // Handle the case where the inflater is null.
        }
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

//                mentorlist.getAsync(task -> {
//                    if (task.isSuccess()) {
//                        Toast.makeText(alumniView.this, "stap2", Toast.LENGTH_SHORT).show();
//                        MongoCursor<Document> resu = task.get();
//
//                        while (resu.hasNext()) {
//                            LinearLayout echrow = (LinearLayout) findViewById(R.id.linearLayout);
//                            Document curDoc = resu.next();
//                            if (curDoc.getString("email") != null) {
//                                Toast.makeText(alumniView.this, "stap3", Toast.LENGTH_SHORT).show();
//                                //  mentorlist.add(curDoc.getString("name"));
//                                TextView mentoremail = findViewById(R.id.emailinput);
//                                mentoremail.setText(curDoc.getString("email"));
//                                TextView mentorname = findViewById(R.id.mentorname);
//                                mentorname.setText(curDoc.getString("name"));
//                                TextView phone = findViewById(R.id.mentorphone);
//                                phone.setText(curDoc.getString("phone"));
//
//                                // 'this' is the context
//
//
//                                // You can customize the TextView here, e.g., set text size, text color, etc.
//                                Toast.makeText(alumniView.this, "stap4", Toast.LENGTH_SHORT).show();
//                                // Add the TextView to the LinearLayout
//                                echrow.addView(mentoremail);
//                                echrow.addView(mentorname);
//                                echrow.addView(phone);
//
//                            }
//                            Toast.makeText(alumniView.this, "stap5", Toast.LENGTH_SHORT).show();
//                            linear.addView(echrow);
//
//                        }
//                        Toast.makeText(alumniView.this, "stap6", Toast.LENGTH_SHORT).show();
//                        ScrollView scroller = findViewById(R.id.scrollView2);
//                        scroller.addView(linear);
//                    }
//                });
//            }
//        });
//    }
//}
//


//}