package com.example.navigationbtm.videoLectures;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.navigationbtm.R;

public class Viewvideo extends AppCompatActivity {
    VideoView videoView;
    String videourl,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        title=getIntent ().getStringExtra ( "videotitle" );
        ProgressDialog progressDialog=new ProgressDialog ( this );
        progressDialog.setTitle ( title );
        progressDialog.setMessage ( "Opening..." );
        setContentView ( R.layout.activity_viewvideo );
         videoView=(VideoView)findViewById(R.id.videoView);
         videourl=getIntent ().getStringExtra ( "videourl" );

        //Creating MediaController
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        //specify the location of media file
        Uri uri=Uri.parse(videourl);

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        progressDialog.dismiss ();
        videoView.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu ( menu );
    }
}
