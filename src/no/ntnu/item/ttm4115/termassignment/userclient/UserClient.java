package no.ntnu.item.ttm4115.termassignment.userclient;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class UserClient extends Block {

	public String user_id;
	public Order current_order;
	
	

	public static String getAlias(String user_id) {
		return user_id;
	}

	public boolean request() {
		
		if (current_order != null) {
			current_order.msg_to_user = "Please cancel your previous order to make a new request.";
			return false;
		}
		
		current_order = new Order();
		current_order.order_status = Status.USER_ORDER;
		current_order.topic = "central";
		current_order.user_id = user_id;
		current_order.address = "Lool";
		
		return true;
	}

	public boolean cancel() {
		if(current_order!=null){
			current_order.order_status=Status.USER_CANCEL;
			return true;
		}
		return false;
	}

	public AdvancedConfiguration generateConf() {
		return new AdvancedConfiguration("test,u"+user_id, 99);
		
	}

	public String getOrderMessage() {
		
		if(current_order != null && current_order.order_status == Status.CENTRAL_USER_CANCEL_CONF) {
			current_order = null;
			return "Your order has been canceled."; 
		}
		
		return current_order.user_id+" has sent a "+current_order.order_status+" with topic: "+current_order.topic;
	}
	
	
	







}
