package ebills.screens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;

import com.apathon.ebills.App;
import com.apathon.ebills.R;

import ebills.adapters.ItemAdapter;
import ebills.adapters.SearchSuggestionAdapter;
import ebills.db.DataBaseHelper;

/**
 * Created by Suraj on 09-05-2015.
 */
public class SearchActivity extends Activity {
	private SearchSuggestionAdapter mSuggestionAdapter;
	private ListView mListView;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.button)));
		setContentView(R.layout.layout_search);
		mListView = (ListView) findViewById(R.id.list);
		Cursor cursor = App.getDb().search("");
		mSuggestionAdapter = new SearchSuggestionAdapter(this, cursor);


		
		
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_search, menu);
		final SearchView searchView = (SearchView) menu
				.findItem(R.id.action_search).getActionView();
		searchView.setIconifiedByDefault(false);
		searchView.setSuggestionsAdapter(mSuggestionAdapter);
		searchView.setOnSuggestionListener(new OnSuggestionListener() {
			@Override
			public boolean onSuggestionSelect(int position) {
				searchView.setQuery(
						mSuggestionAdapter.getColumnString(position), true);
				return false;
			}

			@Override
			public boolean onSuggestionClick(int position) {
				searchView.setQuery(
						mSuggestionAdapter.getColumnString(position), true);
				return false;
			}
		});
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				onSearch(s);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				mSuggestionAdapter.getFilter().filter(s);
				mSuggestionAdapter.notifyDataSetChanged();
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	private void onSearch(String query) {
		mListView.setAdapter(new ItemAdapter(this, App.getDb().searchItem(
				query, DataBaseHelper.Column_item_tag)));
	}

}
