package com.manning.androidhacks.hack004;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class PrefFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {
	private Context mContext;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.prefs);

		Preference sharePref = findPreference("pref_share");
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check this app!");
		shareIntent.putExtra(Intent.EXTRA_TEXT,
				"Check this awesome app at: ...");
		sharePref.setIntent(shareIntent);

		Preference ratePref = findPreference("pref_rate");
		Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		ratePref.setIntent(goToMarket);

		updateUserText();
	}

	private void updateUserText() {
		EditTextPreference pref;
		pref = (EditTextPreference) findPreference("pref_username");
		String user = pref.getText();

		if (user == null) {
			user = "?";
		}

		pref.setSummary(String.format("Username: %s", user));
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		if (key.equals("pref_username")) {
			updateUserText();
		}
	}

	  @Override
	  public void onResume() {
	    super.onResume();

	    getPreferenceScreen().getSharedPreferences()
	        .registerOnSharedPreferenceChangeListener(this);

	  }

	  @Override
	  public void onPause() {
	    super.onPause();

	    getPreferenceScreen().getSharedPreferences()
	        .unregisterOnSharedPreferenceChangeListener(this);
	  }
}
