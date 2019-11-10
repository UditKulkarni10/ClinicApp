package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Services extends AppCompatActivity {

    private ArrayList<Service> serviceList;
    private ArrayAdapter<Service> adapter;
    //private EditText addText;
    //private Button addBtn;
    private ListView list;
    private DatabaseReference mDatabase;
    private EditText serviceName,serviceRole;
   //private String service;


    //private ImageButton addBtnImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        serviceName = findViewById(R.id.serviceNameSetText);
        serviceRole = findViewById(R.id.serviceRoleSetText);
    }
    /*
    private class ServiceListAdapter extends ArrayAdapter<Service>{
        private int layout;
        public ServiceListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Service> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            Services.ServicesViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
            }
            Services.ServicesViewHolder viewHolder= new Services.ServicesViewHolder();
            viewHolder.deleteButton = convertView.findViewById(R.id.serviceDeleteBtn);
            viewHolder.editButton = convertView.findViewById(R.id.serviceEditBtn);
            viewHolder.name = convertView.findViewById(R.id.serviceName);
            viewHolder.role = convertView.findViewById(R.id.serviceRole);
            viewHolder.name.setText(getItem(position).getName());
            viewHolder.role.setText(getItem(position).getRole());
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(AccountsManagement.this,"Implement this",Toast.LENGTH_SHORT).show();
                    //FirebaseAuth.getInstance().


                }
            });
            convertView.setTag(viewHolder);

            if(convertView !=null){
                mainViewHolder = (Services.ServicesViewHolder) convertView.getTag();
                mainViewHolder.name.setText(getItem(position).getName());
                mainViewHolder.role.setText(getItem(position).getRole());
            }
            return convertView;
        }
    }

    public class ServicesViewHolder{
        TextView name;
        TextView role;
        Button deleteButton;
        Button editButton;

    }

     */


    public void onClick(View view){
        switch(view.getId()){
            case R.id.addBtn:
                String name= serviceName.getText().toString();
                String role= serviceRole.getText().toString();
                final Service mService = new Service(name,role);
                mDatabase.child("Services").child(name).setValue(mService).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(Services.this, "Service Added!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(this, Services.class));
                    }
                    else{
                        Toast.makeText(Services.this, "Firebase Database error", Toast.LENGTH_SHORT).show();
                    }

                });
        }
    }







        /*
        list = (ListView) findViewById(R.id.list);
        addText = (EditText) findViewById(R.id.serviceNmaeSetText);
        addBtn = (Button) findViewById(R.id.addBtn);

        serviceList = new ArrayList<>();
        adapter =  new ArrayAdapter<String>(Services.this, android.R.layout.simple_list_item_multiple_choice, serviceList);

        View.OnClickListener addListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(addText.getText().toString().isEmpty()){
                    Toast.makeText(Services.this,"Please enter a service", Toast.LENGTH_SHORT).show();
                }
                else {
                    serviceList.add(addText.getText().toString());
                    addText.setText("");
                    adapter.notifyDataSetChanged();
                }

            }
        };

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                SparseBooleanArray positioncheker = list.getCheckedItemPositions();

                int count = list.getCount();

                for(int service = count-1; service>=0; service--){

                    if(positioncheker.get(service)){
                        adapter.remove(serviceList.get(service));

                        Toast.makeText(Services.this, "Service Deleted", Toast.LENGTH_SHORT).show();
                    }

                }

                positioncheker.clear();

                adapter.notifyDataSetChanged();

                return false;
            }
        });

        addBtn.setOnClickListener(addListner);

        list.setAdapter(adapter);
    }

    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_home);

        addBtnImg = (ImageButton) findViewById(R.id.addBtnImg);

        addBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Services.this, ServiceInfo.class);
                startActivity(intent);
            }
        });

    }*/



}
