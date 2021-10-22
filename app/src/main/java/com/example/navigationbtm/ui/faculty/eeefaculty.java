package com.example.navigationbtm.ui.faculty;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class eeefaculty extends AppCompatActivity {
    private RecyclerView eeeDepartment;
    private LinearLayout eeeNoDataFound;
    DatabaseReference databaseReference,dbref;
    List<FacultyData> eeelist;
    FacultyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_eeefaculty );
        eeeDepartment = findViewById ( R.id.eeeDepartment );
        eeeNoDataFound = findViewById ( R.id.eeeNoDataFound );
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ( "Faculty" );


        eeeDepartment ();
    }

    private void eeeDepartment() {
        dbref = databaseReference.child ( "Electrical" );
        dbref.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eeelist = new ArrayList<> ();
                if (!snapshot.exists ())              /* if data doesn't exist in the cs */ {
                    /* making the no data available layout visible */
                    eeeNoDataFound.setVisibility ( View.VISIBLE );
                    eeeDepartment.setVisibility ( View.GONE );
                } else                          /* if data available */ {
                    eeeNoDataFound.setVisibility ( View.GONE );
                    eeeDepartment.setVisibility ( View.VISIBLE );
                    for (DataSnapshot snapshot1 : snapshot.getChildren ()) {
                        FacultyData data = snapshot1.getValue ( FacultyData.class );
                        eeelist.add ( data );
                    }
                    eeeDepartment.setHasFixedSize ( true );
                    eeeDepartment.setLayoutManager ( new LinearLayoutManager ( eeefaculty.this ) );
                    adapter = new FacultyAdapter ( eeelist, eeefaculty.this );
                    eeeDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( eeefaculty.this, error.getMessage (), Toast.LENGTH_SHORT ).show ();


            }

        } );
    }
}