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

public class facultycs extends AppCompatActivity {
    private RecyclerView csDepartment;
    private LinearLayout csNoDataFound;
    DatabaseReference databaseReference,dbref;
    List<FacultyData> cslist;
    FacultyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_facultycs );
        csDepartment = findViewById ( R.id.csDepartment );
        csNoDataFound = findViewById ( R.id.csNoDataFound );
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ( "Faculty" );


        csDepartment ();
    }

    private void csDepartment() {
        dbref=databaseReference.child ( "Computer Science" );
        dbref.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                cslist=new ArrayList<> ();
                if(!snapshot.exists ())              /* if data doesn't exist in the cs */
                {
                    /* making the no data available layout visible */
                    csNoDataFound.setVisibility ( View.VISIBLE );
                    csDepartment.setVisibility ( View.GONE );
                }
                else                          /* if data available */
                {
                    csNoDataFound.setVisibility ( View.GONE );
                    csDepartment.setVisibility ( View.VISIBLE );
                    for(DataSnapshot snapshot1:snapshot.getChildren ())
                    {
                        FacultyData data=snapshot1.getValue (FacultyData.class);
                        cslist.add( data );
                    }
                    csDepartment.setHasFixedSize ( true );
                    csDepartment.setLayoutManager ( new LinearLayoutManager ( facultycs.this) );
                    adapter=new FacultyAdapter ( cslist,facultycs.this);
                    csDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( facultycs.this, error.getMessage (),Toast.LENGTH_SHORT).show ();


            }

        });

}
}