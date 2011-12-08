package pt.up.fe.cmov.propertymarket;

import java.util.ArrayList;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.fe.cmov.propertymarket.rest.JSONOperations;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class PropertyMarketActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
			JSONArray propertiesJSON = RailsRestClient.GetArray("properties/items");
			
			ArrayList<Property> properties = new ArrayList<Property>();
			
			for (int i=0; i < propertiesJSON.length(); i++) {
				JSONObject obj = propertiesJSON.getJSONObject(i);
				Property property = JSONOperations.JSONToProperty(obj);
				properties.add(property);
			}
			
			GridView gridview = (GridView) findViewById(R.id.propertiesGrid);
		    gridview.setAdapter(new PropertyGridAdapter(this, properties));
			
		} catch (ConnectTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}