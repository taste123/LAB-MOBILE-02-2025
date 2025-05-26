package com.example.tp6;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    public void addUsers(List<User> users) {
        int startPosition = userList.size();
        userList.addAll(users);
        notifyItemRangeInserted(startPosition, users.size());
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("id", user.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameTextview, speciesTextview;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameTextview = itemView.findViewById(R.id.name);
            speciesTextview = itemView.findViewById(R.id.species);
        }

        public void bind(User user) {
            Picasso.get().load(user.getImage()).into(imageView);
            nameTextview.setText(user.getName());
            speciesTextview.setText(user.getSpecies());
        }
    }
}
