package com.apathon.ebills;

import java.util.ArrayList;

import ebills.adapters.PicAdapter;
import ebills.models.Bill_Pic;
import ebills.screens.FullImageView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ThumbsAtivity extends Activity implements OnItemClickListener {

	private GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.button)));
		setContentView(R.layout.layout_thumbs);
		mGridView = (GridView) findViewById(R.id.gridView);
		ArrayList<Bill_Pic> mPics = App.getDb().getAllBill_Pics();
		if (mPics != null) {
			mGridView.setAdapter(new PicAdapter(this, mPics));
		}
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Bill_Pic mPic = (Bill_Pic) parent.getAdapter().getItem(position);
		Intent mIntent = new Intent(this, FullImageView.class);
		mIntent.putExtra("path", mPic.getColumn_image_path());
		startActivity(mIntent);

	}
}
