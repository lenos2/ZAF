package com.kapture.zaf;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kapture.zaf.fragments.ArtistDetailFragment;
import com.kapture.zaf.fragments.ArtistsFragment;
import com.kapture.zaf.fragments.BuyTicketDetailFragment;
import com.kapture.zaf.fragments.BuyTicketFragment;
import com.kapture.zaf.fragments.EventDetailsFragment;
import com.kapture.zaf.fragments.EventsLineUpFragment;
import com.kapture.zaf.fragments.GalleryFragment;
import com.kapture.zaf.fragments.HomeFragment;
import com.kapture.zaf.fragments.SignUpFragment;
import com.kapture.zaf.fragments.StartFragment;
import com.kapture.zaf.pojos.Event;
import com.kapture.zaf.pojos.Ticket;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  static final String TICKET = "ticket";
    static ImageButton ibMenu;

    StartFragment sf;
    SignUpFragment suf;
    HomeFragment hf;
    ArtistsFragment af;
    ArtistDetailFragment adf;
    EventsLineUpFragment elf;
    EventDetailsFragment edf;
    GalleryFragment gf;
    BuyTicketFragment btf;
    BuyTicketDetailFragment btdf;


    FragmentManager fm = getFragmentManager();
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the Notification Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                ibMenu.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                ibMenu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ibMenu = (ImageButton)findViewById(R.id.ibmenu);
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                    ibMenu.setVisibility(View.GONE);
                }
            }
        });

       setUpFragments();


        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.start_screen, sf);
        fragmentTransaction.commit();
    }

    private void setUpFragments(){
        sf = new StartFragment();
        suf = new SignUpFragment();
        hf = new HomeFragment();
        af = new ArtistsFragment();
        adf = new ArtistDetailFragment();
        elf = new EventsLineUpFragment();
        edf = new EventDetailsFragment();
        gf = new GalleryFragment();
        btf = new BuyTicketFragment();

        btf.setOnTicketPickedListener(new BuyTicketFragment.OnTicketPickedListener() {
            @Override
            public void onClick(Ticket t) {
                btdf = setBtdf(t);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,btdf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        suf.setOnLogInListener(new SignUpFragment.OnLogInListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,hf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ibMenu.setVisibility(View.VISIBLE);
            }
        });

        elf.setOnListItemClickListener(new EventsLineUpFragment.OnListItemClickListener() {
            @Override
            public void onClick(Event event) {
                Toast.makeText(MainActivity.this,"The selected Option is : "+ event.getName(),Toast.LENGTH_SHORT).show();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,edf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        sf.setOnClickListener(new StartFragment.ClickListener() {
            @Override
            public void onClickSignInActual() {
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,hf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ibMenu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onClickSignUp() {
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,suf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        hf.setOnClickListener(new HomeFragment.OnHomeClickListener() {
            @Override
            public void onClickEvents() {
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,elf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onClickArtists() {
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,af);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onClickPictures() {
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,gf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        af.setOnArtistClickListener(new ArtistsFragment.OnArtistClickListener() {
            @Override
            public void onArtistClick(String artist) {
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,adf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private BuyTicketDetailFragment setBtdf(Ticket t){

        BuyTicketDetailFragment f = new BuyTicketDetailFragment();
        Bundle extra = new Bundle();
        extra.putSerializable(TICKET,t);
        f.setArguments(extra);

        return f;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
            ibMenu.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
        ibMenu.setVisibility(View.VISIBLE);
        return true;
    }
}
