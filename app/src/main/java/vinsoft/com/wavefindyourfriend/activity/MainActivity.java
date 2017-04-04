package vinsoft.com.wavefindyourfriend.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.adapter.AdapterViewPager;
import vinsoft.com.wavefindyourfriend.fragment.ChatFramgent;
import vinsoft.com.wavefindyourfriend.fragment.ContactFragment;
import vinsoft.com.wavefindyourfriend.fragment.MapsFragment;

/**
 * Created by DONG on 04-Apr-17.
 */

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    List<Fragment> fragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
    }

    private void addControl() {
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        fragments=new ArrayList<Fragment>();

        ContactFragment contactFragment=new ContactFragment();
        ChatFramgent chatFramgent=new ChatFramgent();
        MapsFragment mapsFragment=new MapsFragment();

        fragments.add(contactFragment);
        fragments.add(chatFramgent);
        fragments.add(mapsFragment);


        AdapterViewPager adapterViewPager=new AdapterViewPager(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapterViewPager);
    }
}
