package com.example.clinic;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Services extends AppCompatActivity {

    private ArrayList<String> serviceList;
    private ArrayAdapter<String> adapter;
    private EditText addText;
    private Button addBtn;
    private ListView list;

    private String service;



    private ImageButton addBtnImg;


    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services);

        list = (ListView) findViewById(R.id.list);
        addText = (EditText) findViewById(R.id.addText);
        addBtn = (Button) findViewById(R.id.addBtn);

        serviceList = new ArrayList<>();
        adapter =  new ArrayAdapter<String>(Services.this, android.R.layout.simple_list_item_multiple_choice, serviceList);

        View.OnClickListener addListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(addText.getText().toString().isEmpty()){
                    Toast.makeText(Services.this,"Please enter a service", Toast.LENGTH_SHORT).show();
                }
                else {
                    serviceList.add(addText.getText().toString());
                    addText.setText("");
                    adapter.notifyDataSetChanged();
                }

            }
        };

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                SparseBooleanArray positioncheker = list.getCheckedItemPositions();

                int count = list.getCount();

                for(int service = count-1; service>=0; service--){

                    if(positioncheker.get(service)){
                        adapter.remove(serviceList.get(service));

                        Toast.makeText(Services.this, "Service Deleted", Toast.LENGTH_SHORT).show();
                    }

                }

                positioncheker.clear();

                adapter.notifyDataSetChanged();

                return false;
            }
        });

        addBtn.setOnClickListener(addListner);

        list.setAdapter(adapter);
    }

    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_home);

        addBtnImg = (ImageButton) findViewById(R.id.addBtnImg);

        addBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Services.this, ServiceInfo.class);
                startActivity(intent);
            }
        });

    }*/



    }
