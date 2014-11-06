package nz.co.trademe.fedex5.magiccardwall.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import nz.co.trademe.fedex5.magiccardwall.R;
import nz.co.trademe.fedex5.magiccardwall.fragment.LoginFragment;

public class LoginActivity extends Activity {

	private static final String FRAGMENT = LoginFragment.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_fullscreen);

		if (savedInstanceState == null) {
			// create fragment
			Fragment fragment = Fragment.instantiate(this, FRAGMENT);

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.add(R.id.fragmentContainer, fragment, FRAGMENT);
			transaction.commit();
		}
	}


}
