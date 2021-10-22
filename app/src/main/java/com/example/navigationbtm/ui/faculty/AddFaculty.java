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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class AddFaculty extends AppCompatActivity {
    private ImageView facultyImage;
    private EditText nameOfFaculty,qualification,mailId,post;
    private Spinner selectDepartment;
    private CardView addFaculty;
    private final int REQ=1;
    private Bitmap bitmap;
    private String selectedDepartment;
    private StorageReference storageReference;
    private DatabaseReference databaseReference,dbref;
    private ProgressDialog progressDialog;
    private String downloadUrl="";
    private String Name,Qualification,MailId,Post,Department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_faculty );
        /* initializing the storageReference and databaseReference */
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ( "Faculty" );
        storageReference= FirebaseStorage.getInstance ().getReference ();
        progressDialog=new ProgressDialog ( this );

        facultyImage=findViewById ( R.id.facultyImage );
        nameOfFaculty=findViewById ( R.id.nameOfFaculty );
        qualification=findViewById ( R.id.qualification );
        mailId=findViewById ( R.id.mailId );
        post=findViewById ( R.id.post );
        selectDepartment=findViewById ( R.id.selectDepartment );
        addFaculty=findViewById ( R.id.addFaculty );

        facultyImage.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        } );

        /* this string sets in the spinner by doing below process */
        String[] departments={"Select Catogery","Computer Science","Electronics","Electrical","Mechanical","Civil","Information Tech"};
        selectDepartment.setAdapter ( new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item,departments  ) );
        selectDepartment.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment=selectDepartment.getSelectedItem ().toString ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


        /* now after clicking the add button we have to check whether we entered all the data or not */
        addFaculty.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                /* checking whether we selected image or not */
                if(bitmap==null)
                {
                    Toast.makeText (AddFaculty.this,"Please select Image to proceed",Toast.LENGTH_SHORT).show ();
                }
                else if(nameOfFaculty.getText ().toString ().equals ( "" )||qualification.getText ().toString ().equals ( "" )||
                        mailId.getText ().toString ().equals ( "" ) || post.getText ().toString ().equals ( "" )
                        || selectedDepartment.equals ( "Select Catogery" ))
                {
                    Toast.makeText (AddFaculty.this,"Sorry U have missed Something",Toast.LENGTH_SHORT).show ();

                }
                else
                {
                    /* if we have entered every thing then we proceed to uploading the faculty data */
                    uploadFacultyData();
                }
            }
        } );

    }

    private void uploadFacultyData() {
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
        uploadTask.addOnCompleteListener ( AddFaculty.this, new OnCompleteListener<UploadTask.TaskSnapshot> () {
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
                    Toast.makeText ( AddFaculty.this,"Something Went Wrong",Toast.LENGTH_SHORT ).show ();
                }

            }
        } );
    }

    private void uploaddata() {
        Name=nameOfFaculty.getText ().toString ();
        Qualification=qualification.getText ().toString ();
        MailId=mailId.getText ().toString ();
        Post=post.getText ().toString ();
        /* create a java class of notice data */

        /* storing data in database */
        dbref=databaseReference.child ( selectedDepartment );

        /* databaseReference.push ().getKey () generates a unique key */
        final String uniqueKey = dbref.push ().getKey ();



        FacultyData facultyData=new FacultyData ( downloadUrl,Name,Qualification,MailId,Post,selectedDepartment,uniqueKey );

        /* setting the values to database and checking whether success or not */
        dbref.child ( uniqueKey ).setValue ( facultyData ).addOnSuccessListener ( new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss ();
                Toast.makeText ( AddFaculty.this,"Faculty Added Successfully",Toast.LENGTH_SHORT ).show ();
            }
        } ).addOnFailureListener ( new OnFailureListener () { /* if it failed */
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss ();
                Toast.makeText ( AddFaculty.this,"Something went wrong in adding faculty",Toast.LENGTH_SHORT ).show ();
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
            facultyImage.setImageBitmap ( bitmap );
        }
    }
}