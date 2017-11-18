package types;

import com.google.gson.annotations.SerializedName;

public class Shot {

	@SerializedName("nameEn")
	private String address;
	
	@SerializedName("lat")
	private double lat;
	  
	@SerializedName("lng")
	private double lng;
	  
	@SerializedName("img")
	private String imageLink;
	
	 public Shot(String address,double lat, double lng,String imageLink){
		 setAddress(address);
		 setLng(lng);
		 setImageLink(imageLink);
		 setLat(lat);
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	} 
	
     
}
