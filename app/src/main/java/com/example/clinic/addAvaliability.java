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

public class addAvaliability extends AppCompatActivity {

    private ListView listviewavailibilites;
    private ArrayList<WorkHours> models = new ArrayList<>();
    boolean editMode=false;
    private String editWorkDate;
    private String editWorkStartTime;
    private String editWorkEndTime;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private static final int CALENDAR_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avaliability);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        listviewavailibilites = (ListView) findViewById(R.id.listviewavailibilitiescard);



        WorkHoursListAdapter arrayAdapterModels = new WorkHoursListAdapter(this, R.layout.work_hour_layout, models);

        getWorkHourList();

    }

    protected void refreshList(){
        models=new ArrayList<>();
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
            addAvaliability.WorkHourViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            addAvaliability.WorkHourViewHolder viewHolder= new addAvaliability.WorkHourViewHolder();
            viewHolder.deleteButton = convertView.findViewById(R.id.workHourDeleteBtn);
            viewHolder.editButton = convertView.findViewById(R.id.workHourEditBtn);
            viewHolder.date = convertView.findViewById(R.id.workHourDate);
            viewHolder.hours = convertView.findViewById(R.id.workHours);
            viewHolder.date.setText((getItem(position)).getDate());
            viewHolder.hours.setText(getItem(position).getStartTime()+"-"+getItem(position).getEndTime());
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(Services.this,"I'm a delete button",Toast.LENGTH_SHORT).show();
                    String databaseVal ="Work Hour On "+getItem(position).getDate()+" at "+getItem(position).getStartTime()+" to "+getItem(position).getEndTime();
                    mDatabase.child("Users").child(mUser.getUid()).child("Work Hours").child(databaseVal).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            models.remove(getItem(position));
                            Toast.makeText(addAvaliability.this, "Work Hour Deleted!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(addAvaliability.this, addAvaliability.class));
                        } else {
                            Toast.makeText(addAvaliability.this, "Firebase Database Deletion error", Toast.LENGTH_SHORT).show();
                        }

                    });


                }
            });
            viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    editWorkDate=getItem(position).getDate();
                    editWorkStartTime=getItem(position).getStartTime();
                    editWorkEndTime=getItem(position).getEndTime();
                    String newTitle = "Work Hour On "+editWorkDate+" at "+editWorkStartTime+" to "+editWorkEndTime;
                    mDatabase.child("Users").child(mUser.getUid()).child("Work Hours").child(newTitle).removeValue();
                    Intent i = new Intent(addAvaliability.this, Calendar.class);
                    startActivityForResult(i, CALENDAR_REQUEST_CODE);




                }
            });

            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (addAvaliability.WorkHourViewHolder) convertView.getTag();
                mainViewHolder.date.setText(getItem(position).getDate());
                mainViewHolder.hours.setText(getItem(position).getStartTime()+"-"+getItem(position).getEndTime());
            }
            return convertView;
        }
    }

    public class WorkHourViewHolder{
        TextView date;
        TextView hours;
        //TextView slots;
        Button deleteButton;
        Button editButton;

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
    public void getWorkHourList() {
        FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Users").child(mUser.getUid()).child("Work Hours").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    WorkHours workHour = postSnapshot.getValue(WorkHours.class);
                    //Toast.makeText(Services.this,"Added: "+service.getName(),Toast.LENGTH_SHORT).show();
                    models.add(workHour);
                }
                listviewavailibilites.setAdapter(new addAvaliability.WorkHoursListAdapter(addAvaliability.this, R.layout.work_hour_layout, models));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(addAvaliability.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CALENDAR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Gets this data from the Calender activity class
                //data=getIntent();
                String date = data.getStringExtra("date");
                String startTime = data.getStringExtra("startTime");
                String endTime = data.getStringExtra("endTime");
                String newTitle = "Work Hour On "+date+" at "+startTime+" to "+endTime;

                int slots=Integer.valueOf(data.getStringExtra("slots"));
                //Toast.makeText(this,slots,Toast.LENGTH_SHORT).show();



                    WorkHours newModel = new WorkHours(date,startTime,endTime,slots,0);

                    mDatabase.child("Users").child(mUser.getUid()).child("Work Hours").child(newTitle).setValue(newModel);
                    refreshList();
                    //getWorkHourList();
                    //models.add(newModel);

                    //WorkHoursListAdapter arrayAdapterModels = new WorkHoursListAdapter(this, R.layout.work_hour_layout, models);
                    //listviewavailibilites.setAdapter(arrayAdapterModels);



            }


        }
    }
}
