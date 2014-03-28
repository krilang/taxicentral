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
		
		if(initialReceivedOfferCheck()) {
			return false;
		}
		
		setDutyStatus(true);
		on_duty = true;
		return true;
	}

	public boolean offDufy() {
		
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
		
		if (!isOnDutyCheck()) {
			return false;
		}
		return setAvailableStatus(false);
	}
	
	public boolean available() {
		
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
		if (current_order != null && current_order.order_status == Status.CENTRAL_TAXI_OFFER && !bool) {
			current_order.order_status = Status.TAXI_ANSWER;
			current_order.topic = "central";
			current_order.answer = false;
			current_order.reject_list.add(taxi_id);
			
			return true;
		}
		else {
			
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
	}

	public boolean confirm() {
		
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
			current_order.msg_to_taxi = "Please answer the tour offer from the central first..";
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
	
	public String getOrderMessage() {
		
		if (current_order == null) {
			return "No orders in the system";
		}
		
		if (current_order.order_status == Status.CENTRAL_TAXI_OFFER) {
			return "New incomming order. Accept/Decline order at "+ current_order.address;
		}
		
		return current_order.msg_to_taxi;
	}
	
	public static String getAlias(String taxi_id) {
		return taxi_id;
	}
	
	public static String getAlias(Order order) {
		return order.taxi_id;
	}

	public AdvancedConfiguration advConfig() {
		
		return new AdvancedConfiguration("test,t"+taxi_id+"", 99);
	}
	
}