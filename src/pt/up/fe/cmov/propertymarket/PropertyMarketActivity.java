package pt.up.fe.cmov.propertymarket;

import java.util.ArrayList;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.cmov.entities.Property;
import pt.up.fe.cmov.gridadapter.PropertyGridAdapter;
import pt.up.fe.cmov.propertymarket.rest.JSONOperations;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.GridView;

import com.google.android.c2dm.C2DMessaging;

public class PropertyMarketActivity extends Activity {
    /** Called when the activity is first created. */
    public static final String PREFS_NAME = "PropertyMarketPrefs";
    public static final String USER_EMAIL = "user_email";
    public static final String REGISTRATION_ID = "registration_id";
    public static long selectedPropertyID; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	Editor prefsEditor = prefs.edit();

        if (!prefs.contains(USER_EMAIL)) {
            prefsEditor.putString(USER_EMAIL, "joao.portela@gmail.com");
            prefsEditor.commit();
        	C2DMessaging.register(this, "cmov2.dcjp@gmail.com");
        }
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