package com.WMS1.drawful.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrencesManager {

    private static SharedPrefrencesManager instance;
    public static final String PREF_NAME = "app_settings";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static SharedPrefrencesManager getInstance(Context context) {
        if(instance == null)
            instance = new SharedPrefrencesManager(context.getApplicationContext());
        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    private SharedPrefrencesManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }

    public String getToken() {
        return preferences.getString("token", "");
    }

    public void setRefresh(String token) {
        editor.putString("refresh", token);
        editor.commit();
    }

    public String getRefresh() {
        return preferences.getString("refresh", "");
    }

    public void setJoinCode(String code) {
        editor.putString("join_code", code);
        editor.commit();
    }

    public String getJoinCode() {
        return preferences.getString("join_code", "");
    }

    public void setGameId(String id) {
        editor.putString("game_id", id);
        editor.commit();
    }

    public String getGameid() {
        return preferences.getString("game_id", "");
    }

    public void setUserId(String userId) {
        editor.putString("user_id", userId);
        editor.commit();
    }

    public String getUserId() {
        return preferences.getString("user_id", "");
    }
}
