package com.example.navigationbtm.ui.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.navigationbtm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DeleteNotice extends AppCompatActivity {
    private RecyclerView deleteNotice;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_delete_notice );

        deleteNotice=findViewById ( R.id.deleteNotice );
        progressBar=findViewById ( R.id.progressBar );
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Notice" );

        deleteNotice.setLayoutManager ( new LinearLayoutManager ( this ) );
        deleteNotice.setHasFixedSize ( true );

        getNotice();
    }

    private void getNotice() {
        databaseReference.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list=new ArrayList<> ();
                for(DataSnapshot dataSnapshot:snapshot.getChildren ())
                {
                    NoticeData noticeData=dataSnapshot.getValue (NoticeData.class);
                    list.add ( noticeData );
                }
                adapter=new NoticeAdapter ( list,DeleteNotice.this );
                progressBar.setVisibility ( View.GONE );
                deleteNotice.setAdapter ( adapter );
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                progressBar.setVisibility ( View.GONE );
                Toast.makeText ( DeleteNotice.this,error.getMessage (),Toast.LENGTH_SHORT ).show ();
            }
        } );
    }
}
