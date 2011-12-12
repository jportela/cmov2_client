package pt.up.cmov.gestures;

import pt.up.fe.cmov.app.PropertyMarketActivity;
import pt.up.fe.cmov.app.PropertyTabMenuActivity;
import pt.up.fe.cmov.gridadapter.PropertyGridAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class SwipeGestureDetector extends SimpleOnGestureListener {
	private static final int SWIPE_MIN_DISTANCE = 50;
    private static final int SWIPE_MAX_OFF_PATH = 100;
	private static final int SWIPE_THRESHOLD_VELOCITY = 50;
	private Context context;
	
	public SwipeGestureDetector(Context context){
		this.context = context;
	}
	
	  @Override
	  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	    try {
	         Log.e("Xst - Xend", Float.toString(e1.getX()) + "-" +  Float.toString(e2.getX()));
	         if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH){
	            return false;
	         }
	         Log.e("Xst - Xend", Float.toString(e1.getX()) + "-" +  Float.toString(e2.getX()));
	         if(Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
		         if(Math.abs(e1.getX() - e2.getX()) > SWIPE_MIN_DISTANCE) {
		        	 PropertyMarketActivity.selectedPropertyID = PropertyGridAdapter.getItemStaticId(getPositionToMoveFoward());
		             Intent intent = new Intent(context,PropertyTabMenuActivity.class);
		             context.startActivity(intent);
		             ((Activity) context).finish();	         
		         }else if (Math.abs(e2.getX() - e1.getX()) > SWIPE_MIN_DISTANCE) {
		        	 PropertyMarketActivity.selectedPropertyID = PropertyGridAdapter.getItemStaticId(getPositionToMoveBackward());
		             Intent intent = new Intent(context,PropertyTabMenuActivity.class);
		             context.startActivity(intent);
		             ((Activity) context).finish();	         
		         }
	         }
	     } catch (Exception e) {
	     return false;
	    }
		return true;   
	 }
	

	public int getPositionToMoveFoward(){
		if(PropertyGridAdapter.getStaticCount() - 1 > PropertyMarketActivity.selectedPropertyPosition)
			 	PropertyMarketActivity.selectedPropertyPosition += 1;
		 	else
		 		PropertyMarketActivity.selectedPropertyPosition = 0;
		return PropertyMarketActivity.selectedPropertyPosition;
	}
	
	public int getPositionToMoveBackward(){
		 if(PropertyMarketActivity.selectedPropertyPosition > 0)
			 PropertyMarketActivity.selectedPropertyPosition -= 1;
		 else
			 PropertyMarketActivity.selectedPropertyPosition = PropertyGridAdapter.getStaticCount();
		return PropertyMarketActivity.selectedPropertyPosition;
	}
}
