package com.seminaire.ateliers.atelier1;

import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
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

    protected DrawerLayout activityContainer;
    protected FrameLayout activityContent;
    protected Toolbar mainToolbar;
    protected NavigationView navigationView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        activityContainer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
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
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        if (useHamburgerMenu()) {
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityContainer, mainToolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            activityContainer.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        } else if (useToolbar() && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_home, null));
        }
    }

    protected boolean useHamburgerMenu() {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Snackbar.make(activityContent, item.getTitle(), Snackbar.LENGTH_LONG).show();
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
