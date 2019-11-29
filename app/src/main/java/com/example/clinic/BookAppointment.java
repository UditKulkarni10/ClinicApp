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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookAppointment extends AppCompatActivity {

    RadioButton serviceBtn,addressBtn,workHoursBtn;
    EditText searchText;
    TextInputLayout searchTextLayout;
    private ArrayList<Clinic> clinicChoice;
    private ListView bookingOptions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        searchText=findViewById(R.id.searchText);
        searchTextLayout=findViewById(R.id.searchTextLayout);
        searchText.setHint("Address");
        searchTextLayout.setHint("Address");
        clinicChoice=new ArrayList<Clinic>();
        bookingOptions = findViewById(R.id.serviceBookingListView);
        serviceBtn=findViewById(R.id.serviceChoice);
        addressBtn=findViewById(R.id.addressChoice);
        workHoursBtn=findViewById(R.id.workHourChoice);


    }

    public void getSearchResults(String search){
        FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();
        if(addressBtn.isChecked()){
           // Toast.makeText(this,"Search: "+search,Toast.LENGTH_SHORT).show();

            mFirebaseInstance.getReference("Users").getRef().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //Toast.makeText(AccountsManagement.this,"Role: "+postSnapshot.child("role"),Toast.LENGTH_SHORT).show();
                        if (postSnapshot.child("Address").getValue() != null) {
                        //    Toast.makeText(BookAppointment.this,postSnapshot.child("Address").getValue().toString(),Toast.LENGTH_SHORT).show();
                            if (postSnapshot.child("Address").getValue().toString().toLowerCase().contains(search) ) {

                                Clinic clinic = new Clinic(postSnapshot.getValue().toString(), postSnapshot.child("Clinic Name").getValue().toString(), postSnapshot.child("Address").getValue().toString(), postSnapshot.child("Phone Number").getValue().toString());
                               // Toast.makeText(BookAppointment.this,"Clinic found",Toast.LENGTH_SHORT).show();
                                clinicChoice.add(clinic);
                            }

                        }
                    }
                    if(clinicChoice.size()==0){
                        Toast.makeText(BookAppointment.this,"No Results Found",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(BookAppointment.this,"Found "+clinicChoice.size()+" Result(s)",Toast.LENGTH_SHORT).show();
                    }
                    bookingOptions.setAdapter(new BookAppointment.AccListAdapter(BookAppointment.this,R.layout.clinic_booking_layout,clinicChoice));

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(BookAppointment.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            });
        }

        else if(workHoursBtn.isChecked()){

        }
        else if (serviceBtn.isChecked()){
            mFirebaseInstance.getReference("Users").getRef().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //Toast.makeText(AccountsManagement.this,"Role: "+postSnapshot.child("role"),Toast.LENGTH_SHORT).show();
                        if (postSnapshot.child("Services").getValue() != null) {
                            //Toast.makeText(BookAppointment.this,"I am not null",Toast.LENGTH_SHORT).show();
                            postSnapshot.child("Services").getRef().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                    for(DataSnapshot postSnapshot2: dataSnapshot2.getChildren()){
                                        //Toast.makeText(BookAppointment.this,postSnapshot2.child("name").getValue().toString(),Toast.LENGTH_SHORT).show();
                                        if(postSnapshot2.child("name").getValue()!=null) {
                                           // Toast.makeText(BookAppointment.this,"User "+postSnapshot.getKey(),Toast.LENGTH_SHORT).show();
                                            if (postSnapshot2.child("name").getValue().toString().toLowerCase().contains(search)) {
                                                //Toast.makeText(BookAppointment.this,"Adding service",Toast.LENGTH_SHORT).show();
                                                Clinic clinic = new Clinic(postSnapshot.getKey(), postSnapshot.child("Clinic Name").getValue().toString(), postSnapshot.child("Address").getValue().toString(), postSnapshot.child("Phone Number").getValue().toString());
                                                // Toast.makeText(BookAppointment.this,"Clinic found",Toast.LENGTH_SHORT).show();
                                                clinicChoice.add(clinic);
                                                bookingOptions.setAdapter(new BookAppointment.AccListAdapter(BookAppointment.this,R.layout.clinic_booking_layout,clinicChoice));
                                            }
                                        }
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            //    Toast.makeText(BookAppointment.this,postSnapshot.child("Address").getValue().toString(),Toast.LENGTH_SHORT).show();

                        }
                    }



                    //bookingOptions.setAdapter(new BookAppointment.AccListAdapter(BookAppointment.this,R.layout.clinic_booking_layout,clinicChoice));

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(BookAppointment.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            });
            /*
            if(clinicChoice.size()==0){
                Toast.makeText(BookAppointment.this,"No Results Found",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(BookAppointment.this,"Found "+clinicChoice.size()+" Result(s)",Toast.LENGTH_SHORT).show();
            }
            
             */

        }
        else{
            //something went wrong toast
        }
    }

    private class AccListAdapter extends ArrayAdapter<Clinic> {
        private int layout;
        public AccListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Clinic> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            BookAppointment.AccViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            BookAppointment.AccViewHolder viewHolder= new BookAppointment.AccViewHolder();
            //viewHolder.deleteButton = convertView.findViewById(R.id.accountDeleteBtn);
            viewHolder.address = convertView.findViewById(R.id.addressBook);
            viewHolder.phoneNumber = convertView.findViewById(R.id.phoneNumberBook);
            viewHolder.clinicName = convertView.findViewById(R.id.clinicNameBook);
            viewHolder.bookButton = convertView.findViewById(R.id.bookBtn);
            viewHolder.address.setText(getItem(position).getAddress());
            viewHolder.phoneNumber.setText(getItem(position).getPhoneNumber());
            viewHolder.clinicName.setText(getItem(position).getClinicName());
            viewHolder.bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(BookAppointment.this,"UID: "+getItem(position).getUid(),Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(BookAppointment.this,BookServiceChoice.class);
                    i.putExtra("chosenClinic",getItem(position));
                    startActivity(i);


                }
            });
            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (BookAppointment.AccViewHolder) convertView.getTag();
                viewHolder.address.setText(getItem(position).getAddress());
                viewHolder.phoneNumber.setText(getItem(position).getPhoneNumber());
                viewHolder.clinicName.setText(getItem(position).getClinicName());

            }
            return convertView;
        }
    }

    public class AccViewHolder{
        TextView clinicName;
        TextView phoneNumber;
        TextView address;
        Button bookButton;

    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.searchClinic:
                clinicChoice=new ArrayList<Clinic>();
                bookingOptions.setAdapter(new BookAppointment.AccListAdapter(BookAppointment.this,R.layout.clinic_booking_layout,clinicChoice));
                String search=searchText.getText().toString();
                if(!search.isEmpty()){
                    getSearchResults(search);
                }
                else{
                    Toast.makeText(this,"Please enter something in the search field",Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }

    public void onRadioButtonClicked(View view) {
        // checks to see is the radio button is already checked
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.addressChoice:
                if (checked){

                    searchText.setHint("Address");
                    searchTextLayout.setHint("Address");

                }
                break;
            case R.id.workHourChoice:
                if (checked){

                    searchText.setHint("YYYY-MM-DD or HH:MM (Military Time");
                    searchTextLayout.setHint("YYYY-MM-DD or HH:MM (Military Time)");

                }
                break;
            case R.id.serviceChoice:
                if (checked){

                    searchText.setHint("Service Name");
                    searchTextLayout.setHint("Service Name");

                }
                break;


        }
    }
}
