package com.example.gndectouch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

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


     //   Realm.init(this);

//        real work
//        first connect with data base and check which login belong to faculity,mentor or mentee?

        App app = new App(new AppConfiguration.Builder(Appid).build());


        app.loginAsync(Credentials.emailPassword("monika8427084@gmail.com", "Monika8427@#"), new App.Callback<User>() {
                    @Override
                    public void onResult(App.Result<User> resulting) {

                        Toast.makeText(alumniView.this, "stap1", Toast.LENGTH_SHORT).show();
                        Document document = new Document("occ", "mentor");
                        LinearLayout linear = findViewById(R.id.linearlay);
                        User user = app.currentUser();
                        mongoClient = user.getMongoClient("mongodb-atlas");
                        mongoDatabase = mongoClient.getDatabase("GNDECdb");
                        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Mentor");
                        Toast.makeText(alumniView.this, "stap2 hello ", Toast.LENGTH_SHORT).show();
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
                                                itemLayout.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); // Change the color as needed


                                                TextView nameTextView = new TextView(alumniView.this);
                                                nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                                ));
                                                nameTextView.setText(curDoc.getString("name"));

                                                TextView emailTextView = new TextView(alumniView.this);
                                                emailTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                                ));
                                                emailTextView.setText(curDoc.getString("email"));

                                                TextView phoneTextView = new TextView(alumniView.this);
                                                phoneTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                                ));
                                                phoneTextView.setText(curDoc.getString("phone"));

                                                // Add TextViews to the item's layout
                                                itemLayout.addView(nameTextView);
                                                itemLayout.addView(emailTextView);

                                                linearLayout.addView(itemLayout);
                                                Toast.makeText(alumniView.this, curDoc.getString("email"), Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    }

                                });
                            }
                        });

                    }
                });
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