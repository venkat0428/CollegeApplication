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

public class convocation extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private RecyclerView convocation;
    private LinearLayout noconvocationimage;
    private GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_convocation );
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Gallery" );
        noconvocationimage=findViewById ( R.id.convocation_noimage );
        convocation=findViewById ( R.id.Convocation );




        getConvocation ();
    }
    private void getConvocation() {
        databaseReference.child ( "Convocation" ).addValueEventListener ( new ValueEventListener () {
            List<ImageData> convocationlist=new ArrayList<> ();
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.exists ())
                {
                    convocation.setVisibility ( View.GONE );
                    noconvocationimage.setVisibility ( View.VISIBLE );
                }
                else
                {
                    convocation.setVisibility ( View.VISIBLE );
                    noconvocationimage.setVisibility ( View.GONE );
                    for(DataSnapshot dataSnapshot: snapshot.getChildren ())
                    {
                        ImageData data= dataSnapshot.getValue (ImageData.class);
                        convocationlist.add ( data );
                    }
                    adapter=new GalleryAdapter ( convocation.this,convocationlist );
                    convocation.setLayoutManager ( new GridLayoutManager (  convocation.this,2 ) );
                    convocation.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( convocation.this,error.getMessage (),Toast.LENGTH_SHORT ).show ();

            }
        } );
    }
}