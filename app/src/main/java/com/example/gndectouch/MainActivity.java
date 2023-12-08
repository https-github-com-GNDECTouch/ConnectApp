package com.example.gndectouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

import java.util.ArrayList;

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


public class MainActivity extends AppCompatActivity {


    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    //real mongodb

    String Appid="application-0-kdmkx";

    ArrayList<String> mentorlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button log=findViewById(R.id.submitbtn);
        EditText email,password;
        email=findViewById(R.id.emailinput);
        password=findViewById(R.id.passinput);
        // TODO Auto-generated method stub

        Realm.init(this);

        //real work
        //first connect with data base and check which login belong to faculity,mentor or mentee?

        App app=new App(new AppConfiguration.Builder(Appid).build());
        

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=email.getText().toString();
                String p=password.getText().toString();
                if(e.equals("monika8427084@gmail.com")&&p.equals("Monika8427@#"))
                {

                    Intent intent=new Intent(MainActivity.this,alumniView.class);

                    //intent.putStringArrayListExtra("data",mentorlist);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                   // overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_to_top);
                //    overridePendingTransition(R.anim.slide_from_top,R.anim.slide_to_bottom);


                }
                else {
                    Credentials credentials = Credentials.emailPassword("jagjit.2626@gmail.com", "Monika8427@#");

                    app.loginAsync(credentials, new App.Callback<User>() {

                        @Override
                        public void onResult(App.Result<User> result) {
                            //if condition to check email belong to admin
                            //check pass and call toast
                            //Intent for Faculity Activity pass userid
                            //put data of mentor

                            User user = app.currentUser();
                            mongoClient = user.getMongoClient("mongodb-atlas");
                            mongoDatabase = mongoClient.getDatabase("GNDECdb");
                            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Mentor");
                            MongoCollection<Document> mongoCollection2 = mongoDatabase.getCollection("Mentee");

                            Document queryFilter = new Document("email", e);
                            RealmResultTask<MongoCursor<Document>> queryfilter = mongoCollection.find(queryFilter).iterator();
                            RealmResultTask<MongoCursor<Document>> queryfilter2 = mongoCollection2.find(queryFilter).iterator();

                            queryfilter.getAsync(task -> {
                                if (task.isSuccess()) {
//                                Document q  = new Document("password", p);
//                                RealmResultTask<MongoCursor<Document>> qu=mongoCollection.find(q).iterator();
//
//                                qu.getAsync(task->{
//                                    if(task.isSuccess())
//                                    {
                                    MongoCursor<Document> resu = task.get();

                                    while (resu.hasNext()) {

                                        Document curDoc = resu.next();
                                        if (curDoc.getString("email") != null && curDoc.getString("email").equals(e) && curDoc.getString("password").equals(p)) {
                                            //  mentorlist.add(curDoc.getString("name"));

                                            Intent intent = new Intent(MainActivity.this, MentorActivity.class);
                                            intent.putExtra("name",curDoc.getString("name"));
                                            intent.putExtra("email",curDoc.getString("email"));
                                            intent.putExtra("id",curDoc.getObjectId("_id"));
                                            intent.putExtra("phone",curDoc.getString("phone"));
                                            //intent.putStringArrayListExtra("data",mentorlist);
                                            if (user != null) {
                                                user.logOutAsync(resul -> {
                                                    if (resul.isSuccess()) {
                                                        // Logout was successful
                                                        // You can perform any additional actions after logout here
                                                        startActivity(intent);
                                                        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                                                    } else {
                                                        // Logout failed
                                                        // Handle the error, if necessary
                                                    }
                                                });
                                            }
                                            // This code will log out the user asynchronously after the Intent and related activity have been started. You should include this code after starting each new activity. This will ensure that the user is logged out before navigating to the next screen.

                                            //   Make sure you have the app instance available to access the current user. You can adapt this code to your specific use case to ensure that users are logged out when needed.


                                            //Toast.makeText(MainActivity.this, curDoc.getString("name"), Toast.LENGTH_SHORT).show();
                                            //  TextView text=findViewById(R.id.mentorname);
                                            // text.setText(curDoc.getString("name"));

//                                    }
//
                                        }

                                    }
                                    //});

                                }

                            });


                            RealmResultTask<MongoCursor<Document>> qf = mongoCollection2.find(queryFilter).iterator();
                            queryfilter2.getAsync(task -> {
                                if (task.isSuccess()) {
//                                Document q  = new Document("password", p);
//                                RealmResultTask<MongoCursor<Document>> qu=mongoCollection.find(q).iterator();
//
//                                qu.getAsync(task->{
//                                    if(task.isSuccess())
                                    //   {
                                    MongoCursor<Document> resu = task.get();

                                    while (resu.hasNext()) {

                                        Document curDoc = resu.next();
                                        if (curDoc.getString("email") != null && curDoc.getString("email").equals(e)&& curDoc.getString("password").equals(p)) {
                                            //  mentorlist.add(curDoc.getString("name"));

                                            Intent intent = new Intent(MainActivity.this, MenteeActivity.class);

                                            intent.putExtra("name",curDoc.getString("name"));
                                            intent.putExtra("email",curDoc.getString("email"));
                                            intent.putExtra("id",curDoc.getObjectId("_id"));
                                            intent.putExtra("phone",curDoc.getString("phone"));
                                            intent.putExtra("mentor",curDoc.getString("mentor"));
                                            //intent.putStringArrayListExtra("data",);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                                            //Toast.makeText(MainActivity.this, curDoc.getString("name"), Toast.LENGTH_SHORT).show();
                                            //  TextView text=findViewById(R.id.mentorname);
                                            // text.setText(curDoc.getString("name"));

                                        }

                                    }

//                                    }
//                                });
//                                MongoCursor<Document> resu=task.get();
//
//                                while (resu.hasNext())
//                                {
//
//                                    Document curDoc=resu.next();
//                                    if(curDoc.getString("name")!=null)
//                                    {
//                                        mentorlist.add(curDoc.getString("name"));
//                                        //Toast.makeText(MainActivity.this, curDoc.getString("name"), Toast.LENGTH_SHORT).show();
//                                      //  TextView text=findViewById(R.id.mentorname);
//                                       // text.setText(curDoc.getString("name"));
//
//                                    }
//
//                                }
                                }

                            });


//                        Intent intent=new Intent(MainActivity.this, FaculityActivity.class);
//                        intent.putStringArrayListExtra("data",mentorlist);
//                        startActivity(intent);


                            //else if condition belong to mentor
                            //check pass and call toast
                            //Intent mentor activity pass userid


                            //else check mentee
                            //check pass and call toast
                            //Intent mentee Activity pass userId


                            //elsse break out of it logout with credential


                            //
                        }

                    });
                }
            }
        });
































        //test part


        Button guest=findViewById(R.id.guest);


