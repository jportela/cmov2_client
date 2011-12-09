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

public class PropertyDetailsActivity extends ListActivity {
	
	ArrayList<Item> items = new ArrayList<Item>();
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Property propertyInfo = null;	 
			
			try {
				JSONObject selectedProperty = RailsRestClient.Get("properties/"+ PropertyMarketActivity.selectedPropertyID +"/long");
				propertyInfo = JSONOperations.JSONToProperty(selectedProperty);		
			} catch (ConnectTimeoutException e) {
				e.printStackTrace();
			}	
	        
	        try {
	        	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(RailsRestClient.SERVER_URL + propertyInfo.getPhoto()).getContent());
	        	  items.add(new EntryItem(bitmap,false));
	        	  items.add(new EntryItem(this.getString(R.string.property_name),propertyInfo.getName(),false));
				  items.add(new EntryItem(this.getString(R.string.property_city),propertyInfo.getCity(),false));
				  items.add(new EntryItem(this.getString(R.string.property_address),propertyInfo.getAddress(),false));
				  items.add(new EntryItem(this.getString(R.string.property_state),propertyInfo.getState(),false));
				  items.add(new EntryItem(this.getString(R.string.property_type),propertyInfo.getType(),false));
				  items.add(new EntryItem(this.getString(R.string.property_description),propertyInfo.getDescription(),false));		
	        	} catch (MalformedURLException e) {
	        	  e.printStackTrace();
	        	} catch (IOException e) {
	        	  e.printStackTrace();
	        }
	                
		 	EntryAdapter adapter = new EntryAdapter(this, items);
		 	setListAdapter(adapter);
	 }
}
