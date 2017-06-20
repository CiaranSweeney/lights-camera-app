package com.example.ciaran.lightscameraapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.TintTypedArray;
import android.view.View;
import android.widget.TextView;

public class LightsCameraApp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights_camera_app);
        //Intent intent=getIntent();
        Bundle bundle=getIntent().getExtras();
        String filmName="";
        /*if(intent != null)
            filmName= intent.getExtras().getString("newFilm");*/
        if(bundle != null)
            filmName= bundle.getString("newFilm");
        TextView film;
        if(!(filmName.equals(""))) {
            film = (TextView) findViewById(R.id.films);
            film.append(filmName);
        }
    }
    public void goAddFilm(View view){
        MediaPlayer mediaPlayer= MediaPlayer.create(this,R.raw.click);
        mediaPlayer.start();
        startActivity(new Intent(LightsCameraApp.this,SearchFilm.class));
        finish();
    }
    public void goLogOut(View view){
        MediaPlayer mediaPlayer= MediaPlayer.create(this,R.raw.click);
        mediaPlayer.start();
        startActivity(new Intent(LightsCameraApp.this,LogOut.class));
        finish();
    }

}
