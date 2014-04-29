package no.ntnu.item.ttm4115.termassignment.maptest.component;

import com.bitreactive.library.android.maps.model.MapUpdate;
import com.bitreactive.library.android.maps.model.Marker;
import com.bitreactive.library.android.maps.model.Position;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.simulation.routeplanner.Journey;
import no.ntnu.item.ttm4115.simulation.routeplanner.Route;
import no.ntnu.item.ttm4115.termassignment.taximapstatus.taxiMapStatus;

public class Component extends Block {
	
	


	
	
	public java.lang.String taxiId;
	public com.bitreactive.library.android.maps.model.Position taxiPoint;

	public Journey CreateJourney() {
		System.out.println("creating journey");
		Position p=new Position(63.429200, 10.390869);
		
		//String fromAddress="Vangslunds gate 3, 7030 Trondheim, Norge";
		//String fromAddress="63.429200, 10.390869";
		String fromAddress=""+p.getLatitude()+","+p.getLongitude()+"";
		String toAddress="Selsbakkvegen 65, 7028 Trondheim, Norge";
		Journey j = new Journey("taxi",fromAddress, toAddress);
		
		
		return j;
	}

	public MapUpdate CreateMarker() {
		
		Position p1 =new  Position(1.0371261596679688 * 1E7, 6.338902282714844 * 1E7);
		MapUpdate mu=new MapUpdate();
		Marker mm= Marker.createMarker("id").description("description").title("title").position(p1).hue(45);
		
		mu.addMarker(mm);
		mu.setCenter(p1);
		mu.setZoom(15);
		System.out.println("returning mapupdate");
		return mu;
	}
	
	public MapUpdate createMapUpdate() {
		String taxiAlias="testTaxi";
		System.out.println("Creating MapUpdate for taxi: "+ taxiAlias);
		
		
		MapUpdate mapUpdate = new MapUpdate();
		//* 1E6
		Position p1 = new Position(63.429699 , 10.394645 ); 
		Marker m1 = Marker.createMarker("m1").position(p1);
		mapUpdate.addMarker(m1);
		mapUpdate.setCenter(p1);
		mapUpdate.setZoom(15);
		System.out.println("returning");
		return mapUpdate;
	
	}

	
	

	


	

	public void setVirtualTaxi() {
		
		taxiId="taxi";
		taxiPoint= new Position(63422193, 10387607);
	}
	//10.39221659117403 * 1E6, 63.43048084459458 * 1E6
//63.422826, 10.394559
	//63.422193, 10.387607
	public taxiMapStatus createTaxiMapUpdate() {
		
		taxiMapStatus tms= new taxiMapStatus(taxiId, taxiPoint);
		System.out.println("sendingtms");
		return tms;
		
		
	}

	public taxiMapStatus removeTaxi() {
		System.out.println("removing taxi");
		taxiMapStatus tms= new taxiMapStatus(taxiId, taxiPoint);
		return tms;
	}
	

}
