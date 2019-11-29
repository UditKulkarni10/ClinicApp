package com.example.clinic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Calendar extends AppCompatActivity {

    CalendarView calendarView;
    EditText startTime, endTime;
    Button addTime;
    String text;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                text = i + "-" + (i1+1) + "-" + i2; // This is the date for the calender that is stored
            }
        });
        addTime = (Button) findViewById(R.id.addTime);
        addTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //I i don't define them as null then the catch gives me a syntax error
                LocalTime startTimeFinal;
                LocalTime endTimeFinal;

                //Gathers user input and sends it to the addAvaliability class
                String startTimeToPass = startTime.getText().toString();
                String endTimeToPass = endTime.getText().toString();
                if(text==null){
                    Toast.makeText(Calendar.this,"Please select a date",Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm");

                        startTimeFinal = LocalTime.parse(startTimeToPass,parser);
                        endTimeFinal = LocalTime.parse(endTimeToPass,parser);
                        String slots= String.valueOf((int)(Duration.between(startTimeFinal,endTimeFinal).toMinutes()/15));
                        Toast.makeText(Calendar.this,"slots: "+slots,Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Calendar.this,addAvaliability.class);
                        i.putExtra("startTime", startTimeFinal.toString());
                        i.putExtra("endTime", endTimeFinal.toString());
                        i.putExtra("date", text);
                        i.putExtra("slots",slots);

                        setResult(RESULT_OK, i);
                        finish();
                    } catch(DateTimeParseException e){
                        Toast.makeText(Calendar.this,"Invalid Time",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}
