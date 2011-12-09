package pt.up.cmov.entities;

import java.util.Date;

public class Property {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String ADDRESS = "address";
	public static final String CITY = "city";
	public static final String DESCRIPTION = "description";
	public static final String PRICE = "price";
	public static final String STATE = "state";
	public static final String CREATED_AT = "created_at";
	public static final String PHOTO = "photo";

	int id;
	String name;
	String type;
	String address;
	String city;
	String description;
	int price;
	String state;
	Date createdAt;
	String photo;
	
	public Property(int id, String name, String type, int price, String photo) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.photo = photo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
