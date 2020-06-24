package com.WMS1.drawful.helpers;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.WMS1.drawful.R;

import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder> {

    // @TODO currently as example, I don't know how you'll get the data
    private String[] answers = new String[]{
            "Antwoord1",
            "Antwoord2",
            "Antwoord3",
            "Antwoord4",
            "Antwoord5",
            "Antwoord6",
            "Antwoord7",
    };

    public static class AnswersViewHolder extends RecyclerView.ViewHolder{

        public View view;

        public AnswersViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    public AnswersAdapter(){
        // @TODO set real data
    }

    @NonNull
    @Override
    public AnswersAdapter.AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_view, parent, false);
        return new AnswersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersViewHolder holder, int position) {
        Button answerButton = holder.itemView.findViewById(R.id.answerButton);

        // @TODO currently all examples
        if (position == 3) {
            answerButton.setBackgroundColor(Color.parseColor("#26a65b"));
        }else{
            answerButton.setBackgroundColor(Color.parseColor("#e74c3c"));
        }
        answerButton.setText(this.answers[position]);
    }

    @Override
    public int getItemCount() {
        return answers.length;
    }
}
