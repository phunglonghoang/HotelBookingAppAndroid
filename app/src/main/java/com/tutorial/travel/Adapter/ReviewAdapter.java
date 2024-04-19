package com.tutorial.travel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.ReviewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{
    private List<ReviewModel> rvList;

    private Context context;


    public ReviewAdapter(Context context, List<ReviewModel> rvList) {
        this.context = context;
        this.rvList = rvList;

    }
    public void setReviews(List<ReviewModel> reviews) {
        this.rvList = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_review, parent, false);
        return new ReviewViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {

        ReviewModel rv = rvList.get(position);
        String uname = DatabaseHelper.getUsernameById(context, rv.getUser_id());
        holder.txtnamereview.setText(uname);
        holder.txtreview.setText(rv.getReviewDetail());
        holder.scoreRating.setText(String.valueOf(rv.getRating()));

    }

    @Override
    public int getItemCount() {
        return rvList.size();
    }
    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView txtnamereview, scoreRating,txtreview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnamereview = itemView.findViewById(R.id.txtnamereview);
            txtreview = itemView.findViewById(R.id.txtreview);
            scoreRating = itemView.findViewById(R.id.scoreRating);
        }
    }
}
