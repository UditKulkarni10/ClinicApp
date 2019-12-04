package com.example.clinic;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ServiceInfo extends AppCompatActivity {

    private EditText serviceName;
    private EditText role;
    private Button confirmBtn;


    private ArrayList<String> serviceList;
    private ArrayAdapter<String> adapter;
    private ListView list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_content);

        serviceName = (EditText) findViewById(R.id.serviceName);
        role = (EditText) findViewById(R.id.role);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        //list = (ListView) findViewById(R.id.list);


        serviceName.addTextChangedListener(loginTextWatcher);
        role.addTextChangedListener(loginTextWatcher);


        //attempt to add service to list but not working
        /*
        serviceList = new ArrayList<String>();
        adapter =  new ArrayAdapter<String>(ServiceInfo.this, android.R.layout.simple_list_item_1, serviceList);
        list.setAdapter(adapter);

        onBtnClick();*/

    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String serviceInput = serviceName.getText().toString().trim();
            String roleInput = role.getText().toString().trim();

            confirmBtn.setEnabled(!serviceInput.isEmpty() && !roleInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    /*
    public void onBtnClick(){
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String service = serviceName.getText().toString();
                serviceList.add(service);
                adapter.notifyDataSetChanged();
            }
        });
    }*/


}
