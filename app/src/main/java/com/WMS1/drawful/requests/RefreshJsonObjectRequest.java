package com.WMS1.drawful.requests;

import android.content.Context;

import androidx.annotation.Nullable;

import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RefreshJsonObjectRequest extends JsonObjectRequest {
    Context context;

    public RefreshJsonObjectRequest(@Nullable Response.ErrorListener errorListener, Context context) {
        super(Request.Method.POST, RequestQueueSingleton.BASE_URL + "/user/refresh", null,
                response -> handleResponse(response, context), error -> handleError(errorListener, error));
        this.context = context;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> params = new HashMap<>();
        String token = SharedPrefrencesManager.getInstance(context).getRefresh();
        params.put("Authorization", "Bearer " + token);
        return params;
    }

    /**
     * Adds the new jwt token to the sharedprefrences.
     *
     * @param response the response to handle
     * @param context application context
     */
    private static void handleResponse(JSONObject response, Context context){
        try {
            SharedPrefrencesManager.getInstance(context).setToken(response.getString("access_token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles an error.
     *
     * @param errorListener the errorlistener to use
     * @param error the error to handle
     */
    private static void handleError(Response.ErrorListener errorListener, VolleyError error){
        if (errorListener != null){
            errorListener.onErrorResponse(error);
        }
    }
}
