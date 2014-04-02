package no.ntnu.item.ttm4115.termassignment.ordertojourney;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.simulation.routeplanner.Journey;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class OrderToJourney extends Block {

	public boolean isOrderConf(Order order) {
		
		return order.order_status == Status.TAXI_USER_CONF;
	}

	public Journey generateJourney(Order o) {
		return new Journey(o.taxi_id, o.taxi_position.getLatitude()+","+o.taxi_position.getLongitude(), o.address);
	}

}
