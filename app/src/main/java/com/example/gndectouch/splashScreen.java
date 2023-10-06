package com.example.gndectouch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //make splash screen
        //using thread concept
        //parrallel working
        Thread thread=new Thread(){
            public void run(){
                try{
                    //dar hai bhai
                    //system crash nhi hogi dude relax
                    sleep( 2000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //okk move to another slide bache hehehhe

                    Intent intent=new Intent(splashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}