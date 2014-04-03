package no.ntnu.item.ttm4115.termassignment.findclosesttaxi;

import java.util.ArrayList;

import com.bitreactive.library.android.maps.model.Position;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.simulation.routeplanner.Journey;
import no.ntnu.item.ttm4115.termassignment.TaxiPosition.TaxiPosition;
import no.ntnu.item.ttm4115.termassignment.distanceAndId.DistanceAndId;
import no.ntnu.item.ttm4115.termassignment.order.Order;

public class FindClosestTaxi extends Block {

	public int taxiListSize;

	public java.lang.String orderLocation;
	public Order incommingOrder;	

	public int counter;

	public ArrayList<DistanceAndId> proxList;

	public java.util.ArrayList<no.ntnu.item.ttm4115.termassignment.TaxiPosition.TaxiPosition> taxiList;

	public void setIncommingOrder(Order order) {
		this.incommingOrder = order;
		this.orderLocation= order.address;
	}

	public void setListSize(ArrayList<TaxiPosition> taxiList) {

		this.taxiList= taxiList;
		taxiListSize=taxiList.size();

	}

	public Journey sendTaxiJourney() {
		
		String taxiAlias = taxiList.get(counter).taxi_id;
		String toAddress= taxiList.get(counter).positionAsString();
		
		return new Journey(taxiAlias, orderLocation, toAddress);


	}

	public void incrementCounter() {
		
		counter++;
	}

	public void initCounter() {

		counter=0;
	}

	public void addToProxList(DistanceAndId dai) {
		proxList.add(dai);
	}

	public boolean checkLast() {
		return (counter < taxiListSize-1);
		
	}
	public Order returnOrder() {
		counter = 0;
		int[] distances = new int[] {2, 4, 6};
		
		for (int i = 0; i < distances.length; i++) {
			
			int limit = distances[i];
			
			for (DistanceAndId dai : proxList) {
				if (dai.distance <= limit) {
					return generateOrder(dai.id);
				}
			}
		}
		
		// TODO sorter og return closest;
		return incommingOrder;
	}

	private Order generateOrder(String taxi_id) {
		// TODO 
		return null;
	}
}
