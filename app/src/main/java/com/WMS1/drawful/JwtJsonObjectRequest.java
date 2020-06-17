package com.WMS1.drawful;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
        super(method, url, jsonRequest, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 422) {
                    System.out.println(SharedPrefrencesManager.getInstance(context).getToken());
                    Toast toast = Toast.makeText(context, "Please log in", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (error.networkResponse.statusCode == 401) {
                    RefreshJsonObjectRequest refreshjsonObjectRequest = new RefreshJsonObjectRequest
                            (Request.Method.GET, RequestQueueSingleton.BASE_URL + "/user/refresh", null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast toast = Toast.makeText(context, " Refreshed: " + response.toString(), Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    assert errorListener != null;
                                    errorListener.onErrorResponse(error);
                                }
                            }, context);
                    RequestQueueSingleton.getInstance(context).addToRequestQueue(refreshjsonObjectRequest);
                } else if (errorListener != null) {
                    errorListener.onErrorResponse(error);
                }
            }
        });
        this.context = context;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        String token = SharedPrefrencesManager.getInstance(context).getToken();
        params.put("Authorization", "Bearer "+ token);
        return params;
    }
}