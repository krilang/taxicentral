package no.ntnu.item.ttm4115.termassignment.TaxiPosition;

import no.ntnu.item.ttm4115.termassignment.TaxiType.TaxiType;

import com.bitreactive.library.android.maps.model.Position;

public class TaxiInformation {
	
	public String taxi_id;
	public Position taxi_position;
	public TaxiType taxi_type;
	
	public TaxiInformation(String taxi_id, Position taxi_position, TaxiType tt) {
		
		this.taxi_id = taxi_id;
		this.taxi_position = taxi_position;
		this.taxi_type = tt;
	}
	
	public String positionAsString() {
		return ""+taxi_position.getLatitude()/1000000+","+taxi_position.getLongitude()/1000000;
	}

}
