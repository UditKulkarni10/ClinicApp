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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

                    mDatabase.child("Users").child(mUser.getUid()).child("Appointments").child("Appointment On "+getItem(position).getWorkHour().getDate()).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            appList.remove(getItem(position));
                            Toast.makeText(PatientMainScreen.this, "Appointment Cancelled!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(PatientMainScreen.this, PatientMainScreen.class));
                        } else {
                            Toast.makeText(PatientMainScreen.this, "Firebase Database Deletion error", Toast.LENGTH_SHORT).show();
                        }

                    });




                }
            });
            viewHolder.checkInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getItem(position).isOverdue()){
                        Toast.makeText(PatientMainScreen.this,"Sorry, you missed your appointment :(",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mDatabase.child("Users").child(mUser.getUid()).child("Appointments").child("Appointment On "+getItem(position).getWorkHour().getDate()).removeValue().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                appList.remove(getItem(position));
                                Toast.makeText(PatientMainScreen.this, "Checked in! Please make sure to leave a rating :)", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PatientMainScreen.this, PatientMainScreen.class));
                            } else {
                                Toast.makeText(PatientMainScreen.this, "Firebase Database error", Toast.LENGTH_LONG).show();
                            }

                        });
                    }



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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date today=new Date();
        String date= dateFormat.format(today);
        String time = timeFormat.format(today);
        //Toast.makeText(this,"Date: "+date,Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,"Time: "+time,Toast.LENGTH_SHORT).show();
        FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Users").child(mUser.getUid()).child("Appointments").getRef().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Appointment app = postSnapshot.getValue(Appointment.class);
                    //Toast.makeText(Services.this,"Added: "+service.getName(),Toast.LENGTH_SHORT).show();
                    if(date.compareTo(app.getWorkHour().getDate())>0){
                        app.setOverdue(true);
                        //Toast.makeText(PatientMainScreen.this,"OVERDUE appointment",Toast.LENGTH_SHORT).show();
                    }
                    else if(date.compareTo(app.getWorkHour().getDate())==0 && time.compareTo(app.getWorkHour().getEndTime())>1){
                        app.setOverdue(true);
                        //Toast.makeText(PatientMainScreen.this,"OVERDUE appointment",Toast.LENGTH_SHORT).show();
                    }

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
                //startActivity(new Intent(this, PastAppointments.class));
                break;
            case R.id.rateClinic:
                startActivity(new Intent (this, rateClinic.class));
                break;
        }
    }
}
