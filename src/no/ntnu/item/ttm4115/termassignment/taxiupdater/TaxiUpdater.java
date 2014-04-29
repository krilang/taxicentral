package no.ntnu.item.ttm4115.termassignment.taxiupdater;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;
import no.ntnu.item.ttm4115.termassignment.taximapstatus.taxiMapStatus;

public class TaxiUpdater extends Block {

	public no.ntnu.item.ttm4115.termassignment.order.Order order;

	public int isDutyUpdate() {
		if (order == null) { return 0; }
		if (order.order_status == Status.TAXI_DUTY) { return 1; }
		if (order.order_status== Status.TAXI_AVAILABLE) { return 2; }
		return 0;
	}

	public boolean isOnDuty() {
		return order.on_duty;
	}

	public taxiMapStatus mapUpdate() {
		taxiMapStatus tms= new taxiMapStatus(order.taxi_id, order.taxi_position);
		tms.taxiType=order.taxiType;
		
		System.out.println("order avail "+ order.available);
		System.out.println("order onduty "+ order.on_duty);
		
		if (order.order_status==Status.TAXI_AVAILABLE){
			tms.available=order.available;
		}
		if (order.order_status==Status.TAXI_DUTY){
			tms.onDuty=order.on_duty;
		}
		
		return tms;
	}
}
