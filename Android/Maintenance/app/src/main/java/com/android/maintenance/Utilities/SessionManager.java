package com.android.maintenance.Utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.maintenance.activities.Login;

import java.util.HashMap;

/**
 * Created by anand on 20-Sep-16.
 */
public class SessionManager {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;
    private static final String PREF_NAME = "Maintenance";
    private static final String IS_LOGIN = "IsLoggedIn";
   // public static final String KEY_USER_ID ="user_id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_PHONE_NO = "phone";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ROLE = "role";

    public static final String KEY_TOKEN = "token";


    public SessionManager(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(Long user_id, String user_name, String first_name, String last_name, String phone,String gender,String role, String token){
        // Storing login value as TRUE
        Log.e("","Inside createLoginSession");
        editor.putBoolean(IS_LOGIN, true);
        editor.putLong("KEY_USER_ID", user_id);
        editor.putString(KEY_USER_NAME, user_name);
        editor.putString(KEY_FIRST_NAME,first_name);
        editor.putString(KEY_LAST_NAME,last_name);
        editor.putString(KEY_PHONE_NO,phone);
        editor.putString(KEY_GENDER,gender);
        editor.putString(KEY_ROLE,role);
        editor.putString(KEY_TOKEN,token);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(ctx, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            ctx.startActivity(i);
        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        long mVal = prefs.getLong("KEY_USER_ID", 0);
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USER_NAME, prefs.getString(KEY_USER_NAME, null));
        user.put(KEY_FIRST_NAME, prefs.getString(KEY_FIRST_NAME, null));
        user.put(KEY_LAST_NAME, prefs.getString(KEY_LAST_NAME, null));
        user.put(KEY_GENDER, prefs.getString(KEY_GENDER, null));
        user.put(KEY_PHONE_NO, prefs.getString(KEY_PHONE_NO, null));
        user.put("KEY_USER_ID", Long.toString(mVal));
        user.put(KEY_TOKEN,prefs.getString(KEY_TOKEN, null));
        user.put(KEY_ROLE, prefs.getString(KEY_ROLE, null));
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(ctx, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        ctx.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State

    public boolean isLoggedIn(){
        return prefs.getBoolean(IS_LOGIN, false);
    }



}
