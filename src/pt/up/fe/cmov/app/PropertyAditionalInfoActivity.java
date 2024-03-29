package pt.up.fe.cmov.app;

import java.util.ArrayList;

import pt.up.fe.cmov.display.Display;
import pt.up.fe.cmov.listadapter.EntryAdapter;
import pt.up.fe.cmov.listadapter.EntryItem;
import pt.up.fe.cmov.listadapter.Item;
import pt.up.fe.cmov.listadapter.SectionItem;
import pt.up.fe.cmov.propertymarket.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PropertyAditionalInfoActivity extends ListActivity {
	
	ArrayList<Item> items = new ArrayList<Item>();
	private final int syncBtnId = Menu.FIRST;	
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			items.add(new SectionItem(this.getString(R.string.details_section)));
			items.add(new EntryItem(this.getString(R.string.property_price),Integer.toString(PropertyTabMenuActivity.propertyInfo.getPrice()),false));
			items.add(new EntryItem(this.getString(R.string.property_type),PropertyTabMenuActivity.propertyInfo.getType(),false));
			items.add(new SectionItem(this.getString(R.string.location_section)));
			items.add(new EntryItem(this.getString(R.string.property_city),PropertyTabMenuActivity.propertyInfo.getCity(),false));
			items.add(new EntryItem(this.getString(R.string.property_address),PropertyTabMenuActivity.propertyInfo.getAddress(),false));
			items.add(new SectionItem(this.getString(R.string.aditional_section)));
			items.add(new EntryItem(this.getString(R.string.property_description),PropertyTabMenuActivity.propertyInfo.getDescription(),false));
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
