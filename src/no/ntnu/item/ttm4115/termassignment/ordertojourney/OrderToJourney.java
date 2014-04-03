package no.ntnu.item.ttm4115.termassignment.ordertojourney;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.simulation.routeplanner.Journey;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class OrderToJourney extends Block {

	public no.ntnu.item.ttm4115.termassignment.order.Order order;

	public boolean isOrderConf() {
		
		return order.order_status == Status.TAXI_USER_CONF;
	}

	public Journey generateJourney() {
		String lat = ""+order.taxi_position.getLatitude()/1000000+"";
		String lng = ""+order.taxi_position.getLongitude()/1000000+"";
		return new Journey(order.taxi_id, lat+","+lng, order.address);
	}

}
