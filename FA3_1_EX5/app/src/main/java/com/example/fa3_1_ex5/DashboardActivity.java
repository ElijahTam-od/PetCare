package com.example.fa3_1_ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.fa3_1_ex5.session.SessionManager;

public class DashboardActivity extends AppCompatActivity {
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeComponents();
    }

    private void initializeComponents(){
        session = new SessionManager(this);
        LinearLayout logoutBtn = findViewById(R.id.layoutLogout);
        LinearLayout profileBtn = findViewById(R.id.layoutProfile);
        LinearLayout dogsBtn = findViewById(R.id.layoutDogs);
        LinearLayout helpBtn = findViewById(R.id.layoutHelp);
        LinearLayout aboutUsBtn = findViewById(R.id.layoutAboutUs);

        //Profile
        profileBtn.setOnClickListener(View ->{
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        //Logout
        logoutBtn.setOnClickListener(View ->{
            // Create an AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");

            // Set positive button and its action
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked Yes button
                    // Set current login user to none
                    session.logout();

                    // Finish the current activity to remove it from the back stack
                    finish();
                }
            });

            // Set negative button and its action
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked No button
                    // Dismiss the dialog (do nothing)
                    dialog.dismiss();
                }
            });

            // Create and show the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        //Dogs
        dogsBtn.setOnClickListener(View ->{

            Intent intent = new Intent(DashboardActivity.this, DogActivity.class);
            startActivity(intent);
        });

        //Help
        helpBtn.setOnClickListener(View ->{
            Intent intent = new Intent(DashboardActivity.this, HelpActivity.class);
            startActivity(intent);
        });

        //About us
        aboutUsBtn.setOnClickListener(View ->{
            Intent intent = new Intent(DashboardActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });
    }
}