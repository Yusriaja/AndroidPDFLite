package com.kopihao.apdfl.demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.kopihao.apdfl.R;
import com.kopihao.apdfl.demo.Activity_ViewPDF.PDFViewCaller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * This project demo capability of JoanZapata's android-pdfview<br>
 * Relatively lightweight PDFViewer<br>
 * Custom view into your layout<br>
 * And yet offline.<br>
 * <br>
 * <u>Why use this</u><br>
 * Generic UI that cope with any project<br>
 * Simpler way to render PDF<br>
 * Activity Caller Method is intuitive.<br>
 * </p>
 *
 * @author Jasper
 */
public class Activity_Main extends Activity {

	private static final int PDF_REQUESTCODE = 9989;
	private Button btnOpen;
	private Button btnStream;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_main);
		btnOpen = (Button) findViewById(R.id.btnOpen);
		btnStream = (Button) findViewById(R.id.btnStream);
		btnOpen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				openPdf();
			}
		});
		btnStream.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				if (isNetworkAvailable()) {
					new StreamFile("http://oceanservice.noaa.gov/facts/coralbleaching.pdf").execute();
				} else {
					Toast.makeText(getBaseContext(), "Please check your internet connection.", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	protected void openPdf() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("application/pdf");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		Intent i = Intent.createChooser(intent, "PICK PDF");
		startActivityForResult(i, PDF_REQUESTCODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		final String ERR_PDF_NOTEXITS = "PDF NOT EXITS.";

		switch (requestCode) {
		case PDF_REQUESTCODE:
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				File myFile = new File(uri.getPath());
				PDFViewCaller.render(this, myFile);
			}
			break;
		case PDFViewCaller.REQUEST_CODE:
			Toast.makeText(this, "ERROR" + ERR_PDF_NOTEXITS, Toast.LENGTH_SHORT).show();
			break;
		}
	}

	class StreamFile extends AsyncTask<Void, String, String> {

		public StreamFile(String pUrl) {
			f_url = pUrl;
			pDialog = new ProgressDialog(Activity_Main.this);
			pDialog.setMessage("Streaming PDF from " + pUrl);
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(false);
			try {
				String fileName = pUrl.substring(pUrl.lastIndexOf('/') + 1, pUrl.length());
				fPath = Environment.getExternalStorageDirectory() + fileName;
			} catch (Exception e) {
			}

		}

		private ProgressDialog pDialog;
		private String fPath = Environment.getExternalStorageDirectory() + "/joza.jasper";
		private String f_url;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			int count;
			try {
				URL url = new URL(f_url);
				URLConnection conection = url.openConnection();
				conection.connect();
				int lenghtOfFile = conection.getContentLength();
				InputStream input = new BufferedInputStream(url.openStream(), 8192);
				OutputStream output = new FileOutputStream(fPath);
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}
				output.flush();
				output.close();
				input.close();
				return fPath;
			} catch (Exception e) {
			}
			return null;
		}

		protected void onProgressUpdate(String... progress) {
			pDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if (!TextUtils.isEmpty(file_url)) {
				PDFViewCaller.render(Activity_Main.this, file_url);
			}
		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
