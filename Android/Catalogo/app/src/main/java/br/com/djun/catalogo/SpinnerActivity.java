package br.com.djun.catalogo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class SpinnerActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {
    private SpinnerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options,
                R.layout.dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        getSupportActionBar().setListNavigationCallbacks(adapter,this);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Fragment fragment = new SimpleFragment(itemPosition);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
        return true;
    }
}
