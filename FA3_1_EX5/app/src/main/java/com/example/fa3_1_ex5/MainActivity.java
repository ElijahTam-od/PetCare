package com.example.fa3_1_ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fa3_1_ex5.model.User;
import com.example.fa3_1_ex5.retrofit.RetrofitService;
import com.example.fa3_1_ex5.retrofit.UserAPI;
import com.example.fa3_1_ex5.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    DialogBoxTools dialogBoxTools = new DialogBoxTools(this);
    RetrofitService retrofitService=new RetrofitService();
    UserAPI UserAPI = retrofitService.getRetrofit().create(UserAPI.class);

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "App Started!", Toast.LENGTH_SHORT).show();
        initializeComponents();

    }

    private void initializeComponents(){

        sessionManager = new SessionManager(this);
        EditText input_email = findViewById(R.id.loginEmail);
        EditText input_password = findViewById(R.id.loginPassword);
        ImageView imageBtnLogin = findViewById(R.id.imageBtnLogin);
        TextView textView_Register = findViewById(R.id.textViewRegister);

        //Login
        imageBtnLogin.setOnClickListener(view ->{
            //Toast.makeText(this, "imageBtnLogin Clicked", Toast.LENGTH_SHORT).show();
            String email = String.valueOf(input_email.getText());
            String password = String.valueOf(input_password.getText());

            UserAPI.getUserByEmailAndPassword(email, password).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User currentUser = response.body();

                    if(currentUser != null){
                        //Create shared preferences to access user in multiple activities
                        sessionManager.createLoginSession(currentUser.getId(),currentUser.getEmail(),currentUser.getUsername(),currentUser.getPassword(),currentUser.getNumber(),currentUser.getIsAdmin());

                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }else {
                        dialogBoxTools.createDialogBox("Error", "User not found.");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    dialogBoxTools.createDialogBox("Error","User not found.");
                }
            });
        });

        //Register
        textView_Register.setOnClickListener(View ->{
            //Toast.makeText(this, "textView_Register Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    };
}
