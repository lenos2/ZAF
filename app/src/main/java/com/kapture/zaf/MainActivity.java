package com.kapture.zaf;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import com.kapture.zaf.pojos.Event2;
import com.kapture.zaf.pojos.Sale;
import com.kapture.zaf.pojos.Ticket;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  static final String TICKET = "ticket";
    static ImageButton ibMenuOpen;
    static Button btnTickets;
    TextView tvLogOut;
    public static Sale boughtTicket;
    static boolean ic1 = false,ic2 = false,ic3 = false,ic4 = false;
    DrawerLayout drawer;

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
    SmsVerifyCatcher smsVerifyCatcher;

    ListView lvPeople,lvItinerary,lvStages;
    ArrayList<String> people,itinerary,stages;
    ArrayAdapter<String> adpPeople,adpItinerary,adpStages;


    FragmentManager fm = getFragmentManager();
    FragmentTransaction fragmentTransaction;

    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(final View drawerView) {
            ibMenuOpen.setVisibility(View.GONE);
            lvPeople = (ListView)drawerView.findViewById(R.id.listviewPeople);
            lvItinerary = (ListView)drawerView.findViewById(R.id.listviewItinerary);
            lvStages = (ListView)drawerView.findViewById(R.id.listviewStages);

            tvLogOut = (TextView)drawerView.findViewById(R.id.tvLogOut);
            tvLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();

                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.start_screen, sf);
                    fragmentTransaction.commit();

                }
            });

            adpPeople = new ArrayAdapter<String>(drawerView.getContext(),R.layout.layout_menu_list_item,people){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                    View row;

                    if (null == convertView) {
                        LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                        row = mInflater.inflate(R.layout.layout_menu_list_item, null);
                    } else {
                        row = convertView;
                    }

                    TextView tv = (TextView) row.findViewById(R.id.tvSubMenuItem);
                    tv.setText(getItem(position));

                    return row;
                    //return super.getView(position, convertView, parent);
                }
            };
            lvPeople.setAdapter(adpPeople);

            adpStages = new ArrayAdapter<String>(drawerView.getContext(),R.layout.layout_menu_list_item,stages){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                    View row;

                    if (null == convertView) {
                        LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                        row = mInflater.inflate(R.layout.layout_menu_list_item, null);
                    } else {
                        row = convertView;
                    }

                    TextView tv = (TextView) row.findViewById(R.id.tvSubMenuItem);
                    tv.setText(getItem(position));

                    return row;
                    //return super.getView(position, convertView, parent);
                }
            };
            lvStages.setAdapter(adpStages);

            adpItinerary = new ArrayAdapter<String>(drawerView.getContext(),R.layout.layout_menu_list_item,itinerary){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                    View row;

                    if (null == convertView) {
                        LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                        row = mInflater.inflate(R.layout.layout_menu_list_item, null);
                    } else {
                        row = convertView;
                    }

                    TextView tv = (TextView) row.findViewById(R.id.tvSubMenuItem);
                    tv.setText(getItem(position));

                    return row;
                    //return super.getView(position, convertView, parent);
                }
            };
            lvItinerary.setAdapter(adpItinerary);

            ImageButton ibMenuClose = (ImageButton)drawerView.findViewById(R.id.ibMenuClose);
            ibMenuClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(Gravity.RIGHT);
                }
            });

            final ImageView ivShowItinerary = (ImageView)drawerView.findViewById(R.id.ivShowItinerary),
                    ivShowStages = (ImageView)drawerView.findViewById(R.id.ivShowStages),
                    ivShowPeople = (ImageView)drawerView.findViewById(R.id.ivShowPeople),
                    ivShowSystem = (ImageView)drawerView.findViewById(R.id.ivShowSystem);


            ivShowItinerary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ic1){
                        lvItinerary.setVisibility(View.GONE);
                        ivShowItinerary.setImageResource(R.mipmap.ic_down);
                        ic1 = false;
                    }else{
                        lvItinerary.setVisibility(View.VISIBLE);
                        ivShowItinerary.setImageResource(R.mipmap.ic_up);
                        ic1 = true;
                    }


                }
            });

            ivShowPeople.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ic2){
                        lvPeople.setVisibility(View.GONE);
                        ivShowPeople.setImageResource(R.mipmap.ic_down);
                        ic2 = false;
                    }else{
                        lvPeople.setVisibility(View.VISIBLE);
                        ivShowPeople.setImageResource(R.mipmap.ic_up);
                        ic2 = true;
                    }
                }
            });

            ivShowStages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ic3){
                        lvStages.setVisibility(View.GONE);
                        ivShowStages.setImageResource(R.mipmap.ic_down);
                        ic3 = false;
                    }else{
                        lvStages.setVisibility(View.VISIBLE);
                        ivShowStages.setImageResource(R.mipmap.ic_up);
                        ic3 = true;
                    }
                }
            });

            ivShowSystem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View llSettings = drawerView.findViewById(R.id.llSettings);


                    if (ic4){
                        llSettings.setVisibility(View.GONE);
                        ivShowSystem.setImageResource(R.mipmap.ic_down);
                        ic4 = false;
                    }else{
                        llSettings.setVisibility(View.VISIBLE);
                        ivShowSystem.setImageResource(R.mipmap.ic_up);
                        ic4 = true;
                    }
                }
            });

        }

        @Override
        public void onDrawerClosed(View drawerView) {
            ibMenuOpen.setVisibility(View.VISIBLE);
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the Notification Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);

        people = new ArrayList<>();
        itinerary = new ArrayList<>();
        stages = new ArrayList<>();

        sf = new StartFragment();
        suf = new SignUpFragment();
        hf = new HomeFragment();
        af = new ArtistsFragment();
        adf = new ArtistDetailFragment();
        elf = new EventsLineUpFragment();
        edf = new EventDetailsFragment();
        gf = new GalleryFragment();
        btf = new BuyTicketFragment();

        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                //String code = parseCode(message);//Parse verification code
                if (parseCode(message)){
                    Toast.makeText(MainActivity.this,"Payment Successful",Toast.LENGTH_SHORT).show();
                }
                //etCode.setText(code);//set code in edit text
                //then you can send verification code to server

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("zap/tickets");
                String key = ref.getKey();

                ref.child(key).setValue(boughtTicket);


            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("zap/menu");

        ref.child("people").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                people.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    people.add((String)snapshot.getValue());
                }
                //adpPeople.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("Stages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    stages.add((String)snapshot.getValue());
                }
                //adpStages.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("itinerary").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itinerary.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    itinerary.add((String)snapshot.getValue());
                }
                //adpItinerary.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ibMenuOpen = (ImageButton)findViewById(R.id.ibMenuOpen);
        ibMenuOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                    ibMenuOpen.setVisibility(View.GONE);
                }
            }
        });

        btnTickets = (Button) findViewById(R.id.btnTickets);
        btnTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTickets.setVisibility(View.GONE);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.start_screen,btf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        setUpFragments();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.start_screen, sf);
        fragmentTransaction.commit();
    }

    private boolean parseCode(String message) {

        String merchantCode, s;
        boolean validation;

        merchantCode = message.substring(message.indexOf("(")+1,message.indexOf(")"));

        if (merchantCode == "12345"){
            validation = true;
        } else
            validation = false;
        /*Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }*/
        return validation;
    }

    private void setUpFragments(){


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
                ibMenuOpen.setVisibility(View.VISIBLE);
            }
        });

        elf.setOnListItemClickListener(new EventsLineUpFragment.OnListItemClickListener() {
            @Override
            public void onClick(Event event) {
                edf = setEdf(event);
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
                ibMenuOpen.setVisibility(View.VISIBLE);
                btnTickets.setVisibility(View.VISIBLE);
                setUpDrawer();
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

    private void setUpDrawer(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        drawer.setDrawerListener(drawerListener);
    }
    private void removeDrawer(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private BuyTicketDetailFragment setBtdf(Ticket t){

        BuyTicketDetailFragment f = new BuyTicketDetailFragment();
        f.setOnSaleListener(new BuyTicketDetailFragment.OnSaleListener() {
            @Override
            public void onSale(Sale s) {
                boughtTicket = s;
            }
        });
        Bundle extra = new Bundle();
        extra.putSerializable(TICKET,t);
        f.setArguments(extra);

        return f;
    }

    private EventDetailsFragment setEdf(Event event){
        EventDetailsFragment frag = new EventDetailsFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("event",event);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
            ibMenuOpen.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
        ibMenuOpen.setVisibility(View.VISIBLE);
        return true;
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        String fragmentName = fragment.getClass().getSimpleName();

        //Toast.makeText(this,fragmentName,Toast.LENGTH_SHORT).show();
        switch (fragmentName){
            case "StartFragment":
                removeDrawer();
                break;
            case "HomeFragment" :

            default:
                setUpDrawer();
        }
    }


}
