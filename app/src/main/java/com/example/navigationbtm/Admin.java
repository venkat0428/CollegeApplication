package com.example.navigationbtm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {
    private TextView adminusr,switchtostd;
    private String admin,switchacc;
    private EditText adminmail,adminpassword;
    private CardView adminlogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart ();
        if(mAuth.getCurrentUser ()!=null)
        {
            Intent intent=new Intent (Admin.this,AdminHome.class);
            startActivity ( intent );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_admin );
        adminusr=findViewById ( R.id.presentadmin );
        admin=getIntent ().getStringExtra ( "admin" );
        switchacc=getIntent ().getStringExtra ( "switch" );
        adminusr.setText ( admin );
        mAuth=FirebaseAuth.getInstance ();

        switchtostd=findViewById ( R.id.switchtostd );
        switchtostd.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (Admin.this,Login.class);

                startActivity ( intent );
            }
        } );
        switchtostd.setText ( switchacc);

        adminmail=findViewById ( R.id.adminmail );
        adminpassword=findViewById ( R.id.adminpassword );
        adminlogin=findViewById ( R.id.adminloginbtn );

        adminlogin.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                loginUserAccount ();
            }
        } );


    }
    private void loginUserAccount ()
    {

        // show the visibility of progress bar to show loading

        // Take the value of two edit texts in Strings
        String email, password;
        email = adminmail.getText ().toString ();
        password = adminpassword.getText ().toString ();

        // validations for input email and password
        if (TextUtils.isEmpty ( email )) {
            adminmail.setText ( "Enter Mail-ID" );
            adminmail.requestFocus ();
            return;
        }

        if (TextUtils.isEmpty ( password )) {
            adminpassword.setText ( "Enter Password" );
            adminpassword.requestFocus ();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword ( email, password )
                .addOnCompleteListener (
                        new OnCompleteListener<AuthResult> () {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task) {
                                if (task.isSuccessful ()) {
                                    Toast.makeText ( getApplicationContext (),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG )
                                            .show ();
                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent ( Admin.this,
                                            AdminHome.class );
                                    startActivity ( intent );
                                } else {

                                    // sign-in failed
                                    Toast.makeText ( getApplicationContext (),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG )
                                            .show ();

                                }
                            }
                        } );


    }

}