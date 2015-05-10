package ebills.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.apathon.ebills.R;
import com.squareup.picasso.Picasso;

import ebills.models.Bill_Pic;

public class PicAdapter extends BaseAdapter {

    private List<Bill_Pic> objects = new ArrayList<Bill_Pic>();

    private Context context;
    private LayoutInflater layoutInflater;


    public PicAdapter(Context context,List<Bill_Pic> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        
        for (Iterator<Bill_Pic> iterator = objects.iterator(); iterator.hasNext();) {
			Bill_Pic bill_Pic = (Bill_Pic) iterator.next();
			if (!TextUtils.isEmpty(bill_Pic.getColumn_image_path())) {
				this.objects.add(bill_Pic);
			}
		}
        
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Bill_Pic getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
        	ViewHolder viewHolder = new ViewHolder();
        	convertView = layoutInflater.inflate(R.layout.layout_pic_item, null);
            viewHolder.mImageView = (ImageView) convertView;
            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Bill_Pic object, ViewHolder holder) {
    	
    	Picasso.with(context).load(new File(object.getColumn_image_path())).into(holder.mImageView);
    }

    protected class ViewHolder {
        private ImageView mImageView;
    }
}
