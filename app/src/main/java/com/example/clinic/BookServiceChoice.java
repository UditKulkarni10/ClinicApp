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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookServiceChoice extends AppCompatActivity {
    private ListView serviceListView;
    private ArrayList<Service> serviceList;
    Clinic clinic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service_choice);
        serviceListView=findViewById(R.id.bookServiceListView);
        serviceList=new ArrayList<>();
        Intent i =getIntent();
        clinic = (Clinic) i.getSerializableExtra("chosenClinic");
        getServiceList();

    }


    private class BookServiceListAdapter extends ArrayAdapter<Service> {
        private int layout;
        public BookServiceListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Service> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            BookServiceChoice.BookServicesViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            BookServiceChoice.BookServicesViewHolder viewHolder= new BookServiceChoice.BookServicesViewHolder();
            viewHolder.bookButton = convertView.findViewById(R.id.serviceBookBtn);
            viewHolder.name = convertView.findViewById(R.id.bookServiceName);
            viewHolder.role = convertView.findViewById(R.id.bookServiceRole);
            viewHolder.name.setText((getItem(position)).getName());
            viewHolder.role.setText((getItem(position)).getRole());
            viewHolder.bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =new Intent(BookServiceChoice.this,BookWorkHourChoice.class);
                    i.putExtra("chosenService",getItem(position));
                    i.putExtra("chosenClinic",clinic);
                    startActivity(i);



                }
            });

            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (BookServiceChoice.BookServicesViewHolder) convertView.getTag();
                mainViewHolder.name.setText(getItem(position).getName());
                mainViewHolder.role.setText(getItem(position).getRole());
            }
            return convertView;
        }
    }

    public class BookServicesViewHolder{
        TextView name;
        TextView role;
        Button bookButton;


    }
    public void getServiceList() {
        FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("Users").child(clinic.getUid()).child("Services").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    //Toast.makeText(BookServiceChoice.this,"Added: "+service.getName(),Toast.LENGTH_SHORT).show();
                    serviceList.add(service);
                }
                serviceListView.setAdapter(new BookServiceListAdapter(BookServiceChoice.this, R.layout.book_service_layout, serviceList));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(BookServiceChoice.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
