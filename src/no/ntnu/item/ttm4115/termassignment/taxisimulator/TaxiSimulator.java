package no.ntnu.item.ttm4115.termassignment.taxisimulator;

import com.bitreactive.library.android.maps.model.MapUpdate;
import com.bitreactive.library.android.maps.model.Marker;
import com.bitreactive.library.android.maps.model.Position;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.taximapstatus.taxiMapStatus;

public class TaxiSimulator extends Block {

	public java.lang.String taxiMapId;
	public com.bitreactive.library.android.maps.model.Position tempPosition;

	public MapUpdate createMapUpdate(taxiMapStatus tms) {
		System.out.println("starting");
		MapUpdate mu= new MapUpdate();
		Position p=tms.position;
		Marker m1 = Marker.createMarker(tms.mTaxiID).position(p);
		mu.addMarker(m1);
		mu.setCenter(p);
		mu.setZoom(15);
		System.out.println("sending mapupdate");
		return mu;
	}

	public void setTaxiandPosition(MapUpdate mu) {
		tempPosition=mu.getMarkers().get(0).getPosition();
		taxiMapId = mu.getMarkers().get(0).getId();
	}

	public  taxiMapStatus createTaxiStatus() {
		taxiMapStatus tms=new taxiMapStatus(taxiMapId, tempPosition);
		return tms;
	}

}
