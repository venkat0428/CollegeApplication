package com.example.navigationbtm.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.navigationbtm.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageView extends AppCompatActivity {
    private PhotoView fullimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_full_image_view );

        fullimage=findViewById ( R.id.fullimage );

        String image=getIntent ().getStringExtra ( "image" );

        Glide.with ( FullImageView.this ).load ( image ).into ( fullimage );

    }
}