//login
//        log.setOnClickListener(
//                new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//                        //instance of realm which is online placed
//
//                        //part of credential
//                        String e=email.getText().toString();
//                       String p=password.getText().toString();
                        //mongodb link 1. connect mongodb+srv://monika8427084:123@cluster0.r7gdsrb.mongodb.net/
                        //step2. click()-->
                        // -->admin mail--> if faculity --password --
                        //                      --> id else mentee
                        //                       -->if else mentor
                        //                       --> else(without login)
                       // Credentials credentials=Credentials.anonymous();
           //            Credentials credentials=Credentials.emailPassword("monika8427084@gmail.com","Monika8427@#");
//                       app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
//                           @Override
//                           public void onResult(App.Result<User> result) {
//                               Toast.makeText(MainActivity.this, "HELLO MONIKA 1", Toast.LENGTH_SHORT).show();
//
//                                   if(result.isSuccess())
//                                   {
//
//                                       User user=app.currentUser();
//                                       Toast.makeText(MainActivity.this, user.getId().toString(), Toast.LENGTH_SHORT).show();
//                                       mongoClient=user.getMongoClient("mongodb-atlas");
//                                       mongoDatabase=mongoClient.getDatabase("GNDECdb");
//                                       //mywebsite link so they can firstly recover their password from website
////                                    startActivity(intent);
//
//                                       MongoCollection<Document> mongoCollection=mongoDatabase.getCollection("Faculity");
//
//                                       Toast.makeText(MainActivity.this, mongoCollection.find().toString(), Toast.LENGTH_SHORT).show();
//                                       System.out.println(mongoCollection.find().toString());
//
//                                       mongoCollection.insertOne(new Document("userId",user.getId()).append("data","hello dude")).getAsync(result1 -> {
//                                           if(result1.isSuccess())
//                                           {
//                                               Toast.makeText(MainActivity.this, "HELLO MONIKA", Toast.LENGTH_SHORT).show();
//                                           }
//                                           else {
//                                               Toast.makeText(MainActivity.this, "dont allow", Toast.LENGTH_SHORT).show();
//                                           }
//                                       });
//
//                                       Intent intent=new Intent(MainActivity.this,MainActivity2log.class);
//                                       startActivity(intent);
//                                   }
//                                   else{
//                                       Toast.makeText(MainActivity.this, "CHECK INTERNET", Toast.LENGTH_SHORT).show();
//                                   }
//                               }
//
//                       });

