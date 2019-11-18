package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class createLogin extends AppCompatActivity {
    private EditText firstName,lastName, username, pwd, confpwd;
    private Button create;
    private RadioGroup role;
    private String rolVal;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);
        create = (Button) findViewById(R.id.create);
        mAuth =FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        setUpUIviews();

    }

    private void setUpUIviews(){
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        role = (RadioGroup) findViewById(R.id.userRole);
        username = (EditText)findViewById(R.id.username);
        pwd = (EditText)findViewById(R.id.pwd);
        confpwd = (EditText)findViewById(R.id.confpwd);
    }

    public void onClick(View view)
    {
        switch (view.getId()) {

            case R.id.create:
                if(validate()) {
                    String nameVal = firstName.getText().toString();
                    String lastNameVal = lastName.getText().toString();
                    String usernameVal = username.getText().toString();
                    String pwdVal = pwd.getText().toString();
                    if (rolVal.equals("employee")) {
                        final Employee mEmployee = new Employee(nameVal, lastNameVal, usernameVal);
                        mAuth.createUserWithEmailAndPassword(usernameVal, pwdVal).addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).setValue(mEmployee).addOnCompleteListener(task1 -> {
                                    if (task.isSuccessful()) {
                                        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("role").setValue(rolVal);

                                        Toast.makeText(createLogin.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(this, Login.class));
                                    } else {
                                        Toast.makeText(createLogin.this, "Firebase Database error", Toast.LENGTH_SHORT).show();
                                    }

                                });
                            } else {
                                Toast.makeText(createLogin.this, "Firebase Authentication Error", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else if(rolVal.equals("patient")){
                        final Patient mPatient = new Patient(nameVal, lastNameVal, usernameVal);
                        mAuth.createUserWithEmailAndPassword(usernameVal, pwdVal).addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).setValue(mPatient).addOnCompleteListener(task1 -> {
                                    if (task.isSuccessful()) {
                                        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("role").setValue(rolVal);
                                        Toast.makeText(createLogin.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(this, Login.class));
                                    } else {
                                        Toast.makeText(createLogin.this, "Firebase Database error", Toast.LENGTH_SHORT).show();
                                    }

                                });
                            } else {
                                Toast.makeText(createLogin.this, "Firebase Authentication Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        }
    }
    public void onRadioButtonClicked(View view) {
        // checks to see is the radio button is already checked
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.patientBtn:
                if (checked){
                    rolVal="patient";
                    //Toast.makeText(createLogin.this, "Patient", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.employeeBtn:
                if (checked){
                    rolVal="employee";
                    //Toast.makeText(createLogin.this, "Employee", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }



    private Boolean validate(){


        String nameVal = firstName.getText().toString();
        String lastNameVal=lastName.getText().toString();
        String usernameVal = username.getText().toString();
        String pwdVal = pwd.getText().toString();
        String confpwdVal = confpwd.getText().toString();

        if (nameVal.isEmpty() || lastNameVal.isEmpty()|| rolVal==null||usernameVal.isEmpty() || pwdVal.isEmpty() || confpwdVal.isEmpty()){
            Toast.makeText(createLogin.this, "You are missing some information", Toast.LENGTH_SHORT).show();
            return false;
            }

        else if(!pwdVal.equals(confpwdVal)){
            Toast.makeText(createLogin.this, "Your Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
            }

        else if(pwdVal.length()<6){
            Toast.makeText(createLogin.this, "This password is too short", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}
