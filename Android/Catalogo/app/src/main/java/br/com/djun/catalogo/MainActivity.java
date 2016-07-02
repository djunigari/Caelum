package br.com.djun.catalogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener{
    private List<String> products = Arrays.asList("android","ios","mobile","java","agile");
    private ShareActionProvider shareActionProvider;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(shareItem);
        shareActionProvider.setShareIntent(getShareIntent());
        return true;
    }

    public Intent getShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_TEXT, getInformation());
        return intent;
    }

    public String getInformation(){
        return "Information for sharing";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }else if(id == R.id.action_checkout){
            Toast.makeText(this,getString(R.string.action_checkout),Toast.LENGTH_SHORT).show();
            return true;
        }else if(id == R.id.action_filter_android){
            item.setChecked(true);
            return true;
        }else if(id == R.id.action_filter_java){
            item.setChecked(true);
            return true;
        }else if(id == R.id.action_filter_mobile){
            item.setChecked(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String text) {
        text = text.toLowerCase();
        TextView view = (TextView) getSupportFragmentManager()
                .findFragmentById(R.id.container)
                .getView()
                .findViewById(R.id.search_result);

        for(String name : products){
            if(name.contains(text)){
                String result = getString(R.string.results_found, text);
                view.setText(result);
                return true;
            }
        }
        String result = getString(R.string.results_not_found, text);
        view.setText(result);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
