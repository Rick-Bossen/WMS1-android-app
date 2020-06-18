package com.WMS1.drawful.requests;

import android.content.Context;

import androidx.annotation.Nullable;

import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RefreshJsonObjectRequest extends JsonObjectRequest {
    Context context;

    public RefreshJsonObjectRequest(Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener, Context context) {
        super(Request.Method.POST, RequestQueueSingleton.BASE_URL + "/user/refresh", null, listener, errorListener);
        this.context = context;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        String token = SharedPrefrencesManager.getInstance(context).getRefresh();
        params.put("Authorization", "Bearer " + token);
        return params;
    }
}
