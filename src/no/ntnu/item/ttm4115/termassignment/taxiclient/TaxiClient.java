package no.ntnu.item.ttm4115.termassignment.taxiclient;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class TaxiClient extends Block {

	public no.ntnu.item.ttm4115.termassignment.order.Order current_order;
	public String taxi_id;
	public boolean on_duty;
	
	public boolean onDuty() {
		
		if(isOnTour()) {
			current_order.msg_to_taxi = "You are currently on a tour.";
			return false;
		}
		
		if(initialReceivedOfferCheck()) {
			return false;
		}
		
		setDutyStatus(true);
		on_duty = true;
		return true;
	}

	public boolean offDufy() {
		
		if(isOnTour()) {
			current_order.msg_to_taxi = "You are currently on a tour.";
			return false;
		}
		
		if (!isOnDutyCheck()) {
			return false;
		}
		if(initialReceivedOfferCheck()) {
			return false;
		}
		
		
		setDutyStatus(false);
		on_duty = false;
		return true;
	}

	public void setDutyStatus(boolean duty_status) {
		
		current_order = new Order();
		current_order.taxi_id = taxi_id;
		current_order.topic = "central";
		current_order.order_status = Status.TAXI_DUTY;
		current_order.on_duty = duty_status;
	}

	public boolean unavailable() {
		
		if(isOnTour()) {
			current_order.msg_to_taxi = "You are currently on a tour.";
			return false;
		}
		
		if (!isOnDutyCheck()) {
			return false;
		}
		if (current_order != null && current_order.order_status == Status.CENTRAL_TAXI_OFFER) {
			current_order.order_status = Status.TAXI_ANSWER;
			current_order.topic = "central";
			current_order.answer = false;
			current_order.reject_list.add(taxi_id);
			
			current_order.msg_to_taxi = "You declined the tour.";
			
			return true;
		}
		
		return setAvailableStatus(false);
	}
	
	public boolean available() {
		
		if(isOnTour()) {
			current_order.order_status = Status.TAXI_USER_ABORT;
			current_order.topic = "u"+current_order.user_id;
			current_order.msg_to_taxi = "Your tour has been aborted. You are set as available at the taxicentral.";
			current_order.reject_list.add(taxi_id);
			return true;
		}
		if (!isOnDutyCheck()) {
			return false;
		}
		if(initialReceivedOfferCheck()) {
			return false;
		}
		
		setAvailableStatus(true);
		return true;
	}

	public boolean setAvailableStatus(boolean bool) {

		if(initialReceivedOfferCheck()) {
			return false;
		}

		current_order = new Order();
		current_order.taxi_id = taxi_id;
		current_order.topic = "central";
		current_order.order_status = Status.TAXI_AVAILABLE;
		current_order.available = bool;

		return true;

	}

	public boolean confirm() {
		
		if(isOnTour()) {
			current_order.msg_to_taxi = "You are currently on a tour.";
			return false;
		}
		
		if (!isOnDutyCheck()) {
			return false;
		}
		
		if(current_order == null || current_order.order_status != Status.CENTRAL_TAXI_OFFER) {
			return false;
		}
		
		
		current_order.order_status = Status.TAXI_ANSWER;
		current_order.topic = "central";
		current_order.answer = true;
		return true;
	}
	
	private boolean initialReceivedOfferCheck() {
		if (current_order != null && current_order.order_status == Status.CENTRAL_TAXI_OFFER) {
			return true;
		}
		return false;
	}
	
	private boolean isOnDutyCheck() {
		if (on_duty) {
			return true;
		}
		current_order = new Order();
		current_order.msg_to_taxi = "Please REGISTER as ON DUTY."; 
		return false;
	}
	
	public boolean isOnTour() {
		if (current_order == null) {
			return false;
		}
		Status s = current_order.order_status;
		
		return s == Status.TAXI_USER_COM || s == Status.TAXI_USER_CONF;
	}
	
	public String getOrderMessage() {
		
		if (current_order == null) {
			return "No orders in the system";
		}
		
		switch (current_order.order_status) {
		
		case CENTRAL_TAXI_OFFER:
			return "New incomming order. Accept/Decline order at "+ current_order.address;

		default:
			return current_order.msg_to_taxi;
		}
	}
	
	public boolean isEstablished() {
		return current_order.order_status == Status.CENTRAL_TAXI_ORDER_CONF;
	}

	public boolean objectReceived() {
		
		switch (current_order.order_status) {
		
		case CENTRAL_TAXI_ORDER_CONF:
			current_order.order_status = Status.TAXI_USER_CONF;
			current_order.topic = "u"+current_order.user_id;
			current_order.msg_to_user = "Your order has been confirmed.";
			current_order.msg_to_taxi = "Your tour with User "+current_order.user_id+" has been confirmed. Pick up at "+current_order.address;
			return true;
			
		case TAXI_USER_COM:
			return false;
			
		case USER_CANCEL:
			current_order.topic = "central";
			current_order.msg_to_taxi = "Your tour was canceled. You are set as available at the central.";
			return true;
		
		default:
			return false;
		}
	}
	
	public static String getAlias(String taxi_id) {
		return taxi_id;
	}
	
	public static String getAlias(Order order) {
		return order.taxi_id;
	}

	public AdvancedConfiguration advConfig() {
		
		return new AdvancedConfiguration("t"+taxi_id, 99);
	}

	public boolean hasMsgToTaxi() {
		if (current_order == null || current_order.msg_to_taxi == null) {
			return false;
		}
	
		return  current_order.msg_to_taxi.length() > 0;
	}
}
