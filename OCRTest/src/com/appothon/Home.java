package com.appothon;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import edu.sfsu.cs.orange.ocr.CaptureActivity;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tagList.add("CITY");
		tagList.add("STATE");
		tagList.add("PIN");
		tagList.add("TIN");
		tagList.add("Seller");
		startActivityForResult(new Intent(this, CaptureActivity.class), 101);
	}

	int requestCode = 101;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_CANCELED) {
			Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show();
		} else if (this.requestCode == requestCode
				&& resultCode == Activity.RESULT_OK) {
			String txt = data.getStringExtra("data");
			getValuesOfAllField(txt, tagList);
		}
	}

	ArrayList<String> tagList = new ArrayList<String>();

	public HashMap<String, String> getValuesOfAllField(String mReadingString,
			ArrayList<String> mListTag) {
		mReadingString=mReadingString.replace(": ", "");
		Log.e("Whole String", mReadingString);
		HashMap<String, String> mKeyValue = new HashMap<String, String>();
		String[] allTag = mReadingString.split(" ");
		for (String temp : mListTag) {

			for (int i = 0; i < allTag.length; i++) {
				if (allTag[i].contains(temp) || temp.contains(allTag[i])) {
					try {
						mKeyValue.put(temp, allTag[i++]);
						break;
					} catch (ArrayIndexOutOfBoundsException e) {
						mKeyValue.put(temp, "");
						break;
					}
				}
			}
		}
		Log.e("values", mKeyValue.toString());
		return mKeyValue;
	}
}
