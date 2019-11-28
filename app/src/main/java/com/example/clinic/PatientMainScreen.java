package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PatientMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_screen);
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.bookAppBtn:
                startActivity(new Intent(this, BookAppointment.class));
                break;
            case R.id.pastAppBtn:
                //startActivity(new Intent(this, EmployeeAddServices.class));
                break;

        }
    }
}
