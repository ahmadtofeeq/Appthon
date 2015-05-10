package ebills.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ebills.models.Item;
import com.apathon.ebills.R;

public class ItemAdapter extends BaseAdapter {

    private List<Item> objects = new ArrayList<Item>();

    private LayoutInflater layoutInflater;


    public ItemAdapter(Context context,List<Item> objects) {
        this.layoutInflater = LayoutInflater.from(context);
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Item getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_item_row, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Item object, ViewHolder holder) {
        holder.textView1.setText("Name: " +object.getColumn_item_name());
        holder.textView2.setText("Tag: " +object.getColumn_item_tag());
    }

    protected class ViewHolder {
        private TextView textView1, textView2;
    }
}
