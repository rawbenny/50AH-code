package com.manning.androidhacks.hack004;


import android.app.Activity;
import android.os.Bundle;

public class AnotherMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.add(android.R.id.content, new PrefFragment()).commit();
	}

}
