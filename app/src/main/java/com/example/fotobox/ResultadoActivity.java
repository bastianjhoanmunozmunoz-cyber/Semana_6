package com.example.fotobox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultadoActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "photo_name";
    public static final String EXTRA_PHOTO = "photo_bitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);

        View root = findViewById(R.id.main);
        final int initialLeft = root.getPaddingLeft();
        final int initialTop = root.getPaddingTop();
        final int initialRight = root.getPaddingRight();
        final int initialBottom = root.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(root, (view, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(initialLeft + bars.left, initialTop + bars.top,
                    initialRight + bars.right, initialBottom + bars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra(EXTRA_NAME);
        TextView nameView = findViewById(R.id.TVNombreFoto);
        nameView.setText(name == null || name.trim().isEmpty()
                ? getString(R.string.sin_nombre) : name);

        Bitmap photo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            photo = intent.getParcelableExtra(EXTRA_PHOTO, Bitmap.class);
        } else {
            //noinspection deprecation
            photo = intent.getParcelableExtra(EXTRA_PHOTO);
        }
        ImageView resultImage = findViewById(R.id.IVResultado);
        if (photo != null) {
            resultImage.setImageBitmap(photo);
            resultImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            resultImage.setVisibility(View.GONE);
            TextView emptyPhoto = findViewById(R.id.TVSinFoto);
            emptyPhoto.setVisibility(View.VISIBLE);
        }

        Button backButton = findViewById(R.id.BTVolver);
        backButton.setOnClickListener(view -> finish());
    }
}