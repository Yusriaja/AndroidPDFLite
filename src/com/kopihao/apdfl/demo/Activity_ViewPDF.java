package com.kopihao.apdfl.demo;

import java.io.File;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.kopihao.apdfl.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_ViewPDF extends Activity implements OnPageChangeListener {

	private static final String ERR_PDF_NOTEXITS = "PDF NOT EXITS.";

	private static final String STATE_FILEPATH = "STATE_URI";
	private static File TARGET_FILE = null;
	private static String TARGET_NAME = "";

	private TextView tvPageStat;

	public static class PDFViewCaller {

		public static final int REQUEST_CODE = 939125863;

		public static int render(Activity curAct, String pdfPath) {
			return render(curAct, new File(pdfPath));
		}

		public static int render(Activity curAct, File pdfFile) {
			TARGET_FILE = pdfFile;
			TARGET_NAME = pdfFile.getName();
			Intent intent = new Intent(curAct, Activity_ViewPDF.class);
			curAct.startActivityForResult(intent, REQUEST_CODE);
			return REQUEST_CODE;
		}
	}

	// ----------------------------------------
	// Activity Logic
	// ----------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_render_pdf);
		tvPageStat = (TextView) findViewById(R.id.tvPageStat);
		PDFView pdfView = (PDFView) findViewById(R.id.vPdfContent);
		pdfView.hasFocus();
		if (TARGET_FILE == null) {
			if (savedInstanceState != null && savedInstanceState.containsKey(STATE_FILEPATH)) {
				TARGET_FILE = new File(savedInstanceState.getString(STATE_FILEPATH));
			}
		}
		if (TARGET_FILE != null && TARGET_FILE.exists()) {
			pdfView.fromFile(TARGET_FILE).onPageChange(this).defaultPage(1).showMinimap(false).enableSwipe(true).load();
		} else {
			Toast.makeText(this, "ERROR" + ERR_PDF_NOTEXITS, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(STATE_FILEPATH, TARGET_FILE.getPath());
	}

	@Override
	public void onPageChanged(int page, int pageCount) {
		String title = String.format("(%s/%s) %s ", page, pageCount, TARGET_NAME);
		tvPageStat.setText("" + title);
	}

}