//                          app.loginAsync(credentials, new App.Callback<User>() {
//                            @Override
//                            public void onResult( App.Result<User> result) {
//                                if(result.isSuccess())
//                                {
//
//                                    User user=app.currentUser();
//                                    mongoClient=user.getMongoClient("mongodb-atlas");
//                                    mongoDatabase=mongoClient.getDatabase("GNDECdb");
////                                  mywebsite link so they can firstly recover their password from website
////                                  startActivity(intent);
//
//
//
//                                    MongoCollection<Document> mongoCollection=mongoDatabase.getCollection("Faculity");
//
//                                    RealmResultTask<MongoCursor<Document>> field=mongoCollection.find().iterator();
//                                    field.getAsync(result1 -> {
//                                        Intent intent=new Intent(MainActivity.this,MainActivity2log.class);
//                                        String s="";
//                                        if(result.isSuccess())
//                                        {
//                                            Toast.makeText(MainActivity.this, "HELLO MONIKA", Toast.LENGTH_SHORT).show();
//                                        }
//                                        intent.putExtra("data",result.get().getId().toString());
//                                        startActivity(intent);
//                                        finish();
//                                    });


//                                    try {
//                                        mongoCollection.insertOne(new Document("userid",user.getId()).append("data","anonymous hu kya kr logy")).getAsync(result1 -> {
//                                        if(result1.isSuccess())
//                                        {
//                                            Toast.makeText(MainActivity.this, "HELLO MONIKA", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                        else {
//                                            Toast.makeText(MainActivity.this, "dont allow", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                        Intent intent=new Intent(MainActivity.this,MainActivity2log.class);
//                                        startActivity(intent);
//                                        intent.putExtra("data", mongoCollection.count().toString());
//
//                                    } catch (Exception ex) {
//                                        throw new RuntimeException(ex);
//                                    }
//                                    mongoCollection.insertOne(new Document("userid",user.getId()).append("data","hello dude")).getAsync(result1 -> {
//                                        if(result1.isSuccess())
//                                        {
//                                            Toast.makeText(MainActivity.this, "HELLO MONIKA", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                        else {
//                                            Toast.makeText(MainActivity.this, "dont allow", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });

//
//                                }
//                                else{
//                                     Toast.makeText(MainActivity.this, e+" "+p+"CHECK INTERNET", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//
//
//                    }
//                }
//
//
//
//        );





//login as guest
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code
            }
        });

    }




}
