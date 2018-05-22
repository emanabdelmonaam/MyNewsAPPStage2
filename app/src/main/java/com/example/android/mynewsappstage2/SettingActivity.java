package com.example.android.mynewsappstage2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);
    }
    //we will add the preference Fragment class
    public static class NewsPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

           //add the PreferencesFromResource what i want to shaw
            Preference categoryNews = findPreference(getString(R.string.settings_category_key));
            bindPreferenceSummaryToValue(categoryNews);

            Preference orderByW = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderByW);

            Preference orderByDate = findPreference(getString(R.string.settings_order_date_key));
            bindPreferenceSummaryToValue(orderByDate);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            // The code in this method takes care of updating the displayed preference summary after it has been changed
            String stringValue = newValue.toString();
            preference.setSummary(stringValue);

            if (preference instanceof ListPreference){
                ListPreference listPreference =(ListPreference) preference;
                int prefIndex =listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0){
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);

                }else {
                    preference.setSummary(stringValue);
                }
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {

            preference.setOnPreferenceChangeListener(this);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
                        String preferenceString = preferences.getString(preference.getKey(), "");
                        onPreferenceChange(preference, preferenceString);
        }
    }
}