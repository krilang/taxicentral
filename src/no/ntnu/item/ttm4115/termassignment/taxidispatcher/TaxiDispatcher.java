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
			return performReceivedUserOrder(object, false);
		
		case USER_CANCEL:
			return performCancelOrder(object);
			
		case TAXI_USER_ABORT:
			return performTaxiAbortAction(object);
			
		default:
			System.out.println(object.order_status.toString());
			object.msg_to_central = "Default action was triggered";
			object.topic = "";
			return object;
		}
		
		
	}

	private Order performTaxiAbortAction(Order object) {
		
		setTaxiAsAvailable(object);
		object.taxi_id = null;
		
		return performReceivedUserOrder(object,true);
	}

	private Order performTaxiAnswerAction(Order object) {
		
		if(object.answer) {

			if (! (canceled_orders.contains(Integer.valueOf(object.order_id)))) {
				
				int pos = getQueuedOrderByID(object.order_id);
				
				if(pos != -1) {
					queued_orders.remove(pos);
				}
				
				object.order_status = Status.CENTRAL_TAXI_ORDER_CONF;
				object.topic = "t"+object.taxi_id;
				object.msg_to_central = "Order "+object.order_id+" is now being hadeled by taxi "+object.taxi_id+".";
				
			} else {
				object.order_status = Status.CENTRAL_TAXI_ORDER_CANCELED;
				object.msg_to_taxi = "The order was canceled. You are set as available.";
				object.msg_to_central = "Taxi "+object.taxi_id+" was informed that the order was canceled.";
				object.topic = "t"+object.taxi_id;
				
				setTaxiAsAvailable(object);
			}
			return object;
		}
		else if(object.incomming_taxi) {
			
			int pos = getQueuedOrderByID(object.order_id);
			
			if(pos != -1) {
				queued_orders.get(pos).reject_list.add(object.taxi_id);
			}
			
			return performAvailableUpdate(object);
		}
		else {
			setTaxiAsAvailable(object);
			return performReceivedUserOrder(object, false);
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
				object.incomming_taxi = false;
				
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
				taxi_avail_object.topic = "t"+taxi_avail_object.taxi_id;
				taxi_avail_object.order_id = o.order_id;
				taxi_avail_object.user_id = o.user_id;
				taxi_avail_object.address = o.address;
				taxi_avail_object.incomming_taxi = true;
				
				taxi_avail_object.msg_to_central = "Taxi "+taxi_avail_object.taxi_id+" was offered order "+o.order_id+" at "+o.address;
				
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
	
	private Order performReceivedUserOrder(Order object, boolean prioritized) {
		
		object.msg_to_central = "User "+object.user_id+"s order at address: "+object.address+", is being processed.";
		
		if(availableTaxiMatch(object)) {
			return object;
		}
		else {
			
			if(prioritized) {
				queued_orders.add(0,object);
				object.queue_position = 1;
				object.msg_to_user = "Your order was aborted. You are currently in queue. Position: 1";
			} else {
				queued_orders.add(object);
				object.queue_position = queued_orders.size();
				object.msg_to_user = "You are currently in queue. Position: "+object.queue_position;
			}
			
			object.order_status = Status.CENTRAL_USER_ORDER_Q;
			object.topic = "u"+object.user_id;
			
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
		
		canceled_orders.add(Integer.valueOf(object.order_id));
			
		int pos = getQueuedOrderByID(object.order_id);
		
		if(pos != -1) {
			queued_orders.remove(pos);
		}
		
		setTaxiAsAvailable(object);
		
		object.order_status = Status.CENTRAL_USER_CANCEL_CONF;
		object.topic = "u"+object.user_id;
		object.msg_to_central = "User "+object.user_id+" canceled order "+object.order_id+" at "+object.address;
		
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
		return object.msg_to_central;
	}
	
	public void setTaxiAsAvailable(Order object) {
		if (object.taxi_id == null) {
			return;
		}
		
		if(! (available_taxies.contains(object.taxi_id))) {
			available_taxies.add(object.taxi_id);
		}
	}
}
