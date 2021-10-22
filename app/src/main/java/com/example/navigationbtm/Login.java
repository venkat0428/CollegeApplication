package com.example.navigationbtm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
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

public class Login extends AppCompatActivity {
    private CardView loginaccount;
    private EditText entermail, enterpassword;
    private TextView loginbtn, presentusr, switchaccount;
    private String student;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart ();
        if(mAuth.getCurrentUser ()!=null)
        {
            Intent intent=new Intent (Login.this,MainActivity.class);
            startActivity ( intent );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
        loginaccount=findViewById ( R.id.stdloginbtn );
        entermail=findViewById ( R.id.stdmail );
        enterpassword=findViewById ( R.id.stdpassword );
        switchaccount = findViewById ( R.id.switchtoadm );
        mAuth=FirebaseAuth.getInstance ();
        switchaccount.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( Login.this, Admin.class );
                intent.putExtra ( "admin", "ADMIN Login" );
                intent.putExtra ( "switch", "Login as Student" );
                startActivity ( intent );


            }
        } );
        // Set on Click Listener on Sign-in button
        loginaccount.setOnClickListener ( new View.OnClickListener () {
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
            email = entermail.getText ().toString ();
            password = enterpassword.getText ().toString ();

            // validations for input email and password
            if (TextUtils.isEmpty ( email )) {
                entermail.setText ( "Enter Mail-ID" );
                entermail.requestFocus ();
                return;
            }

            if (TextUtils.isEmpty ( password )) {
                enterpassword.setText ( "Enter Password" );
                enterpassword.requestFocus ();
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
                                                = new Intent ( Login.this,
                                                MainActivity.class );
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
