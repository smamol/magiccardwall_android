package nz.co.trademe.fedex5.magiccardwall.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import nz.co.trademe.fedex5.magiccardwall.R;
import nz.co.trademe.fedex5.magiccardwall.api.network.JiraRequestInterceptor;
import nz.co.trademe.fedex5.magiccardwall.fragment.LoginFragment;

public class LoginActivity extends BaseActivity {

	private static final String FRAGMENT = LoginFragment.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("data", 0);

        String token = preferences.getString("token", null);

        if (token != null && token.length() > 0) {
            // already logged in
            JiraRequestInterceptor.getSingleton().setToken(token);
            Intent i = new Intent(this, HistoryActivity.class);
            startActivity(i);
            finish();
        }
        else {
            setContentView(R.layout.activity);

            if (savedInstanceState == null) {
                // create fragment
                Fragment fragment = Fragment.instantiate(this, FRAGMENT);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.fragmentContainer, fragment, FRAGMENT);
                transaction.commit();
            }
        }
	}
}
