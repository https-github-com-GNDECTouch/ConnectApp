package com.example.gndectouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {
 ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //make splash screen
        //using thread concept
        //parrallel working
        imageView =findViewById(R.id.imageView);
        Animation translating= AnimationUtils.loadAnimation(this,R.anim.translating);
        Thread thread=new Thread(){
            public void run(){
                try{

                    imageView.startAnimation(translating);
                    //dar hai bhai
                    //system crash nhi hogi dude relax
                    sleep( 3000);
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