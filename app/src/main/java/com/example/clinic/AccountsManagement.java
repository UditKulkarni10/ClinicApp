package com.example.clinic;

import android.content.Context;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountsManagement extends AppCompatActivity {
    private ListView accountList;
    private ArrayList userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_management);
        Toast.makeText(AccountsManagement.this,"Getting User Accounts, this may take a second!",Toast.LENGTH_SHORT).show();
        accountList =findViewById(R.id.accountList);
        userList = new ArrayList<>();
        getUserList();

        //Toast.makeText(this,"Got User data?",Toast.LENGTH_SHORT);

    }

    private class AccListAdapter extends ArrayAdapter<User>{
        private int layout;
        public AccListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            AccViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            AccViewHolder viewHolder= new AccViewHolder();
            viewHolder.deleteButton = convertView.findViewById(R.id.accountDeleteBtn);
            viewHolder.name = convertView.findViewById(R.id.accNameTxt);
            viewHolder.role = convertView.findViewById(R.id.accRoleTxt);
            viewHolder.name.setText(getItem(position).getName()+" "+getItem(position).getLastName());
            if(getItem(position) instanceof Patient){
                viewHolder.role.setText("Patient");
            }
            else if(getItem(position) instanceof Employee){
                viewHolder.role.setText("Employee");
            }

            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(AccountsManagement.this,"Implement this",Toast.LENGTH_SHORT).show();
                    //FirebaseAuth.getInstance().


                }
            });
            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (AccViewHolder) convertView.getTag();
                mainViewHolder.name.setText(getItem(position).getName()+" "+getItem(position).getLastName());
                if(getItem(position) instanceof Patient) {
                    mainViewHolder.role.setText("Patient");
                }
                else if(getItem(position)instanceof Employee){
                    mainViewHolder.role.setText("Employee");
                }
            }
            return convertView;
        }
    }

    public class AccViewHolder{
        TextView name;
        TextView role;
        Button deleteButton;

    }

    public void getUserList() {
        FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Users").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Toast.makeText(AccountsManagement.this,"Role: "+postSnapshot.child("role"),Toast.LENGTH_SHORT).show();
                    if(postSnapshot.child("role").getValue().equals("patient")){
                        Patient patient = postSnapshot.getValue(Patient.class);
                        //Toast.makeText(AccountsManagement.this,"Added: "+patient.getUsername(),Toast.LENGTH_SHORT).show();
                        userList.add(patient);
                    }
                    else if(postSnapshot.child("role").getValue().equals("employee")){
                        Employee employee = postSnapshot.getValue(Employee.class);
                        //Toast.makeText(AccountsManagement.this,"Added: "+employee.getUsername(),Toast.LENGTH_SHORT).show();
                        userList.add(employee);
                    }
                    //User user = postSnapshot.getValue(User.class);
                    //Toast.makeText(AccountsManagement.this,"Added: "+user.getUsername(),Toast.LENGTH_SHORT).show();
                    //userList.add(user);
                }
                accountList.setAdapter(new AccListAdapter(AccountsManagement.this,R.layout.account_list_layout,userList));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AccountsManagement.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
