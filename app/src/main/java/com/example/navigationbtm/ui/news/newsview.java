package com.example.navigationbtm.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class newsview extends Fragment {
    private RecyclerView deleteNotice;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;
    private DatabaseReference databaseReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate ( R.layout.fragment_newsview, container, false );
        progressBar=view.findViewById ( R.id.progressBar );
        deleteNotice=view.findViewById ( R.id.deleteNotice );
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Notice" );



        try {
            deleteNotice.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
            deleteNotice.setHasFixedSize ( true );
        } catch (Exception e) {
            e.printStackTrace ();
        }

        getNotice();
        return view;
    }

    private void getNotice() {
        databaseReference.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list=new ArrayList<> ();
                for(DataSnapshot dataSnapshot:snapshot.getChildren ())
                {
                    NoticeData noticeData=dataSnapshot.getValue (NoticeData.class);
                    list.add (0, noticeData );
                }
                adapter=new NoticeAdapter ( list,getContext ());
                adapter.notifyDataSetChanged ();
                progressBar.setVisibility ( View.GONE );
                deleteNotice.setAdapter ( adapter );
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                progressBar.setVisibility ( View.GONE );
                Toast.makeText ( getContext (),error.getMessage (),Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

}