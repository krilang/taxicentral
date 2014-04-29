package no.ntnu.item.ttm4115.termassignment.userchat;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class UserChat extends Block {

	public java.lang.String current_taxi_id;

	public int isRelevant(Order o) {
		Status stat = o.order_status;
		
		if ( stat == Status.CENTRAL_USER_ORDER_CONF || stat == Status.TAXI_USER_CONF) {
			current_taxi_id = o.taxi_id;
			return 1; 
		}
		if ( stat == Status.TAXI_USER_ABORT || stat == Status.CENTRAL_USER_CANCEL_CONF ) { return 2; }
		
		return 0;
	}

	public AdvancedConfiguration setAdvSub(String user_id) {
		return new AdvancedConfiguration("u"+user_id+"chat", 99);
	}

	public String topic() {
		return "t"+current_taxi_id+"chat";
	}
}