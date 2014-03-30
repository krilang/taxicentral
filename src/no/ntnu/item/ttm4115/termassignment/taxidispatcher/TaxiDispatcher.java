package no.ntnu.item.ttm4115.termassignment.taxidispatcher;

import java.util.ArrayList;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class TaxiDispatcher extends Block {

	public ArrayList<String> onduty_taxies = new ArrayList<String>();
	public ArrayList<String> available_taxies = new ArrayList<String>();
	
	public ArrayList<Order> queued_orders = new ArrayList<Order>();
	public ArrayList<Integer> canceled_orders = new ArrayList<Integer>();
	
	public void initialize() {
		
		// nix
	}

	public Order objectRecieved(Order object) {

		switch (object.order_status) {
		case TAXI_DUTY:
			return performDutyUpdate(object);

		case TAXI_AVAILABLE:
			return performAvailableUpdate(object);
			
		case TAXI_ANSWER:
			return performTaxiAnswerAction(object);
			
		case USER_ORDER:
			return performReceivedUserOrder(object);
		
		case USER_CANCEL:
			return performCancelOrder(object);
			
		default:
			return null;
		}
		
		
	}

	private Order performTaxiAnswerAction(Order object) {
		
		if(object.answer) {
			object.order_status = Status.CENTRAL_USER_ORDER_CONF;
			object.topic = "u"+object.user_id;
			return object;
		}
		else {
			return performReceivedUserOrder(object);
		}
		
		
	}

	private Order performAvailableUpdate(Order object) {
		
		String current_taxi_id = object.taxi_id;
		String msg = "Your status is already updated";
		String senderAsString = "Taxi "+ current_taxi_id; 
		String central_msg = " is still " + ((object.available) ? "AVAILABLE." : "UNAVAILABLE.");
		
		if(object.available) {
			if(!available_taxies.contains(current_taxi_id)) {
				
				if(availableOrderMatch(object)) {
					return object;
				}
				
				available_taxies.add(current_taxi_id);
				msg = "You are now registred as AVAILABLE.";
				central_msg = " is now AVAILABLE.";
			}
		}else if (available_taxies.contains(current_taxi_id)) {
			available_taxies.remove(current_taxi_id);
			msg = "You are now registred as UNAVAILABLE.";
			central_msg = " is now UNAVAILABLE.";
		}
		
		object.order_status = Status.CENTRAL_TAXI_CONF;
		object.topic = "t"+current_taxi_id;
		object.msg_to_taxi = msg;
		object.msg_to_central = senderAsString + central_msg;
	
		return object;
	}

	private boolean availableOrderMatch(Order taxi_avail_object) {
		
		if(queued_orders.size() == 0) {
			return false;
		}
		
		for (int i = 0; i < queued_orders.size(); i++) {
			Order o = queued_orders.get(i);
			if(! (o.reject_list.contains(taxi_avail_object.taxi_id))) {
				taxi_avail_object.order_status = Status.CENTRAL_TAXI_OFFER;
				taxi_avail_object.user_id = o.user_id;
				taxi_avail_object.address = o.address;
				return true;
			}
		}
		
		return false;
	}

	private Order performDutyUpdate(Order object) {
		
		String current_taxi_id = object.taxi_id;
		String msg = "Your status is already updated";
		String senderAsString = "Taxi "+ current_taxi_id; 
		String central_msg = " is still " + ((object.on_duty) ? "ON DUTY." : "OFF DUTY.");
		
		if(object.on_duty) {
			if(!onduty_taxies.contains(current_taxi_id)) { 
				onduty_taxies.add(current_taxi_id);
				msg = "You are now registred ON DUTY.";
				central_msg = " is now ON DUTY.";
			}
		}else if (onduty_taxies.contains(current_taxi_id)) {
			
			onduty_taxies.remove(current_taxi_id);
			available_taxies.remove(current_taxi_id);
			
			msg = "You are now registred OFF DUTY.";
			central_msg = " is now OFF DUTY.";
		}

		object.order_status = Status.CENTRAL_TAXI_CONF;
		object.topic = "t"+current_taxi_id;
		object.msg_to_taxi = msg;
		object.msg_to_central = senderAsString + central_msg;
		
		return object;
	}
	
	private Order performReceivedUserOrder(Order object) {
		
		if(availableTaxiMatch(object)) {
			return object;
		}
		else {
			queued_orders.add(object);
			object.order_status = Status.CENTRAL_USER_ORDER_Q;
			object.topic = "u"+object.user_id;
			object.queue_position = queued_orders.size();
			return object;
		}
	}
	
	private boolean availableTaxiMatch(Order new_order_object) {
		
		if(! (available_taxies.size() > 0)) {
			return false;
		}
		
		for(int i = 0; i < available_taxies.size(); i++) {
			if(! (new_order_object.reject_list.contains(available_taxies.get(i)))) {
				new_order_object.order_status = Status.CENTRAL_TAXI_OFFER;
				new_order_object.taxi_id = available_taxies.remove(i);
				new_order_object.topic = "t"+new_order_object.taxi_id;
				return true;
			}
		}
		return false;
	}

	private Order performCancelOrder(Order object) {
		
		canceled_orders.add(object.orderIDToInteger());
		int pos = getQueuedOrderByID(object.order_id);
		
		if(pos != -1) {
			queued_orders.remove(pos);
		}
		object.order_status = Status.CENTRAL_USER_CANCEL_CONF;
		object.topic = "u"+object.user_id;
		
		return object;
	}
	
	private int getQueuedOrderByID(int id) {		
		for (int i = 0; i < queued_orders.size(); i++) {
			if(queued_orders.get(i).order_id == id) {
				return i;
			}
		}
		return -1;
	}

	public String getOrderMessage(Order object) {
		
		switch (object.order_status) {
		
		case USER_ORDER:
			return "User "+object.user_id+" placed an order at address: "+object.address;
			
		case USER_CANCEL:
			return "User "+object.user_id+" canceled order "+object.order_id+" at "+object.address; 
		
		
		default:
			
			return object.msg_to_central;
		}
	}
}
