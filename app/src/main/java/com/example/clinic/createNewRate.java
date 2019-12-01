package com.example.clinic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class createNewRate extends AppCompatActivity {

    private ListView listView;
    private EmployeeAdapter myAdapter;
    private List<Employee> employeesList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rate);
        listView = (ListView) findViewById(R.id.listemployees);

        getEmployeeList();

        myAdapter = new EmployeeAdapter(this, R.layout.reviews_layout, employeesList);
        listView.setAdapter(myAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private class EmployeeListAdapter extends ArrayAdapter<Employee> {
        private int layout;

        public EmployeeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Employee> objects) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            createNewRate.EmployeeViewHolder mainViewHolder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
            }
            createNewRate.EmployeeViewHolder viewHolder = new createNewRate.EmployeeViewHolder();
            viewHolder.rateclinic = convertView.findViewById(R.id.ratethisone);
            viewHolder.clinicname = convertView.findViewById(R.id.reviewname);

            viewHolder.clinicname.setText(getItem(position).getClinicName());

            viewHolder.rateclinic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String ClinicName = getItem(position).getClinicName();

                    Intent i = new Intent(createNewRate.this, Calendar.class);
                    i.putExtra("ClinicName", ClinicName);
                    startActivity(i);

                }
            });
            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (createNewRate.EmployeeViewHolder) convertView.getTag();
                mainViewHolder.clinicname.setText(getItem(position).getClinicName());

            }
            return convertView;
        }
    }

    public void getEmployeeList() {
//            ArrayList<Employee> tempEmployee = new ArrayList<Employee>();
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Users").getRef().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.child("role").getValue() != null) {
                        //Toast.makeText(AccountsManagement.this,"Role: "+postSnapshot.child("role"),Toast.LENGTH_SHORT).show();
                        if (postSnapshot.child("role").getValue().equals("employee")) {
                            //                            Employee employee = postSnapshot.getValue(Employee.class);
                            Employee employee = new Employee(postSnapshot.child("name").getValue().toString(), postSnapshot.child("lastName").getValue().toString(), postSnapshot.child("username").getValue().toString());
                            if (postSnapshot.child("Clinic Name").getValue() != null) {
                                employee.setClinicName(postSnapshot.child("Clinic Name").getValue().toString());
                                employeesList.add(employee);
                                //                                   Log.i("employeees", employeesList.toString());
                            }
                        }
                    }
                }
                myAdapter = new EmployeeAdapter(createNewRate.this, R.layout.reviews_layout, employeesList);
                listView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(createNewRate.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ratethisone:
                startActivity(new Intent(this, RatingClinic.class));
                break;
        }
    }

    public class EmployeeViewHolder {
        TextView clinicname;
        Button rateclinic;
    }

}
