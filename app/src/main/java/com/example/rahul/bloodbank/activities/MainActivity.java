package com.example.rahul.bloodbank.activities;

import android.content.res.Configuration;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.adapters.CustomListAdapterDrawer;
import com.example.rahul.bloodbank.fragments.DashboardFragment;
import com.example.rahul.bloodbank.fragments.ProfileFragment;
import com.example.rahul.bloodbank.fragments.SearchFragment;
import com.example.rahul.bloodbank.fragments.SettingFragment;
import com.example.rahul.bloodbank.pojo.DrawerItemPojo;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<DrawerItemPojo> drawerItemPojos;
    private View header;
    private ListView drawerListView;
    private ImageView headerImage;
    private TextView headerText;
    private String[] titleDrawer = new String[]{"Home", "Profile", "Search", "Setting"};
    private int[] imageIdDrawer = {R.drawable.home_drawer, R.drawable.profile_drawer, R.drawable.search_drawer, R.drawable.settings_drawer};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_container, new DashboardFragment()).commit();


    }

    private void initializeViews() {
        mToolbar = (Toolbar) findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(mToolbar);

        drawerItemPojos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            DrawerItemPojo drawerItemPojo = new DrawerItemPojo();
            drawerItemPojo.setTitle(titleDrawer[i]);
            drawerItemPojo.setImageId(imageIdDrawer[i]);
            drawerItemPojos.add(drawerItemPojo);
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        drawerListView = (ListView) findViewById(R.id.drawer);

        header = getLayoutInflater().inflate(R.layout.header_nav, null);
        headerImage = (ImageView) header.findViewById(R.id.image_header_dashboard);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.user_browse_ic);
        headerImage.setImageBitmap(getCircleBitmap(bm));

        headerText = (TextView) header.findViewById(R.id.tv_header_dashboard);
        drawerListView.addHeaderView(header);

        CustomListAdapterDrawer adapter = new CustomListAdapterDrawer(this, drawerItemPojos);
        drawerListView.setAdapter(adapter);


        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, new DashboardFragment()).commit();

                    mDrawer.closeDrawer(Gravity.LEFT);
                } else if (position == 2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, new ProfileFragment()).commit();
                    mDrawer.closeDrawer(Gravity.LEFT);

                } else if (position == 3) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, new SearchFragment()).commit();
                    mDrawer.closeDrawer(Gravity.LEFT);

                } else if (position == 4) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, new SettingFragment()).commit();
                    mDrawer.closeDrawer(Gravity.LEFT);

                }

            }
        });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                return true;
            case R.id.rate_app:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}


