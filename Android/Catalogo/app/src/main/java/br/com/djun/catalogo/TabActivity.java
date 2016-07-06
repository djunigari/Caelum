package br.com.djun.catalogo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public class TabActivity extends ActionBarActivity{
    private ViewPager viewPager;
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        viewPager = (ViewPager) findViewById(R.id.pager);

        FragmentManager fm = getSupportFragmentManager();
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(fm);
        viewPager.setAdapter(tabPagerAdapter);

        actionBar =	getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        SimpleTabListener listener = new SimpleTabListener(viewPager);
        actionBar.addTab(actionBar.newTab()
                .setText(R.string.top_sellers)
                .setTabListener(listener));
        actionBar.addTab(actionBar.newTab()
                .setText(R.string.new_arrivals)
                .setTabListener(listener));
        actionBar.addTab(actionBar.newTab()
                .setText(R.string.off)
                .setTabListener(listener));

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener(){
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                }
        );
    }
}
