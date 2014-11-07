package nz.co.trademe.fedex5.magiccardwall.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
public class HistoryActivity extends BaseActivity implements ShakeDetector.Listener {

	private static final String TAG = HistoryActivity.class.getSimpleName();

	private static final String FRAGMENT = HistoryFragment.class.getName();

	private String lastIssueId;

	private ShakeDetector sd;

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
		} else {
			lastIssueId = savedInstanceState.getString("lastIssueId");
		}

//        Picasso.with(this).load("avatarUrl").into(imageView);

		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sd = new ShakeDetector(this);
		sd.start(sensorManager);
	}

	@Override
	protected void onPause() {
		if (sd != null) {
			sd.stop();
		}

		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			dialog = null;
		}

		super.onPause();
	}

	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
	    savedInstanceState.putString("lastIssueId", lastIssueId);
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
                            Toast.makeText(HistoryActivity.this, "success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            lastIssueId = null;
                            Toast.makeText(HistoryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            default:
        }
    }

    @Override
    public void hearShake() {
	    Log.d(TAG, "Shake shake shake");

	    if (lastIssueId != null && lastIssueId.length() > 0) {
		    showDialog("Shake it off", "Really move " + lastIssueId + " back?", "Just kidding", "Of course",
				    new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
				    undo();
			    }
		    });
	    }
    }

	private void undo() {
		JiraApiWrapper.getSingleton().getApi().status(lastIssueId, true, new ResponseCallback() {
			@Override
			public void success(Response response) {
				lastIssueId = null;
				Toast.makeText(HistoryActivity.this, "undo success", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void failure(RetrofitError error) {
				Toast.makeText(HistoryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
