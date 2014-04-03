package no.ntnu.item.ttm4115.termassignment.TaxiPosition;

import com.bitreactive.library.android.maps.model.Position;

public class TaxiPosition {
	
	public String taxi_id;
	public Position taxi_position;
	
	public TaxiPosition(String taxi_id, Position taxi_position) {
		
		this.taxi_id = taxi_id;
		this.taxi_position = taxi_position;
	}

}
