package pt.up.fe.cmov.app;

import java.util.ArrayList;

import pt.up.fe.cmov.listadapter.EntryAdapter;
import pt.up.fe.cmov.listadapter.EntryItem;
import pt.up.fe.cmov.listadapter.Item;
import pt.up.fe.cmov.propertymarket.R;
import android.app.ListActivity;
import android.os.Bundle;

public class PropertyAditionalInfoActivity extends ListActivity {
	
	ArrayList<Item> items = new ArrayList<Item>();
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			items.add(new EntryItem(this.getString(R.string.property_price),Integer.toString(PropertyTabMenuActivity.propertyInfo.getPrice()),false));
			items.add(new EntryItem(this.getString(R.string.property_city),PropertyTabMenuActivity.propertyInfo.getCity(),false));
			items.add(new EntryItem(this.getString(R.string.property_address),PropertyTabMenuActivity.propertyInfo.getAddress(),false));
			items.add(new EntryItem(this.getString(R.string.property_type),PropertyTabMenuActivity.propertyInfo.getType(),false));
			items.add(new EntryItem(this.getString(R.string.property_description),PropertyTabMenuActivity.propertyInfo.getDescription(),false));
			EntryAdapter adapter = new EntryAdapter(this, items);
		 	setListAdapter(adapter);
	 }
}
