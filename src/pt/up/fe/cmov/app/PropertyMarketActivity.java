package pt.up.fe.cmov.app;

import java.util.ArrayList;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.cmov.entities.Property;
import pt.up.fe.cmov.display.Display;
import pt.up.fe.cmov.gridadapter.PropertyGridAdapter;
import pt.up.fe.cmov.propertymarket.R;
import pt.up.fe.cmov.propertymarket.rest.JSONOperations;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.GridView;

import com.google.android.c2dm.C2DMessaging;

public class PropertyMarketActivity extends Activity implements Runnable {

	public static final String PREFS_NAME = "PropertyMarketPrefs";
    public static final String USER_EMAIL = "user_email";
    public static final String REGISTRATION_ID = "registration_id";
    public static long selectedPropertyID;
    public static int selectedPropertyPosition; 
	private final int syncBtnId = Menu.FIRST;
	boolean connection = true;
	Thread thread;
	SharedPreferences prefs;
	ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
        prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	Editor prefsEditor = prefs.edit();

        if (!prefs.contains(USER_EMAIL)) {
        	String email = null;
        	AccountManager accMan = (AccountManager) getSystemService(ACCOUNT_SERVICE);
            Account[] accounts = accMan.getAccounts();
           /* for (Account account : accounts) {
              email = account.name;
              Log.w("EMAIL ACCOUNT", email);
              prefsEditor.putString(USER_EMAIL, email);
              prefsEditor.commit();
              break;	//uses the first email account defined
            }*/
            
        	C2DMessaging.register(this, "cmov2.dcjp@gmail.com");
        }
        
        dialog = ProgressDialog.show(this, this.getString(R.string.loading), this.getString(R.string.please_wait),true,false);
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuItem searchMItm = menu.add(Menu.NONE,syncBtnId ,syncBtnId,this.getString(R.string.sync_menu));
	    searchMItm.setIcon(R.drawable.ic_menu_sync);
	    return super.onCreateOptionsMenu(menu);
	  }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case syncBtnId:
	        	onCreate(new Bundle());
	        break;
	    }
	    return true;
	}

	@Override
	public void run() {
		ArrayList<Property> properties = new ArrayList<Property>();
		try {

		    if (!prefs.contains(PropertyMarketActivity.USER_EMAIL)) {
		    	Log.w("PM-GetItems", "Client doesn't have an email account. Using default!");
		    }
		    
        	String email = prefs.getString(USER_EMAIL, "joao.portela@gmail.com");
        	
			JSONArray propertiesJSON = RailsRestClient.GetArray("properties/items", "user_email="+email);
						
			for (int i=0; i < propertiesJSON.length(); i++) {
				JSONObject obj = propertiesJSON.getJSONObject(i);
				Property property = JSONOperations.JSONToProperty(obj);
				properties.add(property);
			}
			
			GridView gridview = (GridView) findViewById(R.id.propertiesGrid);
		    gridview.setAdapter(new PropertyGridAdapter(this, properties));
		    handler.sendEmptyMessage(0);
			
		} catch (ConnectTimeoutException e) {
			//if(thread != null){
				connection = false;
				handler.sendEmptyMessage(1);
			//}
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	dialog.dismiss();
        	thread.interrupt();
			if(!connection)
				Display.dialogMessageNotConnected(PropertyMarketActivity.this);
        }
	};
}