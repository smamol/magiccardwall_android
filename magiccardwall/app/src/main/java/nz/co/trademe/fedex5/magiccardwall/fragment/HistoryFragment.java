package nz.co.trademe.fedex5.magiccardwall.fragment;


import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nz.co.trademe.fedex5.magiccardwall.R;
import nz.co.trademe.fedex5.magiccardwall.activity.BaseActivity;
import nz.co.trademe.fedex5.magiccardwall.api.JiraApiWrapper;
import nz.co.trademe.fedex5.magiccardwall.api.response.HistoryItem;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HistoryFragment extends Fragment {

	private static final String TAG = HistoryFragment.class.getSimpleName();

	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private RecyclerView.Adapter adapter;

	private String username;

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
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		setFilter(Filter.EVERYONE);

		username = getActivity().getSharedPreferences("data", 0).getString("username", "none");

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

	private void setFilter(final Filter filter) {

		JiraApiWrapper.getSingleton().getApi().history(new Callback<ArrayList<HistoryItem>>() {
			@Override
			public void success(ArrayList<HistoryItem> historyItems, Response response) {
				if (filter == Filter.ME) {
					Iterator<HistoryItem> iterator = historyItems.iterator();
					while (iterator.hasNext()) {
						HistoryItem itm = iterator.next();
						if (!itm.getUsername().equals(username)) {
							iterator.remove();
						}
					}
				}

				adapter = new HistoryAdapter(historyItems);
				recyclerView.setAdapter(adapter);
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e("HistoryTestActivity", error.toString());
				((BaseActivity) getActivity()).showDialog("Oops", "Can't get the history at the moment", "Fine", null, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
			}
		});
	}


	class HistoryAdapter extends RecyclerView.Adapter {

		class ViewHolder extends RecyclerView.ViewHolder {
			@InjectView(R.id.cardTextViewTitle)
			TextView title;

			@InjectView(R.id.cardTextViewTimestamp)
			TextView timestamp;

			@InjectView(R.id.cardImageViewAvatar)
			ImageView avatar;

			@InjectView(R.id.cardViewColor)
			View status;

			public ViewHolder(View v) {
				super(v);
				ButterKnife.inject(this, v);
			}
		}

		private ArrayList<HistoryItem> data;

		public HistoryAdapter(ArrayList<HistoryItem> data) {
			this.data = data;
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.card_history, viewGroup, false));
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
			ViewHolder h = (ViewHolder) viewHolder;

			HistoryItem item = data.get(i);

			h.title.setText(item.getUsername() + " moved " + item.getTitle() + " into " + item.getStatus());
			h.timestamp.setText(item.getTimestamp());

			int col = 0;
			if (item.getType() != null) {
				if (item.getType().equals("Dev")) {
					col = getResources().getColor(R.color.status_dev);
				} else if (item.getType().equals("Design")) {
					col = getResources().getColor(R.color.status_design);
				} else if (item.getType().equals("Test")) {
					col = getResources().getColor(R.color.status_test);
				}

				if (col != 0) {
					h.status.setBackgroundColor(col);
				}
			}

			String avatarUrl = item.getAvatarUrl();
			if (avatarUrl != null) {
				Log.v(TAG, "url --> " + avatarUrl);
				try {
					Picasso.with(getActivity()).load(avatarUrl).into(h.avatar);
				} catch (NullPointerException ignored) {}
			}
		}

		@Override
		public int getItemCount() {
			return data.size();
		}
	}

}
