package com.WMS1.drawful.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Singleton class to access the sharedprefrences.
 */
public class SharedPrefrencesManager {

    private static SharedPrefrencesManager instance;
    public static final String PREF_NAME = "app_settings";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    /**
     * Returns a sharedprefrencesmanager instance, creates a new instance if it hasn't been called yet.
     *
     * @param context application context
     * @return a sharedprefrencesmanager instance
     */
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

    /**
     * Sets the jwt token in the sharedprefrences.
     *
     * @param token token to set
     */
    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }

    /**
     * Returns the jwt token.
     *
     * @return the jwt token
     */
    public String getToken() {
        return preferences.getString("token", "");
    }

    /**
     * Sets the refresh token in the sharedprefrences.
     *
     * @param token token to set
     */
    public void setRefresh(String token) {
        editor.putString("refresh", token);
        editor.commit();
    }

    /**
     * Returns the refresh token.
     *
     * @return the refresh token
     */
    public String getRefresh() {
        return preferences.getString("refresh", "");
    }

    /**
     * Sets the joincode in the sharedprefrences.
     *
     * @param code code to set
     */
    public void setJoinCode(String code) {
        editor.putString("join_code", code);
        editor.commit();
    }

    /**
     * Returns the join code.
     *
     * @return the join code
     */
    public String getJoinCode() {
        return preferences.getString("join_code", "");
    }

    /**
     * Sets the game id in the sharedprefrences.
     *
     * @param id id to set
     */
    public void setGameId(String id) {
        editor.putString("game_id", id);
        editor.commit();
    }

    /**
     * Returns the game id.
     *
     * @return the game id
     */
    public String getGameid() {
        return preferences.getString("game_id", "");
    }

    /**
     * Sets the user id in the sharedprefrences.
     *
     * @param userId id to set
     */
    public void setUserId(String userId) {
        editor.putString("user_id", userId);
        editor.commit();
    }

    /**
     * Returns the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return preferences.getString("user_id", "");
    }
}
