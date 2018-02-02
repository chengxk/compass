package com.cxk.compass.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cxk.compass.Config;
import com.cxk.compass.R;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        sharedPreferences = getSharedPreferences(Config.CONFIG, 0);

        String mode = sharedPreferences.getString(Config.MODE_KEY, Config.COMPASS);
        replace(mode);

        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);

        if (Config.COMPASS.equals(mode)) {
            item.setTitle(R.string.level);
            setTitle(R.string.compass);
        } else if (Config.LEVEL.equals(mode)) {
            item.setTitle(R.string.compass);
            setTitle(R.string.level);
        }

    }

    private void replace(String mode) {
        if (Config.COMPASS.equals(mode)) {
            CompassFragment compassFragment = (CompassFragment) Fragment.instantiate(this, CompassFragment.class.getName(), null);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, compassFragment).commitAllowingStateLoss();
        } else if (Config.LEVEL.equals(mode)) {
            LevelFragment levelFragment = (LevelFragment) Fragment.instantiate(this, LevelFragment.class.getName(), null);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, levelFragment).commitAllowingStateLoss();
        } else {
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mode) {
            SharedPreferences.Editor edit = sharedPreferences.edit();

            String mode = sharedPreferences.getString(Config.MODE_KEY, Config.COMPASS);
            if (Config.COMPASS.equals(mode)) {
                item.setTitle(R.string.compass);
                edit.putString(Config.MODE_KEY, Config.LEVEL);
                setTitle(R.string.level);
                replace(Config.LEVEL);
            } else if (Config.LEVEL.equals(mode)) {
                item.setTitle(R.string.level);
                setTitle(R.string.compass);
                edit.putString(Config.MODE_KEY, Config.COMPASS);
                replace(Config.COMPASS);
            }
            edit.commit();

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mark) {
            openMark();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void openMark() {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("market://details?id=" + getPackageName()));
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(this, "您的手机上没有安装Android应用市场", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
