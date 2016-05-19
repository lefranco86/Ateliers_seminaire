package com.seminaire.ateliers.atelier1;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout activityContainer;
    FrameLayout activityContent;
    Toolbar mainToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContainer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        activityContent = (FrameLayout) activityContainer.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContent, true);
        super.setContentView(activityContainer);

        setUpToolbar();
        setUpNavigationView();
    }

    protected void setUpToolbar() {
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (useToolbar()) {
            setSupportActionBar(mainToolbar);
            setTitle("Activity title");
        } else {
            mainToolbar.setVisibility(View.GONE);
        }
    }

    protected boolean useToolbar() {
        return true;
    }

    protected void setUpNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (useHamburgerMenu()) {
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityContainer, mainToolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            activityContainer.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        } else if (useToolbar() && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_manage, null));
        }
    }

    protected boolean useHamburgerMenu() {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        activityContainer.closeDrawer(GravityCompat.START);
        return onOptionsItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

}
