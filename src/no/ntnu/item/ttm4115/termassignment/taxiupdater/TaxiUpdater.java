package no.ntnu.item.ttm4115.termassignment.taxiupdater;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;
import no.ntnu.item.ttm4115.termassignment.taximapstatus.taxiMapStatus;

public class TaxiUpdater extends Block {

	public no.ntnu.item.ttm4115.termassignment.order.Order order;

	public Integer isDutyUpdate() {
		if (order == null) { return 0; }
		if (order.order_status == Status.TAXI_DUTY){
			return 1;
		}if(order.order_status==Status.TAXI_AVAILABLE){
			return 2;
		}else {return 0;
		
		}
	}

	public boolean isOnDuty() {
		return order.on_duty;
	}

	public taxiMapStatus mapUpdate() {
		taxiMapStatus tms= new taxiMapStatus(order.taxi_id, order.taxi_position);
		tms.available=order.available;
		tms.onDuty=order.on_duty;
		tms.taxiType=order.taxiType;
		return tms;
	}
}
