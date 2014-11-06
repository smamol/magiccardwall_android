package nz.co.trademe.fedex5.magiccardwall.fragment;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import nz.co.trademe.fedex5.magiccardwall.R;
import nz.co.trademe.fedex5.magiccardwall.activity.HistoryActivity;
import nz.co.trademe.fedex5.magiccardwall.api.JiraApiWrapper;
import nz.co.trademe.fedex5.magiccardwall.api.request.LoginRequest;
import nz.co.trademe.fedex5.magiccardwall.api.response.LoginResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginFragment extends Fragment {

	private ImageView showPasswordButton;

	private EditText usernameField, passwordField;
	private TextView usernameError, passwordError;

	private Handler handler;
	private View[] views;
	private int num = 0;
	private Runnable r = new Runnable() {
		@Override
		public void run() {
			revealSingleView(views, num);
		}
	};

	public LoginFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_login, container, false);

		usernameField = (EditText) v.findViewById(R.id.editTextUsername);
		passwordField = (EditText) v.findViewById(R.id.editTextPassword);

		usernameError = (TextView) v.findViewById(R.id.textViewUsernameError);
		passwordError = (TextView) v.findViewById(R.id.textViewPasswordError);

		showPasswordButton = (ImageView) v.findViewById(R.id.imageViewShowPassword);



		View loginButton = v.findViewById(R.id.textViewLoginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loginClicked();
			}
		});

		v.findViewById(R.id.imageViewShowPassword).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPasswordClicked();
			}
		});


		usernameField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					usernameError.setVisibility(View.INVISIBLE);
				}
			}
		});

		passwordField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					passwordError.setVisibility(View.INVISIBLE);
				}
			}
		});

		ImageView logo = (ImageView) v.findViewById(R.id.imageViewLogo);
		LinearLayout passwordRow = (LinearLayout) v.findViewById(R.id.layoutPasswordRow);
		if (savedInstanceState == null) {
			handler = new Handler();
			revealViews(logo, usernameField, passwordRow, loginButton);
		}

		return v;
	}

	private void revealViews(final View... views) {
		for (View v : views) {
			v.setVisibility(View.INVISIBLE);
		}
		this.views = views;
		num = 0;
		handler.postDelayed(r, 200);
	}

	private void revealSingleView(View[] views, int idx) {
		View v = views[idx];
		v.setVisibility(View.VISIBLE);

		AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.reveal_view);
		set.setTarget(v);
		set.start();

		num++;

		if(num < views.length) {
			handler.postDelayed(r, 200);
		}
	}

	private void showPasswordClicked() {
		if (showPasswordButton != null && passwordField != null) {
			switch (passwordField.getInputType()) {
				case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD:
					passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					showPasswordButton.setImageResource(R.drawable.eye_open);
					break;
				case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:
				default:
					passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					showPasswordButton.setImageResource(R.drawable.eye_closed);
					break;
			}
		}
	}

	private void loginClicked() {
		if (getView() != null) {
			boolean fail = false;
			if(usernameField.getText().length() == 0) {
				fail = true;
				usernameError.setVisibility(View.VISIBLE);
			}

			if(passwordField.getText().length() == 0) {
				fail = true;
				passwordError.setVisibility(View.VISIBLE);
			}

			if (!fail) {
				login(usernameField.getText().toString(), passwordField.getText().toString());
			}
		}
	}

	private void login(String username, String password) {
        hideKeyboard();

        JiraApiWrapper.getSingleton().getApi().login(new LoginRequest(username, password), new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                if (loginResponse.isSuccess()) {
                    cacheToken(loginResponse.getToken());
                    Intent i = new Intent(getActivity(), HistoryActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getActivity(), loginResponse.getErrorMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
	}

    private void cacheToken(String token) {
        SharedPreferences preferences = getActivity().getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
