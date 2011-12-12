/***
  Copyright (c) 2008-2011 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Advanced Android Development_
    http://commonsware.com/AdvAndroid
*/

package pt.up.fe.cmov.propertymarket;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.fe.cmov.app.PropertyMarketActivity;
import pt.up.fe.cmov.app.PropertyTabMenuActivity;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.android.c2dm.C2DMBaseReceiver;

public class C2DMReceiver extends C2DMBaseReceiver {

  private NotificationManager mManager;
  private static final int APP_ID = 0;	

  private final String C2DM_DATA_TYPE = "ptype";
  private final String C2DM_DATA_NAME = "name";
  private final String C2DM_DATA_ID = "id";
  private final String C2DM_DATA_PRICE = "price";
  private final String C2DM_DATA_CITY = "city";
  private final String C2DM_DATA_NUM = "num";
	
  public C2DMReceiver() {
    super("cmov2.dcjp@gmail.com");
  }

  @Override
  public void onRegistered(Context context, String registrationId) {
    Log.w("C2DMReceiver-onRegistered", registrationId);

    SharedPreferences prefs = context.getSharedPreferences(PropertyMarketActivity.PREFS_NAME, MODE_PRIVATE);
        
    if (!prefs.contains(PropertyMarketActivity.USER_EMAIL)) {
    	Log.w("PM-Registration", "Client doesn't have an email account. Using default!");
    }
    
    String userEmail = prefs.getString(PropertyMarketActivity.USER_EMAIL, "joao.portela@gmail.com");
    
    Editor prefsEditor = prefs.edit();
    prefsEditor.putString(PropertyMarketActivity.REGISTRATION_ID, registrationId);
    prefsEditor.commit();

    
    JSONObject obj;
	try {
		obj = new JSONObject("{ 'email': '" + userEmail + "', 'registration_id': '" + registrationId + "'}");
	    RailsRestClient.Post("users/update_registration", obj);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ConnectTimeoutException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
  }
  
  @Override
  public void onUnregistered(Context context) {
    Log.w("C2DMReceiver-onUnregistered", "got here!");
  }
  
  @Override
  public void onError(Context context, String errorId) {
    Log.w("C2DMReceiver-onError", errorId);
  }
  
  @Override
  protected void onMessage(Context context, Intent intent) {
	  mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	  
	  int icon = R.drawable.icon;
	  String type = intent.getStringExtra(C2DM_DATA_TYPE);
	  String name = intent.getStringExtra(C2DM_DATA_NAME);
	  String city = intent.getStringExtra(C2DM_DATA_CITY);
	  int id = Integer.parseInt(intent.getStringExtra(C2DM_DATA_ID));
	  int price = Integer.parseInt(intent.getStringExtra(C2DM_DATA_PRICE));
	  String status = intent.getStringExtra(C2DM_DATA_NUM) + " props!";

	  String title = city + " - " + price + "€";
	  String message = name + " is now available in Property Market!";
	  
	  if (type.equals("apartment"))
		  icon = R.drawable.apartment_icon;
	  else if (type.equals("castle"))
		  icon = R.drawable.castle_icon;
	  else if (type.equals("home"))
		  icon = R.drawable.home_icon;
	  
	  PropertyMarketActivity.selectedPropertyID = id;
	  
	  Intent viewIntent = new Intent(this, PropertyTabMenuActivity.class);
	  
	  viewIntent.putExtra("yourpackage.notifyId", id);
	  PendingIntent contentIntent = PendingIntent.getActivity(this, 1, viewIntent, 0);

	  Notification notification = new Notification(icon,status, System.currentTimeMillis());
	  notification.setLatestEventInfo(this,title,message,contentIntent);
	   
	  mManager.notify(APP_ID, notification);
  }
}