package com.example.navigationbtm.ui.faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbtm.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FacAdapter extends RecyclerView.Adapter<FacAdapter.FacViewAdapter> {
    public List<FacultyData> list;
    public Context context;
    private String selectedDepartment;

    public FacAdapter(List<FacultyData> list, Context context,String selectedDepartment) {
        this.list = list;
        this.context = context;
        this.selectedDepartment=selectedDepartment;
    }

    @NonNull
    @NotNull
    @Override
    public FacViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.facdatafound,parent,false );
        return new FacViewAdapter ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FacAdapter.FacViewAdapter holder, int position) {
        FacultyData item=list.get ( position );
        holder.falname.setText ( item.getName () );
        holder.falqualification.setText ( item.getQualification () );
        holder.falemailid.setText ( item.getMailId () );
        holder.faldepartment.setText ( item.getDepartment () );
        holder.falpost.setText ( item.getPost () );
        try {
            Picasso.get ().load ( item.getImage () ).into ( holder.falimages );
        } catch (Exception e) {
            e.printStackTrace ();
        }

        holder.updateInfo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (context,UpdateFacInfo.class);
                intent.putExtra ( "name",item.getName () );
                intent.putExtra ( "email",item.getMailId () );
                intent.putExtra ( "post",item.getPost () );
                intent.putExtra ( "image",item.getImage () );
                intent.putExtra ( "key",item.getKey () );
                intent.putExtra ( "department",selectedDepartment );
                context.startActivity ( intent );
            }
        } );


    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class FacViewAdapter extends RecyclerView.ViewHolder {
        TextView falname,falqualification,faldepartment,falemailid,falpost;
        ImageView falimages;
        Button updateInfo;
        public FacViewAdapter(@NonNull @NotNull View itemView) {
            super ( itemView );
            falname=itemView.findViewById ( R.id.falname );
            falqualification=itemView.findViewById ( R.id.falqualification );
            faldepartment=itemView.findViewById ( R.id.faldepartment );
            falemailid=itemView.findViewById ( R.id.falemailid );
            falpost=itemView.findViewById ( R.id.falpost );
            falimages=itemView.findViewById ( R.id.falimages );
            updateInfo=itemView.findViewById ( R.id.updateInfo );
        }
    }
}
