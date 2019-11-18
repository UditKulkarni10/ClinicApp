package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeMainScreen extends AppCompatActivity {
    private Button addService;
    private Button viewProfile;
    private Button viewHours;
    private ListView employeeServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main_screen);
        addService = findViewById(R.id.addEmployeeService);
        viewProfile = findViewById(R.id.profileBtn);
        viewHours = findViewById(R.id.viewWorkHoursBtn);
        employeeServices = findViewById(R.id.employeeServiceListView);
        getEmployeeServices();
    }

    public void getEmployeeServices(){

    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.profileBtn:
                startActivity(new Intent(this, EmployeeProfile.class));
                break;
            case R.id.addEmployeeService:
                //startActivity(new Intent(this, AddEmployeeService.class));
                break;
            case R.id.viewWorkHoursBtn:
                break;

        }
    }
}
