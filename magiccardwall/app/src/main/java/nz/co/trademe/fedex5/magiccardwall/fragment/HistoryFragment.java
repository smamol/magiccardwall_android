package nz.co.trademe.fedex5.magiccardwall.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import nz.co.trademe.fedex5.magiccardwall.R;

public class HistoryFragment extends Fragment {

	public HistoryFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_history, container, false);

		ImageView fab = (ImageView) v.findViewById(R.id.fab);
		ViewCompat.setElevation(fab, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				beginScanQRCode();
			}
		});


		return v;
	}

	private void beginScanQRCode() {

	}


}
