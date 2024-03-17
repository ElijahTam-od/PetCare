package com.example.fa3_1_ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fa3_1_ex5.model.User;
import com.example.fa3_1_ex5.retrofit.RetrofitService;
import com.example.fa3_1_ex5.retrofit.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    DialogBoxTools dialogBoxTools = new DialogBoxTools(this);
    RetrofitService retrofitService=new RetrofitService();
    UserAPI UserAPI = retrofitService.getRetrofit().create(UserAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeComponents();
    }

    private void initializeComponents() {

        TextView textView_login = findViewById(R.id.textViewLogin);
        ImageView submitBtn = findViewById(R.id.imageBtnRegisterSubmit);
        EditText registerName = findViewById(R.id.registerName);
        EditText registerEmail = findViewById(R.id.registerEmail);
        EditText registerPassword = findViewById(R.id.registerPassword);
        EditText registerConfirmPassword = findViewById(R.id.registerConfirmPassword);
        EditText registerNumber = findViewById(R.id.registerNumber);

        //Submit button
        submitBtn.setOnClickListener(View ->{
            String name = registerName.getText().toString();
            String email = registerEmail.getText().toString();
            String password = registerPassword.getText().toString();
            String confirmPassword = registerConfirmPassword.getText().toString();
            String number = registerNumber.getText().toString();

            if(password.equals(confirmPassword)){
                LoggingTools loggingTools = new LoggingTools(this);
                //loggingTools.registerUserDetails(name, email, password, number);
            }else{
                DialogBoxTools passwordCheck = new DialogBoxTools(this);
                passwordCheck.confirmPassword(password,confirmPassword);
            }

            User user = new User();
            user.setUsername(name);
            user.setPassword(password);
            user.setEmail(email);
            user.setIsAdmin("No");
            user.setNumber(number);

            //Check if there is an existing user with the same name or email
            UserAPI.getUserByUsername(user.getUsername()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    // User with the same name already exists, do not add
                    dialogBoxTools.createDialogBox("Error", "User with the same name already exists!");
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    //Check for email
                    checkEmail(user);
                }
            });
        });



        textView_login.setOnClickListener(View ->{
            // Simply finish the current activity to navigate back to the previous activity on the stack
            finish();
        });
    }

    private void checkEmail(User user){
        UserAPI.getUserByEmail(user.getEmail()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // User with the same EMAIL already exists, do not add
                    dialogBoxTools.createDialogBox("Error", "User with the same email already exists!");
                } else if (response.code() == 404) {
                    // No existing user with the same email, add the user
                    addUser(user);
                } else {
                    dialogBoxTools.createDialogBox("Error", "Something went wrong. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialogBoxTools.createDialogBox("Network Error", "Something went wrong. Please try again later.");
            }
        });
    }


    private void addUser(User user){
        UserAPI.add(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialogBoxTools.createDialogBox("Error", "Unable to save!");
            }
        });
    }

}