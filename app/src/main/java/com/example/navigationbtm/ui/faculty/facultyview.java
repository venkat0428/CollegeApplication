package com.example.navigationbtm.ui.faculty;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationbtm.R;


public class facultyview extends Fragment {


    private CardView csfaculty,eeefaculty,cefaculty,mechfaculty,itfaculty,ecefaculty;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate ( R.layout.fragment_facultyview, container, false );

        csfaculty=view.findViewById ( R.id.csfaculty );
        eeefaculty=view.findViewById ( R.id.eeefaculty );
        ecefaculty=view.findViewById ( R.id.ecefaculty );
        mechfaculty=view.findViewById ( R.id.mechfaculty );
        itfaculty=view.findViewById ( R.id.itfaculty );
        cefaculty=view.findViewById ( R.id.cefaculty );

        csfaculty.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent (getContext (), facultycs.class);
               startActivity ( intent );
            }
        } );

        eeefaculty.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getContext (), com.example.navigationbtm.ui.faculty.eeefaculty.class);
                startActivity ( intent );
            }
        } );

        ecefaculty.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getContext (), com.example.navigationbtm.ui.faculty.ecefaculty.class);
                startActivity ( intent );
            }
        } );
        mechfaculty.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getContext (), com.example.navigationbtm.ui.faculty.mechfaculty.class);
                startActivity ( intent );
            }
        } );
        itfaculty.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getContext (), com.example.navigationbtm.ui.faculty.itfaculty.class);
                startActivity ( intent );
            }
        } );
        cefaculty.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getContext (), civilfaculty.class);
                startActivity ( intent );
            }
        } );





        return view;
    }











}
