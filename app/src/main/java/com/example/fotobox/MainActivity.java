package com.example.fotobox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_PHOTO = "selected_photo";
    private ImageView photoPreview;
    private Bitmap selectedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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

        photoPreview = findViewById(R.id.IVFoto);
        TextInputEditText nameInput = findViewById(R.id.ETNombreFoto);
        if (savedInstanceState != null) {
            selectedPhoto = savedInstanceState.getParcelable(STATE_PHOTO);
            if (selectedPhoto != null) photoPreview.setImageBitmap(selectedPhoto);
        }

        Button loadPhotoButton = findViewById(R.id.BTHacerFoto);
        loadPhotoButton.setOnClickListener(view -> {
            selectedPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.foto_ejemplo);
            photoPreview.setImageBitmap(selectedPhoto);
            photoPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        });

        Button saveButton = findViewById(R.id.BTAceptar);
        saveButton.setOnClickListener(view -> {
            String photoName = nameInput.getText() == null
                    ? "" : nameInput.getText().toString().trim();
            if (photoName.isEmpty()) {
                nameInput.setError(getString(R.string.error_nombre));
                nameInput.requestFocus();
                return;
            }
            Intent intent = new Intent(MainActivity.this, ResultadoActivity.class);
            intent.putExtra(ResultadoActivity.EXTRA_NAME, photoName);
            if (selectedPhoto != null) {
                intent.putExtra(ResultadoActivity.EXTRA_PHOTO, selectedPhoto);
            }
            startActivity(intent);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (selectedPhoto != null) outState.putParcelable(STATE_PHOTO, selectedPhoto);
        super.onSaveInstanceState(outState);
    }
}