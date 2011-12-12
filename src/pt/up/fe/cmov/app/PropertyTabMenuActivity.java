package pt.up.fe.cmov.app;


import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import pt.up.cmov.entities.Property;
import pt.up.fe.cmov.display.Display;
import pt.up.fe.cmov.propertymarket.R;
import pt.up.fe.cmov.propertymarket.rest.JSONOperations;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;

public class PropertyTabMenuActivity extends TabActivity {
	
	static ProgressDialog dialog;
	private static Handler handler;
	private Thread informationThread;
	static Property propertyInfo = null;
	public static GestureDetector gestureDetector;
	public static View.OnTouchListener gestureListener;
	static Resources res;
	static TabHost tabHost;
	static Intent intent;
	static TabHost.TabSpec spec;  

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab_property_layout);

	    res = getResources(); 
	    tabHost = getTabHost();  
	        	    
	    handler = new Handler();
	    informationThread = new LoadInfoThread();
        LoadInfoThread.context = this;
        informationThread.start();
        dialog = ProgressDialog.show(this, this.getString(R.string.loading), this.getString(R.string.please_wait));
	    
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	  if (gestureDetector.onTouchEvent(event))
		return true;
	  else
		return false;
	}
	 
	private static class LoadInfoThread extends Thread{
		
		public static Context context;
			
		@Override
		public void run() {
			MyRunnable.context = context;
			try {
				JSONObject selectedProperty = RailsRestClient.Get(context.getString(R.string.proprety_controller) + 
			    PropertyMarketActivity.selectedPropertyID + context.getString(R.string.long_field));
				PropertyTabMenuActivity.propertyInfo = JSONOperations.JSONToProperty(selectedProperty);
			    handler.post(new MyRunnable());
			} catch (ConnectTimeoutException e) {
				MyRunnable.connection = false;
			    handler.post(new MyRunnable());
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
			    tabHost.setCurrentTab(0);
				intent = new Intent().setClass(context, PropertyDetailsActivity.class);

			    spec = tabHost.newTabSpec(context.getString(R.string.property_title_lowercase))
			    			  .setIndicator(context.getString(R.string.property_title),res.getDrawable(R.drawable.ic_tab_ic_tab_info_selected))
			                  .setContent(intent);
			    tabHost.addTab(spec);

			    intent = new Intent().setClass(context, PropertyAditionalInfoActivity.class);
			    spec = tabHost.newTabSpec(context.getString(R.string.details_title_lowercase))
			    			  .setIndicator(context.getString(R.string.details_title),res.getDrawable(R.drawable.ic_tab_aditional_info_unselected))
			                  .setContent(intent);
			    tabHost.addTab(spec);
			}
		}
	}

}
