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

public class EmployeeMainScreen extends AppCompatActivity {
    private Button addService;
    private Button viewProfile;
    private Button viewHours;
    private ArrayList<Service> serviceList;
    private ListView list;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main_screen);
        addService = findViewById(R.id.addEmployeeService);
        viewProfile = findViewById(R.id.profileBtn);
        viewHours = findViewById(R.id.viewWorkHoursBtn);
        list = findViewById(R.id.employeeServiceListView);
        serviceList=new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        getEmployeeServices();
    }

    public void getEmployeeServices(){
        FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Users").child(mUser.getUid()).child("Services").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    //Toast.makeText(EmployeeMainScreen.this,"Added: "+service.getName(),Toast.LENGTH_SHORT).show();
                    serviceList.add(service);
                }
                list.setAdapter(new EmployeeMainScreen.ServiceListAdapter(EmployeeMainScreen.this, R.layout.employee_main_screen_service_layout, serviceList));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EmployeeMainScreen.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
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
            EmployeeMainScreen.ServicesViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            EmployeeMainScreen.ServicesViewHolder viewHolder= new EmployeeMainScreen.ServicesViewHolder();
            viewHolder.deleteButton = convertView.findViewById(R.id.serviceDelBtn);
            viewHolder.name = convertView.findViewById(R.id.serviceNameMain);
            viewHolder.role = convertView.findViewById(R.id.serviceRoleMain);
            viewHolder.name.setText((getItem(position)).getName());
            viewHolder.role.setText((getItem(position)).getRole());
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(EmployeeMainScreen.this,mUser.getUid(),Toast.LENGTH_SHORT).show();
                    mDatabase.child("Users").child(mUser.getUid()).child("Services").child(getItem(position).getName()).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            serviceList.remove(getItem(position));
                            Toast.makeText(EmployeeMainScreen.this, "Service Deleted from clinic!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(EmployeeMainScreen.this, EmployeeMainScreen.class));
                        } else {
                            Toast.makeText(EmployeeMainScreen.this, "Firebase Database Deletion error", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            });

            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (EmployeeMainScreen.ServicesViewHolder) convertView.getTag();
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

    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.profileBtn:
                startActivity(new Intent(this, EmployeeProfile.class));
                break;
            case R.id.addEmployeeService:
                startActivity(new Intent(this, EmployeeAddServices.class));
                break;
            case R.id.viewWorkHoursBtn:
                break;

        }
    }


}
