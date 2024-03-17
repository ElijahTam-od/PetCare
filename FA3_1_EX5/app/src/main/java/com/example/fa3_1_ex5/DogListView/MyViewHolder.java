package com.example.fa3_1_ex5.DogListView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fa3_1_ex5.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView dog_picture;
    TextView dog_name;

    ImageButton dog_edit_btn;

    ImageButton dog_delete_btn;

    TextView dog_adoption_status;
    public MyViewHolder(@NonNull View dog_item_view) {
        super(dog_item_view);

        dog_picture = dog_item_view.findViewById(R.id.dogListImage);
        dog_name = dog_item_view.findViewById(R.id.dogListName);
        dog_edit_btn = dog_item_view.findViewById(R.id.dogListEditBtn);
        dog_delete_btn = dog_item_view.findViewById(R.id.dogListDeleteBtn);
        dog_adoption_status = dog_item_view.findViewById(R.id.dogAdoptionStatus);
    }
}
