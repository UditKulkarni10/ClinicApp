package com.example.clinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    private List<Employee> employeesList;

    public EmployeeAdapter(Context context,int resource, List<Employee> employeesList){
        super(context, resource);
        this.employeesList = employeesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Employee employee = employeesList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reviews_layout, parent, false);
        }
        TextView employeeName = (TextView) convertView.findViewById(R.id.reviewname);
//        employeeName.setText(employee.getClinicName());
//        employeeName.setText("HELLLOOOOOOOO");
        employeeName.setText(employee.getClinicName());
        return convertView;
    }

    @Override
    public int getCount() {
        return employeesList.size();
    }
}
