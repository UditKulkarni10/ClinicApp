package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText userEmail, userPassword;
    private Button loginBtn, createAcc;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private static boolean isAdmin;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.loginBtn);
        createAcc = (Button) findViewById(R.id.createAcc);
        isAdmin=false;
        mAuth = FirebaseAuth.getInstance();
        setUpUIViews();


    }
    public static boolean getIsAdmin(){
        return isAdmin;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.loginBtn:
                    String username = userEmail.getText().toString();
                    String password = userPassword.getText().toString();
                    System.out.println("User: "+username+" Pass: "+password);
                    if(username.isEmpty() || password.isEmpty()){
                        Toast.makeText(this,"Please enter username or password", Toast.LENGTH_SHORT).show();
                    }
                    else if(username.equals("admin")&& password.equals("5T5ptQ")){
                        isAdmin=true;
                        Toast.makeText(Login.this,"Admin Mode",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, WelcomePage.class));
                    }
                    else{
                        isAdmin=false;
                        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(Login.this,"Account Authenticated",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, WelcomePage.class));
                                }
                                else{
                                    Toast.makeText(Login.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                break;
            case R.id.createAcc:
                startActivity(new Intent(Login.this, createLogin.class));
                break;
        }
    }

    private void setUpUIViews(){
        userEmail = (EditText)findViewById(R.id.userEmail);
        userPassword = (EditText)findViewById(R.id.userPassword);
        loginBtn = (Button)findViewById(R.id.loginBtn);

    }




}
