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

public class civilfaculty extends AppCompatActivity {
    private RecyclerView ceDepartment;
    private LinearLayout ceNoDataFound;
    DatabaseReference databaseReference,dbref;
    List<FacultyData> celist;
    FacultyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_civilfaculty );
        ceDepartment = findViewById ( R.id.civilDepartment );
        ceNoDataFound = findViewById ( R.id.civilNoDataFound );
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ( "Faculty" );


        ceDepartment ();
    }
    private void ceDepartment() {
        dbref=databaseReference.child ( "Civil" );
        dbref.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                celist=new ArrayList<> ();
                if(!snapshot.exists ())              /* if data doesn't exist in the cs */
                {
                    /* making the no data available layout visible */
                    ceNoDataFound.setVisibility ( View.VISIBLE );
                    ceDepartment.setVisibility ( View.GONE );
                }
                else                          /* if data available */
                {
                    ceNoDataFound.setVisibility ( View.GONE );
                    ceDepartment.setVisibility ( View.VISIBLE );
                    for(DataSnapshot snapshot1:snapshot.getChildren ())
                    {
                        FacultyData data=snapshot1.getValue (FacultyData.class);
                        celist.add ( data );
                    }
                    ceDepartment.setHasFixedSize ( true );
                    ceDepartment.setLayoutManager ( new LinearLayoutManager ( civilfaculty.this) );
                    adapter=new FacultyAdapter ( celist,civilfaculty.this );
                    ceDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( civilfaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
    }
}