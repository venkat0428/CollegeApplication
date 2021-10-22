package com.example.navigationbtm.Document;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
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

import java.io.File;

public class AddDocument extends AppCompatActivity {
    private CardView selectDocument,uploadDocument;
    private EditText documentTitle;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Uri documentData;
    private String docName,title;
    private ProgressDialog progressDialog;
    private final  int REQ=1;
    private TextView documentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_document );
        storageReference= FirebaseStorage.getInstance ().getReference ();
        databaseReference= FirebaseDatabase.getInstance ().getReference ();
        progressDialog=new ProgressDialog ( this );

        selectDocument=findViewById ( R.id.selectdocument );
        uploadDocument=findViewById ( R.id.uploadDocument );
        documentTitle=findViewById ( R.id.documentTitle );
        documentName=findViewById ( R.id.documentName );

        selectDocument.setOnClickListener ( new View.OnClickListener () {
            /* on clicking select notice we have to move to gallery so creating opengallery method */
            @Override
            public void onClick(View v) {
                openGallery();
                /* moves to open gallery method */
            }
        } );

        /* upload the document */
        uploadDocument.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                title=documentTitle.getText ().toString ();

                /* checking whether we named the document or not */
                if(title.isEmpty ())
                {
                    documentTitle.setError ( "SORRY ENTER TITLE TO PROCEED" );
                    documentTitle.requestFocus ();
                }
                /* if not document seleted */
                else if(documentData==null)
                {
                    Toast.makeText ( AddDocument.this,"Select any DOCUMENT to proceed",Toast.LENGTH_SHORT ).show ();
                }
                else
                {
                    uploadDoc();
                }

            }
        } );
    }

    private void uploadDoc() {
        progressDialog.setMessage ( "Uploading document..." );
        progressDialog.show ();

        /* to make the doc name unique we are taking currenttimemillis */
        StorageReference storageReference1=storageReference.child ( "doc/"+docName+"="+System.currentTimeMillis () );

        storageReference1.putFile ( documentData ).addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
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
                progressDialog.dismiss ();

                Toast.makeText ( AddDocument.this,"Something went wrong ",Toast.LENGTH_SHORT );
            }
        } );
    }

    private void uploadData(String downloadUrl) {
        /* databaseReference.push ().getKey () generates a unique key */
        String uniquekey=databaseReference.child ( "doc" ).push ().getKey ();

        /* now we create a Hashmap to use to store data */
        DocumentData data=new DocumentData (documentTitle.getText ().toString (),downloadUrl);

        /* setting path of database storage */
        databaseReference.child ( "doc" ).child ( uniquekey ).setValue ( data ).addOnCompleteListener ( new OnCompleteListener<Void> () {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                progressDialog.dismiss ();
                Toast.makeText ( AddDocument.this," DOCUMENT UPLOADED SUCCESSFULLY ",Toast.LENGTH_SHORT ).show ();

            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss ();
                Toast.makeText ( AddDocument.this,"Failed to upload DOCUMENT ",Toast.LENGTH_SHORT ).show ();
            }
        } );
    }


    private void openGallery() {
        Intent intent =new Intent ( );     /* to select docs we dont have path like images to following different way */
        intent.setType ( "pdf/docs/ppt" ); /* "*" is to allow selection of all types of files */
        intent.setAction (Intent.ACTION_GET_CONTENT );
        startActivityForResult ( Intent.createChooser ( intent,"select document" ),REQ );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        /* if we successfully moved to gallery activity this method occurs */
        super.onActivityResult ( requestCode, resultCode, data );

        if(requestCode==REQ && resultCode==RESULT_OK)
        {
            /* took uri above because we get result in form of uri */
            documentData=data.getData ();            /* Uri documentData*/

            if(documentData.toString ().startsWith ( "content://" ))
            {
                Cursor cursor=null;
                try {
                    cursor =AddDocument.this.getContentResolver ().query ( documentData,null,null,null,null );

                    /* now checking whether cursur not null and it has something */
                    if(cursor!=null && cursor.moveToFirst ())
                    {
                        docName=cursor.getString ( cursor.getColumnIndex ( OpenableColumns.DISPLAY_NAME ) );

                        /* now setting the name to know we have selected a file */
                        documentName.setText ( docName );
                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                }

            }
            else if(documentData.toString ().startsWith ( "file://" ))
            {
                docName =new File (documentData.toString ()).getName ();
            }
        }
    }

}