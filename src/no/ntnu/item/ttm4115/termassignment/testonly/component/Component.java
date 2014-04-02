package no.ntnu.item.ttm4115.termassignment.testonly.component;

import com.bitreactive.library.android.maps.model.Position;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.simulation.routeplanner.Distance;
import no.ntnu.item.ttm4115.simulation.routeplanner.Journey;
import no.ntnu.item.ttm4115.simulation.routeplanner.Route;
import no.ntnu.item.ttm4115.simulation.routeplanner.Step;
import no.ntnu.item.ttm4115.termassignment.distanceAndId.DistanceAndId;

public class Component extends Block {

	public Journey createJourney() {
		String taxiAlias= "en";
		String fromAddress="Stavanger, Norge";
		String toAddress="Oslo, Norge";
		
		Journey j=new Journey(taxiAlias, fromAddress, toAddress);
		return j;
	}

	public void console(Route r) {
		
		//System.out.println(r.legs.get(0).steps.get(0).endLocation.latitude   step.endLocation.latitude*1E6);
		
		System.out.println("anntall legs er "+ r.legs.size());
		for (int i = 0; i < r.legs.size(); i++) {
			r.legs.get(i).steps.size();
			System.out.println("anntall steps er "+r.legs.get(i).steps.size());
//			for (int j = 0; j <r.legs.get(i).steps.size(); j++) {
//				System.out.println("NY STEP");
//				System.out.println();
//				System.out.println();
//				System.out.println(r.legs.get(i).steps.get(i).distance.toString());
//				System.out.println(r.legs.get(i).steps.get(i).duration);
//				System.out.println(r.legs.get(i).steps.get(i).travelMode);
//				System.out.println(r.legs.get(i).steps.get(i).startLocation.latitude);
//				System.out.println(r.legs.get(i).steps.get(i).endLocation.longitude);
//				
//				
//			}
			Step start= r.legs.get(0).steps.get(0);
			
			int last= r.legs.get(0).steps.size()-1;
			
			Step stop = r.legs.get(0).steps.get(last);
			
			Position startp =new Position(start.startLocation.latitude, start.startLocation.longitude);
			Position stopp= new Position(stop.endLocation.latitude, stop.endLocation.longitude);
			System.out.println(""+startp.getLatitude()+" "+startp.getLongitude());
			System.out.println(""+stopp.getLatitude()+" "+stopp.getLongitude());
			
			
			System.out.println("Distance is "+findDistance(startp, stopp));
		}
		System.out.println("summary er "+r.summary);
		
		
		
		
	}

	
	public String findDistance(Position start, Position stop){
		
		double xDist=start.getLongitude()-stop.getLongitude();
		double yDist=start.getLatitude()-stop.getLatitude();
		
		
		
		double tDist= Math.sqrt( Math.pow(xDist, 2) + Math.pow(yDist, 2));
		
		return ""+tDist;
		
	}

	public void getOut(DistanceAndId dai) {
		
		System.out.println(dai.distance);
		System.out.println(dai.id);
	}
}
