package nz.co.trademe.fedex5.magiccardwall.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import nz.co.trademe.fedex5.magiccardwall.R;
import nz.co.trademe.fedex5.magiccardwall.fragment.HistoryFragment;

/**
 * Created by shindmarsh on 06/11/2014.
 */
public class HistoryActivity extends ActionBarActivity {

	private static final String FRAGMENT = HistoryFragment.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_toolbar);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		if (savedInstanceState == null) {
			// create fragment
			Fragment fragment = Fragment.instantiate(this, FRAGMENT);

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.add(R.id.fragmentContainer, fragment, FRAGMENT);
			transaction.commit();
		}
	}
}
