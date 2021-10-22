package com.example.navigationbtm.Document;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class Document extends AppCompatActivity {
    private RecyclerView documentLayout;
    private DatabaseReference databaseReference;
    private List<DocumentData> documentDataList;
    private DocumentAdapter documentAdapter;

    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout shimmarvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_document );
        documentLayout=findViewById ( R.id.documentlayout );
        shimmerFrameLayout=findViewById ( R.id.shimmer_view_container );
        shimmarvisible=findViewById ( R.id.shimmervisible );

        /* to show back button at the top */
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );

        /* to set title of viewing activity at the top */
        getSupportActionBar ().setTitle ( "Documents" );


        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ("doc");

        try {

            documentLayout.setLayoutManager ( new LinearLayoutManager ( Document.this ) );
            documentLayout.setHasFixedSize ( true );
        } catch (Exception e) {
            e.printStackTrace ();
        }
        getData();
    }

    private void getData() {
        databaseReference.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                documentDataList=new ArrayList<> ();
                for(DataSnapshot dataSnapshot:snapshot.getChildren ())
                {
                    DocumentData data=dataSnapshot.getValue (DocumentData.class);
                    documentDataList.add ( data );
                }
                documentAdapter=new DocumentAdapter ( Document.this,documentDataList );
                documentAdapter.notifyDataSetChanged ();
               documentLayout.setAdapter ( documentAdapter );
               shimmerFrameLayout.stopShimmer ();
               shimmarvisible.setVisibility ( View.GONE );

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( Document.this,error.getMessage (),Toast.LENGTH_SHORT ).show ();
            }
        } );
    }
}