package com.example.gndectouch;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class EventsShowing extends AppCompatActivity {
    String Appid = "application-0-kdmkx";
    App app = new App(new AppConfiguration.Builder(Appid).build());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_showing);
        Intent intent=getIntent();
        Boolean b=intent.getBooleanExtra("readwrite",false);
        if(b) {
//            Document document = new Document("event", "event");
//            User user = app.currentUser();
//            MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("GNDECdb");
//            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Events");
//            // Toast.makeText(alumniView.this, "stap2 hello ", Toast.LENGTH_SHORT).show();
//            RealmResultTask<MongoCursor<Document>> eventlist = mongoCollection.find(document).iterator();
//            eventlist.getAsync(task ->
//            {
//                if (task.isSuccess()) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            LinearLayout linearLayout = findViewById(R.id.linearlay);
//                            MongoCursor<Document> resu = task.get();
//                            while (resu.hasNext()) {
////                               //
//                                }
//
//                            }
//
//
//                    });
//                }
//            });
        }


        else{

        }
    }
}