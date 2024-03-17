package com.example.fa3_1_ex5.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private static final String KEY_NUMBER = "number";
    private static final String KEY_IS_ADMIN = "isAdmin";

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void createLoginSession(int userId, String email, String username, String password,String number,String isAdmin) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_IS_ADMIN, isAdmin);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_NUMBER, number);
        editor.apply();
    }

    public boolean isLoggedIn() {
        String userEmail = preferences.getString(KEY_EMAIL, null);
        return userEmail != null && !userEmail.isEmpty();
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    public String getUsername() {
        return preferences.getString(KEY_USERNAME, null);
    }

    public String isAdmin() {
        return preferences.getString(KEY_IS_ADMIN, null);
    }

    public String getPassword(){return preferences.getString(KEY_PASSWORD,null);}

    public String getNumber(){return preferences.getString(KEY_NUMBER,null);}

    public int getUserId() {
        return preferences.getInt(KEY_USER_ID, 0);
    }
}
