package no.ntnu.item.ttm4115.termassignment.userchatdialog;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class UserChatDialog extends Block {

	public java.lang.String current_taxi;

	public AdvancedConfiguration getAdvConfig(String uid) {
		return new AdvancedConfiguration("u"+uid+"chat",99);
	}

	public int analyzeOrder(Order o) {
		
		if(o == null || o.order_status == null) { return -1; }
		
		Status oStat = o.order_status;
		current_taxi = o.taxi_id;
		
		if(oStat == Status.TAXI_USER_CONF || oStat == Status.CENTRAL_USER_ORDER_CONF) {
			return 1; // Opening chatwindow
		}
		if(oStat == Status.CENTRAL_USER_CANCEL_CONF || 
				oStat == Status.TAXI_USER_ABORT || 
				oStat == Status.CENTRAL_TOUR_ENDED_CONF ||
				oStat == Status.CENTRAL_USER_TAXI_ABORTED) { return 2; } // Closing chatwindow 
		
		return 0;
	}

	public String getTopic() {
		return "t"+current_taxi+"chat";
	}

}