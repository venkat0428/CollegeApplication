package com.example.navigationbtm.ui.faculty;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.navigationbtm.R;
import com.example.navigationbtm.ui.news.FullImageView;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewAdapter> {
    public List<FacultyData> list;
    public Context context;

    public FacultyAdapter(List<FacultyData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NotNull
    @Override
    public FacultyViewAdapter onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.datafound, parent, false );
        return new FacultyViewAdapter ( view );
    }

    @Override
    public void onBindViewHolder(@NotNull FacultyAdapter.FacultyViewAdapter holder, int position) {

        FacultyData item = list.get ( position );
        holder.falname.setText ( item.getName () );
        holder.falqualification.setText ( item.getQualification () );
        holder.falemailid.setText ( item.getMailId () );
        holder.falpost.setText ( item.getPost () );
        try {
            Glide.with ( context ).load ( item.getImage () ).into ( holder.falimages );
        } catch (Exception e) {
            e.printStackTrace ();
        }
        holder.falimages.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (context, FullImageView.class);
                intent.putExtra ( "image",item.getImage () );
                context.startActivity ( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class FacultyViewAdapter extends RecyclerView.ViewHolder {
        TextView falname, falqualification, falemailid, falpost;
        ImageView falimages;

        public FacultyViewAdapter(@NotNull View itemView) {
            super ( itemView );

            falname = itemView.findViewById ( R.id.falname );
            falqualification = itemView.findViewById ( R.id.falqualification );
            falemailid = itemView.findViewById ( R.id.falemailid );
            falpost = itemView.findViewById ( R.id.falpost );
            falimages = itemView.findViewById ( R.id.falimages );

        }
    }
}
