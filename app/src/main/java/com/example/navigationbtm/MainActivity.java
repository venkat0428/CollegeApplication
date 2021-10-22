package com.example.navigationbtm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.navigationbtm.Document.Document;
import com.example.navigationbtm.ui.about.aboutview;
import com.example.navigationbtm.ui.faculty.facultyview;
import com.example.navigationbtm.ui.gallery.galleryview;
import com.example.navigationbtm.ui.home.homeview;
import com.example.navigationbtm.ui.news.newsview;
import com.example.navigationbtm.videoLectures.VideoLectures;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkeditem;
    private String selected;
    private final String CHECKEDITEM="checked_item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );


        sharedPreferences=this.getSharedPreferences ( "Themes", Context.MODE_PRIVATE );
        editor =sharedPreferences.edit ();
        switch (getCheckedItem ())
        {
            case 0:
                AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM );
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_YES );
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_NO );

                break;
        }
        setCheckedItem ( checkeditem );

        bottomNavigationView=findViewById ( R.id.bottomNavigation );
        navController= Navigation.findNavController ( MainActivity.this,R.id.fragmentLayout );

        drawerLayout=findViewById ( R.id.drawerLayout );
        navigationView=findViewById ( R.id.navigation_view );

        drawerToggle=new ActionBarDrawerToggle ( this,drawerLayout,R.string.nav_open,R.string.nav_close );

        NavigationUI.setupWithNavController ( bottomNavigationView,navController );
        drawerLayout.addDrawerListener ( drawerToggle );
        drawerToggle.syncState ();
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );

        navigationView.setNavigationItemSelectedListener ( this );

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
        switch (item.getItemId ())
        {
            case R.id.navigation_visit:
                openMap();
                break;
            case R.id.navigation_documents:
                Intent intent=new Intent (MainActivity.this, Document.class );
                startActivity ( intent );
                break;
            case R.id.navigation_website:
            {
                gotoUrl("https://www.sreenidhi.edu.in/");
            }
            break;
            case R.id.navigation_theme:
                showDialog ( );
                break;

            case R.id.navigation_video:
            {
                Intent intent1=new Intent (MainActivity.this, VideoLectures.class );
                startActivity ( intent1 );
            }
            break;
            case R.id.navigation_developer:{
                FirebaseAuth.getInstance ().signOut ();
                startActivity ( new Intent (MainActivity.this,Login.class) );
            }
                
        }
        return false;
    }

    private void openMap() {
        Uri uri=Uri.parse ( "https://www.google.com/maps/dir//snist+google+maps/@17.45531,78.596456,12z/data=!3m1!4b1!4m9!4m8!1m1!4e2!1m5!1m1!1s0x3bcb7612c2c68369:0x9ea4b426b7e3699!2m2!1d78.6664965!2d17.4553223" );
        Intent intent=new Intent (Intent.ACTION_VIEW,uri) ;
        intent.setPackage ( "com.google.android.apps.maps" );
        startActivity ( intent );

    }

    private void showDialog() {
        String[] themes=this.getResources ().getStringArray ( R.array.Theme );
        MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder ( this );
        builder.setTitle ( "Select Theme" );
        builder.setSingleChoiceItems ( R.array.Theme, getCheckedItem (), new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                selected=themes[i];
                checkeditem=i;

            }
        } );
        builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (selected==null)
                {
                    selected=themes[i];
                    checkeditem=i;
                }
                switch (selected)
                {
                    case "System default":
                        AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM );
                        break;
                    case "Dark":
                        AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_YES );
                        break;
                    case "Light":
                        AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_NO );

                        break;
                }
                setCheckedItem(i);
            }
        } );
        builder.setNegativeButton ( "Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss ();
            }
        } );
        AlertDialog alertDialog=builder.create ();
        alertDialog.show ();
    }


    private int getCheckedItem()
    {
        return sharedPreferences.getInt ( CHECKEDITEM,0 );
    }

    private void setCheckedItem(int i)
    {
        editor.putInt (CHECKEDITEM,i );
        editor.apply ();
    }

    private void gotoUrl(String s) {
        Uri uri=Uri.parse ( s );
        startActivity ( new Intent (Intent.ACTION_VIEW,uri) );
    }

    @Override
    public void onBackPressed() {
        /* if drawer is open and we pressed back button then the drawer had to close not the app so writing this */
        if(drawerLayout.isDrawerOpen ( GravityCompat.START ))
        {
            drawerLayout.closeDrawer ( GravityCompat.START );
        }
        else
        super.onBackPressed ();
    }
}