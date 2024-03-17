package com.example.fa3_1_ex5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogBoxTools {
    Context context;
    public DialogBoxTools(Context context){
        this.context = context;
    }

    //Shows dialog box containing all information about properties
    public void confirmPassword(String password, String confirmPassword) {
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the title and message for the dialog
        builder.setTitle("Incorrect Password")
                .setMessage("Passwords do not match. Please try again.");

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

    public void createDialogBox(String title, String message){
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the title and message for the dialog
        builder.setTitle(title)
                .setMessage(message);

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
