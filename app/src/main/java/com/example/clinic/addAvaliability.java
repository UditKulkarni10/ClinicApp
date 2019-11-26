package com.example.clinic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class addAvaliability extends AppCompatActivity {

    ListView listviewavailibilites;
    ArrayList<Model> models = new ArrayList<>();

    //    ArrayList<Model> models;
    private static final int CALENDAR_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avaliability);

        listviewavailibilites = (ListView) findViewById(R.id.listviewavailibilitiescard);

//        ArrayList<Model> models = new ArrayList<>();
//        models = Model.initialModels();

        ModelAdapter arrayAdapterModels = new ModelAdapter(this, android.R.layout.simple_list_item_1, models);

        listviewavailibilites.setAdapter(arrayAdapterModels);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:

//                startActivity(new Intent(this, Calendar.class));
//                break;

                Intent i = new Intent(this, Calendar.class);
                startActivityForResult(i, CALENDAR_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CALENDAR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Gets this data from the Calender activity class
                String date = data.getStringExtra("date");
                String startTime = data.getStringExtra("startTime");
                String endTime = data.getStringExtra("endTime");


                Model newModel = new Model();
                newModel.setTitle(date);
                newModel.setDescription(startTime + " - " + endTime);
//                addModel(startTime, endTime, date);
                models.add(newModel);
                //               ModelAdapter adapter = new ModelAdapter(models);
                ModelAdapter arrayAdapterModels = new ModelAdapter(this, android.R.layout.simple_list_item_1, models);
                listviewavailibilites.setAdapter(arrayAdapterModels);

            }


        }
    }
}
