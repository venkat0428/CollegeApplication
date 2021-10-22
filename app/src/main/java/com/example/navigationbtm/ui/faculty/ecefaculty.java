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

public class ecefaculty extends AppCompatActivity {
    private RecyclerView eceDepartment;
    private LinearLayout eceNoDataFound;
    DatabaseReference databaseReference,dbref;
    List<FacultyData> ecelist;
    FacultyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_ecefaculty );
        eceDepartment = findViewById ( R.id.eceDepartment );
        eceNoDataFound = findViewById ( R.id.eceNoDataFound );
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ( "Faculty" );


        eceDepartment ();
    }
    private void eceDepartment() {
        dbref=databaseReference.child ( "Electronics" );
        dbref.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ecelist=new ArrayList<> ();
                if(!snapshot.exists ())              /* if data doesn't exist in the cs */
                {
                    /* making the no data available layout visible */
                    eceNoDataFound.setVisibility ( View.VISIBLE );
                    eceDepartment.setVisibility ( View.GONE );
                }
                else                          /* if data available */
                {
                    eceNoDataFound.setVisibility ( View.GONE );
                    eceDepartment.setVisibility ( View.VISIBLE );
                    for(DataSnapshot snapshot1:snapshot.getChildren ())
                    {
                        FacultyData data=snapshot1.getValue (FacultyData.class);
                        ecelist.add ( data );
                    }
                    eceDepartment.setHasFixedSize ( true );
                    eceDepartment.setLayoutManager ( new LinearLayoutManager ( ecefaculty.this ) );
                    adapter=new FacultyAdapter ( ecelist,ecefaculty.this );
                    eceDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( ecefaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
    }

}