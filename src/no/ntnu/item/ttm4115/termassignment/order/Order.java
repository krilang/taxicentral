package no.ntnu.item.ttm4115.termassignment.order;

import java.util.ArrayList;

import com.bitreactive.library.android.maps.model.Position;

import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.TaxiType.TaxiType;

public class Order {
	
	//Status related
	
	public Status order_status;
	public int order_id;
	public String topic;
	public int queue_position;

	// User related
	public String user_id;
	public String address;
	public TaxiType requestedTaxiType;
	public String msg_to_user;
	
	// Taxi related
	public String taxi_id;
	public Position taxi_position;
	public boolean on_duty; // Update to central
	public boolean available; // Update to central
	public boolean answer; // Answer to central
	public boolean incomming_taxi;
	public TaxiType taxiType;
	
	public ArrayList<String> reject_list = new ArrayList<String>();
	
	public String msg_to_taxi;
	
	// Central related
	
	public String msg_to_central;
	
	// Static field
	private static int ORDER_COUNT;
	
	public Order() {
		ORDER_COUNT++;
		order_id = ORDER_COUNT;
	};
	
	public Integer taxiIDToInteger() {
		return new Integer(taxi_id);
	}
	
	public Integer userIDToInteger() {
		return new Integer(user_id);
	}
	
	public Integer orderIDToInteger() {
		return new Integer(order_id);
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("OrderID: "+order_id+"\n");
		sb.append("TaxiID: "+taxi_id+"\n");
		sb.append("UserID: "+user_id+"\n");
		sb.append("Status: "+order_status+"\n");
		sb.append("Topic: "+topic);
		
		return sb.toString();
	}
	
}
