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

public class BookWorkHourChoice extends AppCompatActivity {
    private ListView workHourListView;
    private ArrayList<WorkHours> workHourList;
    private Clinic clinic;
    private Service service;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_work_hour_choice);
        workHourListView=findViewById(R.id.bookWorkHourListView);
        workHourList=new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        Intent i =getIntent();
        clinic = (Clinic)i.getSerializableExtra("chosenClinic");
        service = (Service)i.getSerializableExtra("chosenService");
        getWorkHourList();
    }

    private class WorkHoursListAdapter extends ArrayAdapter<WorkHours> {
        private int layout;
        public WorkHoursListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<WorkHours> objects) {
            super(context, resource, objects);
            layout = resource;
        }


        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            BookWorkHourChoice.WorkHourViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            BookWorkHourChoice.WorkHourViewHolder viewHolder= new BookWorkHourChoice.WorkHourViewHolder();
            viewHolder.bookButton = convertView.findViewById(R.id.bookWorkHour);
            viewHolder.date = convertView.findViewById(R.id.bookingWorkHourDate);
            viewHolder.hours = convertView.findViewById(R.id.bookingWorkHours);
            viewHolder.takenSlots = convertView.findViewById(R.id.slotsTakenText);
            viewHolder.slots = convertView.findViewById(R.id.slotsText);
            viewHolder.date.setText((getItem(position)).getDate());
            viewHolder.hours.setText(getItem(position).getStartTime()+"-"+getItem(position).getEndTime());

            viewHolder.bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Appointment appointment = new Appointment(clinic,service,getItem(position),false);
                    mDatabase.child("Users").child(mUser.getUid()).child("Appointments").child("Appointment On "+getItem(position).getDate()).setValue(appointment).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            mDatabase.child("Users").child(mUser.getUid()).child("Appointments").child("Appointment On "+getItem(position).getDate()).child("workHour").child("slots").setValue(appointment.getWorkHour().getSlots()-1);
                            mDatabase.child("Users").child(mUser.getUid()).child("Appointments").child("Appointment On "+getItem(position).getDate()).child("workHour").child("takenSlots").setValue(appointment.getWorkHour().getTakenSlots()+1);
                            mDatabase.child("Users").child(clinic.getUid()).child("Work Hours").child("Work Hour On "+getItem(position).getDate()+" at "+getItem(position).getStartTime()+" to "+getItem(position).getEndTime()).child("slots").setValue(getItem(position).getSlots()-1);
                            mDatabase.child("Users").child(clinic.getUid()).child("Work Hours").child("Work Hour On "+getItem(position).getDate()+" at "+getItem(position).getStartTime()+" to "+getItem(position).getEndTime()).child("takenSlots").setValue(getItem(position).getTakenSlots()+1);

                            Toast.makeText(BookWorkHourChoice.this, "Appointment Booked!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(BookWorkHourChoice.this, PatientMainScreen.class));
                        } else {
                            Toast.makeText(BookWorkHourChoice.this, "Firebase Database error", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            });

            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (BookWorkHourChoice.WorkHourViewHolder) convertView.getTag();
                mainViewHolder.date.setText(getItem(position).getDate());
                mainViewHolder.hours.setText(getItem(position).getStartTime()+"-"+getItem(position).getEndTime());
                mainViewHolder.slots.setText(Integer.toString(getItem(position).getSlots()));
                mainViewHolder.takenSlots.setText(Integer.toString(getItem(position).getTakenSlots()*15)+" minutes");
            }
            return convertView;
        }
    }

    public class WorkHourViewHolder{
        TextView date;
        TextView hours;
        TextView slots;
        TextView takenSlots;
        Button bookButton;

    }
    public void getWorkHourList() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date today=new Date();
        String date= dateFormat.format(today);
        String time = timeFormat.format(today);
        FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Users").child(clinic.getUid()).child("Work Hours").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    WorkHours workHour = postSnapshot.getValue(WorkHours.class);
                    //Toast.makeText(Services.this,"Added: "+service.getName(),Toast.LENGTH_SHORT).show();
                    if(date.compareTo(workHour.getDate())>0){
                        //delete
                        //Toast.makeText(BookWorkHourChoice.this,"Stale date",Toast.LENGTH_SHORT).show();
                        mDatabase.child("Users").child(clinic.getUid()).child("Work Hours").child("Work Hour On "+workHour.getDate()+" at "+workHour.getStartTime()+" to "+workHour.getEndTime()).removeValue();

                    }
                    else if(date.compareTo(workHour.getDate())==0 && time.compareTo(workHour.getEndTime())>1){
                       //delete
                        //Toast.makeText(BookWorkHourChoice.this,"Stale time",Toast.LENGTH_SHORT).show();
                        mDatabase.child("Users").child(clinic.getUid()).child("Work Hours").child("Work Hour On "+workHour.getDate()+" at "+workHour.getStartTime()+" to "+workHour.getEndTime()).removeValue();

                    }
                    else{
                        workHourList.add(workHour);
                    }

                }
                if(workHourList.size()==0){
                    Toast.makeText(BookWorkHourChoice.this,"Sorry, there are no time slots for this clinic right now",Toast.LENGTH_SHORT).show();
                }
                workHourListView.setAdapter(new BookWorkHourChoice.WorkHoursListAdapter(BookWorkHourChoice.this, R.layout.book_work_hour_layout, workHourList));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(BookWorkHourChoice.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
