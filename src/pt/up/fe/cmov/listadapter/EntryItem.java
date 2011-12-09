package pt.up.fe.cmov.listadapter;

import android.graphics.Bitmap;


public class EntryItem implements Item{

	private final String title;
	private final String subtitle;
	private final Bitmap bitmap; 
	private boolean clickable;

	public EntryItem(String title, String subtitle,boolean clickable) {
		this.title = title;
		this.subtitle = subtitle;
		this.clickable = clickable;
		this.bitmap = null;
	}
	
	public EntryItem(Bitmap bitmap,boolean clickable){
		this.title = "";
		this.subtitle = "";
		this.bitmap = bitmap;
		this.clickable = clickable;
	}
	
	public boolean getClickable(){
		return this.clickable;
	}
	
	public String getTile(){
		return this.title;
	}
	
	public String getSubtitle(){
		return this.subtitle;
	}
	
	public Bitmap getBitmap(){
		return this.bitmap;
	}
	
	public boolean noImage(){
		return this.bitmap == null;
	}
	
	@Override
	public boolean isSection() {
		return false;
	}
}
