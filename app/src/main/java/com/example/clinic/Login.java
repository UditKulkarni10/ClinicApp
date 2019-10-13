package com.example.clinic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpUIViews();
        validate();

    }

    private void setUpUIViews(){
        userEmail = (EditText)findViewById(R.id.userEmail);
        userPassword = (EditText)findViewById(R.id.userPassword);
        loginBtn = (Button)findViewById(R.id.loginBtn);
    }

    private Boolean validate(){
        Boolean result = false;

        String username = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if(username.isEmpty() && password.isEmpty()){
            Toast.makeText(this,"Please enter username or password", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }

        return result;
    }

}
