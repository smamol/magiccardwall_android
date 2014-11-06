package nz.co.trademe.fedex5.magiccardwall.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.seismic.ShakeDetector;

import nz.co.trademe.fedex5.magiccardwall.R;
import nz.co.trademe.fedex5.magiccardwall.api.JiraApiWrapper;
import nz.co.trademe.fedex5.magiccardwall.fragment.HistoryFragment;
import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by shindmarsh on 06/11/2014.
 */
public class HistoryActivity extends ActionBarActivity implements ShakeDetector.Listener {

	private static final String FRAGMENT = HistoryFragment.class.getName();
    private String lastIssueId;

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
        else {
            lastIssueId = savedInstanceState.getString("lastIssueId");
        }

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
	}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString("lastIssueId", lastIssueId);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
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
                    JiraApiWrapper.getSingleton().getApi().status(result, false, new ResponseCallback() {
                        @Override
                        public void success(Response response) {
                            lastIssueId = result;
                            Toast.makeText(HistoryActivity.this, "success", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            lastIssueId = null;
                            Toast.makeText(HistoryActivity.this, error.toString(), Toast.LENGTH_SHORT);
                        }
                    });
                }
                break;
            default:
        }
    }

    @Override
    public void hearShake() {
        if (lastIssueId != null && lastIssueId.length() > 0) {
            JiraApiWrapper.getSingleton().getApi().status(lastIssueId, true, new ResponseCallback() {
                @Override
                public void success(Response response) {
                    lastIssueId = null;
                    Toast.makeText(HistoryActivity.this, "undo success", Toast.LENGTH_SHORT);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(HistoryActivity.this, error.toString(), Toast.LENGTH_SHORT);
                }
            });
        }
    }
}
