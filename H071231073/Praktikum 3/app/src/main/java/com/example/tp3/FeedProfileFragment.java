package com.example.tp3;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FeedProfileFragment extends Fragment {
    private ImageView content;
    private TextView caption;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_profile, container, false);

        content = view.findViewById(R.id.content);
        caption = view.findViewById(R.id.caption);

        if (getArguments() != null) {
            String imageUri = getArguments().getString("imageUri");
            String captionText = getArguments().getString("caption");

            Glide.with(this).load(Uri.parse(imageUri)).into(content);
            caption.setText(captionText);
        }

        return view;
    }
}