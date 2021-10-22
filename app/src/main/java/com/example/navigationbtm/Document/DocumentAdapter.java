package com.example.navigationbtm.Document;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbtm.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {

    public Context context;
    public List<DocumentData> documentDataList;

    public DocumentAdapter(Context context, List<DocumentData> documentDataList) {
        this.context = context;
        this.documentDataList = documentDataList;
    }

    @NonNull
    @NotNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from ( context ).inflate ( R.layout.document_layout,parent,false );
        return new DocumentViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DocumentAdapter.DocumentViewHolder holder, int position) {
        DocumentData data=documentDataList.get ( position );
       holder.titleofdco.setText ( data.getTitle () );

       holder.itemView.setOnClickListener ( new View.OnClickListener () {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent ( context, Docview.class );
               intent.putExtra ( "docname", documentDataList.get ( position ).getTitle () );
               intent.putExtra ( "docurl", documentDataList.get ( position ).getDownloadurl () );
               holder.itemView.getContext ().startActivity ( intent );
           }
       } );

        holder.documentDownload.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( context,"clicked22",Toast.LENGTH_SHORT ).show ();

            }
        } );
    }

    @Override
    public int getItemCount() {
        return documentDataList.size ();
    }

    public class DocumentViewHolder extends RecyclerView.ViewHolder {
        private TextView titleofdco;
        private ImageView documentDownload;

        public DocumentViewHolder(@NonNull @NotNull View itemView) {
            super ( itemView );
            titleofdco=itemView.findViewById ( R.id.titleofdoc );
            documentDownload=itemView.findViewById ( R.id.documentdownload );
        }
    }
}

