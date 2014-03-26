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
		current_order.user_id=user_ID;
		current_order.topic="1";
		
	}

	public void cancel() {
		if(current_order!=null){
		current_order.order_status=Status.USER_CANCEL;
		}else return;
		
		
	}

	public AdvancedConfiguration generateConf() {
		String s=user_ID;
		return new AdvancedConfiguration("test,"+s+"", 99);
		
	}

	public String genString(Order order) {
		
		
		return order.user_id+" has sent a "+order.order_status+" with topic: "+order.topic;
	}
	
	
	







}
