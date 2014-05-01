package no.ntnu.item.ttm4115.termassignment.taxichatdialog;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class TaxiChatDialog extends Block {

	public java.lang.String current_user;
	public java.lang.String taxi_ID;

	public AdvancedConfiguration getAdvConfig(String taxi_id) {
		taxi_ID=taxi_id;
		
		return new AdvancedConfiguration("t"+taxi_id+"chat", 99);
		
		
	}

	public int analyzeOrder(Order o) {
		
		if(o == null || o.order_status == null) { return -1; }
		
		Status oStat = o.order_status;
		
		if(oStat == Status.CENTRAL_TAXI_ORDER_CONF) {
			current_user = o.user_id;
			return 1; // Opening chatwindow
		}
		if(oStat == Status.CENTRAL_TAXI_ORDER_CANCELED || oStat == Status.USER_CANCEL || oStat == Status.TOUR_FINISHED) {
			return 2;  // Closing chatwindow  
		}
		
		return 0;
	}

	public String getTopic() {
		return "u"+current_user+"chat"; 
	}

	public String getTaxiID() {
		return "t"+taxi_ID;
	}

}
