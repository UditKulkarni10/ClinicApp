package com.example.clinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

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


}
