package pt.up.fe.cmov.app;

import java.util.ArrayList;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.cmov.entities.Property;
import pt.up.fe.cmov.gridadapter.PropertyGridAdapter;
import pt.up.fe.cmov.propertymarket.R;
import pt.up.fe.cmov.propertymarket.rest.JSONOperations;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
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
        	String email = "joao.portela@gmail.com";	//for emulator only!
        	
        	AccountManager accMan = (AccountManager) getSystemService(ACCOUNT_SERVICE);
            Account[] accounts = accMan.getAccounts();
            for (Account account : accounts) {
              email = account.name;
              Log.w("EMAIL ACCOUNT", email);
              break;	//uses the first email account defined
            }
            
            prefsEditor.putString(USER_EMAIL, email);
            prefsEditor.commit();
        	C2DMessaging.register(this, "cmov2.dcjp@gmail.com");
        }
        try {
        	String email = prefs.getString(USER_EMAIL, "joao.portela@gmail.com");
			JSONArray propertiesJSON = RailsRestClient.GetArray("properties/items", "user_email="+email);
			
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