package com.example.navigationbtm.ui.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.navigationbtm.R;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BranchAdapter extends PagerAdapter {
    private Context context;
    private List<BranchModel> branchModelList;

    public BranchAdapter(Context context,List<BranchModel> branchModelList) {
        this.context=context;
        this.branchModelList=branchModelList;
    }

    @Override
    public int getCount() {
        return branchModelList.size ();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view.equals ( object );
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view= LayoutInflater.from (context ).inflate ( R.layout.branchitem,container,false );
        ImageView branchimage;
        TextView branchTitle,branchDescription;
        branchDescription=view.findViewById ( R.id.branchdescription );
        branchimage=view.findViewById ( R.id.branchimage );
        branchTitle=view.findViewById ( R.id.branchTitle );

        branchimage.setImageResource ( branchModelList.get ( position ).getImg () );
        branchTitle.setText ( branchModelList.get ( position ).getTitle () );
        branchDescription.setText ( branchModelList.get ( position ).getDescription () );

        container.addView ( view,0 );
        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
       container.removeView ( (View )object );
    }
}
