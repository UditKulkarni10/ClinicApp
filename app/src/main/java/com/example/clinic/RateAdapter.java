package com.example.clinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RateAdapter extends ArrayAdapter<Rate>{
    private List<Rate> rateList;

    public RateAdapter(Context context, int resource, List<Rate> rateList){
        super(context, resource);
        this.rateList = rateList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Rate rate = rateList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rate_layout, parent, false);
        }
        TextView clinicname = (TextView) convertView.findViewById(R.id.clinicname);
        TextView clinicrating = (TextView) convertView.findViewById(R.id.clinicrating);
        TextView cliniccomments = (TextView) convertView.findViewById(R.id.cliniccomments);

        clinicname.setText(rate.getClinicName());
        clinicrating.setText(rate.getRating());
        cliniccomments.setText(rate.getComments());
        return convertView;
    }

    @Override
    public int getCount() {
        return rateList.size();
    }

}
