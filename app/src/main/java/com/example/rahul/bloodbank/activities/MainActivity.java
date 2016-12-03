package com.example.rahul.bloodbank.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.adapters.CustomListAdapterDrawer;
import com.example.rahul.bloodbank.constants.Constant;
import com.example.rahul.bloodbank.fragments.DashboardFragment;
import com.example.rahul.bloodbank.fragments.ProfileFragment;
import com.example.rahul.bloodbank.fragments.SearchFragment;
import com.example.rahul.bloodbank.fragments.SettingFragment;
import com.example.rahul.bloodbank.interfaces.Communicator;
import com.example.rahul.bloodbank.pojo.DrawerItemPojo;
import com.example.rahul.bloodbank.pojo.RegistrationPojo;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<DrawerItemPojo> drawerItemPojos;
    private View header;
    private ListView drawerListView;
    private TextView headerText;
    private String[] titleDrawer = new String[]{"Home", "Profile", "Search", "Setting"};
    private int[] imageIdDrawer = {R.drawable.home_drawer, R.drawable.profile_drawer, R.drawable.search_drawer, R.drawable.settings_drawer};
    private ProfileFragment profileFragment;
    private SearchFragment searchFragment;
    private SettingFragment settingFragment;
    private DashboardFragment dashboardFragment;
    Communicator mCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_container, new DashboardFragment()).commit();

        RegistrationPojo registrationPojo = (RegistrationPojo) getIntent().getSerializableExtra("personObject");
        if (registrationPojo != null) {
            Constant.registrationPojo = registrationPojo;
            headerText.setText("Hello " + registrationPojo.getName());
        }
    }

    private void initializeViews() {
        mToolbar = (Toolbar) findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(mToolbar);


        profileFragment = new ProfileFragment();
        mCommunicator = profileFragment;


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

        headerText = (TextView) header.findViewById(R.id.tv_header_dashboard);
        drawerListView.addHeaderView(header);

        CustomListAdapterDrawer adapter = new CustomListAdapterDrawer(this, drawerItemPojos);
        drawerListView.setAdapter(adapter);


        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                  @Override
                                                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                      if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                                                          getSupportFragmentManager().popBackStack();
                                                      }
                                                      if (position == 1) {
                                                          dashboardFragment = new DashboardFragment();
                                                          if (!Constant.editStatus) {

                                                              if (profileFragment.isVisible()) {
                                                                  new AlertDialog.Builder(MainActivity.this)
                                                                          .setTitle("Warning!")
                                                                          .setMessage("Do you want to save data?")
                                                                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                              public void onClick(DialogInterface dialog, int which) {

                                                                                  mDrawer.closeDrawer(Gravity.LEFT);
                                                                                  mCommunicator.updateProfile();
                                                                                  if (Constant.updateStatus == false) {
                                                                                      getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, dashboardFragment).commit();
                                                                                      Constant.editStatus = true;
                                                                                  }
                                                                                  dialog.dismiss();

                                                                              }
                                                                          })
                                                                          .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                                              public void onClick(DialogInterface dialog, int which) {
                                                                                  getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, dashboardFragment).commit();
                                                                                  mDrawer.closeDrawer(Gravity.LEFT);
                                                                                  refreshProfileFragment();
                                                                                  dialog.dismiss();
                                                                                  Constant.editStatus = true;
                                                                                  Toast.makeText(MainActivity.this, "You cancelled this operation", Toast.LENGTH_SHORT).show();

                                                                              }
                                                                          })
                                                                          .show();
                                                              }
                                                          } else {
                                                              getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, dashboardFragment).commit();
                                                              mDrawer.closeDrawer(Gravity.LEFT);
                                                          }

                                                      } else if (position == 2) {

                                                          if (!Constant.editStatus) {
                                                              if (profileFragment.isVisible()) {
                                                                  new AlertDialog.Builder(MainActivity.this)
                                                                          .setTitle("Warning!")
                                                                          .setMessage("Do you want to save data?")
                                                                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                              public void onClick(DialogInterface dialog, int which) {
                                                                                  mDrawer.closeDrawer(Gravity.LEFT);
                                                                                  mCommunicator.updateProfile();
                                                                                  if (Constant.updateStatus == false) {
                                                                                      getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, profileFragment, "Progile_Fragment").commit();
                                                                                      Constant.editStatus = true;
                                                                                  }
                                                                                  dialog.dismiss();

                                                                              }
                                                                          })
                                                                          .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                                              public void onClick(DialogInterface dialog, int which) {
                                                                                  getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, profileFragment, "Progile_Fragment").commit();
                                                                                  mDrawer.closeDrawer(Gravity.LEFT);
                                                                                  refreshProfileFragment();
                                                                                  dialog.dismiss();
                                                                                  Constant.editStatus = true;
                                                                                  Toast.makeText(MainActivity.this, "You cancelled this operation", Toast.LENGTH_SHORT).show();

                                                                              }
                                                                          })
                                                                          .show();
                                                              }
                                                          } else {
                                                              getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, profileFragment).commit();
                                                              mDrawer.closeDrawer(Gravity.LEFT);
                                                          }


                                                      } else if (position == 3)

                                                      {
                                                          searchFragment = new SearchFragment();

                                                          if (!Constant.editStatus) {

                                                              if (profileFragment.isVisible()) {
                                                                  new AlertDialog.Builder(MainActivity.this)
                                                                          .setTitle("Warning!")
                                                                          .setMessage("Do you want to save data?")
                                                                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                              public void onClick(DialogInterface dialog, int which) {
                                                                                  mDrawer.closeDrawer(Gravity.LEFT);
                                                                                  mCommunicator.updateProfile();
                                                                                  if (Constant.updateStatus == false) {
                                                                                      getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, searchFragment).commit();
                                                                                      Constant.editStatus = true;
                                                                                  }
                                                                                  dialog.dismiss();
                                                                              }
                                                                          })
                                                                          .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                                              public void onClick(DialogInterface dialog, int which) {
                                                                                  getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, searchFragment).commit();
                                                                                  mDrawer.closeDrawer(Gravity.LEFT);
                                                                                  refreshProfileFragment();
                                                                                  Constant.editStatus = true;
                                                                                  dialog.dismiss();
                                                                                  Toast.makeText(MainActivity.this, "You cancelled this operation", Toast.LENGTH_SHORT).show();

                                                                              }
                                                                          })
                                                                          .show();
                                                              }
                                                          } else {
                                                              getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, searchFragment).commit();
                                                              mDrawer.closeDrawer(Gravity.LEFT);
                                                          }


                                                      } else if (position == 4) {
                                                          settingFragment = new SettingFragment();
                                                          if (!Constant.editStatus) {

                                                              if (profileFragment.isVisible()) {
                                                                  new AlertDialog.Builder(MainActivity.this)
                                                                          .setTitle("Warning!")
                                                                          .setMessage("Do you want to save data?")
                                                                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                              public void onClick(DialogInterface dialog, int which) {
                                                                                  mDrawer.closeDrawer(Gravity.LEFT);
                                                                                  mCommunicator.updateProfile();
                                                                                  if (Constant.updateStatus == false) {
                                                                                      getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, settingFragment).commit();
                                                                                      Constant.editStatus = true;
                                                                                  }
                                                                                  dialog.dismiss();
                                                                              }
                                                                          })
                                                                          .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                                              public void onClick(DialogInterface dialog, int which) {
                                                                                  getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, settingFragment).commit();
                                                                                  mDrawer.closeDrawer(Gravity.LEFT);
                                                                                  refreshProfileFragment();
                                                                                  Constant.editStatus = true;

                                                                                  dialog.dismiss();
                                                                                  Toast.makeText(MainActivity.this, "You cancelled this operation", Toast.LENGTH_SHORT).show();

                                                                              }
                                                                          })
                                                                          .show();
                                                              }
                                                          } else {
                                                              getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_container, settingFragment).commit();
                                                              mDrawer.closeDrawer(Gravity.LEFT);
                                                          }


                                                      }

                                                  }
                                              }

        );
    }

    public void showSaveDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Warning!")
                .setMessage("Do you want to save data?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mCommunicator.updateProfile();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        refreshProfileFragment();
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "You cancelled this operation", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!Constant.editStatus) {
            Constant.editStatus = true;
            showSaveDialog();

        } else {
            showDialog();

        }
    }

    public void refreshProfileFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainactivity_container, new ProfileFragment()).commit();

    }

    public void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Logout")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FragmentManager fm = getSupportFragmentManager();
                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
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
                showDialog();
                return true;
            case R.id.rate_app:
                Toast.makeText(getApplicationContext(), "functionality is in progress", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}


