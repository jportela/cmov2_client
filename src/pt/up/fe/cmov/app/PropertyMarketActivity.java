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
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.GridView;

import com.google.android.c2dm.C2DMessaging;

public class PropertyMarketActivity extends Activity{

	public static final String PREFS_NAME = "PropertyMarketPrefs";
    public static final String USER_EMAIL = "user_email";
    public static final String REGISTRATION_ID = "registration_id";
    public static long selectedPropertyID;
    public static int selectedPropertyPosition; 
	private final int syncBtnId = Menu.FIRST;
	static SharedPreferences prefs;
	static ProgressDialog dialog;
	private static Handler handler;
	private Thread informationThread;
	static ArrayList<Property> properties;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        properties = new ArrayList<Property>();
        handler = new Handler();
        prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	Editor prefsEditor = prefs.edit();

        if (!prefs.contains(USER_EMAIL)) {
        	String email = null;
        	AccountManager accMan = (AccountManager) getSystemService(ACCOUNT_SERVICE);
            Account[] accounts = accMan.getAccounts();
           for (Account account : accounts) {
              email = account.name;
              Log.w("EMAIL ACCOUNT", email);
              prefsEditor.putString(USER_EMAIL, email);
              prefsEditor.commit();
              break;	//uses the first email account defined
            }
            
        	C2DMessaging.register(this, "cmov2.dcjp@gmail.com");
        }
        
        informationThread = new LoadInfoThread();
        LoadInfoThread.context = this;
        informationThread.start();
        dialog = ProgressDialog.show(this, this.getString(R.string.loading), this.getString(R.string.please_wait));
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

	
	private static class LoadInfoThread extends Thread{
		
		public static Context context;
			
		@Override
		public void run() {
			MyRunnable.context = context;
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
								
			    handler.post(new MyRunnable());
				
			} catch (ConnectTimeoutException e) {
				MyRunnable.connection = false;
			    handler.post(new MyRunnable());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	static public class MyRunnable implements Runnable {
		static Context context;
		static boolean connection = true;

		public void setContext(Context context){
			MyRunnable.context = context;
		}
		
		public void run() {
			if(connection == false){
				dialog.dismiss();
				Display.dialogMessageNotConnected(context);
			}
			else{
			    dialog.dismiss();
				GridView gridview = (GridView) ((Activity) context).findViewById(R.id.propertiesGrid);
			    gridview.setAdapter(new PropertyGridAdapter(context, properties));
			}
		}
	}
}