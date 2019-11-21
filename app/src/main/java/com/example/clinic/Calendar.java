package com.example.clinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

public class Calendar extends AppCompatActivity {

    CalendarView calendarView;
    EditText startTime, endTime;
    Button addTime;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        startTime = (EditText) findViewById(R.id.startTime);
        endTime = (EditText) findViewById(R.id.endTime);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                text = i + "/" + i1 + "/" + i2; // This is the date for the calender that is stored
            }
        });
        addTime = (Button) findViewById(R.id.addTime);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Gathers user input and sends it to the addAvaliability class
                String startTimeToPass = startTime.getText().toString();
                String endTimeToPass = endTime.getText().toString();
                Intent i = new Intent();
                i.putExtra("startTime", startTimeToPass);
                i.putExtra("endTime", endTimeToPass);
                i.putExtra("date", text);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
