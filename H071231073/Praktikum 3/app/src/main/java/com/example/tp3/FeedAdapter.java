package com.example.tp3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<Feed> feeds;

    private ItemClickListener listener;
    public interface ItemClickListener {
        void onItemClick(Feed feed);
    }
    public FeedAdapter(ArrayList<Feed> feeds, ItemClickListener listener) {
        this.feeds = feeds;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        Feed feed = feeds.get(position);
        holder.pfp.setImageResource(feed.getPfp());
        holder.content.setImageResource(feed.getContent());
        holder.username.setText(feed.getUsername());
        holder.caption.setText(feed.getCaption());
        holder.likedBy.setImageResource(feed.getLikedBy());

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(feed);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView pfp, likedBy;
        private ImageView content;
        private TextView username, caption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pfp = itemView.findViewById(R.id.pfp);
            content = itemView.findViewById(R.id.content);
            username = itemView.findViewById(R.id.username);
            caption = itemView.findViewById(R.id.caption);
            likedBy = itemView.findViewById(R.id.liked_by);
        }
    }
}
