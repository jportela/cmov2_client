package pt.up.fe.cmov.propertymarket;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import pt.up.cmov.entities.Property;
import pt.up.fe.cmov.listadapter.EntryAdapter;
import pt.up.fe.cmov.listadapter.EntryItem;
import pt.up.fe.cmov.listadapter.Item;
import pt.up.fe.cmov.propertymarket.rest.JSONOperations;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PropertyDetailsActivity extends ListActivity {
	
	ArrayList<Item> items = new ArrayList<Item>();
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.list_property_details);
			Property propertyInfo = null;	        
			try {
				JSONObject selectedProperty = RailsRestClient.Get("properties/"+ PropertyMarketActivity.selectedPropertyID +"/long");
				propertyInfo = JSONOperations.JSONToProperty(selectedProperty);
				items.add(new EntryItem(this.getString(R.string.property_name),propertyInfo.getName()));
				items.add(new EntryItem(this.getString(R.string.property_city),propertyInfo.getCity()));
				items.add(new EntryItem(this.getString(R.string.property_address),propertyInfo.getAddress()));
				items.add(new EntryItem(this.getString(R.string.property_state),propertyInfo.getState()));
				items.add(new EntryItem(this.getString(R.string.property_type),propertyInfo.getType()));
				items.add(new EntryItem(this.getString(R.string.property_description),propertyInfo.getDescription()));				
			} catch (ConnectTimeoutException e) {
				items.add(new EntryItem(this.getString(R.string.propert_failed_1),this.getString(R.string.property_failed_2)));
			}
			
			ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
            imageView.setPadding(20, 20, 20, 20);
            
	        
	        try {
	        	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(RailsRestClient.SERVER_URL + propertyInfo.getPhoto()).getContent());
	        	  imageView.setImageBitmap(bitmap); 
	        	} catch (MalformedURLException e) {
	        	  e.printStackTrace();
	        	} catch (IOException e) {
	        	  e.printStackTrace();
	        }
	        
	        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout1);
	        layout.addView(imageView);
		 	EntryAdapter adapter = new EntryAdapter(this, items);
		 	setListAdapter(adapter);
	 }
}
