package com.example.navigationbtm.ui.news;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbtm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.deleteViewAdapter>
{
    private ArrayList<NoticeData> list;
    private Context context;

    public DeleteAdapter(ArrayList<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public deleteViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from ( context ).inflate ( R.layout.delete,parent,false );
        return new deleteViewAdapter ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DeleteAdapter.deleteViewAdapter holder, int position) {
        NoticeData item =list.get ( position );
        holder.titleNotice.setText ( item.getTitle () );
        try {
            if(item.getImage ()!=null)
                Picasso.get ().load ( item.getImage () ).into ( holder.deleteImage );
        } catch (Exception e) {
            e.printStackTrace ();
        }
        holder.deleteButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder ( context );
                builder.setMessage ( "Do You Want to Delete this ?" );
                builder.setCancelable ( true );

                builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* refering where the data is present in database */
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Notice" );

                        /* also refering the key of the image and removing and checking completed or failed by listeners */
                        databaseReference.child ( item.getKey () ).removeValue ().addOnCompleteListener ( new OnCompleteListener<Void> () {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {

                            }
                        } ).addOnFailureListener ( new OnFailureListener () {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText ( context,"Something went wrong while deleting",Toast.LENGTH_SHORT ).show ();
                            }
                        } );
                        /* to know which item have been removed */
                        notifyItemRemoved ( position );
                    }
                } );
                builder.setNegativeButton ( "Cancel", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel ();
                    }
                } );
                AlertDialog dialog=null;

                try {
                    dialog= builder.create ();
                } catch (Exception e) {
                    e.printStackTrace ();
                }
                if(dialog!=null)
                    dialog.show ();
            }
        } );

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class deleteViewAdapter extends RecyclerView.ViewHolder {
        private Button deleteButton;
        private TextView titleNotice;
        private ImageView deleteImage;

        public deleteViewAdapter(@NonNull @NotNull View itemView) {
            super ( itemView );
            deleteButton=itemView.findViewById ( R.id.deleteButton );
            titleNotice=itemView.findViewById ( R.id.titleNotice );
            deleteImage=itemView.findViewById ( R.id.deleteImage );
        }
    }

}

