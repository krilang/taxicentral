package no.ntnu.item.ttm4115.termassignment.order;

import no.ntnu.item.ttm4115.termassignment.Status.Status;

public class Order {
	
	//Status related
	
	public Status order_status;

	// User related
	public int user_id;
	public String address;
	
	public boolean confirmed = false;
	public int queue_position;
	
	public String msg_to_user;
	
	// Taxi related
	public int taxi_id;
	public boolean on_duty = false;
	public boolean answer = false;
	
	public String msg_to_taxi;
	
	public Order() {};
	
	
}
