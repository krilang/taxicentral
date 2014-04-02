package no.ntnu.item.ttm4115.termassignment.taxiupdater;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;
import no.ntnu.item.ttm4115.termassignment.taximapstatus.taxiMapStatus;

public class TaxiUpdater extends Block {

	public no.ntnu.item.ttm4115.termassignment.order.Order order;

	public boolean isDutyUpdate(Order o) {
		if (o == null) { return false; }
		return o.order_status == Status.TAXI_DUTY;
	}

	public boolean isOnDuty() {
		return order.on_duty;
	}

	public taxiMapStatus mapUpdate() {
		return new taxiMapStatus(order.taxi_id, order.taxi_position);
	}
}
