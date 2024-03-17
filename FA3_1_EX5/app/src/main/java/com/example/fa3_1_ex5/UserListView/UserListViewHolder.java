package com.example.fa3_1_ex5.UserListView;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fa3_1_ex5.R;

public class UserListViewHolder extends RecyclerView.ViewHolder{
    TextView user_username, user_email, user_number;

    RelativeLayout layout;
    public UserListViewHolder(@NonNull View itemView) {
        super(itemView);

        user_username = itemView.findViewById(R.id.username);
        user_email = itemView.findViewById(R.id.userEmail);
        user_number = itemView.findViewById(R.id.userNumber);
        layout = itemView.findViewById(R.id.userLayout);
    }

}
