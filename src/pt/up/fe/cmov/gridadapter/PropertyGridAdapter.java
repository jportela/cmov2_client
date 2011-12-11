package pt.up.fe.cmov.gridadapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import pt.up.cmov.entities.Property;
import pt.up.fe.cmov.app.PropertyMarketActivity;
import pt.up.fe.cmov.app.PropertyTabMenuActivity;
import pt.up.fe.cmov.display.Display;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

    public View getView(final int position, View convertView, ViewGroup parent) {
    	Property property = properties.get(position);
    	LinearLayout layout;
    	ImageView imageView;
        if (convertView == null) {  
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
            
	        try {
	        	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(RailsRestClient.SERVER_URL + property.getPhoto()).getContent());
	        	  imageView.setImageBitmap(bitmap); 
	        	} catch (MalformedURLException e) {
	        	  e.printStackTrace();
	        	} catch (IOException e) {
	        	  e.printStackTrace();
	        }
	        
	        imageView.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	              PropertyMarketActivity.selectedPropertyID = getItemId(position);
	              Intent intent = new Intent(mContext,PropertyTabMenuActivity.class);
	              mContext.startActivity(intent);
	            }
	          });
	        
	        imageView.setOnLongClickListener(new OnLongClickListener() {

	            @Override
	            public boolean onLongClick(View v) {
	            	Display.dialogBuildDeleteDiscard(getItemId(position),mContext);
	                return true;
	            }
	        });

	        
	        layout = new LinearLayout(mContext);
	        layout.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	        layout.setOrientation(LinearLayout.VERTICAL);
	        layout.setGravity(Gravity.CENTER);
	        
	        layout.addView(imageView);
        } else {
            layout = (LinearLayout) convertView;
        }
        return layout;
    }
}
