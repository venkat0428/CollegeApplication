package com.example.navigationbtm.ui.gallery;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class AddImage extends AppCompatActivity {
    private CardView selectImage,uploadImage;
    private Spinner imageCatogery;
    private ImageView galleryImage;
    private final int REQ=1;
    private Bitmap bitmap;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String downloadUrl= "",catogery;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_image );
        /* initializing the storageReference and databaseReference */
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Gallery" );
        storageReference= FirebaseStorage.getInstance ().getReference ().child ( "Gallery" );
        progressDialog=new ProgressDialog ( this );

        selectImage=findViewById ( R.id.selectImage );
        uploadImage=findViewById ( R.id.uploadImage );
        imageCatogery=findViewById ( R.id.imageCatogery );
        galleryImage=findViewById ( R.id.galleryImage );

        String[] list={"Select Catogery","Convocation","Fresher's day","Cult night","other events"};
        imageCatogery.setAdapter ( new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,list  ) );

        imageCatogery.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catogery=imageCatogery.getSelectedItem ().toString ();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        selectImage.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        } );

        uploadImage.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(bitmap==null)
                {
                    Toast.makeText ( AddImage.this,"Select Any Image to proceed",Toast.LENGTH_SHORT ).show ();
                }
                else if(catogery.equals ( "Select Catogery" ))
                {
                    Toast.makeText ( AddImage.this,"Select Catogery to Upload",Toast.LENGTH_SHORT ).show ();
                }
                else
                {
                    uploadGalleryImage();
                }
            }
        } );

    }

    private void uploadGalleryImage() {
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
        filepath=storageReference.child ( finalImage+"jpg" );
        final UploadTask uploadTask=filepath.putBytes ( finalImage );

        /* checking whether the task completed or not */
        uploadTask.addOnCompleteListener ( AddImage.this, new OnCompleteListener<UploadTask.TaskSnapshot> () {
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
                    Toast.makeText ( AddImage.this,"Something Went Wrong",Toast.LENGTH_SHORT ).show ();
                }

            }
        } );
    }

    private void uploaddata() {
        /* create a java class of notice data */

        /* storing data in database */
        databaseReference=databaseReference.child (catogery );

        /* databaseReference.push ().getKey () generates a unique key */
        final String uniqueKey = databaseReference.push ().getKey ();

        /* setting date */
        Calendar calfordate=Calendar.getInstance ();
        SimpleDateFormat currentdate =new SimpleDateFormat ("dd-mm-yy");
        String date = currentdate.format ( calfordate.getTime () );

        /* setting time */
        Calendar calfortime=Calendar.getInstance ();
        SimpleDateFormat currenttime =new SimpleDateFormat ("hh-mm");
        String time = currenttime.format ( calfortime.getTime () );

        ImageData noticeData=new ImageData (catogery,downloadUrl,date,time,uniqueKey);

        /* setting the values to database and checking whether success or not */
        databaseReference.child ( uniqueKey ).setValue ( noticeData ).addOnSuccessListener ( new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss ();
                Toast.makeText ( AddImage.this,"Image Uploaded Successfully",Toast.LENGTH_SHORT ).show ();
            }
        } ).addOnFailureListener ( new OnFailureListener () { /* if it failed */
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss ();
                Toast.makeText ( AddImage.this,"Something went wrong in adding Image",Toast.LENGTH_SHORT ).show ();
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
            galleryImage.setImageBitmap ( bitmap );
        }
    }
}