package com.smartapps.saveyourreferrals;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.smartapps.saveyourreferrals.dao.AppInfo;
import com.smartapps.saveyourreferrals.dao.AppInfoDao.Properties;

import java.util.List;

import saveyourreferrals.lohith.com.saveyourreferrals.R;

public class AddManualReferralActivity extends Activity {
	EditText _emailText;
	EditText _passwordText;
	TextView _loginButton;
	private ProgressDialog progressDialog;
	private List<AppInfo> temp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_referral);
		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
		// .addTestDevice(
		// "32AD717F3EDA76A9F0C839FF9588E39C")
				.build();
		mAdView.loadAd(adRequest);

		_emailText = (EditText) findViewById(R.id.input_email);
		_passwordText = (EditText) findViewById(R.id.input_password);
		_loginButton = (TextView) findViewById(R.id.btn_Submit);
		_loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});
		findViewById(R.id.nav_icon).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void login() {

		if (!validate()) {
			// onLoginFailed();
			return;
		}
		String email = _emailText.getText().toString();
		String password = _passwordText.getText().toString();
		saveDetails(email, System.currentTimeMillis() + "", "", password, "0",
				System.currentTimeMillis() + "");
	}

	public boolean validate() {
		boolean valid = true;

		String email = _emailText.getText().toString();
		String password = _passwordText.getText().toString();

		if (email.isEmpty()) {
			showToast(AddManualReferralActivity.this, "Enter valid App name");
			valid = false;
		} else {
			_emailText.setError(null);
		}

		if (password.isEmpty()) {
			showToast(AddManualReferralActivity.this, "Enter valid Description");
			valid = false;
		} else {
			_passwordText.setError(null);
		}

		return valid;
	}

	private void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public void saveDetails(String appname, String pakckagename,
                            String base64image, String referraltext, String isfavouite,
                            String time) {
		AppInfo appInfo = new AppInfo();
		appInfo.setApp_name(appname + "");
		appInfo.setPackage_name_name(pakckagename + "");
		appInfo.setBase64_applogo(base64image + "");
		appInfo.setReferral_text(referraltext + "");
		temp = MyApplication.getInstance().getDaoSession().getAppInfoDao()
				.queryBuilder()
				.where(Properties.Package_name_name.eq(pakckagename + ""))
				.list();
		if (temp.size() > 0) {
			appInfo.setIs_favourite(temp.get(0).getIs_favourite() + "");
		} else {
			appInfo.setIs_favourite(isfavouite + "");
		}
		appInfo.setTime(time + "");

		MyApplication.getInstance().getDaoSession().insertOrReplace(appInfo);
		finish();

	}

}
