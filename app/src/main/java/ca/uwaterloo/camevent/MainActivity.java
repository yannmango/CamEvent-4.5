package ca.uwaterloo.camevent;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class  MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {

    private static final String TAG = "MainActivity";
    private int theme = 0;
    public final static int CREATE_DIALOG  = -1;
    public final static int Red_Theme  = 0;
    public final static int Brown_Theme  = 1;
    public final static int BlueGrey_Theme=2;

    int position;

    private FragmentPagerAdapter mPagerAdapter;

    NavigationView navigationView = null;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        position = getIntent().getIntExtra("position", -1);

        switch(position)
        {
            /*case CREATE_DIALOG:
                createDialog();
                break;*/
            case Red_Theme:
                setTheme(R.style.RedTheme);
                break;
            case Brown_Theme:
                setTheme(R.style.BrownTheme);
                break;
            case BlueGrey_Theme:
                setTheme(R.style.BlueGreyTheme);
                break;
            default:
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new MyPostsFragment(),
                    new MyTopPostsFragment(),
                    new Recom(),
            };
            private final String[] mFragmentNames = new String[] {
                    "My Posts",
                    "My Top",
                    "RECOM"
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container1);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //How to change elements in the header programatically
        View headerView = navigationView.getHeaderView(0);
        TextView emailText = (TextView) headerView.findViewById(R.id.email);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            // Name, email address, and profile photo Url
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            emailText.setText(email);
        } else {
            // No user is signed in

        }

        navigationView.setNavigationItemSelectedListener(this);
    }
    private void createDialog()
    {
        /** Options for user to select*/
        String choose[] = {"Red_Theme","Brown_Theme","BlueGrey_Theme"};

        AlertDialog.Builder b = new AlertDialog.Builder(this);

        /** Setting a title for the window */
        b.setTitle("Choose your Application Theme");

        /** Setting items to the alert dialog */
        b.setSingleChoiceItems(choose, 0, null);

        /** Setting a positive button and its listener */
        b.setPositiveButton("OK",this);

        /** Setting a positive button and its listener */
        b.setNegativeButton("Cancel", null);

        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();

        /** show dialog*/
        d.show();
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        // TODO Auto-generated method stub
        AlertDialog alert = (AlertDialog)dialog;
        int position = alert.getListView().getCheckedItemPosition();

        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", position);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //new add:2016/10/27
    private void goToMapActivity() {
        //jump to second activity
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }
    private void goToSearchActivity() {
        //jump to second activity
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    private void goToPostActivity() {
        //jump to second activity
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }
    private void logOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_me, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id==R.id.nav_search){
            goToSearchActivity();
        }
        if(id==R.id.post){
            goToPostActivity();
        }
        if(id==R.id.nav_map){
            goToMapActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_theme){
            createDialog();
        }
        if(id==R.id.nav_logout){
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
