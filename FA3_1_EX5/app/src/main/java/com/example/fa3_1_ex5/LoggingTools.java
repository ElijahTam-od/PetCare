package com.example.fa3_1_ex5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class LoggingTools {
    Context context;
    public LoggingTools(Context context){
        this.context = context;
    }

    //Shows dialog box containing all information about properties
    public void registerUserDetails(String username, String email, String password, String number) {
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the title and message for the dialog
        builder.setTitle("Register User")
                .setMessage("Name: " + username +"\nEmail: " + email + "\nPassword: " + password +"\nNumber: " + number);

        // Add buttons to the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
