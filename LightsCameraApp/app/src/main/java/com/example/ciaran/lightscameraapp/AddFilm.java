package com.example.ciaran.lightscameraapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import static com.example.ciaran.lightscameraapp.R.id.editText;


/**
 * Created by ciara on 09/06/2017.
 */

public class AddFilm extends Activity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film);
        TextView textView = (TextView) findViewById(R.id.text_film_name);
        Intent intent=getIntent();
        String filmName= intent.getStringExtra("title");
        Toast.makeText(this,filmName,Toast.LENGTH_SHORT).show();
        textView.setText(filmName);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(dm.widthPixels*0.8),(int)(dm.heightPixels*0.6));
    }
    public void addFilm(View view){
        MediaPlayer mediaPlayer= MediaPlayer.create(this,R.raw.click);
        mediaPlayer.start();
        Intent intent = new Intent(this,LightsCameraApp.class);
        TextView textView = (TextView) findViewById(R.id.text_film_name);
        String filmName = textView.getText().toString();
        intent.putExtra("newFilm", filmName);
       // Film f=new Film(filmName);
        //FirebaseUser user = firebaseAuth.getCurrentUser();
        //databaseReference.child(user.getUid()).setValue(f);
        startActivity(intent);
        finish();
    }
}
