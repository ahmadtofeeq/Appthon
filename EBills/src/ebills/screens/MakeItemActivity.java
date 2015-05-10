package ebills.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apathon.ebills.App;
import com.apathon.ebills.R;

import ebills.models.Bill_Pic;
import ebills.models.Item;
import ebills.models.Tags;

public class MakeItemActivity extends ActionBarActivity implements View.OnClickListener {

	String imagePath, imageSellerId, imageName;
	private boolean[] checkedItems;
	private ArrayList<Tags> tags;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.button)));
		setContentView(R.layout.layout_insert_item);
		imagePath = getIntent().getStringExtra("imagePath");
		imageSellerId = getIntent().getStringExtra("imageSellerId");
		imageName = getIntent().getStringExtra("imageName");
		findViewById(R.id.submit).setOnClickListener(this);
		findViewById(R.id.addTag).setOnClickListener(this);

	}

	private EditText getName() {
		return (EditText) findViewById(R.id.name);
	}

	private EditText getDescription() {
		return (EditText) findViewById(R.id.description);
	}

	private EditText getInvoice() {
		return (EditText) findViewById(R.id.invoice);
	}

	private EditText getOrderNo() {
		return (EditText) findViewById(R.id.orderNo);
	}

	private EditText getAmount() {
		return (EditText) findViewById(R.id.amount);
	}

	private EditText getTags() {
		return (EditText) findViewById(R.id.tags);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.submit:
			Item item = new Item();
			item.setColumn_item_name(getName().getText().toString());
			item.setColumn_item_desc(getDescription().getText().toString());
			item.setColumn_invoice_no(getInvoice().getText().toString());
			item.setColumn_order_no(getOrderNo().getText().toString());
			item.setColumn_item_amount(getAmount().getText().toString());
			item.setColumn_item_tag(getTags().getText().toString());

			Bill_Pic bill_Pic = new Bill_Pic();
			bill_Pic.setColumn_image_name(imageName);
			bill_Pic.setColumn_image_path(imagePath);
			bill_Pic.setColumn_image_seller_id(imageSellerId);

			long insertBill_Pic = App.getDb().insertBill_Pic(bill_Pic);
			item.setColumn_bill_pic_id(insertBill_Pic + "");

			long l = App.getDb().insertItem(item);

			if (l != -1) {
				Toast.makeText(getApplicationContext(), "Item details Inserted successfully.", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), BrowseActivity.class);
				startActivity(intent);

			} else {
				Toast.makeText(getApplicationContext(), "Oops something went wrong!", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.addTag:

			tags = App.getDb().getAllTags();

			String[] items = new String[tags.size()];
			checkedItems = new boolean[tags.size()];
			Arrays.fill(checkedItems, true);
			int i = 0;
			for (Iterator<Tags> iterator = tags.iterator(); iterator.hasNext();) {
				Tags tag = (Tags) iterator.next();
				items[i++] = tag.getColumn_tags_name();
			}
			AlertDialog.Builder mBuilder = new AlertDialog.Builder(this).setTitle("Set Tags").setMultiChoiceItems(items, checkedItems, new OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					checkedItems[which] = isChecked;
				}
			}).setPositiveButton("Done", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					int i = 0;
					StringBuilder mBuilder = new StringBuilder();
					for (Iterator<Tags> iterator = tags.iterator(); iterator.hasNext();) {
						Tags tag = (Tags) iterator.next();
						if (checkedItems[i]) {
							mBuilder.append(tag.getColumn_tags_name() + " , ");
						}
						i++;
					}
					getTags().setText(mBuilder.toString());
				}
			});
			mBuilder.show();

			// new
			// CreateTagDialog().show(getSupportFragmentManager(),CreateTagDialog.class.getName());

			break;

		}
	}

}
