package ebills.screens;

import java.io.File;

import com.apathon.ebills.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

public class FullImageView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.button)));
		setContentView(R.layout.full_imageview);
		String path = getIntent().getStringExtra("path");

		if (!TextUtils.isEmpty(path)) {
			ImageView mImageView = (ImageView) findViewById(R.id.image_view);
			Picasso.with(this).load(new File(path)).into(mImageView);
		}
	}

}
