package com.example.navigationbtm.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navigationbtm.R;
import com.example.navigationbtm.ui.news.FullImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewAdapter> {

    private Context context;
    private List<ImageData> list;

    public GalleryAdapter(Context context, List<ImageData> list) {
        this.context = context;
        this.list = list;
    }

    @NotNull
    @Override
    public GalleryViewAdapter onCreateViewHolder( @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from ( context ).inflate ( R.layout.gallery_images,parent,false );
        return new GalleryViewAdapter ( view );
    }

    @Override
    public void onBindViewHolder( @NotNull GalleryAdapter.GalleryViewAdapter holder, int position) {
        ImageData data=list.get ( position );
        Picasso.get ().load (data.getImage () ).into ( holder.imagee );
        holder.imagee.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (context, FullImageView.class);
                intent.putExtra ( "image",data.getImage () );
                context.startActivity ( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class GalleryViewAdapter extends RecyclerView.ViewHolder {
        private ImageView imagee;

        public GalleryViewAdapter( @NotNull View itemView) {
            super ( itemView );
            imagee=itemView.findViewById ( R.id.imagee );

        }
    }
}
