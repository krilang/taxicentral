package no.ntnu.item.ttm4115.termassignment.taxidispatcher;

import java.util.ArrayList;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class TaxiDispatcher extends Block {

	public ArrayList<Integer> onduty_taxies = new ArrayList<Integer>();
	public ArrayList<Integer> available_taxies = new ArrayList<Integer>();
	
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
			
		default:
			return null;
		}
		
		
	}

	private Order performAvailableUpdate(Order object) {
		
		String msg = "Your status is already updated";
		String senderAsString = "Taxi "+ object.taxi_id; 
		String central_msg = " is still " + ((object.available) ? "AVAILABLE." : "UNAVAILABLE.");
		
		if(object.available) {
			if(!available_taxies.contains(object.taxiIDToInteger())) { 
				available_taxies.add(object.taxiIDToInteger());
				msg = "You are now registred as AVAILABLE.";
				central_msg = " is now AVAILABLE.";
			}
		}else if (available_taxies.contains(object.taxiIDToInteger())) {
			available_taxies.remove(object.taxiIDToInteger());
			msg = "You are now registred as UNAVAILABLE.";
			central_msg = " is now UNAVAILABLE.";
		}
		
		object.order_status = Status.CENTRAL_TAXI_CONF;
		object.topic = "t"+object.taxi_id;
		object.msg_to_taxi = msg;
		object.msg_to_central = senderAsString + central_msg;
	
		return object;
	}

	private Order performDutyUpdate(Order object) {
		String msg = "Your status is already updated";
		String senderAsString = "Taxi "+ object.taxi_id; 
		String central_msg = " is still " + ((object.on_duty) ? "ON DUTY." : "OFF DUTY.");
		
		if(object.on_duty) {
			if(!onduty_taxies.contains(object.taxiIDToInteger())) { 
				onduty_taxies.add(object.taxiIDToInteger());
				msg = "You are now registred ON DUTY.";
				central_msg = " is now ON DUTY.";
			}
		}else if (onduty_taxies.contains(object.taxiIDToInteger())) {
			onduty_taxies.remove(object.taxiIDToInteger());
			msg = "You are now registred OFF DUTY.";
			central_msg = " is now OFF DUTY.";
		}

		object.order_status = Status.CENTRAL_TAXI_CONF;
		object.topic = "t"+object.taxi_id;
		object.msg_to_taxi = msg;
		object.msg_to_central = senderAsString + central_msg;
		
		return object;
	}

	public String getOrderMessage(Order object) {
		return object.msg_to_central;
	}
}
