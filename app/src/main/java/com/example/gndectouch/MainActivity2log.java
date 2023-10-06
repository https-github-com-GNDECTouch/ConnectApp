package com.example.gndectouch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2log extends AppCompatActivity {

    String Appid="application-0-kdmkx";
    //app id which is already defined in mongodb atlas
    Toolbar tb;
    EditText numbr;
    Button getrec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2log);

        tb=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Home");
//        numbr=findViewById(R.id.numb_rows);
//        getrec=findViewById(R.id.getrec);
//        getrec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TableLayout tb = (TableLayout) findViewById(R.id.table_1);
//               // TableRow tbr=(TableRow) findViewById(R.id.tbrow);
//               // TextView t1=new TextView(this);
//
//                //tbr.addView();
//
//            }
//        });
//        for(int i=0;i<Integer.parseInt(numbr.getText().toString());i++)
//        {
//
//        }

        //very important intialize realm
//        Realm.init(this);
//
//        //instance of realm which is online placed
//        App app=new App(new AppConfiguration.Builder(Appid).build());
//        //part of credential
//
//        Credentials credentials=Credentials.emailPassword();
//        app.loginAsync(credentials, new App.Callback<User>() {
//            @Override
//            public void onResult(App.Result<User> result) {
//                if(result.isSuccess())
//                {
//                    Toast.makeText(MainActivity2log.this, "mongodb working", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(MainActivity2log.this, "CHECK INTERNET", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate=getMenuInflater();
        inflate.inflate(R.menu.menu,menu);
        return true;
    }

}