package com.example.navigationbtm.videoLectures;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbtm.R;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.List;

public class videoAdapter extends RecyclerView.Adapter<videoAdapter.videoViewHolder> {
    private Context context;
    private List<VideoData> list;

    public videoAdapter(Context context, List<VideoData> list) {
        this.context = context;
        this.list = list;
    }


    @NotNull
    @Override
    public videoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from ( context ).inflate ( R.layout.videolayout,parent,false );
        return new videoViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull  videoAdapter.videoViewHolder holder, int position) {
        VideoData data=list.get ( position );
        holder.titleofvid.setText ( data.getTitle () );
        holder.itemView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (context,Viewvideo.class);
                intent.putExtra ( "videourl",list.get ( position ).getUrl () );
                intent.putExtra ( "videotitle",list.get ( position ).getTitle () );
                holder.itemView.getContext ().startActivity ( intent );
            }
        } );


    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class  videoViewHolder extends RecyclerView.ViewHolder {
        private TextView titleofvid;
        private ImageView videoDownload;

        public videoViewHolder(@NonNull @NotNull View itemView) {
            super ( itemView );
            titleofvid=itemView.findViewById ( R.id.titleofvid );
            videoDownload=itemView.findViewById ( R.id.videodownload );
        }
    }
}
