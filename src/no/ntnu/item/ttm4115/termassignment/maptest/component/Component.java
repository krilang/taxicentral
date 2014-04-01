package no.ntnu.item.ttm4115.termassignment.maptest.component;

import com.bitreactive.library.android.maps.model.MapUpdate;
import com.bitreactive.library.android.maps.model.Marker;
import com.bitreactive.library.android.maps.model.Position;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.simulation.routeplanner.Journey;

public class Component extends Block {

	public Journey CreateJourney() {
		String fromAddress="Vangslunds gate 3, 7030 Trondheim, Norge";
		String toAddress="Belbuvegen 9, 7045 Trondheim, Norge";
		Journey j = new Journey("1",fromAddress, toAddress);
		
		return j;
	}

	public MapUpdate CreateMarker() {
		
		Position p1 =new  Position(10392216, 63430480);
		MapUpdate mu=new MapUpdate();
		mu.addMarker(Marker.createMarker("id").position(p1));
		System.out.println("returning mapupdate");
		return mu;
	}

}
