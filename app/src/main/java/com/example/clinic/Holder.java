package com.example.clinic;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {

    ImageView imView;
    TextView title, desc;


    public Holder(@NonNull View itemView) {
        super(itemView);
        this.imView = itemView.findViewById(R.id.imageIv);
        this.title = itemView.findViewById(R.id.textView4);
        this.desc = itemView.findViewById(R.id.textView5);

    }
}
