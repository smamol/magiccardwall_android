package nz.co.trademe.fedex5.magiccardwall.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import nz.co.trademe.fedex5.magiccardwall.R;
import nz.co.trademe.fedex5.magiccardwall.api.JiraApiWrapper;
import nz.co.trademe.fedex5.magiccardwall.fragment.HistoryFragment;
import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanResult = IntentIntegrator.parseActivityResult(
                        requestCode, resultCode, intent);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents(); // Your result

                if (result != null) {
                    JiraApiWrapper.getSingleton().getApi().status(result, new ResponseCallback() {
                        @Override
                        public void success(Response response) {
                            Toast.makeText(HistoryActivity.this, "success", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(HistoryActivity.this, error.toString(), Toast.LENGTH_SHORT);
                        }
                    });
                }
                break;
            default:
        }
    }
}
