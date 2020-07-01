package com.WMS1.drawful.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.WMS1.drawful.R;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.requests.JwtJsonObjectRequest;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.android.volley.Request;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Adapter for the list of votes.
 */
public class VotelistAdapter extends RecyclerView.Adapter<VotelistAdapter.VotesViewHolder> {

    private LinkedList<String> texts;
    private LinkedList<String> userIds;
    private Context context;
    private LinkedList<Button> buttons;

    /**
     * Inner class containing an itemView
     */
    public static class VotesViewHolder extends RecyclerView.ViewHolder {

        public View view;

        public VotesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    public VotelistAdapter(Map<String, String> texts, Context context) {
        this.texts = new LinkedList<>();
        this.userIds = new LinkedList<>();
        this.buttons = new LinkedList<>();
        texts = new LinkedHashMap<>(texts);

        this.context = context;

        Map<String, String> finalTexts = texts;

        texts.keySet().forEach(key -> {
            userIds.add(key);
            this.texts.add(finalTexts.get(key));
        });
    }

    @NonNull
    @Override
    public VotelistAdapter.VotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.votelist_row, parent, false);
        return new VotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VotesViewHolder holder, int position) {
        Button voteButton = holder.itemView.findViewById(R.id.voteButton);

        String text = texts.get(position);

        String id = SharedPrefrencesManager.getInstance(context).getGameid();
        String url = RequestQueueSingleton.BASE_URL + "/game/" + id + "/theme/vote";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vote", userIds.get(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JwtJsonObjectRequest request = new JwtJsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    Toast.makeText(context, "Vote received", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < buttons.size(); i++) {
                        buttons.get(i).setEnabled(false);
                    }
                }, error -> Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_SHORT), context);


        voteButton.setText(text);
        voteButton.setOnClickListener(v -> RequestQueueSingleton.getInstance(context).addToRequestQueue(request));
        buttons.add(voteButton);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

}
