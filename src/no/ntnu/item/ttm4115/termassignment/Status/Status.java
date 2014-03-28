package no.ntnu.item.ttm4115.termassignment.Status;

public enum Status {
	USER_ORDER, USER_CANCEL, // Objects from user to central
	TAXI_AVAILABLE, TAXI_DUTY,TAXI_ANSWER,  // Objects from taxi to central
	CENTRAL_TAXI_OFFER, CENTRAL_TAXI_CONF,  // Objects from central to taxi
	CENTRAL_USER_CANCEL_CONF, CENTRAL_USER_ORDER_CONF; // Objects from central to user
}
