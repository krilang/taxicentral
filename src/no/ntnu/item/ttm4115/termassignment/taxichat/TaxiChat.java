package no.ntnu.item.ttm4115.termassignment.taxichat;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class TaxiChat extends Block {

	public java.lang.String current_user_id;
	public java.lang.String current_taxi_id;

	public AdvancedConfiguration setAdvSub(String taxi_id) {
		return new AdvancedConfiguration("t"+taxi_id+"chat", 99);
	}

	public String topic() {
		return "u"+current_user_id+"chat";
	}

	public int isRelevant(Order o) {
		Status stat = o.order_status;
		
		if (stat == Status.CENTRAL_TAXI_ORDER_CONF) {
			current_taxi_id = o.taxi_id;
			return 1; 
		}
		if (stat == Status.CENTRAL_TAXI_ORDER_CANCELED || stat == Status.USER_CANCEL) { return 2; }
		
		return 0;
		
	}
}