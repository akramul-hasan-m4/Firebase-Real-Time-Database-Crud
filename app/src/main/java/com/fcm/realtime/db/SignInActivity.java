package com.fcm.realtime.db;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private Button signInButton;
    private Button regButton;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        init();
        showRegButton();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSIGNIN){
            if (isFormEmpty()) return ;
            inProgress(true);
            // sign in by authenticate user
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignInActivity.this, "Successfully Sign In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish(); return;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    inProgress(false);
                    Toast.makeText(SignInActivity.this, "Sign Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(v.getId() == R.id.btnReg){
           if (isFormEmpty()) return ;
            inProgress(true);
            //create authenticate user
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    inProgress(false);
                    Toast.makeText(SignInActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    inProgress(false);
                    Toast.makeText(SignInActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //For showing registration button
    private void showRegButton(){
        Bundle extras = getIntent().getExtras();
        String reg = null;
        if(extras != null){
            reg = extras.getString("regMenu");
        }

        if(reg != null){
            regButton.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
        }
    }

    //initialize view elements
    private void init(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.btnSIGNIN);
        regButton = findViewById(R.id.btnReg);
        progressBar = findViewById(R.id.signInProgress);

        signInButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
    }
    //cheek user email or password isempty
    private boolean isFormEmpty(){
        if (TextUtils.isEmpty(email.getText().toString())){
            email.setError("Email is Required");
            return true;
        }if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Password is Required");
            return true;
        }
        return false;
    }

    //show hide progressbar
    private void inProgress(boolean status){
        if (status){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}
