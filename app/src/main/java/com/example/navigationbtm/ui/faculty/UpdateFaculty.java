package com.example.navigationbtm.ui.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.navigationbtm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {
    private FloatingActionButton floatingActionBtn;
    private RecyclerView csDepartment,eceDepartment,meDepartment,eeeDepartment,ceDepartment,itDepartment;
    private LinearLayout csNoDataFound,eceNoDataFound,meNoDataFound,eeeNoDataFound,ceNoDataFound,itNoDataFound;
    private List<FacultyData> cslist,ecelist,melist,eeelist,celist,itlist;
    private DatabaseReference databaseReference,dbref;
    private FacAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_update_faculty );
        csDepartment=findViewById ( R.id.csDepartment );
        eceDepartment=findViewById ( R.id.eceDepartment );
        meDepartment=findViewById ( R.id.meDepartment );
        ceDepartment=findViewById ( R.id.ceDepartment );
        eeeDepartment=findViewById ( R.id.eeeDepartment );
        itDepartment=findViewById ( R.id.itDepartment );

        csNoDataFound=findViewById ( R.id.csNoDataFound );
        eceNoDataFound=findViewById ( R.id.eceNoDataFound );
        meNoDataFound=findViewById ( R.id.meNoDataFound );
        eeeNoDataFound=findViewById ( R.id.eeeNoDataFound );
        ceNoDataFound=findViewById ( R.id.ceNoDataFound );
        itNoDataFound=findViewById ( R.id.itNoDataFound );

        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Faculty" );

        csDepartment();
        eceDepartment();
        meDepartment ();
        ceDepartment ();
        eeeDepartment ();
        itDepartment ();


        floatingActionBtn=findViewById ( R.id.floatingActionBtn );
        floatingActionBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (UpdateFaculty.this,AddFaculty.class);
                startActivity ( intent );
            }
        } );
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
                    csDepartment.setLayoutManager ( new LinearLayoutManager ( UpdateFaculty.this ) );
                    adapter=new FacAdapter ( cslist,UpdateFaculty.this ,"Computer Science");
                    csDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( UpdateFaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
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
                    eceDepartment.setLayoutManager ( new LinearLayoutManager ( UpdateFaculty.this ) );
                    adapter=new FacAdapter ( ecelist,UpdateFaculty.this,"Electronics" );
                    eceDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( UpdateFaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
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
                    meNoDataFound.setVisibility ( View.VISIBLE );
                    meDepartment.setVisibility ( View.GONE );
                }
                else                          /* if data available */
                {
                    meNoDataFound.setVisibility ( View.GONE );
                    meDepartment.setVisibility ( View.VISIBLE );
                    for(DataSnapshot snapshot1:snapshot.getChildren ())
                    {
                        FacultyData data=snapshot1.getValue (FacultyData.class);
                        melist.add ( data );
                    }
                    meDepartment.setHasFixedSize ( true );
                    meDepartment.setLayoutManager ( new LinearLayoutManager ( UpdateFaculty.this ) );
                    adapter=new FacAdapter ( melist,UpdateFaculty.this,"Mechanical" );
                    meDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( UpdateFaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
    }

    private void eeeDepartment() {
        dbref=databaseReference.child ( "Electrical" );
        dbref.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eeelist=new ArrayList<> ();
                if(!snapshot.exists ())              /* if data doesn't exist in the cs */
                {
                    /* making the no data available layout visible */
                    eeeNoDataFound.setVisibility ( View.VISIBLE );
                    eeeDepartment.setVisibility ( View.GONE );
                }
                else                          /* if data available */
                {
                    eeeNoDataFound.setVisibility ( View.GONE );
                    eeeDepartment.setVisibility ( View.VISIBLE );
                    for(DataSnapshot snapshot1:snapshot.getChildren ())
                    {
                        FacultyData data=snapshot1.getValue (FacultyData.class);
                        eeelist.add ( data );
                    }
                    eeeDepartment.setHasFixedSize ( true );
                    eeeDepartment.setLayoutManager ( new LinearLayoutManager ( UpdateFaculty.this ) );
                    adapter=new FacAdapter ( eeelist,UpdateFaculty.this,"Electrical" );
                    eeeDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( UpdateFaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
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
                    ceDepartment.setLayoutManager ( new LinearLayoutManager ( UpdateFaculty.this ) );
                    adapter=new FacAdapter ( celist,UpdateFaculty.this,"Civil" );
                    ceDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( UpdateFaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
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
                    itDepartment.setLayoutManager ( new LinearLayoutManager ( UpdateFaculty.this ) );
                    adapter=new FacAdapter ( itlist,UpdateFaculty.this,"Information Tech" );
                    itDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText ( UpdateFaculty.this, error.getMessage (),Toast.LENGTH_SHORT).show ();
            }
        } );
    }
}