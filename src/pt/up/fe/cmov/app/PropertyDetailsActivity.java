package pt.up.fe.cmov.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import pt.up.fe.cmov.listadapter.EntryAdapter;
import pt.up.fe.cmov.listadapter.EntryItem;
import pt.up.fe.cmov.listadapter.Item;
import pt.up.fe.cmov.propertymarket.R;
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
			
	        try {
	        	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(RailsRestClient.SERVER_URL + PropertyTabMenu.propertyInfo.getPhoto()).getContent());
	        	  items.add(new EntryItem(bitmap,false));
	        	  items.add(new EntryItem(this.getString(R.string.property_name),PropertyTabMenu.propertyInfo.getName(),false));
				  items.add(new EntryItem(this.getString(R.string.property_state),PropertyTabMenu.propertyInfo.getState(),false));		
	        	} catch (MalformedURLException e) {
	        	  e.printStackTrace();
	        	} catch (IOException e) {
	        	  e.printStackTrace();
	        }
	                
		 	EntryAdapter adapter = new EntryAdapter(this, items);
		 	setListAdapter(adapter);
	 }
}
