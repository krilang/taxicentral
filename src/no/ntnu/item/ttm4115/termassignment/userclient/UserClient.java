package no.ntnu.item.ttm4115.termassignment.userclient;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.TaxiType.TaxiType;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class UserClient extends Block {

	public Order current_order;
	public String user_id;
	public boolean in_queue;

	public static String getAlias(String user_id) {
		return user_id;
	}

	public static String getAlias(Order order) {
		return order.user_id;
	}

	public boolean request(String address) {
		
		if(in_queue) {
			current_order.order_status = Status.USER_Q_REQUEST;
			current_order.topic = "central";
			return true;
		}
		
		String[] parts= address.split("@|\\#");
		
		if (current_order != null) { return false; }

		current_order = new Order();
		current_order.order_status = Status.USER_ORDER;
		current_order.topic = "central";
		current_order.user_id = user_id;
		
		if(address.contains("#")){
			current_order.requestedTaxiType = TaxiType.stringToEnum(parts[1]);
		}
		if(address.contains("@")){
			current_order.order_status=Status.USER_PREBOOK;
		}
		
		current_order.address = parts[0];
		
		return true;
	}

	public boolean cancel() {
		if(current_order!=null){
			
			current_order.order_status = Status.USER_CANCEL;
			
			if(current_order.taxi_id == null) {
				current_order.topic = "central";
			} else {
				current_order.topic = "t"+current_order.taxi_id;
			}
			
			return true;
		}
		
		return false;
	}

	public AdvancedConfiguration generateConf() {
		return new AdvancedConfiguration("u"+user_id, 99);

	}

	public String getOrderMessage() {

		if(current_order == null){
			return "There is no order to cancel";
		}
		
		switch (current_order.order_status) {
		
		case CENTRAL_USER_CANCEL_CONF:
			current_order = null;
			in_queue = false;
			return "Your order has been canceled."; 

		case CENTRAL_USER_ORDER_CONF:
			in_queue = false;
			return "Your order is handeld, you will be picked up by "+current_order.taxi_id;
			
		case CENTRAL_TOUR_ENDED_CONF:
			current_order = null;
			in_queue = false;
			return "Thank you for using the taxi service!";
			
		case USER_ORDER:
			return "You already have an pending request, cancel it to create a new";
			
		default:
			
			return current_order.msg_to_user;
			
		}
	}

	public String requestSent() {
		if (current_order.order_status == Status.USER_Q_REQUEST) {
			return "Please wait for your updated queue position..";
		}
		return "Your request is beeing handeled";
	}
	
	public String orderExists() {
		return "Please cancel your previous order to make a new request.";
	}
	
	public boolean objectRecieved() {
		if(current_order == null) {
			return false;
		}
		if (current_order.order_status == null) {
			return false;
		}
		
		switch (current_order.order_status) {
		
		case TAXI_USER_ABORT:
			current_order.topic = "central";
			current_order.msg_to_user = "Your order was aborted, but is being procesed at the central.";
			return true;
			
		case TOUR_FINISHED:
			current_order.topic = "central";
			current_order.msg_to_user = "You were picked up, and the tour has ended.";
			return true;
			
		case CENTRAL_USER_ORDER_Q: 
			this.in_queue = true;
			return false;
			
		default:
			return false;
		}
	}
}
