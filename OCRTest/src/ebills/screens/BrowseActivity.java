package ebills.screens;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.apathon.ebills.App;
import com.apathon.ebills.R;

import ebills.adapters.ItemAdapter;
import ebills.models.Bill_Pic;
import ebills.models.Item;

public class BrowseActivity extends ListActivity {

	private ArrayList<Item> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.button)));
        setContentView(R.layout.list);

         allItems = App.getDb().getAllItems();
        setListAdapter(new ItemAdapter(getApplicationContext(),allItems));
        getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				ArrayList<Bill_Pic> bill_Pic = App.getDb().searchBill_Pic(allItems.get(position).getColumn_bill_pic_id());
				if(bill_Pic==null||bill_Pic.size()==0){
					Toast.makeText(BrowseActivity.this,"Something went wrong.", Toast.LENGTH_LONG).show();
					return;
				}
				String imagePath  = bill_Pic.get(0).getColumn_image_path();
				Intent mIntent=new Intent(BrowseActivity.this, FullImageView.class);
				mIntent.putExtra("path", imagePath);
				startActivity(mIntent);
				
			}
		});

    }

}