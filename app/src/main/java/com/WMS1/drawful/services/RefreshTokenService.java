package com.WMS1.drawful.services;

import android.content.Context;

import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.requests.RefreshJsonObjectRequest;
import com.WMS1.drawful.requests.RequestQueueSingleton;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MINUTES;

public class RefreshTokenService {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Starts a thread that runs a service to refresh the jwt token every 10 minutes.
     *
     * @param context application context
     */
    public void runService(Context context) {
        final Runnable runnable = () -> {
            if (!SharedPrefrencesManager.getInstance(context).getRefresh().isEmpty()) {
                RefreshJsonObjectRequest request = new RefreshJsonObjectRequest(null, context);
                RequestQueueSingleton queueSingleton = RequestQueueSingleton.getInstance(context);
                queueSingleton.addToRequestQueue(request);
            }
        };
        final ScheduledFuture<?> RefreshHandle =
                scheduler.scheduleAtFixedRate(runnable, 0, 10, MINUTES);
    }
}
