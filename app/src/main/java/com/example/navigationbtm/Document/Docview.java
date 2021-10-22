package com.example.navigationbtm.Document;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.navigationbtm.R;



import java.net.URLEncoder;

public class Docview extends AppCompatActivity {
    private String url,title;
    private WebView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_docview );
        url= getIntent ().getStringExtra ( "docurl" );
        title=getIntent ().getStringExtra ( "docname" );

        pdfView = findViewById(R.id.pdfView);

        pdfView.getSettings ().setJavaScriptEnabled (true);

        ProgressDialog progressDialog=new ProgressDialog ( this);
        progressDialog.setTitle ( title );
        progressDialog.setMessage ( "Opening..." );

        pdfView.setWebViewClient ( new WebViewClient ()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted ( view, url, favicon );
                progressDialog.show ();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished ( view, url );
                progressDialog.dismiss ();
            }
        });
        String surl="";
        try {
            surl= URLEncoder.encode ( url,"UTF-8" );
        }
        catch (Exception e)
        {
            Toast.makeText ( Docview.this,e.getMessage () ,Toast.LENGTH_SHORT).show ();
        }
        pdfView.loadUrl ( "http://docs.google.com/gview?embedded=true&url="+surl );






    }


}