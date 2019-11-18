package com.example.clinic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeProfile extends AppCompatActivity {

    private Button editName;
    private Button updateName;
    private Button editAddress;
    private Button updateAddress;
    private Button editPhone;
    private Button updatePhone;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private EditText phoneNumber;
    private EditText address;
    private EditText clinicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        editName = findViewById(R.id.clinicNameEditBtn);
        updateName = findViewById(R.id.clinicNameUpdateBtn);
        editAddress = findViewById(R.id.addressEditBtn);
        updateAddress = findViewById(R.id.addressUpateBtn);
        editPhone = findViewById(R.id.phoneNumberEditBtn);
        updatePhone = findViewById(R.id.phoneNumberUpdateBtn);
        updateName.setVisibility(View.INVISIBLE);
        updateAddress.setVisibility(View.INVISIBLE);
        updatePhone.setVisibility(View.INVISIBLE);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        clinicName=findViewById(R.id.clinicNameText);
        address=findViewById(R.id.addressText);
        phoneNumber=findViewById(R.id.phoneNumberText);
        setText();

    }

    public void setText(){
        if(mDatabase.child("Users").child(mUser.getUid()).child("Clinic Name")!=null){
            mDatabase.child("Users").child(mUser.getUid()).child("Clinic Name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    clinicName.setText((CharSequence) dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if(mDatabase.child("Users").child(mUser.getUid()).child("Address")!=null){
            mDatabase.child("Users").child(mUser.getUid()).child("Address").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    address.setText((CharSequence) dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if(mDatabase.child("Users").child(mUser.getUid()).child("Phone Number")!=null){
            mDatabase.child("Users").child(mUser.getUid()).child("Phone Number").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    phoneNumber.setText((CharSequence) dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void onClick(View view){

        if(view.getId()==R.id.clinicNameEditBtn) {
            editName.setVisibility(View.INVISIBLE);
            updateName.setVisibility(View.VISIBLE);
        }
        if(view.getId()==R.id.clinicNameUpdateBtn) {
            if (!clinicName.getText().toString().isEmpty()) {
                mDatabase.child("Users").child(mUser.getUid()).child("Clinic Name").setValue(clinicName.getText().toString());
                Toast.makeText(this, "Clinic Name changed", Toast.LENGTH_SHORT).show();
                recreate();
            } else {
                Toast.makeText(this, "Please fill our the clinic name field", Toast.LENGTH_SHORT).show();
            }
        }
        if(view.getId()==R.id.addressEditBtn) {
            editAddress.setVisibility(View.INVISIBLE);
            updateAddress.setVisibility(View.VISIBLE);
        }
        if(view.getId()==R.id.addressUpateBtn) {
            if (!address.getText().toString().isEmpty()) {
                mDatabase.child("Users").child(mUser.getUid()).child("Address").setValue(address.getText().toString());
                Toast.makeText(this, "Address changed", Toast.LENGTH_SHORT).show();
                recreate();
            } else {
                Toast.makeText(this, "Please fill our the address field", Toast.LENGTH_SHORT).show();
            }
        }
        if(view.getId()==R.id.phoneNumberEditBtn) {
            editPhone.setVisibility(View.INVISIBLE);
            updatePhone.setVisibility(View.VISIBLE);
        }
        if(view.getId()==R.id.phoneNumberUpdateBtn) {
            if (!phoneNumber.getText().toString().isEmpty()) {
                mDatabase.child("Users").child(mUser.getUid()).child("Phone Number").setValue(phoneNumber.getText().toString());
                Toast.makeText(this, "Phone Number changed", Toast.LENGTH_SHORT).show();
                recreate();
            } else {
                Toast.makeText(this, "Please fill our the phone number field", Toast.LENGTH_SHORT).show();
            }
        }



        }
    }


