package no.ntnu.item.ttm4115.termassignment.taxisimulator;

import com.bitreactive.library.android.maps.model.MapUpdate;
import com.bitreactive.library.android.maps.model.Marker;
import com.bitreactive.library.android.maps.model.Polyline;
import com.bitreactive.library.android.maps.model.Position;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.simulation.routeplanner.Leg;
import no.ntnu.item.ttm4115.simulation.routeplanner.Route;
import no.ntnu.item.ttm4115.simulation.routeplanner.Step;
import no.ntnu.item.ttm4115.termassignment.TaxiType.TaxiType;
import no.ntnu.item.ttm4115.termassignment.taximapstatus.taxiMapStatus;

public class TaxiSimulator extends Block {

	public java.lang.String taxiMapId;
	public com.bitreactive.library.android.maps.model.Position tempPosition;
	public Polyline polyTemp;

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

	public MapUpdate routeDiscover(Route r) {
		
		
		System.out.println("kjører polylies");
		MapUpdate mu=new MapUpdate();
		Polyline p= new Polyline(r.taxiAlias);
		p.hue(Marker.HUE_BLUE);
		
		System.out.println(r.legs.size()+"Legs size <-");
		System.out.println(r.legs.get(0).steps.size() + "Steps size");
		int size = r.legs.get(0).steps.size();
		for (int i = 0; i < size; i++) {
			
			float latitude= r.legs.get(0).steps.get(i).startLocation.latitude*1000000;
			float longitude= r.legs.get(0).steps.get(i).startLocation.longitude*1000000;
			
			p.addPoint(new Position(latitude, longitude));
			
		}
		polyTemp=p;
		mu.addPolyline(p);
		return mu;
		
		
		
		
		
	}

	public MapUpdate removePolyLine() {
		MapUpdate mu=new MapUpdate();
		
		//removePolyLine().addPolyline(polyTemp);		
		
		polyTemp.setRemove();
		mu.addPolyline(polyTemp);
		
		return mu;
	}

}
