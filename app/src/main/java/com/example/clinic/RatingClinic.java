package com.example.clinic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RatingClinic extends AppCompatActivity {


    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText comments;
    Button addButton;
    String rating;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_clinic);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        Intent i=getIntent();
        Clinic clinic = (Clinic)i.getSerializableExtra("clinicName");
        String ClinicName = clinic.getClinicName();
        comments = (EditText) findViewById(R.id.comment);
        addButton = (Button) findViewById(R.id.addButton);
        radioGroup = (RadioGroup) findViewById(R.id.ratings);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton1:
                        rating = "1";
                        break;
                    case R.id.radioButton2:
                        rating = "2";
                        break;
                    case R.id.radioButton3:
                        rating = "3";
                        break;
                    case R.id.radioButton4:
                        rating = "4";
                        break;
                    case R.id.radioButton5:
                        rating = "5";
                        break;
                }

            }

        });



        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if (comments == null || rating == null) {
                    Toast.makeText(RatingClinic.this, "Please don't leave any fields blank", Toast.LENGTH_SHORT).show();
                } else {
                    //try {

                        Rate newRate = new Rate();



                       String newTitle = "Review for: " + ClinicName;
                        //String newTitle = "Review for: test";

                        newRate.setClinicName(ClinicName);
                        newRate.setRating(rating);
                        newRate.setComments(comments.getText().toString());

                        mDatabase.child("Users").child(mUser.getUid()).child("Rate").child(newTitle).setValue(newRate);






                       startActivity(new Intent(RatingClinic.this,PatientMainScreen.class));

                   // }// catch (RuntimeException e) {
                       // Toast.makeText(RatingClinic.this, "This is taking too long" , Toast.LENGTH_SHORT).show();
                    //}
                }
            }
        });
    }

}
