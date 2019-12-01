package com.example.clinic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Services extends AppCompatActivity {

    private ArrayList<Service> serviceList;
    //private EditText addText;
    //private Button addBtn;
    private ListView list;
    private DatabaseReference mDatabase;
    private EditText serviceName,serviceRole;
    private RadioGroup role;
    private Button addBtn,updateBtn;
    private String roleval="Doctor";
    private Service temp;
    private String tempName;
   //private String service;


    //private ImageButton addBtnImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        serviceName = findViewById(R.id.serviceNameSetText);
        role= findViewById(R.id.serviceRoleRadioGroup);
        list =findViewById(R.id.list);
        addBtn=findViewById(R.id.addBtn);
        updateBtn=findViewById(R.id.updateServiceBtn);
        addBtn.setVisibility(View.VISIBLE);
        updateBtn.setVisibility(View.INVISIBLE);
        serviceList = new ArrayList<>();
        getServiceList();
    }

    private class ServiceListAdapter extends ArrayAdapter<Service>{
        private int layout;
        public ServiceListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Service> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            ServicesViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            Services.ServicesViewHolder viewHolder= new Services.ServicesViewHolder();
            viewHolder.deleteButton = convertView.findViewById(R.id.serviceDeleteBtn);
            viewHolder.editButton = convertView.findViewById(R.id.serviceEditBtn);
            viewHolder.name = convertView.findViewById(R.id.serviceName);
            viewHolder.role = convertView.findViewById(R.id.serviceRole);
            viewHolder.name.setText((getItem(position)).getName());
            viewHolder.role.setText((getItem(position)).getRole());
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(Services.this,"I'm a delete button",Toast.LENGTH_SHORT).show();
                    mDatabase.child("Services").child(getItem(position).getName()).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            serviceList.remove(getItem(position));
                            Toast.makeText(Services.this, "Service Deleted!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(Services.this, Services.class));
                        } else {
                            Toast.makeText(Services.this, "Firebase Database Deletion error", Toast.LENGTH_SHORT).show();
                        }

                    });


                }
            });
            viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(Services.this,"I'm an edit button",Toast.LENGTH_SHORT).show();
                    addBtn.setVisibility(View.INVISIBLE);
                    updateBtn.setVisibility(View.VISIBLE);
                    temp=getItem(position);
                    serviceName.setText(getItem(position).getName());
                    tempName=getItem(position).getName();
                    String getRoleVal=getItem(position).getRole();
                    if(getRoleVal.equals("Doctor")){
                        role.check(R.id.doctorRadioBtn);

                    }
                    else if(getRoleVal.equals("Nurse")){
                        role.check(R.id.nurseRadioBtn);
                    }
                    else if(getRoleVal.equals("Staff")){
                        role.check(R.id.staffRadioBtn);
                    }



                }
            });
            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (Services.ServicesViewHolder) convertView.getTag();
                mainViewHolder.name.setText(getItem(position).getName());
                mainViewHolder.role.setText(getItem(position).getRole());
            }
            return convertView;
        }
    }

    public class ServicesViewHolder{
        TextView name;
        TextView role;
        Button deleteButton;
        Button editButton;

    }




    public void onClick(View view){
        if(view.getId()==R.id.addBtn) {

            //Toast.makeText(Services.this, "Service Button", Toast.LENGTH_SHORT).show();
            if (!serviceName.getText().toString().isEmpty()) {
                String name = serviceName.getText().toString();
                String role = roleval;
                final Service mService = new Service(name, role);
                mDatabase.child("Services").child(name).setValue(mService).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        serviceList.add(mService);
                        Toast.makeText(Services.this, "Service Added!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(this, Services.class));

                    } else {
                        Toast.makeText(Services.this, "Firebase Database error", Toast.LENGTH_SHORT).show();
                    }

                });
            } else {
                Toast.makeText(Services.this, "Please enter a service name", Toast.LENGTH_SHORT).show();
            }
        }
         else if(view.getId()==R.id.updateServiceBtn) {

            if (!serviceName.getText().toString().isEmpty()) {

                String name = serviceName.getText().toString();
                String role = roleval;
                Service newService = new Service(name, role);
                mDatabase.child("Services").child(tempName).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        serviceList.remove(tempName);

                    } else {
                        Toast.makeText(Services.this, "Firebase Database Deletion error", Toast.LENGTH_SHORT).show();
                    }

                });
                mDatabase.child("Services").child(name).setValue(newService).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Services.this, "Service Edited!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(this, Services.class));
                    } else {
                        Toast.makeText(Services.this, "Firebase Database Edit Error", Toast.LENGTH_SHORT).show();
                    }

                });


            }
            else{
                Toast.makeText(Services.this, "Please enter a service name", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void getServiceList() {
        FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Services").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    //Toast.makeText(Services.this,"Added: "+service.getName(),Toast.LENGTH_SHORT).show();
                    serviceList.add(service);
                }
                list.setAdapter(new ServiceListAdapter(Services.this, R.layout.services_list_layout, serviceList));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Services.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        // checks to see is the radio button is already checked
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.doctorRadioBtn:
                if (checked){
                    roleval="Doctor";

                }
                break;
            case R.id.nurseRadioBtn:
                if (checked){
                    roleval="Nurse";

                }
                break;
            case R.id.staffRadioBtn:
                if (checked){
                    roleval="Staff";

                }
                break;


        }
    }

    public void onBackPressed(){
        startActivity(new Intent(this, EmployeeMainScreen.class));
    }




}
