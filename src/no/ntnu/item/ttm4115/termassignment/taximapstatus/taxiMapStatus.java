package no.ntnu.item.ttm4115.termassignment.taximapstatus;

import no.ntnu.item.ttm4115.termassignment.TaxiType.TaxiType;

import com.bitreactive.library.android.maps.model.Position;

public class taxiMapStatus {
	
	public String mTaxiID;
	public Position position;
	public TaxiType taxiType;
	
	
	public taxiMapStatus(String taxiID, Position position) {
		
		
		this.mTaxiID=taxiID;
		this.position= position;
		
		
	
		
	}

}
