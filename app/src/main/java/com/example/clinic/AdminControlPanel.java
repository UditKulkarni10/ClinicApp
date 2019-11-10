package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminControlPanel extends AppCompatActivity implements View.OnClickListener{
    private Button accManage, serviceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control_panel);
        accManage= findViewById(R.id.accManage);
        serviceBtn = findViewById(R.id.serviceBtn);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.accManage:
                startActivity(new Intent(this, AccountsManagement.class));
                break;
            case R.id.serviceBtn:
                startActivity(new Intent(this, Services.class));
                break;

        }
    }
}
