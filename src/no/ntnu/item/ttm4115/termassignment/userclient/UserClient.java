package no.ntnu.item.ttm4115.termassignment.userclient;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class UserClient extends Block {

	public no.ntnu.item.ttm4115.termassignment.order.Order current_order;
	public java.lang.String user_ID;

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
		current_order.order_status=Status.USER_CANCEL;
		
		
	}







}
