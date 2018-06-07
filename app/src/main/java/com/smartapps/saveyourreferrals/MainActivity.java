package com.smartapps.saveyourreferrals;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.smartapps.saveyourreferrals.dao.AppInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import saveyourreferrals.lohith.com.saveyourreferrals.R;

public class MainActivity extends Activity {
	private RecyclerView recyclerView;
	private LinearLayoutManager mLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout_container);
		adjustTheRecyclerViewAccordingly();
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if ("text/plain".equals(type)) {
				handleSendText(intent); // Handle text being sent
			} else if (type.startsWith("image/")) {
				handleSendText(intent); // Handle single image being sent
			}
		} else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
			if (type.startsWith("image/")) {
				handleSendMultipleImages(intent); // Handle multiple images
													// being sent
			}
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				AppOpsManager appOps = (AppOpsManager) this
						.getSystemService(Context.APP_OPS_SERVICE);
				int mode = appOps.checkOpNoThrow("android:get_usage_stats",
						android.os.Process.myUid(), this.getPackageName());
				boolean granted = mode == AppOpsManager.MODE_ALLOWED;
				if (!granted) {
					startActivity(new Intent(
							Settings.ACTION_USAGE_ACCESS_SETTINGS));
				}
			}
		}

	}

	void handleSendText(Intent intent) {
		if (!intent.hasExtra(Intent.EXTRA_TEXT)) {
			return;
		}
		String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (TextUtils.isEmpty(sharedText)) {
			return;
		}
		// TextView textView = (TextView) findViewById(R.id.tv);
		// textView.setText(sharedText + "");
		String topPackageName = "";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			try {
				AppOpsManager appOps = (AppOpsManager) this
						.getSystemService(Context.APP_OPS_SERVICE);
				int mode = appOps.checkOpNoThrow("android:get_usage_stats",
						android.os.Process.myUid(), this.getPackageName());
				boolean granted = mode == AppOpsManager.MODE_ALLOWED;
				if (!granted) {
					Toast.makeText(
							MainActivity.this,
							"Some information can not be fetched due to lack of permission to give permission please go to settings",
							Toast.LENGTH_SHORT).show();
					return;
				}
				UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService("usagestats");
				long time = System.currentTimeMillis();
				// We get usage stats for the last 10 seconds
				List<UsageStats> stats = mUsageStatsManager.queryUsageStats(
						UsageStatsManager.INTERVAL_DAILY, time - 1000 * 20,
						time);
				// Sort the stats by the last time used
				if (stats != null) {
					// SortedMap<Long, UsageStats> mySortedMap = new
					// TreeMap<Long,
					// UsageStats>();
					// for (UsageStats usageStats : stats) {
					// if (!usageStats.getPackageName()
					// .equalsIgnoreCase("android"))
					// mySortedMap.put(usageStats.getLastTimeUsed(),
					// usageStats);
					// }
					// if (mySortedMap != null && !mySortedMap.isEmpty()) {
					long lastUsedAppTime = 0;
					for (UsageStats usageStats : stats) {

						if (usageStats.getLastTimeUsed() > lastUsedAppTime) {
							if (usageStats.getPackageName().contains("process")
									|| usageStats.getPackageName().contains(
											"launcher")
									|| usageStats.getPackageName().equals(
											"android")
									|| usageStats.getPackageName().equals(
											getPackageName())) {
							} else {
								topPackageName = usageStats.getPackageName();
								lastUsedAppTime = usageStats.getLastTimeUsed();
							}
						}
					}

					// textView.setText(topPackageName + "");
					// final ImageView iv = (ImageView) findViewById(R.id.iv);
					Drawable icon;
					try {
						icon = getPackageManager().getApplicationIcon(
								"" + topPackageName);
						saveDetails(getAppNameFromPackage(topPackageName),
								topPackageName, encodeIcon(icon), sharedText,
								"0", "" + System.currentTimeMillis());
						// iv.setImageDrawable(icon);
					} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				saveDetails(getAppNameFromPackage(""),
						System.currentTimeMillis() + "", "", sharedText, "0",
						"" + System.currentTimeMillis());
			}
		} else {
			try {
				ActivityManager am = (ActivityManager) this
						.getSystemService(ACTIVITY_SERVICE);
				List<ActivityManager.RecentTaskInfo> recentTasks = am
						.getRecentTasks(1, ActivityManager.RECENT_WITH_EXCLUDED);
				for (int i = 0; i < 1; i++) {
					final ActivityManager.RecentTaskInfo recentInfo = recentTasks
							.get(i);

					Intent recentintent = new Intent(recentInfo.baseIntent);
					if (recentInfo.origActivity != null) {
						recentintent.setComponent(recentInfo.origActivity);
					}

					final PackageManager pm = getPackageManager();
					final ResolveInfo resolveInfo = pm.resolveActivity(
							recentintent, 0);
					final ActivityInfo info = resolveInfo.activityInfo;
					final String title = info.loadLabel(pm).toString();

					Log.d("hello", "  " + title + " " + info.packageName);
					topPackageName = info.packageName;
					// final ImageView iv = (ImageView) findViewById(R.id.iv);
					// iv.setImageDrawable(info.loadIcon(pm));

					saveDetails(getAppNameFromPackage(topPackageName),
							topPackageName, encodeIcon(info.loadIcon(pm)),
							sharedText, "0", "" + System.currentTimeMillis());
				}
			} catch (Exception e) {
				saveDetails(getAppNameFromPackage(""),
						System.currentTimeMillis() + "", "", sharedText, "0",
						"" + System.currentTimeMillis());
			}
		}
		if (sharedText != null) {
			// Update UI to reflect text being shared
		}
	}

	void handleSendImage(Intent intent) {
		Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (imageUri != null) {
			// Update UI to reflect image being shared
		}
	}

	void handleSendMultipleImages(Intent intent) {
		ArrayList<Uri> imageUris = intent
				.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		if (imageUris != null) {
			// Update UI to reflect multiple images being shared
		}
	}

	private void adjustTheRecyclerViewAccordingly() {
		recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		int orientation;
		if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			orientation = LinearLayoutManager.VERTICAL;
		} else {
			orientation = LinearLayoutManager.HORIZONTAL;
		}
		mLayoutManager = new LinearLayoutManager(this, orientation, false);
		recyclerView.setLayoutManager(mLayoutManager);
		refreshTheData();
	}

	public String getAppNameFromPackage(String pakcagenmae) {
		try {
			final PackageManager pm = getApplicationContext()
					.getPackageManager();
			ApplicationInfo ai;
			try {
				ai = pm.getApplicationInfo(pakcagenmae, 0);
			} catch (final NameNotFoundException e) {
				ai = null;
			}
			final String applicationName = (String) (ai != null ? pm
					.getApplicationLabel(ai) : "(unknown)");
			return applicationName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "(unknown)";
		}
	}

	public void saveDetails(String appname, String pakckagename,
                            String base64image, String referraltext, String isfavouite,
                            String time) {
		AppInfo appInfo = new AppInfo();
		appInfo.setApp_name(appname + "");
		appInfo.setPackage_name_name(pakckagename + "");
		appInfo.setBase64_applogo(base64image + "");
		appInfo.setReferral_text(referraltext + "");
		appInfo.setIs_favourite(isfavouite + "");
		appInfo.setTime(time + "");
		MyApplication.getInstance().getDaoSession()
				.insertOrReplace(appInfo);

		refreshTheData();

	}

	private void refreshTheData() {
		if (recyclerView != null)
			recyclerView
					.setAdapter(new RreferalAdapter(null, true, this, false));
	}

	public String encodeIcon(Drawable icon) {
		try {
			Drawable ic = icon;

			if (ic != null) {

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				// bitmap.compress(CompressFormat.PNG, 0, outputStream);

				BitmapDrawable bitDw = ((BitmapDrawable) ic);
				Bitmap bitmap = bitDw.getBitmap();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				byte[] bitmapByte = stream.toByteArray();

				String base64String = Base64.encodeToString(bitmapByte,
						Base64.DEFAULT);
				System.out.println("..length of image..." + bitmapByte.length);
				return base64String;
			} else {
				return "";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}

	}
}
