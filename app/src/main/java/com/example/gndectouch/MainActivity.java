package com.example.gndectouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;


public class MainActivity extends AppCompatActivity {
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    //real mongodb

    String Appid="application-0-kdmkx";


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
                Credentials credentials=Credentials.emailPassword("monika8427084@gmail.com","Monika8427@#");

                app.loginAsync(credentials,new App.Callback<User>(){

                    @Override
                    public void onResult(App.Result<User> result) {
                        //if condition to check email belong to admin
                        //check pass and call toast
                        //Intent for Faculity Activity pass userid
                        Intent intent=new Intent(MainActivity.this,FaculityActivity.class);
                        startActivity(intent);





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
