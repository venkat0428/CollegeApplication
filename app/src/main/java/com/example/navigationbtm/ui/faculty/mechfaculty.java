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

public class mechfaculty extends AppCompatActivity {
    private RecyclerView mechDepartment;
    private LinearLayout mechNoDataFound;
    DatabaseReference databaseReference,dbref;
    List<FacultyData> melist;
    FacultyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_mechfaculty );
        mechDepartment = findViewById ( R.id.mechDepartment );
        mechNoDataFound = findViewById ( R.id.mechNoDataFound );
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ( "Faculty" );


        meDepartment ();
    }
    private void meDepartment() {
        dbref=databaseReference.child ( "Mechanical" );
        dbref.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                melist=new ArrayList<> ();
                if(!snapshot.exists ())              /* if data doesn't exist in the cs */
                {
                    /* making the no data available layout visible */
                    mechNoDataFound.setVisibility ( View.VISIBLE );
                    mechDepartment.setVisibility ( View.GONE );
                }
                else                          /* if data available */
                {
                    mechNoDataFound.setVisibility ( View.GONE );
                    mechDepartment.setVisibility ( View.VISIBLE );
                    for(DataSnapshot snapshot1:snapshot.getChildren ())
                    {
                        FacultyData data=snapshot1.getValue (FacultyData.class);
                        melist.add ( data );
                    }
                    mechDepartment.setHasFixedSize ( true );
                    mechDepartment.setLayoutManager ( new LinearLayoutManager ( mechfaculty.this ) );
                    adapter=new FacultyAdapter ( melist,mechfaculty.this);
                    mechDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( mechfaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
    }

}