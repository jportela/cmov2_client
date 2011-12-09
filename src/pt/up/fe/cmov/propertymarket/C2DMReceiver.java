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
	
  public C2DMReceiver() {
    super("cmov2.dcjp@gmail.com");
  }

  @Override
  public void onRegistered(Context context, String registrationId) {
    Log.w("C2DMReceiver-onRegistered", registrationId);

    SharedPreferences prefs = context.getSharedPreferences(PropertyMarketActivity.PREFS_NAME, MODE_PRIVATE);
    String userEmail = prefs.getString(PropertyMarketActivity.USER_EMAIL, "");
    
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
	
	mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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
	  Notification notification = new Notification(R.drawable.icon,intent.getStringExtra(this.getString(R.string.name)), System.currentTimeMillis());
	  notification.setLatestEventInfo(this,this.getString(R.string.app_name),intent.getStringExtra(this.getString(R.string.message)),
	  PendingIntent.getActivity(this.getBaseContext(), 0, intent,PendingIntent.FLAG_CANCEL_CURRENT));
	  mManager.notify(APP_ID, notification);
	  
    Log.w("Tester", "Message: " + intent.getStringExtra("message"));
    Log.w("Tester", "id: " + intent.getStringExtra("id"));
    Log.w("Tester", "name: " + intent.getStringExtra("name"));
    
    
    
  }
}