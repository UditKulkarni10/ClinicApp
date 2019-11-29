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

public class PatientMainScreen extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ArrayList<Appointment> appList;
    private ListView appListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_screen);
        appListView=findViewById(R.id.patienAppointmentListView);
        appList=new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        getAppList();
    }

    private class AppListAdapter extends ArrayAdapter<Appointment> {
        private int layout;
        public AppListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Appointment> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            PatientMainScreen.AppViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            PatientMainScreen.AppViewHolder viewHolder= new PatientMainScreen.AppViewHolder();
            viewHolder.checkInButton = convertView.findViewById(R.id.appCheckInBtn);
            viewHolder.cancelButton = convertView.findViewById(R.id.appCancelBtn);
            viewHolder.clinicName = convertView.findViewById(R.id.appClinicName);
            viewHolder.serviceName = convertView.findViewById(R.id.appServiceName);
            viewHolder.workHours = convertView.findViewById(R.id.appWorkHours);
            viewHolder.date = convertView.findViewById(R.id.appDate);
            viewHolder.overdue = convertView.findViewById(R.id.overDueText);
            viewHolder.address = convertView.findViewById(R.id.appAddress);
            viewHolder.waitTime = convertView.findViewById(R.id.appWaitTime);
            viewHolder.clinicName.setText((getItem(position)).getClinic().getClinicName());
            viewHolder.serviceName.setText((getItem(position)).getService().getName());
            viewHolder.workHours.setText((getItem(position)).getWorkHour().getStartTime()+" - "+getItem(position).getWorkHour().getEndTime());
            viewHolder.date.setText((getItem(position)).getWorkHour().getDate());
            viewHolder.address.setText((getItem(position)).getClinic().getAddress());
            viewHolder.waitTime.setText((getItem(position)).getWorkHour().getTakenSlots()*15+" minutes");
            if(getItem(position).isOverdue()){
                viewHolder.overdue.setVisibility(View.VISIBLE);
            }
            else{
                viewHolder.overdue.setVisibility(View.INVISIBLE);
            }

            viewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(Services.this,"I'm a delete button",Toast.LENGTH_SHORT).show();
                    /*
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

                     */


                }
            });
            viewHolder.checkInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                }
            });
            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (PatientMainScreen.AppViewHolder) convertView.getTag();
                mainViewHolder.clinicName.setText((getItem(position)).getClinic().getClinicName());
                mainViewHolder.serviceName.setText((getItem(position)).getService().getName());
                mainViewHolder.workHours.setText((getItem(position)).getWorkHour().getStartTime()+" - "+getItem(position).getWorkHour().getEndTime());
                mainViewHolder.date.setText((getItem(position)).getWorkHour().getDate());
                mainViewHolder.address.setText((getItem(position)).getClinic().getAddress());
                mainViewHolder.waitTime.setText((getItem(position)).getWorkHour().getTakenSlots()*15+" minutes");
                if(getItem(position).isOverdue()){
                    viewHolder.overdue.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }
    }

    public class AppViewHolder{
        TextView clinicName;
        TextView serviceName;
        TextView overdue;
        TextView workHours;
        TextView date;
        TextView address;
        TextView waitTime;
        Button cancelButton;
        Button checkInButton;

    }

    public void getAppList() {
        FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Users").child(mUser.getUid()).child("Appointments").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Appointment app = postSnapshot.getValue(Appointment.class);
                    //Toast.makeText(Services.this,"Added: "+service.getName(),Toast.LENGTH_SHORT).show();
                    appList.add(app);
                }
                appListView.setAdapter(new AppListAdapter(PatientMainScreen.this, R.layout.appointment_layout, appList));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(PatientMainScreen.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.bookAppBtn:
                startActivity(new Intent(this, BookAppointment.class));
                break;
            case R.id.pastAppBtn:
                //startActivity(new Intent(this, EmployeeAddServices.class));
                break;

        }
    }
}
