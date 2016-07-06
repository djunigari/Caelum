package br.com.djun.catalogo;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import static android.support.v7.app.ActionBar.Tab;
import static android.support.v7.app.ActionBar.TabListener;

public class SimpleTabListener implements TabListener {
    private ViewPager viewPager;

    public SimpleTabListener(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {

    }
}
