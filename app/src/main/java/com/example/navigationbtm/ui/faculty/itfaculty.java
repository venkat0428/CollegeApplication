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

public class itfaculty extends AppCompatActivity {
    private RecyclerView itDepartment;
    private LinearLayout itNoDataFound;
    DatabaseReference databaseReference,dbref;
    List<FacultyData> itlist;
    FacultyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_itfaculty );
        itDepartment = findViewById ( R.id.itDepartment );
        itNoDataFound = findViewById ( R.id.itNoDataFound );
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ( "Faculty" );


        itDepartment ();
    }
    private void itDepartment() {
        dbref=databaseReference.child ( "Information Tech" );
        dbref.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                itlist=new ArrayList<> ();
                if(!snapshot.exists ())              /* if data doesn't exist in the cs */
                {
                    /* making the no data available layout visible */
                    itNoDataFound.setVisibility ( View.VISIBLE );
                    itDepartment.setVisibility ( View.GONE );
                }
                else                          /* if data available */
                {
                    itNoDataFound.setVisibility ( View.GONE );
                    itDepartment.setVisibility ( View.VISIBLE );
                    for(DataSnapshot snapshot1:snapshot.getChildren ())
                    {
                        FacultyData data=snapshot1.getValue (FacultyData.class);
                        itlist.add ( data );
                    }
                    itDepartment.setHasFixedSize ( true );
                    itDepartment.setLayoutManager ( new LinearLayoutManager ( itfaculty.this ) );
                    adapter=new FacultyAdapter ( itlist,itfaculty.this );
                    itDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( itfaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
    }
}