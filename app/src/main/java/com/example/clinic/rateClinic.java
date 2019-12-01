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
import java.util.List;

public class rateClinic extends AppCompatActivity {

    private ListView list;
    private List<Rate> rates = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    RateAdapter myAdapter;



    private static final int RATE_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_clinic);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        list = (ListView) findViewById(R.id.list);

        myAdapter = new RateAdapter(this, R.layout.rate_layout, rates );

        getRatesList();

    }

    protected void refreshList(){
        rates = new ArrayList<>();
    }


    private class ReviewsListAdapter extends ArrayAdapter<Rate> {
        private int layout;
        public ReviewsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Rate> objects) {
            super(context, resource, objects);
            layout = resource;
        }


        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            rateClinic.RateViewHolder mainViewHolder = null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            rateClinic.RateViewHolder viewHolder= new rateClinic.RateViewHolder();
            viewHolder.clinicname = convertView.findViewById(R.id.clinicname);
            viewHolder.clinicrating = convertView.findViewById(R.id.clinicrating);
            viewHolder.cliniccomment = convertView.findViewById(R.id.cliniccomments);
/*
            viewHolder.deleteButton = convertView.findViewById(R.id.workHourDeleteBtn);
            viewHolder.editButton = convertView.findViewById(R.id.workHourEditBtn);
*/
   //         viewHolder.clinicname = convertView.findViewById(R.id.reviewname);
            viewHolder.clinicname.setText((getItem(position)).getClinicName());
            viewHolder.clinicrating.setText(getItem(position).getRating());
            viewHolder.cliniccomment.setText(getItem(position).getComments());

 //           viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
//            viewHolder.editButton.setOnClickListener(new View.OnClickListener() {

            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (rateClinic.RateViewHolder) convertView.getTag();
                mainViewHolder.clinicname.setText(getItem(position).getClinicName());
                mainViewHolder.clinicrating.setText(getItem(position).getRating());
                mainViewHolder.cliniccomment.setText(getItem(position).getComments());
            }
            return convertView;
        }
    }

    public class RateViewHolder{
        TextView clinicname;
        TextView clinicrating;
        TextView cliniccomment;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton2:

//                startActivity(new Intent(this, Calendar.class));
//                break;

                Intent i = new Intent(this, createNewRate.class);
                startActivityForResult(i, RATE_REQUEST_CODE);
        }
    }

    public void getRatesList() {
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Users").child(mUser.getUid()).child("Rate").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Rate rate = postSnapshot.getValue(Rate.class);
                    //Toast.makeText(Services.this,"Added: "+service.getName(),Toast.LENGTH_SHORT).show();
                    rates.add(rate);
                }
//                list.setAdapter(new rateClinic.ReviewsListAdapter(rateClinic.this, R.layout.rate_layout, rates));
                myAdapter = new RateAdapter(rateClinic.this, R.layout.rate_layout, rates );
                list.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(rateClinic.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

// ENDED UP NOT ACTUALLY NEEDING THIS CODE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Gets this data from the Calender activity class
                //data=getIntent();
                assert data != null;
//                String clinicName = data.getStringExtra("");
                String clinicRate = data.getStringExtra("rating");
                String clinicComment = data.getStringExtra("comment");
//                String newTitle = "Review for " + clinicName + ": " + clinicRate;
                String newTitle = "Test, Rating: " + clinicRate + ", Comment: " + clinicComment;
//                int slots=Integer.valueOf(data.getStringExtra("slots"));
                //Toast.makeText(this,slots,Toast.LENGTH_SHORT).show();


                Rate newRate = new Rate();
                newRate.setClinicName("testing");
                newRate.setRating(clinicRate);
                newRate.setComments(clinicComment);

                mDatabase.child("Users").child(mUser.getUid()).child("Rate").child(newTitle).setValue(newRate);
                refreshList();

            }


        }
    }



}
