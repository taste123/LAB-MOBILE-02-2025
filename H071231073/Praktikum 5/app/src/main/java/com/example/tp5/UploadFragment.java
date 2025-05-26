package com.example.tp5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadFragment extends Fragment {

    private Uri selectedImageUri;
    private ImageView cover;
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText et_title, et_writer, et_year, et_genre, et_sinopsis;
    private Button uploadBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        cover = view.findViewById(R.id.cover);
        et_title = view.findViewById(R.id.et_title);
        et_writer = view.findViewById(R.id.et_writer);
        et_year = view.findViewById(R.id.et_year);
        et_genre = view.findViewById(R.id.et_genre);
        et_sinopsis = view.findViewById(R.id.et_sinopsis);
        uploadBtn = view.findViewById(R.id.uploadBtn);

        cover.setOnClickListener( v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        uploadBtn.setOnClickListener(v -> {
            String title = et_title.getText().toString();
            String writer = et_writer.getText().toString();
            String genre = et_genre.getText().toString();
            String sinopsis = et_sinopsis.getText().toString();
            if (selectedImageUri == null) {
                Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
                return;
            }
            String coverUri = selectedImageUri.toString();
            String str_year = et_year.getText().toString();
            if (title.isEmpty() || writer.isEmpty() || genre.isEmpty() || sinopsis.isEmpty() || coverUri.isEmpty() || str_year.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int year = 0;
            try {
                year = Integer.parseInt(str_year);
            } catch (NumberFormatException e) {
                et_year.setError("Invalid year, please input year in digit");
                e.printStackTrace();
            }

            Books newBook = new Books(title, writer, genre, year, sinopsis, coverUri, 0);
            DataDummy.books.add(0, newBook);

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).notifyBooksAdapter();
            }

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            cover.setImageURI(selectedImageUri);
        }
    }
}