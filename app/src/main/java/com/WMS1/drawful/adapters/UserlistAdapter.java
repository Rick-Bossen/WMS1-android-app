package com.WMS1.drawful.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.WMS1.drawful.R;

import java.util.List;

/**
 * Adapter for the list of users.
 */
public class UserlistAdapter extends RecyclerView.Adapter<UserlistAdapter.ViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;

    public UserlistAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.userlist_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String user = mData.get(position);
        holder.myTextView.setText(user);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * Inner class containing a textView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.username);
        }
    }
}
