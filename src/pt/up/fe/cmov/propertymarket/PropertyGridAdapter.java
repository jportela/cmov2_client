package pt.up.fe.cmov.propertymarket;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PropertyGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Property> properties;

    public PropertyGridAdapter(Context c, ArrayList<Property> properties) {
        mContext = c;
        this.properties = properties;
    }

    public int getCount() {
        return properties.size();
    }

    public Object getItem(int position) {
        return properties.get(position);
    }

    public long getItemId(int position) {
        return properties.get(position).getId();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	Property property = properties.get(position);
    	LinearLayout layout;
    	ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            
        
	        try {
	        	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(RailsRestClient.SERVER_URL + property.getPhoto()).getContent());
	        	  imageView.setImageBitmap(bitmap); 
	        	} catch (MalformedURLException e) {
	        	  e.printStackTrace();
	        	} catch (IOException e) {
	        	  e.printStackTrace();
	        }
	        
	        layout = new LinearLayout(mContext);
	        layout.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	        layout.setOrientation(LinearLayout.VERTICAL);
	        layout.setGravity(Gravity.CENTER);
	        
	        TextView title = new TextView(mContext);
	        title.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	        title.setText(property.getName());
	        
	        layout.addView(imageView);
	        layout.addView(title);
        } else {
            layout = (LinearLayout) convertView;
        }
        
        
        return layout;
    }


}
