package com.tutorial.travel.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tutorial.travel.R;
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
    public ReviewAdapter(ArrayList<ReviewModel> rvList) {
        this.rvList = rvList;
    }
    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView txtnamereview, scoreRating;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnamereview = itemView.findViewById(R.id.txtnamereview);
            scoreRating = itemView.findViewById(R.id.scoreRating);
        }
    }
    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_review, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        ReviewModel rv = rvList.get(position);
        holder.txtnamereview.setText(rv.getReviewDetail());
        Log.d("TAG", "onBindViewHolder: " +  rv.getReviewDetail() );
        holder.scoreRating.setText(String.valueOf(rv.getRating()));

    }

    @Override
    public int getItemCount() {
        return rvList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView txtnamereview, scoreRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnamereview = itemView.findViewById(R.id.txtnamereview);
            scoreRating = itemView.findViewById(R.id.scoreRating);

//
        }
    }
}
