package vinsoft.com.wavefindyourfriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.adapter.AdapterViewPager;
import vinsoft.com.wavefindyourfriend.fragment.ChatFramgent;
import vinsoft.com.wavefindyourfriend.fragment.ContactFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    List<Fragment> fragments;
    PagerSlidingTabStrip tabStrip;
    TextView txtAccount,txtPhone;
    ImageView imageAvatar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        addControl();
        inforAccount();
    }

    private void addControl() {
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        tabStrip= (PagerSlidingTabStrip) findViewById(R.id.tabs);


        fragments=new ArrayList<Fragment>();

        ChatFramgent chatFramgent=new ChatFramgent();
        ContactFragment contactFragment=new ContactFragment();

        fragments.add(chatFramgent);
        fragments.add(contactFragment);

        AdapterViewPager adapterViewPager=new AdapterViewPager(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapterViewPager);
        tabStrip.setViewPager(viewPager);
    }
    public void inforAccount(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        txtAccount=(TextView) headerView.findViewById(R.id.txtAccount);
        imageAvatar= (ImageView) headerView.findViewById(R.id.imageAvartar);
        txtPhone= (TextView) headerView.findViewById(R.id.txtPhone);
        txtAccount.setText(SignInActivity.person.getName());
        txtPhone.setText(SignInActivity.person.getId());
       // imageAvatar.setImageResource(R.drawable.ic_profile_);

        if(SignInActivity.person.getImage()==null)
            imageAvatar.setImageResource(R.drawable.ic_profile_);
        else {
            Glide.with(this).load(SignInActivity.person.getImage()).thumbnail(0.5f)
                    .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageAvatar);
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

        if (id == R.id.it_compass) {
            Intent intent=new Intent(MainActivity.this,MapsActivity.class);
            startActivity(intent);

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
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
