package no.ntnu.item.ttm4115.termassignment.taximapstatus;

import com.bitreactive.library.android.maps.model.Position;

public class taxiMapStatus {
	
	public String mTaxiID;
	public Position position;
	
	
	public taxiMapStatus(String taxiID, Position position) {
		
		
		this.mTaxiID=taxiID;
		this.position= position;
		
	
		
	}

}
