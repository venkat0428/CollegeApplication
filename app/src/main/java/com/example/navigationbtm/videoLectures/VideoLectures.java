package com.example.navigationbtm.videoLectures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.navigationbtm.Document.Document;
import com.example.navigationbtm.Document.DocumentAdapter;
import com.example.navigationbtm.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VideoLectures extends AppCompatActivity {
    private RecyclerView videolayout;
    private DatabaseReference databaseReference;
    private List<VideoData> videoDataList;
    videoAdapter Adapter;

    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout shimmarvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_video_lectures );

        videolayout=findViewById ( R.id.videolayout );
        shimmerFrameLayout=findViewById ( R.id.shimmer_view_container );
        shimmarvisible=findViewById ( R.id.shimmervisible );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );

        /* to set title of viewing activity at the top */
        getSupportActionBar ().setTitle ( "Video Lectures" );


        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ("Lectures");

        try {

            videolayout.setLayoutManager ( new LinearLayoutManager ( VideoLectures.this ) );
            videolayout.setHasFixedSize ( true );
        } catch (Exception e) {
            e.printStackTrace ();
        }
        getData();
    }

    private void getData() {
        databaseReference.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                videoDataList=new ArrayList<> ();
                for(DataSnapshot dataSnapshot:snapshot.getChildren ())
                {
                    VideoData data=dataSnapshot.getValue (VideoData.class);
                    videoDataList.add ( data );
                }
                Adapter =new videoAdapter ( VideoLectures.this,videoDataList );
                Adapter.notifyDataSetChanged ();
                videolayout.setAdapter ( Adapter );
                shimmerFrameLayout.stopShimmer ();
                shimmarvisible.setVisibility ( View.GONE );

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( VideoLectures.this,error.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }
}