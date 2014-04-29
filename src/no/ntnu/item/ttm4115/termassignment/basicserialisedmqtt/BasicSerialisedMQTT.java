package no.ntnu.item.ttm4115.termassignment.basicserialisedmqtt;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class BasicSerialisedMQTT extends Block {

	public Order objectToOrder(Object obj) {
		return (Order) obj;
	}

	public String getOrderTopic(Order order) {
		
		System.out.println("Sending order with topic:"+ order.topic);
		return order.topic;
	}

	public void printer(Object s) {
		System.out.println("Error at BasicSerializedMQTT: "+s.toString());
	}
}