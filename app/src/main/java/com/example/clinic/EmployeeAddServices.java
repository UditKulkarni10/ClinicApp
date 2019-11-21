package com.example.clinic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeAddServices extends AppCompatActivity {
    private ArrayList<Service> serviceList;
    private ListView list;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add_services);
        list =findViewById(R.id.servicesListView);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        serviceList = new ArrayList<>();
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        getServiceList();
    }
    private class ServiceListAdapter extends ArrayAdapter<Service> {
        private int layout;
        public ServiceListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Service> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            EmployeeAddServices.ServicesViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            EmployeeAddServices.ServicesViewHolder viewHolder= new EmployeeAddServices.ServicesViewHolder();
            viewHolder.addButton = convertView.findViewById(R.id.serviceAddBtn);
            viewHolder.name = convertView.findViewById(R.id.serviceName2);
            viewHolder.role = convertView.findViewById(R.id.serviceRole2);
            viewHolder.name.setText((getItem(position)).getName());
            viewHolder.role.setText((getItem(position)).getRole());
            viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(Services.this,"I'm a delete button",Toast.LENGTH_SHORT).show();
                    mDatabase.child("Users").child(mUser.getUid()).child("Services").child(getItem(position).getName()).setValue(getItem(position)).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(EmployeeAddServices.this, "Service added to clinic!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(EmployeeAddServices.this, EmployeeAddServices.class));
                        } else {
                            Toast.makeText(EmployeeAddServices.this, "Firebase Database error", Toast.LENGTH_SHORT).show();
                        }

                    });


                }
            });

            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (EmployeeAddServices.ServicesViewHolder) convertView.getTag();
                mainViewHolder.name.setText(getItem(position).getName());
                mainViewHolder.role.setText(getItem(position).getRole());
            }
            return convertView;
        }
    }

    public class ServicesViewHolder{
        TextView name;
        TextView role;
        Button addButton;

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
                list.setAdapter(new EmployeeAddServices.ServiceListAdapter(EmployeeAddServices.this, R.layout.employee_services_add_list_layout, serviceList));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EmployeeAddServices.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(EmployeeAddServices.this, EmployeeMainScreen.class));
    }
}
