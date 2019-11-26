package com.example.clinic;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ModelAdapter extends ArrayAdapter<Model> {

    private List<Model> modelsList;

    public ModelAdapter(Context context, int resource, List<Model> modelsList) {
        super(context, resource);
        this.modelsList = modelsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Model model = modelsList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_view, parent, false);
        }
        TextView modelTitle = (TextView) convertView.findViewById(R.id.title);
        TextView modelDescription = (TextView) convertView.findViewById(R.id.description);
        modelTitle.setText(model.getTitle());
        modelDescription.setText(model.getDescription());
        return convertView;
    }

    @Override
    public int getCount() {
        return modelsList.size();
    }
}


