package com.example.fa3_1_ex5.UserListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fa3_1_ex5.R;
import com.example.fa3_1_ex5.model.User;

import java.util.List;

public class UserListViewAdapter extends RecyclerView.Adapter<UserListViewHolder>{
    Context context;
    List<User> users;

    OnUserSelectedListener onUserSelectedListener;

    public UserListViewAdapter(Context context, List<User> users, OnUserSelectedListener listener) {
        this.context = context;
        this.users = users;
        this.onUserSelectedListener = listener;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserListViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        //Load data to view
        holder.user_username.setText(users.get(position).getUsername());
        holder.user_email.setText(users.get(position).getEmail());
        holder.user_number.setText(users.get(position).getNumber());

        holder.layout.setOnClickListener(View ->{
            //1.Retrieve User
            User selectedUser = users.get(position);
            //2. Notify the listener
            onUserSelectedListener.onUserSelected(selectedUser);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
