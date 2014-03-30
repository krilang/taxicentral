package no.ntnu.item.ttm4115.termassignment.userclient;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;
import no.ntnu.item.ttm4115.termassignment.Status.Status;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class UserClient extends Block {

	public Order current_order;
	public String user_id;



	public static String getAlias(String user_id) {
		return user_id;
	}

	public static String getAlias(Order order) {
		return order.user_id;
	}

	public boolean request(String address) {

		if (current_order != null) {
			current_order.msg_to_user = "Please cancel your previous order to make a new request.";
			
			return false;
		}

		current_order = new Order();
		current_order.order_status = Status.USER_ORDER;
		current_order.topic = "central";
		current_order.user_id = user_id;
		current_order.address = address;
		//System.out.println("requset med adresse "+address);
		System.out.println("requsest returnerer true");

		return true;
	}

	public boolean cancel() {
		if(current_order!=null){
			current_order.order_status=Status.USER_CANCEL;
			current_order.topic="central";

			return true;
		}
		
		return false;
	}

	public AdvancedConfiguration generateConf() {
		return new AdvancedConfiguration("test,u"+user_id, 99);

	}

	public String getOrderMessage() {
		System.out.println("Getordermessage");
		if(current_order != null){

			if(current_order.order_status == Status.CENTRAL_USER_CANCEL_CONF) {
				current_order = null;
				return "Your order has been canceled."; 
			}else if(current_order.order_status == Status.CENTRAL_USER_ORDER_CONF){
				 
				return "Your order is handeld, you will be picked up by "+current_order.taxi_id;
			}else if(current_order.order_status == Status.CENTRAL_USER_ORDER_Q){
				
				return "You are currently inn queue, in position "+current_order.queue_position;
				
			}else if (current_order.order_status == Status.USER_ORDER){
				return "You already have an pending request, cancel it to create a new";
			}

		}
		else if(current_order == null){

			return "There is no order to cancel";

		}

		//Skal ikke bli kjørt
		return current_order.user_id+" has sent a "+current_order.order_status+" with topic: "+current_order.topic;
	}

	public String requestSent() {
		return "Your request is beeing handeled";
	}

	public void setaddress(String address) {
		current_order.address=address;
	}

	public void print(String s) {
		System.out.println(s);
	}

	public void te(boolean b) {
		System.out.println("kommer hit da");
	}










}
