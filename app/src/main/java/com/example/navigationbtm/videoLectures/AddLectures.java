package com.example.navigationbtm.videoLectures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddLectures extends AppCompatActivity {
    private CardView selectVideo,uploadvideo;
    private EditText videoTitle;
    private StorageReference storageReference;
    private DatabaseReference databaseReference,dbref;
    private Uri lectureData;
    private ProgressDialog progressDialog;
    private final  int REQ=1;
    String title;
    private TextView VideoName;
    private static final int PICK_VIDEO=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_lectures );
        selectVideo=findViewById ( R.id.selectvideo );
        uploadvideo=findViewById ( R.id.uploadVideo );
        videoTitle=findViewById ( R.id.videoTitle );
        VideoName=findViewById ( R.id.videoName );
        progressDialog=new ProgressDialog ( this );
        storageReference= FirebaseStorage.getInstance ().getReference ();
        databaseReference= FirebaseDatabase.getInstance ().getReference ();

        selectVideo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openFiles();
            }
        } );

        uploadvideo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(lectureData==null)
                {
                    Toast.makeText ( AddLectures.this,"Please Select video to proceed ",Toast.LENGTH_SHORT );
                }
                else if(videoTitle.equals ( "" ))
                {
                    videoTitle.setText ( "Enter title to proceed" );
                    videoTitle.requestFocus ();
                }
                else
                {
                    uploadVideo();
                }
            }
        } );



    }

    private void uploadVideo() {
        progressDialog.setMessage ( "Uploading..." );
        progressDialog.show ();
        title=videoTitle.getText ().toString ();
        StorageReference storageReference1=storageReference.child ( "video/"+title+"="+System.currentTimeMillis ());
        storageReference1.putFile ( lectureData ).addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                /* when the doc is uploaded we take the url and to get url we perform below operation */
                Task<Uri> uriTask=taskSnapshot.getStorage ().getDownloadUrl ();

                /* we perform the same operation untill we get the taskurl */
                while(!uriTask.isComplete ());

                Uri uri =uriTask.getResult ();
                uploadData(String.valueOf ( uri ));

            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText ( AddLectures.this,"Failed to upload video",Toast.LENGTH_SHORT );
            }
        } );


    }

    private void uploadData(String downloadurl) {
        dbref=databaseReference.child ("Lectures" );
        final String uniqueKey = dbref.push ().getKey ();
        Calendar calfordate=Calendar.getInstance ();
        SimpleDateFormat currentdate =new SimpleDateFormat ("dd-mm-yy");
        String date = currentdate.format ( calfordate.getTime () );

        /* setting time */
        Calendar calfortime=Calendar.getInstance ();
        SimpleDateFormat currenttime =new SimpleDateFormat ("hh-mm");
        String time = currenttime.format ( calfortime.getTime () );
        VideoData videoData=new VideoData ( title ,downloadurl,date,time);
        dbref.child ( uniqueKey ).setValue ( videoData ).addOnCompleteListener ( new OnCompleteListener<Void> () {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                progressDialog.dismiss ();
                Toast.makeText ( AddLectures.this,"Video Uploaded Successfully",Toast.LENGTH_SHORT ).show ();

            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText ( AddLectures.this,"Video Upload failed",Toast.LENGTH_SHORT ).show ();

            }
        } );

    }

    private void openFiles() {
        Intent intent=new Intent ( );
        intent.setType ( "video/*" );
        intent.setAction ( Intent.ACTION_GET_CONTENT );
        startActivityForResult (intent,PICK_VIDEO );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );

        if(requestCode==PICK_VIDEO && resultCode==RESULT_OK) {
            lectureData=data.getData ();

        }
        if(lectureData!=null)
        {
            VideoName.setText ( "Video Selected" );
        }
        else
        {
            VideoName.setText ( "No Video Selected" );
        }

    }
    private String getFile(Uri lectureData)
    {
        ContentResolver contentResolver=getContentResolver ();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton ();
        return mimeTypeMap.getExtensionFromMimeType ( contentResolver.getType ( lectureData ) );
    }



}