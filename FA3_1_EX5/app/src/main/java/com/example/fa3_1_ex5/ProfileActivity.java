package com.example.fa3_1_ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fa3_1_ex5.model.User;
import com.example.fa3_1_ex5.retrofit.RetrofitService;
import com.example.fa3_1_ex5.retrofit.UserAPI;
import com.example.fa3_1_ex5.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    DialogBoxTools dialogBox;

    RetrofitService retrofitService=new RetrofitService();
    UserAPI UserAPI = retrofitService.getRetrofit().create(UserAPI.class);

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeComponents();
    }

    private void initializeComponents(){
        dialogBox = new DialogBoxTools(this);
        session = new SessionManager(this);
        EditText name = findViewById(R.id.profileName);
        EditText email = findViewById(R.id.profileEmail);
        EditText newEmail = findViewById(R.id.profileNewEmail);
        EditText confirmEmail = findViewById(R.id.profileConfirmEmail);
        EditText password = findViewById(R.id.profilePassword);
        EditText confirmPassword = findViewById(R.id.profileConfirmPassword);
        EditText number = findViewById(R.id.profileNumber);

        ImageView submitBtn = findViewById(R.id.imageBtnProfileSubmit);

        //Load current user data into the view
        name.setText(session.getUsername().toString());
        email.setText(session.getEmail().toString());
        //confirmEmail.setText(session.getEmail().toString());
        password.setText(session.getPassword().toString());
        //confirmPassword.setText(session.getPassword().toString());
        number.setText(session.getNumber().toString());

        submitBtn.setOnClickListener(View ->{
            String newEmailString = newEmail.getText().toString();
            String confirmEmailString = confirmEmail.getText().toString();

            String passwordString = password.getText().toString();
            String confirmPasswordString = confirmPassword.getText().toString();

            Boolean pass = true;
            if(!passwordString.equals(confirmPasswordString)){
                dialogBox.createDialogBox("Error", "Password does not match");
                pass = false;
            }

            if(!newEmailString.equals(confirmEmailString)){
                dialogBox.createDialogBox("Error", "Email does not match");
                pass = false;
            }

            if(pass){
                User newUser = new User();
                newUser.setUsername(name.getText().toString());
                newUser.setEmail(newEmailString);
                newUser.setPassword(passwordString);
                newUser.setNumber(number.getText().toString());
                newUser.setId(session.getUserId());

                checkExistingName(newUser);
            }
        });
    }

    private void checkExistingName(User user){
        // Find the current user in the database
        UserAPI.getUserByUsername(user.getUsername()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User foundUser = response.body();

                if (foundUser == null || foundUser.getId() == user.getId()) {
                    // Either the username is not taken or it belongs to the current user
                    // Proceed with checking the email
                    checkExistingEmail(user);
                } else {
                    dialogBox.createDialogBox("Error", "Name is already taken by an existing account.");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialogBox.createDialogBox("Network Error: checkExistingName", "Cannot reach database");
            }
        });
    }

    private void checkExistingEmail(User user){
        // Find the user with the given email in the database
        UserAPI.getUserByEmail(user.getEmail()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User foundUser = response.body();

                    if (foundUser == null || foundUser.getId() == user.getId()) {
                        // Either the email is not taken or it belongs to the current user
                        // Proceed with updating the user
                        updateCurrentUser(user);
                    } else {
                        dialogBox.createDialogBox("Error", "Email is already taken by an existing account.");
                    }
                } else if (response.code() == 404) {
                    // Handle 404 Not Found - User not found by email
                    // You can proceed with updating the user or show a message accordingly
                    updateCurrentUser(user);
                } else {
                    int statusCode = response.code();
                    dialogBox.createDialogBox("Error", "Server returned non-successful status code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialogBox.createDialogBox("Network Error: checkExistingEmail", "Cannot reach database");

            }
        });
    }

    private void updateCurrentUser(User user) {
        UserAPI.updateUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //dialogBox.createDialogBox("Success", "Profile has been updated.");
                Toast.makeText(ProfileActivity.this, "Profile has been updated", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialogBox.createDialogBox("Network Error: updateCurrentUser", "Cannot reach database");
            }
        });
    }
}
