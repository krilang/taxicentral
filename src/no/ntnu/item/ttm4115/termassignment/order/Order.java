package no.ntnu.item.ttm4115.termassignment.order;

import java.util.ArrayList;

import no.ntnu.item.ttm4115.termassignment.Status.Status;

public class Order {
	
	//Status related
	
	public Status order_status;
	public String topic;
	public boolean confirmed; //Order is confirmed at central
	public int queue_position;

	// User related
	public String user_id;
	public String address;
	
	public String msg_to_user;
	
	// Taxi related
	public String taxi_id;
	public boolean on_duty; // Update to central
	public boolean available; // Update to central
	public boolean answer; // Answer to central
	
	public ArrayList<String> reject_list = new ArrayList<String>();
	
	public String msg_to_taxi;
	
	// Central related
	
	public String msg_to_central;
	
	public Order() {
		// nix
	};
	
	public Integer taxiIDToInteger() {
		return new Integer(taxi_id);
	}
	
	public Integer userIDToString() {
		return new Integer(user_id);
	}
	
}
