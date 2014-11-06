package nz.co.trademe.fedex5.magiccardwall.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import nz.co.trademe.fedex5.magiccardwall.R;
import nz.co.trademe.fedex5.magiccardwall.fragment.LoginFragment;

public class LoginActivity extends Activity {

	private static final String FRAGMENT = LoginFragment.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
