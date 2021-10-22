package com.example.navigationbtm.ui.news;

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


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {
    private ArrayList<NoticeData> list;
    private Context context;

    public NoticeAdapter(ArrayList<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NotNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.newsfeed, parent, false );
        return new NoticeViewAdapter ( view );
    }

    @Override
    public void onBindViewHolder(@NotNull NoticeAdapter.NoticeViewAdapter holder, int position) {
        NoticeData item = list.get ( position );
        holder.titleNotice.setText ( item.getTitle () );
        holder.date.setText ( item.getDate () );
        holder.time.setText ( item.getTime () );
        try {
            if (item.getImage () != null)
                Glide.with ( context ).load ( item.getImage () ).into ( holder.deleteImage );
        } catch (Exception e) {
            e.printStackTrace ();
        }

        holder.deleteImage.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (context,FullImageView.class);
                intent.putExtra ( "image",item.getImage () );
                context.startActivity ( intent );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {
        private TextView titleNotice, date, time;
        private ImageView deleteImage;

        public NoticeViewAdapter(@NotNull View itemView) {
            super ( itemView );
            titleNotice = itemView.findViewById ( R.id.titleNotice );
            deleteImage = itemView.findViewById ( R.id.deleteImage );
            time = itemView.findViewById ( R.id.time );
            date = itemView.findViewById ( R.id.date );
        }
    }


}
