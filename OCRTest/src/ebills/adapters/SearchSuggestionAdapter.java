package ebills.adapters;

import android.content.Context;
import android.database.Cursor;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;

import com.apathon.ebills.App;

import ebills.db.DataBaseHelper;
import com.apathon.ebills.R;

/**
 * Created by Suraj on 09-05-2015.
 */
public class SearchSuggestionAdapter extends SimpleCursorAdapter {
    public SearchSuggestionAdapter(Context context, Cursor c) {
        super(context, R.layout.layout_search_item, c, new String[]{DataBaseHelper.Column_tag_name}, new int[]{R.id.search}, FLAG_REGISTER_CONTENT_OBSERVER);
//        setFilterQueryProvider(new FilterQueryProvider() {
//            @Override
//            public Cursor runQuery(CharSequence constraint) {
////                return App.getDb().search(constraint.toString());
//            }
//        });
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        FilterQueryProvider filter = getFilterQueryProvider();
        if (filter != null) {
            return filter.runQuery(constraint);
        }
        return App.getDb().search(constraint.toString());
    }

    public String getColumnString(int position) {
        getCursor().moveToPosition(position);
        return getCursor().getString(getCursor().getColumnIndex(DataBaseHelper.Column_tag_name));

    }

}
