package pt.up.fe.cmov.display;

import org.json.JSONObject;

import pt.up.fe.cmov.app.PropertyMarketActivity;
import pt.up.fe.cmov.propertymarket.rest.RailsRestClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class Display {
	
	public static void dialogBuildDeleteDiscard(long id,final Context mContext){
		new AlertDialog.Builder(mContext)
		.setTitle(pt.up.fe.cmov.propertymarket.R.string.discard_property_title)
		.setMessage(pt.up.fe.cmov.propertymarket.R.string.discard_property_desc)
		.setCancelable(true)
		.setIcon(android.R.drawable.ic_menu_delete)
		.setPositiveButton(pt.up.fe.cmov.propertymarket.R.string.confirmation,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						try {
							SharedPreferences prefs = mContext.getSharedPreferences(PropertyMarketActivity.PREFS_NAME, Context.MODE_PRIVATE);

							if (!prefs.contains(PropertyMarketActivity.USER_EMAIL)) {
							Log.w("PM-Discard", "Client doesn't have an email account. Using default!");
							}

							String userEmail = prefs.getString(PropertyMarketActivity.USER_EMAIL, "joao.portela@gmail.com");

							JSONObject obj = new JSONObject("{'user_email': '" + userEmail + "' }");

							RailsRestClient.Post("properties/" + id + "/discard", obj);
							}
							catch(Exception e) {
							e.printStackTrace();
							}
						((Activity) mContext).finish();
						Intent intent = new Intent(mContext, PropertyMarketActivity.class);
						mContext.startActivity(intent);
					}
				})
		.setNegativeButton(pt.up.fe.cmov.propertymarket.R.string.denial, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				}
		}).show();
	}
	
	public static void dialogMessageNotConnected(final Context context){
		new AlertDialog.Builder(context)
		.setTitle(pt.up.fe.cmov.propertymarket.R.string.discon_title)
		.setMessage(pt.up.fe.cmov.propertymarket.R.string.discon_desc)
		.setCancelable(true)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(pt.up.fe.cmov.propertymarket.R.string.cont,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						((Activity) context).finish();					
					}
				}).show();
	} 

}
