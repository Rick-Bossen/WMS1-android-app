package com.WMS1.drawful.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.WMS1.drawful.R;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Adapter for the list of answers.
 */
public class AnswerlistAdapter extends RecyclerView.Adapter<AnswerlistAdapter.AnswersViewHolder> {

    private LinkedList<String> answers;
    private LinkedList<Integer> votes;
    private String correct;
    private Context context;

    /**
     * Inner class containing an itemView
     */
    public static class AnswersViewHolder extends RecyclerView.ViewHolder{

        public View view;

        public AnswersViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    public AnswerlistAdapter(Map<String, Integer> answers, String correct, Context context) {
        this.answers = new LinkedList<>();
        this.votes = new LinkedList<>();
        answers = new LinkedHashMap<>(answers);

        this.correct = correct;
        this.context = context;


        Map<String, Integer> finalAnswers = answers;

        answers.keySet().forEach(key -> {
            this.answers.add(key);
            votes.add(finalAnswers.get(key));
        });
    }

    @NonNull
    @Override
    public AnswerlistAdapter.AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answerlist_row, parent, false);
        return new AnswersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersViewHolder holder, int position) {
        Button answerButton = holder.itemView.findViewById(R.id.answerButton);


        String answer = answers.get(position);


        if (answer.equals(correct)) {
            answerButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorCorrect));
        }else{
            answerButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorIncorrect));
        }
        answerButton.setText(answer);
        setStars(holder, votes.get(position));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    /**
     * Adds a star to an answer for every vote.
     *
     * @param holder holder containing the views
     * @param amount the amount of stars to add
     */
    private void setStars(AnswersViewHolder holder, int amount) {
        TextView[] stars;

        stars = new TextView[]{
                holder.itemView.findViewById(R.id.star1),
                holder.itemView.findViewById(R.id.star2),
                holder.itemView.findViewById(R.id.star3),
                holder.itemView.findViewById(R.id.star4),
                holder.itemView.findViewById(R.id.star5),
                holder.itemView.findViewById(R.id.star6),
                holder.itemView.findViewById(R.id.star7)
        };

        List<TextView> list = Arrays.asList(stars);
        Collections.shuffle(list);

        for (int i = 0; i < amount; i++) {
            list.get(i).setVisibility(View.VISIBLE);
        }
    }
}
