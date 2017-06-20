package com.example.ciaran.lightscameraapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ciara on 13/06/2017.
 */

public class Login extends Activity implements View.OnClickListener{
    private Button loginButton;
    private TextView signUpText;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.start();
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(Login.this,LightsCameraApp.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        loginButton=(Button)findViewById(R.id.buttonLogin);
        signUpText=(TextView) findViewById(R.id.textSignUp);
        loginButton.setOnClickListener(this);
        signUpText.setOnClickListener(this);
        progressDialog=new ProgressDialog(this);
    }
    public void onClick(View view){

        if(view==loginButton){
            login();
        }
        if(view==signUpText){
            startActivity(new Intent(Login.this,Registration.class));
            finish();
        }
    }
    private void login() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.start();
        EditText editTextEmail = (EditText) findViewById(R.id.emailLogin);
        EditText editTextPassword = (EditText) findViewById(R.id.passwordLogin);
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this,"You are now loged in :)",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,LightsCameraApp.class));
                        }else{
                            Toast.makeText(Login.this,"Error:Your email or password is wrong",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
        if (TextUtils.isEmpty(email)) {
            Toast toast = Toast.makeText(this, "You didn't enter in your email", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast toast = Toast.makeText(this, "Your didn't enter in your password", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        progressDialog.setMessage("Please wait, we are logging you in!");
        progressDialog.show();
    }
}
