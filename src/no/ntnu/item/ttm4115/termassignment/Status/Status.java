package no.ntnu.item.ttm4115.termassignment.Status;

public enum Status {
	USER_ORDER, USER_CANCEL,USER_PREBOOK, // Objects from user to central
	TAXI_AVAILABLE, TAXI_DUTY,TAXI_ANSWER, TOUR_FINISHED,  // Objects from taxi to central
	CENTRAL_TAXI_OFFER, CENTRAL_TAXI_CONF, CENTRAL_TAXI_ORDER_CONF, CENTRAL_TAXI_ORDER_CANCELED, // Objects from central to taxi
	CENTRAL_USER_CANCEL_CONF, CENTRAL_USER_ORDER_CONF, CENTRAL_USER_ORDER_Q, CENTRAL_TOUR_ENDED_CONF, CENTRAL_USER_TAXI_ABORTED, // Objects from central to user
	TAXI_USER_COM, TAXI_USER_CONF, TAXI_USER_ABORT, //Objects between user and taxi
	DUMMY; 
}
