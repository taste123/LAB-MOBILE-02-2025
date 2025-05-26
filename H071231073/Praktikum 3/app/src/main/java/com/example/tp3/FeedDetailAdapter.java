package com.example.tp3;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedDetailAdapter extends RecyclerView.Adapter<FeedDetailAdapter.ViewHolder> {
    private ArrayList<FeedProfile> feedProfiles;

    public FeedDetailAdapter(ArrayList<FeedProfile> feedProfiles) {
        this.feedProfiles = feedProfiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item, parent, false); // Use item_feed.xml
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedProfile feed = feedProfiles.get(position);

        // Load images with Glide
        Glide.with(holder.itemView)
                .load(Uri.parse(feed.getProfilePicture()))
                .into(holder.pfp);

        Glide.with(holder.itemView)
                .load(Uri.parse(feed.getImageUri()))
                .into(holder.content);

        // Set text values
        holder.username.setText(feed.getUsername());
        holder.caption.setText(feed.getCaption());
        holder.likeCount.setText(String.valueOf(feed.getLikes()));
    }

    @Override
    public int getItemCount() {
        return feedProfiles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView pfp;
        TextView username;
        ImageView content;
        TextView caption;
        TextView likeCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pfp = itemView.findViewById(R.id.pfp);
            username = itemView.findViewById(R.id.username);
            content = itemView.findViewById(R.id.content);
            caption = itemView.findViewById(R.id.caption);
            likeCount = itemView.findViewById(R.id.like);
        }
    }
}