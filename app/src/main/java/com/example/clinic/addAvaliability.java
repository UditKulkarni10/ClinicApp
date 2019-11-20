package com.example.clinic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class addAvaliability extends AppCompatActivity {

    RecyclerView myRecyclerView;
    Adapter myAdapter;

    ArrayList<Model> models = new ArrayList<>();
    private static final int CALENDAR_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avaliability);

        myRecyclerView = findViewById(R.id.recyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new Adapter(this, getMyList());

    }

    public ArrayList<Model> getMyList(){
        Model s = new Model();
        s.setTitle("Coffee");
        s.setDescription("9am - 10pm");
        models.add(s);

        Model d = new Model();
        d.setTitle("Bone Breaking");
        d.setDescription("10am-3pm");
        models.add(d);

        return models;
    }

    public void addModel(String startTime, String endTime, String date){
        Model m = new Model();
        m.setTitle(startTime+endTime);
        m.setDescription(date);
        models.add(m);
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

                addModel(startTime, endTime, date);
                myAdapter = new Adapter(this, getMyList());
            }


        }
    }


}
