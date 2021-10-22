package com.example.navigationbtm.ui.faculty;

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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateFacInfo extends AppCompatActivity {
    private ImageView updateimage;
    private EditText updatename,updateemail,updatepost;
    private CardView infoupdate,deleteinfo;
    private final int REQ=1;
    private Bitmap bitmap;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String downloadUrl="",uniqueKey,selectedDepartment;
    private ProgressDialog progressDialog;

    private  String name,email,post,department,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_update_fac_info );


        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Faculty" );
        storageReference= FirebaseStorage.getInstance ().getReference ();
        progressDialog=new ProgressDialog ( this );

        name=getIntent ().getStringExtra ( "name" );
        email=getIntent ().getStringExtra ( "email" );
        post=getIntent ().getStringExtra ( "post" );
        image=getIntent ().getStringExtra ( "image" );
        uniqueKey=getIntent ().getStringExtra ( "key" );
        selectedDepartment=getIntent ().getStringExtra ( "department" );


        updateimage=findViewById ( R.id.updateImage );
        updatename=findViewById ( R.id.updatename );
        updateemail=findViewById ( R.id.updatemailId );
        updatepost=findViewById ( R.id.updatepost );

        infoupdate=findViewById ( R.id.infoupdate );
        deleteinfo=findViewById ( R.id.deleteinfo );

        /* this string sets in the spinner by doing below process */
        String[] departments={"Select Catogery","Computer Science","Electronics","Electrical","Mechanical","Civil","Information Tech"};


        try {
            Picasso.get ().load ( image ).into ( updateimage );
        } catch (Exception e) {
            e.printStackTrace ();
        }

        updatename.setText ( name );
        updateemail.setText ( email );
        updatepost.setText ( post );
        updateimage.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        } );

        infoupdate.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                name=updatename.getText ().toString ();
                email=updateemail.getText ().toString ();
                post=updatepost.getText ().toString ();
                validation (name,email,post);

            }
        } );

        deleteinfo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        } );



    }

    private void deleteData() {
        databaseReference.child ( selectedDepartment ).child ( uniqueKey ).removeValue ().addOnCompleteListener ( new OnCompleteListener<Void> () {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText ( UpdateFacInfo.this,"Data deleted Successfully",Toast.LENGTH_SHORT ).show ();

                Intent intent= new Intent (UpdateFacInfo.this,UpdateFaculty.class);
                intent.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity ( intent );
            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText ( UpdateFacInfo.this,"Something went wrong in deleting data ",Toast.LENGTH_SHORT ).show ();

            }
        } );
    }

    private void validation(String name,String email,String post)
    {
        if(name.isEmpty ())
        {
            updatename.setError ( "Enter the Name to Update" );
            updatename.requestFocus ();
        }
        else if(email.isEmpty ())
        {
            updateemail.setError ( "Enter the Mail to Update" );
            updateemail.requestFocus ();
        }
        else if(post.isEmpty ())
        {
            updatepost.setError ( "Enter the Post to Update" );
            updatepost.requestFocus ();
        }
        else if(bitmap==null)
        {
            uploaddata ( image );

        }
        else
        {
            updateImage();
        }
    }

    private void updateImage()
    {
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
        filepath=storageReference.child ( "Faculty" ).child ( finalImage+"jpg" );
        final UploadTask uploadTask=filepath.putBytes ( finalImage );

        /* checking whether the task completed or not */
        uploadTask.addOnCompleteListener ( UpdateFacInfo.this, new OnCompleteListener<UploadTask.TaskSnapshot> () {
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
                                    uploaddata(downloadUrl);
                                }
                            } );
                        }
                    } );
                }
                else  /* if task failed */
                {
                    progressDialog.dismiss ();
                    Toast.makeText ( UpdateFacInfo.this,"Something Went Wrong",Toast.LENGTH_SHORT ).show ();
                }

            }
        } );
    }

    private void uploaddata(String downloadUrl) {
        HashMap hm=new HashMap ();
        hm.put ( "name",name );
        hm.put ( "email",email );
        hm.put ( "post",post );
        hm.put ( "image",downloadUrl );

        databaseReference.child ( selectedDepartment ).child ( uniqueKey ).updateChildren ( hm ).addOnSuccessListener ( new OnSuccessListener () {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText ( UpdateFacInfo.this,"Data updated Successfully",Toast.LENGTH_SHORT ).show ();
                Intent intent= new Intent (UpdateFacInfo.this,UpdateFaculty.class);
                intent.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity ( intent );

            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText ( UpdateFacInfo.this,"Something went wrong in updating data ",Toast.LENGTH_SHORT ).show ();
            }
        } );
    }



    private void openGallery() {
        Intent intent=new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
            updateimage.setImageBitmap ( bitmap );
        }
    }
}