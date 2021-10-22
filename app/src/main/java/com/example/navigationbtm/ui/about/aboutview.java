package com.example.navigationbtm.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.navigationbtm.R;

import java.util.ArrayList;
import java.util.List;


public class aboutview extends Fragment {

    private ViewPager viewPager;
    private BranchAdapter branchAdapter;
    private List<BranchModel> branchModelList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate ( R.layout.fragment_aboutview, container, false );

        branchModelList=new ArrayList<> ();
        branchModelList.add ( new BranchModel ( R.drawable.cse,"Computer Science","To be committed in providing quality education in professional core and multidisplinary areas with continuous upgradation and to prepare IT graduates succeed in industry as an individual and as a team.") );
        branchModelList.add ( new BranchModel ( R.drawable.collegeicon,"mechanical","To be committed in providing quality education in professional core and multidisplinary areas with continuous upgradation and to prepare IT graduates succeed in industry as an individual and as a team." ) );
        branchModelList.add ( new BranchModel ( R.drawable.it,"Information Tech","To be committed in providing quality education in professional core and multidisplinary areas with continuous upgradation and to prepare IT graduates succeed in industry as an individual and as a team") );
        branchModelList.add ( new BranchModel ( R.drawable.electrical,"Electrical","To be committed in providing quality education in professional core and multidisplinary areas with continuous upgradation and to prepare IT graduates succeed in industry as an individual and as a team") );
        branchModelList.add ( new BranchModel ( R.drawable.electronics,"Electronics","To be committed in providing quality education in professional core and multidisplinary areas with continuous upgradation and to prepare IT graduates succeed in industry as an individual and as a team") );
        branchModelList.add ( new BranchModel ( R.drawable.civil,"Civil","To be committed in providing quality education in professional core and multidisplinary areas with continuous upgradation and to prepare IT graduates succeed in industry as an individual and as a team") );

        branchAdapter=new BranchAdapter (getContext (),branchModelList);
        viewPager=view.findViewById ( R.id.viewPager );
        viewPager.setAdapter ( branchAdapter );

        ImageView collegeImage=view.findViewById ( R.id.imageCollege );
        Glide.with ( getContext () ).load ( "https://getmyuni.azureedge.net/college-image/big/sreenidhi-institute-of-science-and-technology-snist-hyderabad.jpg" ).into ( collegeImage );
        return view;
    }
}