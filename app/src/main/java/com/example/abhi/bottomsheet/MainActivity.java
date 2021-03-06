package com.example.abhi.bottomsheet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhi.bottomsheet.BottomSheet.BottomSheetItemObject;
import com.example.abhi.bottomsheet.BottomSheet.BottomSheetRecyclerViewAdapter;
import com.example.abhi.bottomsheet.Coupons.CouponsCardFragmentPagerAdapter;
import com.example.abhi.bottomsheet.Coupons.CouponsCardItem;
import com.example.abhi.bottomsheet.Coupons.CouponsCardPagerAdapter;
import com.example.abhi.bottomsheet.Coupons.CouponsShadowTransformer;
import com.example.abhi.bottomsheet.DatabaseTransaction.PersonDatabaseHelper;
import com.example.abhi.bottomsheet.EcoService.BatteryService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomSheetBehavior mBottomSheetBehavior;
    TextView swipe;
    ImageView swipebut;
    CardView maincard,quotecard,homecard,transcard;
    Toolbar toolbar;
    private GridLayoutManager lLayout;
    private Button buybut;
    private ViewPager mViewPager;

    Toolbar toolbar;


    private PersonDatabaseHelper databaseHelper;

    private CouponsCardPagerAdapter mCardAdapter;
    private CouponsShadowTransformer mCardShadowTransformer;
    private CouponsCardFragmentPagerAdapter mFragmentCardAdapter;
    private CouponsShadowTransformer mFragmentCardShadowTransformer;
    private static final int REQUEST_SELECT_PLACE = 1000;


    private boolean mShowingFragments = false;

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        //Database
        databaseHelper = new PersonDatabaseHelper(this);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable
            //Toast.makeText(getApplicationContext(),"Available",Toast.LENGTH_SHORT).show();
        }else{

            Intent i = new Intent(MainActivity.this,ConnLost_act.class);
            startActivity(i);
            finish();
            //no connection

        }


        swipe =(TextView)findViewById(R.id.swipe);
        maincard=(CardView)findViewById(R.id.maincard);
        quotecard=(CardView)findViewById(R.id.quotecard);
        homecard=(CardView)findViewById(R.id.homecard);
        transcard=(CardView)findViewById(R.id.transcard);

        buybut=(Button)findViewById(R.id.buybut) ;

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Project GI");

        toolbar.setNavigationIcon(R.drawable.ic_home_black_24dp);
        toolbar.setNavigationContentDescription("Nothing");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Sup?",Toast.LENGTH_LONG).show();
            }
        });

        setSupportActionBar(toolbar);


        List<BottomSheetItemObject> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(MainActivity.this, 2);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        BottomSheetRecyclerViewAdapter rcAdapter = new BottomSheetRecyclerViewAdapter(MainActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);

        View bottomSheet = findViewById( R.id.bottom_sheet );
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(210);



        //options for coupons

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mCardAdapter = new CouponsCardPagerAdapter();
        mCardAdapter.addCardItem(new CouponsCardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CouponsCardItem(R.string.title_2, R.string.text_1));
        mCardAdapter.addCardItem(new CouponsCardItem(R.string.title_3, R.string.text_1));
        mCardAdapter.addCardItem(new CouponsCardItem(R.string.title_4, R.string.text_1));
        mCardAdapter.addCardItem(new CouponsCardItem(R.string.title_5, R.string.text_1));
        mCardAdapter.addCardItem(new CouponsCardItem(R.string.title_6, R.string.text_1));
        mFragmentCardAdapter = new CouponsCardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mCardShadowTransformer = new CouponsShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new CouponsShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);


        mViewPager.setCurrentItem(1);
        mCardShadowTransformer.enableScaling(true);
        mFragmentCardShadowTransformer.enableScaling(true);

        //Main act cards onClick

        maincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.insertData("asd","tht");
                //startActivity(new Intent(MainActivity.this,Details.class));
            }
        });
        quotecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.DatabaseDrop();
                //startActivity(new Intent(MainActivity.this,QuotesAct.class));
            }
        });
        homecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HomeAccount.class));
            }
        });
        transcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TransAct.class));
            }
        });




        //services

        //battery
        startService(new Intent(getBaseContext(), BatteryService.class));




    }

    private List<BottomSheetItemObject> getAllItemList(){

        List<BottomSheetItemObject> allItems = new ArrayList<BottomSheetItemObject>();

        allItems.add(new BottomSheetItemObject("Carticipate", R.drawable.ic_audiotrack_dark));
        allItems.add(new BottomSheetItemObject("Recyclers", R.drawable.ic_audiotrack_dark));
        allItems.add(new BottomSheetItemObject("Home", R.drawable.ic_audiotrack_dark));
        allItems.add(new BottomSheetItemObject("Vehicle", R.drawable.ic_audiotrack_dark));

        return allItems;
    }



}
