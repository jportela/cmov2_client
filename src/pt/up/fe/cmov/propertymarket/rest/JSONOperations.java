package pt.up.fe.cmov.propertymarket.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import pt.up.cmov.entities.Property;

public class JSONOperations {
	
	public static final DateFormat dbDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
	
	public static Property JSONToProperty(JSONObject obj) {
		try {
			int id = obj.getInt(Property.ID);
			String name = obj.getString(Property.NAME);
			String type = obj.getString(Property.TYPE);
			int price = obj.getInt(Property.PRICE);
			String photo = obj.getString(Property.PHOTO);
			
			Property property = new Property(id, name, type, price, photo);
			
			if (obj.has(Property.ADDRESS)) {
				String address = obj.getString(Property.ADDRESS);
				String city = obj.getString(Property.CITY);
				String state = obj.getString(Property.STATE);
				String description = obj.getString(Property.DESCRIPTION);
				Date createdAt = dbDateFormatter.parse(obj.getString(Property.CREATED_AT));
				
				property.setAddress(address);
				property.setCity(city);
				property.setState(state);
				property.setCreatedAt(createdAt);
				property.setDescription(description);
				property.setPrice(price);
			}
			
			
			
			return property;
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
