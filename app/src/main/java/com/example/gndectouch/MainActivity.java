package com.example.gndectouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mongodb.lang.NonNull;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;


public class MainActivity extends AppCompatActivity {
    String Appid="application-0-kdmkx";
    String url="mongodb+srv://monika8427084:123@cluster0.r7gdsrb.mongodb.net/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button log=findViewById(R.id.submitbtn);
        EditText email,password;
        email=findViewById(R.id.emailinput);
        password=findViewById(R.id.passinput);
        // TODO Auto-generated method stub

        //real mongodb
        Realm.init(this);

        // MongoClient mongoClient = (MongoClient) MongoClients.create(url);
        // MongoDatabase database = mongoClient.getDatabase("GNDECdb");

        Button guest=findViewById(R.id.guest);
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   MongoCollection<Document> collection = database.getCollection("Faculity");

                // Create a document to insert
               // Document document = new Document("email","luch@mail.com");

                // Insert the document
               // collection.insertOne(document);
            }
        });
        log.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        //instance of realm which is online placed
                        App app=new App(new AppConfiguration.Builder(Appid).build());
                        //part of credential
                        String e=email.getText().toString();
                        String p=password.getText().toString();
                        //mongodb link 1. connect mongodb+srv://monika8427084:123@cluster0.r7gdsrb.mongodb.net/
                        //step2. click()-->
                        // -->admin mail--> if faculity --password --
                        //                      --> id else mentee
                        //                       -->if else mentor
                        //                       --> else(without login)

                        Credentials credentials=Credentials.emailPassword(e,p);
                        app.loginAsync(credentials, new App.Callback<User>() {
                            @Override
                            public void onResult(@NonNull App.Result<User> result) {
                                if(result.isSuccess())
                                {

                                    //mywebsite link so they can firstly recover their password from website
//                                    startActivity(intent);
                                     Intent intent=new Intent(MainActivity.this,MainActivity2log.class);
                                     startActivity(intent);
                                }
                                else{
                                     Toast.makeText(MainActivity.this, e+" "+p+"CHECK INTERNET", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                       //
//                       Intent intent=new Intent(MainActivity2log.this,MainActivity.class);
//                       startActivity(intent);
//                       finish();
                    }
                }
        );

    }




}
