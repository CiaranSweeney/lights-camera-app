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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ciaran on 13/06/2017.
 */

public class Registration extends Activity implements View.OnClickListener{
    private Button signUpButton;
    TextView loginText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        signUpButton=(Button)findViewById(R.id.buttonSignUp);
        loginText=(TextView) findViewById(R.id.textLogin);
        signUpButton.setOnClickListener(this);
        loginText.setOnClickListener(this);
        progressDialog=new ProgressDialog(this);

    }

    private void signUp(){
        MediaPlayer mediaPlayer= MediaPlayer.create(this,R.raw.click);
        mediaPlayer.start();
        EditText editTextEmail=(EditText) findViewById(R.id.emailSignUp);
        EditText editTextPassword=(EditText) findViewById(R.id.passwordSignUp);
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast toast= Toast.makeText(this, "You didn't enter anything in your email", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(password.length()<6){
            Toast toast= Toast.makeText(this, "Your password is too short", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        progressDialog.setMessage("Please wait, we are adding you to this Great app!");
        progressDialog.show();

        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                            if(task.isSuccessful()) {
                                Toast.makeText(Registration.this,"You are now a member of my app :)",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Registration.this,"Error:Couldn't sign you up :(",Toast.LENGTH_SHORT).show();
                            }
                        progressDialog.dismiss();
                    }
                });
        Toast.makeText(Registration.this,"it got here",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view){
        if(view==signUpButton){
            signUp();
        }
        if(view==loginText){
            startActivity(new Intent(Registration.this,Login.class));
            finish();
        }
    }

}
