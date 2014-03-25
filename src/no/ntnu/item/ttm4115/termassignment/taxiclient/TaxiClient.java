package no.ntnu.item.ttm4115.termassignment.taxiclient;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class TaxiClient extends Block {

	public no.ntnu.item.ttm4115.termassignment.order.Order current_order;
	public String taxi_id;

	public String getOrderMessage() {
		
		if (current_order.order_status == Status.CENTRAL_TAXI_OFFER) {
			return "New incomming order. Accept/Decline order at "+ current_order.address;
		}
		
		return current_order.msg_to_taxi;
	}
	
	public void onDuty() {
		setDutyStatus(true);
	}
	public void offDufy() {
		setDutyStatus(false);
	}

	public void setDutyStatus(boolean duty_status) {
		
		current_order = new Order();
		current_order.taxi_id = taxi_id;
		current_order.order_status = Status.TAXI_DUTY;
		current_order.on_duty = duty_status;
	}

	public void unavailable() {
		setAvailableStatus(false);
	}
	
	public void available() {
		setAvailableStatus(true);
	}
	
	public void setAvailableStatus(boolean bol) {
		if (current_order != null && current_order.order_status == Status.CENTRAL_TAXI_OFFER) {
			current_order.order_status = Status.TAXI_ANSWER;
			current_order.answer = false;
			current_order.reject_list.add(taxi_id);
		}
		else {
			current_order = new Order();
			current_order.taxi_id = taxi_id;
			current_order.order_status = Status.TAXI_AVAILABLE;
			current_order.available = false; 
		}
	}

	public void confirm() {
		if(current_order == null || current_order.order_status != Status.CENTRAL_TAXI_OFFER) {
			return;
		}
		
		current_order.order_status = Status.TAXI_ANSWER;
		current_order.answer = true;
	}
	
	public static String getAlias(String taxi_id) {
		return taxi_id;
	}
	
	public static String getAlias(Order order) {
		return order.taxi_id;
	}
	
}