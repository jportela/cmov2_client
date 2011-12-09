package pt.up.fe.cmov.app;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import pt.up.cmov.entities.Property;
import pt.up.fe.cmov.propertymarket.R;
import pt.up.fe.cmov.propertymarket.rest.JSONOperations;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class PropertyTabMenu extends TabActivity {
	
	static Property propertyInfo = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab_property_layout);

	    Resources res = getResources(); 
	    TabHost tabHost = getTabHost();  
	    TabHost.TabSpec spec;  
	    Intent intent; 

	    loadProperty();
	    
	    intent = new Intent().setClass(this, PropertyDetailsActivity.class);

	    spec = tabHost.newTabSpec(this.getString(R.string.property_title_lowercase))
	    			  .setIndicator(this.getString(R.string.property_title),res.getDrawable(R.drawable.ic_tab_ic_tab_info_selected))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, PropertyAditionalInfoActivity.class);
	    spec = tabHost.newTabSpec(this.getString(R.string.details_title_lowercase))
	    			  .setIndicator(this.getString(R.string.details_title),res.getDrawable(R.drawable.ic_tab_aditional_info_unselected))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    tabHost.setCurrentTab(0);
	}
	
	public boolean loadProperty(){
		try {
			JSONObject selectedProperty = RailsRestClient.Get(this.getString(R.string.proprety_controller)+ 
					PropertyMarketActivity.selectedPropertyID + this.getString(R.string.long_field));
			PropertyTabMenu.propertyInfo = JSONOperations.JSONToProperty(selectedProperty);
			return true;
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		}	
		return false;
	}

}
