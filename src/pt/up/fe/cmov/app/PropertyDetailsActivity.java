package pt.up.fe.cmov.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import pt.up.fe.cmov.display.Display;
import pt.up.fe.cmov.listadapter.EntryAdapter;
import pt.up.fe.cmov.listadapter.EntryItem;
import pt.up.fe.cmov.listadapter.Item;
import pt.up.fe.cmov.propertymarket.R;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PropertyDetailsActivity extends ListActivity {
	
	ArrayList<Item> items = new ArrayList<Item>();
	private final int syncBtnId = Menu.FIRST;	
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
	        try {
	        	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(RailsRestClient.SERVER_URL + PropertyTabMenuActivity.propertyInfo.getPhoto()).getContent());
	        	  items.add(new EntryItem(bitmap,false));
	        	  items.add(new EntryItem(this.getString(R.string.property_name),PropertyTabMenuActivity.propertyInfo.getName(),false));
				  items.add(new EntryItem(this.getString(R.string.property_state),PropertyTabMenuActivity.propertyInfo.getState(),false));		
	        	} catch (MalformedURLException e) {
	        	  e.printStackTrace();
	        	} catch (IOException e) {
	        	  e.printStackTrace();
	        }
	                
		 	EntryAdapter adapter = new EntryAdapter(this, items);
		 	setListAdapter(adapter);
	 }
	 
	 @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuItem searchMItm = menu.add(Menu.NONE,syncBtnId ,syncBtnId,this.getString(R.string.discard_menu));
	    searchMItm.setIcon(android.R.drawable.ic_menu_delete);
	    return super.onCreateOptionsMenu(menu);
	  }
	
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		        case syncBtnId:
	            	Display.dialogBuildDeleteDiscard(PropertyTabMenuActivity.propertyInfo.getId(),this);
		        break;
		    }
		    return true;
		}
}
