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
		
		this.current_order = new Order();
		current_order.order_status = Status.TAXI_DUTY;
		current_order.on_duty = duty_status;
	}

	public void unavailable() {
	}

	public void available() {
	}

	public void confirm() {
	}
	
	public static String getAlias(String taxi_id) {
		return taxi_id;
	}
	
	public static String getAlias(Order order) {
		return order.taxi_id;
	}
	
}