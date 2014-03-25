package no.ntnu.item.ttm4115.termassignment.userclient;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class UserClient extends Block {

	public java.lang.String user_ID;
	public no.ntnu.item.ttm4115.termassignment.order.Order current_order;
	
	

	public static java.lang.String getAlias(String user_ID) {
		return user_ID;
	}

//	public void createRequest(String request) {
//		current_order=new Order();
//		current_order.order_status=Status.USER_ORDER;
//	}

	public void request() {
		current_order=new Order();
		current_order.order_status=Status.USER_ORDER;
	}

	public void cancel() {
		if(current_order!=null){
		current_order.order_status=Status.USER_CANCEL;
		}else return;
		
		
	}

	public AdvancedConfiguration generateConf() {
		return new AdvancedConfiguration("test", 99);
		
	}
	
	
	







}
