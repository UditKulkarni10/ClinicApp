package com.example.clinic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class addAvaliability extends AppCompatActivity {

    private static final int CALENDAR_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avaliability);

    }

    public void onClick(View view){
        Intent  i = new Intent(this, Calendar.class);
        startActivityForResult(i, CALENDAR_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CALENDAR_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                //Gets this data from the Calender activity class
                String date = data.getStringExtra("date");
                String startTime = data.getStringExtra("startTime");
                String endTime = data.getStringExtra("endTime");
            }

            // Radhika add code here to store any recieved value in a list view
        }
    }


}
