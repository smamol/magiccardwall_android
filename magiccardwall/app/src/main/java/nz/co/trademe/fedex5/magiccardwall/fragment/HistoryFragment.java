package nz.co.trademe.fedex5.magiccardwall.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import nz.co.trademe.fedex5.magiccardwall.R;

public class HistoryFragment extends Fragment {

	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private RecyclerView.Adapter adapter;

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

		recyclerView = (RecyclerView) v.findViewById(R.id.recyclerHistory);

		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);

		adapter = new HistoryAdapter();

		return v;
	}


	private void beginScanQRCode() {
		IntentIntegrator integrator = new IntentIntegrator(getActivity());
		integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
		integrator.setPrompt("Scan a QR code");
		integrator.setResultDisplayDuration(0);
		integrator.setCameraId(0);  // Use a specific camera of the device
		integrator.initiateScan();
	}

	private class HistoryAdapter extends RecyclerView.Adapter {

		private class ViewHolder extends RecyclerView.ViewHolder {
			public TextView mTextView;

			public ViewHolder(TextView v) {
				super(v);
				mTextView = v;
			}
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			return null;
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

		}

		@Override
		public int getItemCount() {
			return 0;
		}
	}

}
