package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomePage extends AppCompatActivity {


    private TextView welcomeText;
    private String name;
    private String role;
    private String username;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        welcomeText = findViewById(R.id.welcomeText);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        //welcomeText.setText("");
        setWelcomeText();
        //startActivity(new Intent(this, Services.class));

    }

    public void setWelcomeText() {
        if (Login.getIsAdmin()) {
            welcomeText.setText("Welcome admin, you are logged in as admin!!");
            finish();
            startActivity(new Intent(this, AdminControlPanel.class));
        } else {
            //Case where if there is no user logged in for some reason
            if (mUser == null) {
                finish();
                startActivity(new Intent(this, Login.class));
            } else {
                mDatabase.child("Users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("role").getValue().equals("patient")){
                            Patient patient = dataSnapshot.getValue(Patient.class);
                            name = patient.getName();
                            role = patient.getRole();
                            username = patient.getUsername();
                            welcomeText.setText("Welcome " + name + ", you are logged in as " + role + "!");
                            finish();
                            startActivity(new Intent(WelcomePage.this, Services.class));
                            //finish();
                        }
                        else if(dataSnapshot.child("role").getValue().equals("employee")){
                            Employee employee = dataSnapshot.getValue(Employee.class);
                            name = employee.getName();
                            role = employee.getRole();
                            username = employee.getUsername();
                            welcomeText.setText("Welcome " + name + ", you are logged in as " + role + "!");
                            finish();
                            startActivity(new Intent(WelcomePage.this, EmployeeMainScreen.class));
                            //finish();
                        }
                        //User user = dataSnapshot.getValue(User.class);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //this auto generates not sure what to put here
                    }
                });
            }

        }
    }
}
