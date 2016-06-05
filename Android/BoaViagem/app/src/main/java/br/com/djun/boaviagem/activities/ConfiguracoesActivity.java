package br.com.djun.boaviagem.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import br.com.djun.boaviagem.R;

public class ConfiguracoesActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
