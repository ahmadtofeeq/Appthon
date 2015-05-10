package com.apathon.ebills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ebills.models.Tags;
import ebills.screens.BrowseActivity;
import ebills.screens.MakeSellerActivity;
import ebills.screens.SearchActivity;
import edu.sfsu.cs.orange.ocr.CaptureActivity;

@SuppressLint("DefaultLocale")
public class MainActivity extends ActionBarActivity implements View.OnClickListener {

	Button btnBrowse_Bills, btnSearch_Bills, btnAdd_Bills;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.button)));
		setContentView(R.layout.activity_main);
		btnBrowse_Bills = (Button) findViewById(R.id.btnBrowse_Bills);
		btnSearch_Bills = (Button) findViewById(R.id.btnSearch_Bills);
		btnAdd_Bills = (Button) findViewById(R.id.btnAdd_Bills);
		btnBrowse_Bills.setOnClickListener(this);
		btnSearch_Bills.setOnClickListener(this);
		btnAdd_Bills.setOnClickListener(this);
	}

	public void addTag(View mView) {

		final EditText mEditText1 = (EditText) LayoutInflater.from(this).inflate(R.layout.layout_edittext, null);
		AlertDialog.Builder mBuilder = new Builder(this).setTitle("Write your tag").setView(mEditText1).setPositiveButton("Save", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == DialogInterface.BUTTON_POSITIVE) {
					String text = mEditText1.getText().toString();
					Tags tags = new Tags();
					tags.setColumn_tags_name(text);
					long l = App.getDb().insertTags(tags);
					if (l != -1) {
						Toast.makeText(MainActivity.this, "Tag Inserted Successfully", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(MainActivity.this, "Opps , there's some problem!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}).setNegativeButton("Cancel", null);
		mBuilder.show();
	}

	public void viewBill(View mView) {
		Intent mIntent = new Intent(this, ThumbsAtivity.class);
		startActivity(mIntent);
	}


	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btnAdd_Bills:
			// intent = new Intent(getApplicationContext(),
			// MakeSellerActivity.class);
			// startActivity(intent);
			startActivityForResult(new Intent(this, CaptureActivity.class), 101);
			break;
		case R.id.btnBrowse_Bills:
			intent = new Intent(getApplicationContext(), BrowseActivity.class);
			startActivity(intent);
			break;
		case R.id.btnSearch_Bills:
			intent = new Intent(getApplicationContext(), SearchActivity.class);
			startActivity(intent);

			break;

		}

	}

	int requestCode = 101;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_CANCELED) {
			Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show();
		} else if (this.requestCode == requestCode && resultCode == Activity.RESULT_OK) {
			String txt = data.getStringExtra("data");
			ArrayList<Tags> mTagList = App.getDb().getAllTags();
			HashMap<String, String> mList = getValuesOfAllField(txt, mTagList);
			Intent mIntent = new Intent(this, MakeSellerActivity.class);
			mIntent.putExtra("info", mList);
			mIntent.putExtra("path", data.getExtras().getString("path"));
			startActivity(mIntent);
		}
	}

	@SuppressLint("DefaultLocale")
	public HashMap<String, String> getValuesOfAllField(String mReadingString, ArrayList<Tags> mListTag) {

		/*mReadingString = squeez(mReadingString);
		char[] charArray = mReadingString.toCharArray();
		for (int i = 0; i < mListTag.size(); i++) {
			String wordToFind =mListTag.get(i).getColumn_tags_name();
			
			
		}*/
		//mReadingString = mReadingString.replace(":", " ");
		mReadingString = mReadingString.replace("number", " ");
		mReadingString = mReadingString.replace("Number", " ");
		mReadingString = mReadingString.replace("NUMBER", " ");
		Log.e("Whole String", mReadingString);
		String patternString = "\\s+";
		Pattern pattern = Pattern.compile(patternString);
		HashMap<String, String> mKeyValue = new HashMap<String, String>();
		String[] allTag = pattern.split(mReadingString);
		for (Tags temp : mListTag) {

			for (int i = 0; i < allTag.length; i++) {
				if (allTag[i].toLowerCase().contains(temp.getColumn_tags_name().toLowerCase()) || temp.getColumn_tags_name().toLowerCase().contains(allTag[i].toLowerCase())) {
					try {
						int j = i;
						if (allTag[++j].length() < 5) {
							if (temp.getColumn_tags_name().equalsIgnoreCase("seller")) {
								mKeyValue.put(temp.getColumn_tags_name(), allTag[++j] + " " + allTag[++j] + " " + allTag[++j]);
							} else {
								mKeyValue.put(temp.getColumn_tags_name(), allTag[++j]);
							}
						}

						else
							mKeyValue.put(temp.getColumn_tags_name(), allTag[j]);
						break;
					} catch (ArrayIndexOutOfBoundsException e) {
						mKeyValue.put(temp.getColumn_tags_name(), "");
						break;
					}
				}
			}
		}
		Log.e("values", mKeyValue.toString());
		return mKeyValue;
	}

	public String squeez(String string) {
		char[] charArray = string.toCharArray();
		String newString = new String();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] != ' ') {
				newString = newString + charArray[i];
			}
		}

		return newString;

	}
}
