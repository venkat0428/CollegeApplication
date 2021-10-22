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

public class others extends AppCompatActivity {
    private RecyclerView otherimage;
    private LinearLayout nootherimage;
    private GalleryAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_others );
        otherimage=findViewById ( R.id.other );
        nootherimage=findViewById ( R.id.noother );
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Gallery" );
        getOther ();
    }
    private void getOther() {
        databaseReference.child ( "other events" ).addValueEventListener ( new ValueEventListener () {
            List<ImageData> otherList=new ArrayList<> ();
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                /* if no images available then display no image layout*/
                if(!snapshot.exists ())
                {
                    nootherimage.setVisibility ( View.VISIBLE );
                    otherimage.setVisibility ( View.GONE );
                }
                else
                {
                    nootherimage.setVisibility ( View.GONE );
                    otherimage.setVisibility ( View.VISIBLE );
                    for(DataSnapshot dataSnapshot: snapshot.getChildren ())
                    {
                        ImageData data= dataSnapshot.getValue (ImageData.class);
                        otherList.add ( data );
                    }
                    adapter=new GalleryAdapter ( others.this,otherList );
                    otherimage.setLayoutManager ( new GridLayoutManager ( others.this,2 ) );
                    otherimage.setAdapter ( adapter );

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( others.this,error.getMessage (),Toast.LENGTH_SHORT ).show ();

            }
        } );
    }

}