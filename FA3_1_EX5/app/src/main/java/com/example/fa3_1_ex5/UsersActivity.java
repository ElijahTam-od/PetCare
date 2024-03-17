package com.example.fa3_1_ex5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fa3_1_ex5.UserListView.OnUserSelectedListener;
import com.example.fa3_1_ex5.UserListView.UserListViewAdapter;
import com.example.fa3_1_ex5.model.User;
import com.example.fa3_1_ex5.retrofit.RetrofitService;
import com.example.fa3_1_ex5.retrofit.UserAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends AppCompatActivity implements OnUserSelectedListener {
    RetrofitService retrofitService=new RetrofitService();
    UserAPI UserAPI = retrofitService.getRetrofit().create(UserAPI.class);

    DialogBoxTools dialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        initializeComponents();
    }

    private void initializeComponents(){
        dialogBox = new DialogBoxTools(this);

        List<User> users = new ArrayList<User>();

        RecyclerView recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserListViewAdapter adapter = new UserListViewAdapter(this, users, this);
        recyclerView.setAdapter(adapter);

        //Load users from the server
        UserAPI.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    users.addAll(response.body());
                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                } else {
                    dialogBox.createDialogBox("Error", "Failed to fetch users");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                dialogBox.createDialogBox("Error", "Network Error");
            }
        });



        //back btn
        ImageView back_btn = findViewById(R.id.userListBackBtn);
        back_btn.setOnClickListener(View ->{
            finish();
        });
    }


    @Override
    public void onUserSelected(User selectedUser) {
        // pass the selectedUser to the EditDogActivity
        // and update the owner field in EditDogActivity
        Toast.makeText(this, "Selected User: " + selectedUser.getUsername(), Toast.LENGTH_SHORT).show();

        // Example: pass the user data back to EditDogActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("SELECTED_USER", selectedUser);
        setResult(RESULT_OK, resultIntent);

        // Finish the current activity
        finish();
    }
}