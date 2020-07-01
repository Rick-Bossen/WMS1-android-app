package com.WMS1.drawful.requests;

import android.content.Context;
import android.widget.Toast;

import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class JwtJsonObjectRequest extends JsonObjectRequest {

    private Context context;

    public JwtJsonObjectRequest(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable final Response.ErrorListener errorListener, final Context context) {
        super(method, url, jsonRequest, listener, error -> handleError(error, context, errorListener));
        this.context = context;
    }
    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> params = new HashMap<>();
        String token = SharedPrefrencesManager.getInstance(context).getToken();
        params.put("Authorization", "Bearer "+ token);
        return params;
    }

    /**
     * Handles an error by checking it's code.
     * Shows an error message on a 422 error, tries to refresh on a 401 error.
     *
     * @param error error to handle
     * @param context application context
     * @param errorListener errorlistener to use if error doesn't match codes
     */
    private static void handleError(VolleyError error, Context context, Response.ErrorListener errorListener){
        if (error.networkResponse.statusCode == 422) {
            Toast toast = Toast.makeText(context, "Please log in again", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (error.networkResponse.statusCode == 401) {

            RefreshJsonObjectRequest refreshjsonObjectRequest = new RefreshJsonObjectRequest(errorListener, context);
            RequestQueueSingleton.getInstance(context).addToRequestQueue(refreshjsonObjectRequest);


        } else if (errorListener != null) {
            errorListener.onErrorResponse(error);
        }
    }
}