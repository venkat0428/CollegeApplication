package com.example.navigationbtm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.navigationbtm.Document.AddDocument;
import com.example.navigationbtm.ui.faculty.UpdateFaculty;
import com.example.navigationbtm.ui.gallery.AddImage;
import com.example.navigationbtm.ui.news.AddNotice;
import com.example.navigationbtm.ui.news.DeleteNotice;
import com.example.navigationbtm.videoLectures.AddLectures;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {
    private CardView notice,document,gallery,faculty,delete,video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_admin_home );
        notice=findViewById ( R.id.notice );
        notice.setOnClickListener ( this );
        document=findViewById ( R.id.document );
        document.setOnClickListener ( this );
        gallery=findViewById ( R.id.gallery );
        gallery.setOnClickListener ( this );
        faculty=findViewById ( R.id.faculty );
        faculty.setOnClickListener ( this );
        delete=findViewById ( R.id.delete );
        delete.setOnClickListener ( this );
        video=findViewById ( R.id.video );
        video.setOnClickListener ( this );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId ())
        {
            case R.id.notice: {
                Intent intent = new Intent ( AdminHome.this, AddNotice.class );
                startActivity ( intent );
            }
            break;
            case R.id.document: {
                Intent intent =new Intent (AdminHome.this, AddDocument.class);
                startActivity ( intent );
            }
            break;
            case R.id.gallery:{
                Intent intent=new Intent (AdminHome.this, AddImage.class);
                startActivity ( intent );
            }
            break;
            case R.id.faculty:{
                Intent intent=new Intent (AdminHome.this, UpdateFaculty.class );
                startActivity ( intent );
            }
            break;
            case R.id.delete:{
                Intent intent=new Intent (AdminHome.this, DeleteNotice.class );
                startActivity ( intent );
            }
            break;
            case R.id.video:{
                Intent intent=new Intent (AdminHome.this, AddLectures.class );
                startActivity ( intent );
            }
            break;

        }
    }
}