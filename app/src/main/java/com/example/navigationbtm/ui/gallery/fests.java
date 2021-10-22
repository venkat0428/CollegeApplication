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

public class fests extends AppCompatActivity {
    private RecyclerView cultimage;
    private LinearLayout nocultimage;
    private GalleryAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_fests );
        cultimage=findViewById ( R.id.Fest );
        nocultimage=findViewById ( R.id.nofest );
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Gallery" );
        getCult ();
    }
    private void getCult() {
        databaseReference.child ( "Cult night" ).addValueEventListener ( new ValueEventListener () {
            List<ImageData> cultList=new ArrayList<> ();
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.exists ())
                {
                    nocultimage.setVisibility ( View.VISIBLE );
                    cultimage.setVisibility ( View.GONE );
                }
                else
                {
                    nocultimage.setVisibility ( View.GONE);
                    cultimage.setVisibility ( View.VISIBLE );
                    for(DataSnapshot dataSnapshot: snapshot.getChildren ())
                    {
                        ImageData data= dataSnapshot.getValue (ImageData.class);
                        cultList.add ( data );
                    }
                    adapter=new GalleryAdapter ( fests.this,cultList );
                    cultimage.setLayoutManager ( new GridLayoutManager (  fests.this,2 ) );
                    cultimage.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( fests.this,error.getMessage (),Toast.LENGTH_SHORT ).show ();

            }
        } );

    }



}