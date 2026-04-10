package com.example.photogalleryapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageDetailActivity extends AppCompatActivity {

    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imagePath = getIntent().getStringExtra("imagePath");
        if (imagePath == null) {
            finish();
            return;
        }

        File file = new File(imagePath);
        if (!file.exists()) {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ImageView ivFullImage = findViewById(R.id.ivFullImage);
        TextView tvImageName = findViewById(R.id.tvImageName);
        TextView tvImagePath = findViewById(R.id.tvImagePath);
        TextView tvImageSize = findViewById(R.id.tvImageSize);
        TextView tvImageDate = findViewById(R.id.tvImageDate);
        Button btnDelete = findViewById(R.id.btnDelete);

        Glide.with(this).load(file).into(ivFullImage);

        tvImageName.setText("Name: " + file.getName());
        tvImagePath.setText("Path: " + file.getAbsolutePath());
        tvImageSize.setText("Size: " + (file.length() / 1024) + " KB");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        tvImageDate.setText("Date: " + sdf.format(new Date(file.lastModified())));

        btnDelete.setOnClickListener(v -> showDeleteConfirmation(file));
    }

    private void showDeleteConfirmation(File file) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Image")
                .setMessage("Are you sure you want to delete this image?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (file.delete()) {
                        Toast.makeText(ImageDetailActivity.this, "Image deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ImageDetailActivity.this, "Failed to delete image", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}