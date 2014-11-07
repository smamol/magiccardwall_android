package nz.co.trademe.fedex5.magiccardwall.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by shindmarsh on 07/11/2014.
 */
public class BaseActivity extends ActionBarActivity {

	protected AlertDialog dialog;

	public void showDialog(String title, String msg, String negButton, String posButton, DialogInterface.OnClickListener posAction) {
		if(dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
				.setTitle(title)
				.setMessage(msg);

		if (negButton != null) {
			builder.setNegativeButton(negButton, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}

		if(posButton != null) {
			builder.setPositiveButton(posButton, posAction);
		}

		dialog = builder.create();
		dialog.show();
	}

}
