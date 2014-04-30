package no.ntnu.item.ttm4115.termassignment.taxisimulator;

import com.bitreactive.library.android.maps.model.MapUpdate;
import com.bitreactive.library.android.maps.model.Marker;
import com.bitreactive.library.android.maps.model.Position;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.termassignment.TaxiType.TaxiType;
import no.ntnu.item.ttm4115.termassignment.taximapstatus.taxiMapStatus;

public class TaxiSimulator extends Block {

	public java.lang.String taxiMapId;
	public com.bitreactive.library.android.maps.model.Position tempPosition;

	public MapUpdate createMapUpdate(taxiMapStatus tms) {
		
		System.out.println("starting");
		System.out.println(tms.taxiType.toString());
		MapUpdate mu= new MapUpdate();
		Position p=tms.position;
		Marker m1 = Marker.createMarker(tms.mTaxiID).position(p).title("taxiNr"+tms.mTaxiID);
		if(tms.available){
			m1.hue(Marker.HUE_GREEN);}
		else{
			m1.hue(Marker.HUE_RED);
		}
		if(tms.taxiType!=null){
			m1.description(tms.taxiType.toString());
		}
		m1.showWindow(true);
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



	public MapUpdate taxiRemove(taxiMapStatus tms) {

		Marker m1 = Marker.createMarker(tms.mTaxiID);
		m1.remove();
		MapUpdate mu= new MapUpdate();
		mu.addMarker(m1);

		return mu;
	}

}
