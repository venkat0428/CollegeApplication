package com.example.navigationbtm.ui.news;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.navigationbtm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNotice extends AppCompatActivity {
    private CardView selectnotice,uploadNotice;
    private EditText noticeTitle;
    private final int REQ=1;
    private Bitmap bitmap;
    private ImageView noticeImage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference,dbref;
    private String downloadUrl= "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_notice );
        /* initializing the storageReference and databaseReference */
        databaseReference= FirebaseDatabase.getInstance ().getReference ();
        storageReference= FirebaseStorage.getInstance ().getReference ();
        progressDialog=new ProgressDialog ( this );


        setContentView ( R.layout.activity_add_notice );
        selectnotice=findViewById ( R.id.selectnotice );
        noticeTitle=findViewById ( R.id.noticeTitle );
        noticeImage=findViewById ( R.id.noticeImage );
        uploadNotice=findViewById ( R.id.uploadNotice );

        selectnotice.setOnClickListener ( new View.OnClickListener () {
            /* on clicking select notice we have to move to gallery so creating opengallery method */
            @Override
            public void onClick(View v) {
                openGallery();
                /* moves to open gallery method */
            }
        } );

        uploadNotice.setOnClickListener ( new View.OnClickListener () {
            /* after selecting image and storing in noticeimage we have to upload notice to firebase
            so on clicking upload notice we have store the data
             */
            @Override
            public void onClick(View v) {
                /* but first we have check if the image selected or not and named a title to it */
                if(noticeTitle.getText ().toString ().isEmpty ())
                {
                    noticeTitle.setError ( "PLEASE ENTER THE TITLE " );
                    noticeTitle.requestFocus ();
                }
                else if(bitmap==null)
                {
                    noticeTitle.setError ( "NOTICE NOT SELECTED ,PLEASE SELECT ANY NOTICE" );
                }
                else
                {
                    uploadImage();
                }


            }
        } );
    }

    private void uploadImage() {
        /* progressdialog appears while performing task */
        progressDialog.setMessage ( "Uploading..." );
        progressDialog.show ();

        /* to upload image first we have to compress the image */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream ();

        /* compress() takes 3 parameters ,first to which format we have to compress ,second the quatity in form of int
        third sending byteArrayOutputStream created */
        bitmap.compress (Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream  );

        /* byte stores the compressed image */
        byte[] finalImage=byteArrayOutputStream.toByteArray ();

        /* now creating the filepath we want to store the image */
        final StorageReference filepath;

        /* now setting the path and format */
        filepath=storageReference.child ( "Notice" ).child ( finalImage+"jpg" );
        final UploadTask uploadTask=filepath.putBytes ( finalImage );

        /* checking whether the task completed or not */
        uploadTask.addOnCompleteListener ( AddNotice.this, new OnCompleteListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful ())
                {
                    uploadTask.addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener ( new OnSuccessListener<Uri> () {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf ( uri );

                                    /* now uploading the data */
                                    uploaddata();
                                }
                            } );
                        }
                    } );
                }
                else  /* if task failed */
                {
                    progressDialog.dismiss ();
                    Toast.makeText ( AddNotice.this,"Something Went Wrong",Toast.LENGTH_SHORT ).show ();
                }

            }
        } );
    }

    private void uploaddata() {
        /* create a java class of notice data */

        /* storing data in database */
        dbref=databaseReference.child ( "Notice" );

        /* databaseReference.push ().getKey () generates a unique key */
        final String uniqueKey = dbref.push ().getKey ();

        /* setting date */
        Calendar calfordate=Calendar.getInstance ();
        SimpleDateFormat currentdate =new SimpleDateFormat ("dd-mm-yy");
        String date = currentdate.format ( calfordate.getTime () );

        /* setting time */
        Calendar calfortime=Calendar.getInstance ();
        SimpleDateFormat currenttime =new SimpleDateFormat ("hh-mm");
        String time = currenttime.format ( calfortime.getTime () );

        NoticeData noticeData=new NoticeData (noticeTitle.getText ().toString (),downloadUrl,date,time,uniqueKey);

        /* setting the values to database and checking whether success or not */
        dbref.child ( uniqueKey ).setValue ( noticeData ).addOnSuccessListener ( new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss ();
                Toast.makeText ( AddNotice.this,"Notice Uploaded Successfully",Toast.LENGTH_SHORT ).show ();
            }
        } ).addOnFailureListener ( new OnFailureListener () { /* if it failed */
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss ();
                Toast.makeText ( AddNotice.this,"Something went wrong in adding data",Toast.LENGTH_SHORT ).show ();
            }
        } );
    }



    private void openGallery() {
        /* to move from present activity to gallery activity */
        Intent intent =new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult ( intent,REQ );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        /* if we successfully moved to gallery activity this method occurs */
        super.onActivityResult ( requestCode, resultCode, data );
        if(requestCode==REQ && resultCode==RESULT_OK)
        {
            /* take uri because we get result in form of uri */
            Uri uri =data.getData ();

            /* now we have to change uri to bitmap so created bitmap above */
            /* while doing this we might get exceptions so surrounded using try and catch block */
            try {

                bitmap=MediaStore.Images.Media.getBitmap ( getContentResolver (),uri );
                /* getbitmap converts the uri to bitmap  and bitmap takes 2 parameters content and uri so passing them
                getcontentresolver gets content */

            } catch (IOException e) {
                e.printStackTrace ();
            }

            /* now setting the image in bitmap to noticeimage */
            noticeImage.setImageBitmap ( bitmap );
        }
    }
}