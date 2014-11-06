package nz.co.trademe.fedex5.magiccardwall.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import nz.co.trademe.fedex5.magiccardwall.R;

public class HistoryFragment extends Fragment {

	private static final String TAG = HistoryFragment.class.getSimpleName();

	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private RecyclerView.Adapter adapter;

	public HistoryFragment() {}

	private enum Filter {
		EVERYONE, ME
	}

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

		Spinner filter = (Spinner) v.findViewById(R.id.spinnerFilter);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.filters,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		filter.setAdapter(adapter);
		filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				setFilter(Filter.values()[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});

		recyclerView = (RecyclerView) v.findViewById(R.id.recyclerHistory);

		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);

		setFilter(Filter.EVERYONE);

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

	private void setFilter(Filter filter) {
		switch (filter) {
			case ME:
				Log.d(TAG, "filter by ME");
				// todo me
				break;
			case EVERYONE:
			default:
				Log.d(TAG, "filter by EVERYONE");
				// todo everyone

				adapter = new HistoryAdapter(new String[]{"Hello", "Bonjour", "Hallo"});
				break;
		}

		recyclerView.setAdapter(adapter);
	}


	private class HistoryAdapter extends RecyclerView.Adapter {

		class ViewHolder extends RecyclerView.ViewHolder {
			TextView tv;

			public ViewHolder(View v) {
				super(v);
				tv = (TextView) v.findViewById(R.id.cardTextViewTest);
			}
		}

		private String[] data;

		public HistoryAdapter(String[] data) {
			this.data = data;
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.card_history, viewGroup, false));
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
			ViewHolder holder = (ViewHolder) viewHolder;
			holder.tv.setText(data[i]);
		}

		@Override
		public int getItemCount() {
			return data.length;
		}
	}

}
