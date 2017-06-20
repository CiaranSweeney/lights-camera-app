package com.example.ciaran.lightscameraapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ciara on 17/06/2017.
 */

public class LogOut extends Activity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_out_page);
    }
    public void logOut(View view){
        FirebaseAuth auth=FirebaseAuth.getInstance();;
        auth.signOut();
        startActivity(new Intent(LogOut.this,Login.class));
        finish();
    }
}
