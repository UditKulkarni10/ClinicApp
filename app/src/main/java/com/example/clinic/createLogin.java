package com.example.clinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class createLogin extends AppCompatActivity {
    private EditText Name, role, username, pwd, confpwd;
    private Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);
        create = (Button) findViewById(R.id.create);
        setUpUIviews();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                startActivity( new Intent(createLogin.this, Login.class));
            }
        });
    }

    private void setUpUIviews(){
        Name = (EditText)findViewById(R.id.Name);
        role = (EditText) findViewById(R.id.role);
        username = (EditText)findViewById(R.id.username);
        pwd = (EditText)findViewById(R.id.pwd);
        confpwd = (EditText)findViewById(R.id.confpwd);
    }


    private Boolean validate(){
        
        String nameVal = Name.getText().toString();
        String roleVal = role.getText().toString();
        String usernameVal = username.getText().toString();
        String pwdVal = pwd.getText().toString();
        String confpwdVal = confpwd.getText().toString();

        if (nameVal.isEmpty() || roleVal.isEmpty() || usernameVal.isEmpty() || pwdVal.isEmpty() || confpwdVal.isEmpty()){
            Toast.makeText(createLogin.this, "You are missing some information", Toast.LENGTH_SHORT).show();
            }

        else if(pwdVal != confpwdVal){
            Toast.makeText(createLogin.this, "Your Passwords do not match", Toast.LENGTH_SHORT).show();
            }
    }


}
