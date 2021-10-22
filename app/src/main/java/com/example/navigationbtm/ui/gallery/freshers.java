package com.example.navigationbtm.ui.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.navigationbtm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class freshers extends AppCompatActivity {
    private RecyclerView freshersimage;
    private LinearLayout nofresherimage;
    private GalleryAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_freshers );
        freshersimage=findViewById ( R.id.Freshers );
        nofresherimage=findViewById ( R.id.nofreshers );
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Gallery" );
        getFreshers ();
    }
    private void getFreshers() {
        databaseReference.child ( "Fresher's day" ).addValueEventListener ( new ValueEventListener () {
            List<ImageData> fresherList=new ArrayList<> ();
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.exists ())
                {
                    nofresherimage.setVisibility ( View.VISIBLE );
                    freshersimage.setVisibility ( View.GONE );
                }
                else
                {
                    nofresherimage.setVisibility ( View.GONE );
                    freshersimage.setVisibility ( View.VISIBLE );
                    for(DataSnapshot dataSnapshot: snapshot.getChildren ())
                    {
                        ImageData data= dataSnapshot.getValue (ImageData.class);
                        fresherList.add ( data );
                    }
                    adapter=new GalleryAdapter ( freshers.this,fresherList );
                    freshersimage.setLayoutManager ( new GridLayoutManager (  freshers.this,2 ) );
                    freshersimage.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( freshers.this,error.getMessage (),Toast.LENGTH_SHORT ).show ();

            }
        } );
    }
}