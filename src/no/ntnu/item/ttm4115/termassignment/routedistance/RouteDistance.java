package no.ntnu.item.ttm4115.termassignment.routedistance;

import com.bitreactive.library.android.maps.model.Position;

import no.ntnu.item.arctis.runtime.Block;

import no.ntnu.item.ttm4115.simulation.routeplanner.Route;
import no.ntnu.item.ttm4115.simulation.routeplanner.Step;
import no.ntnu.item.ttm4115.termassignment.distanceAndId.DistanceAndId;

public class RouteDistance extends Block {

	public DistanceAndId routeToDistance(Route r) {
		
		
					Step start= r.legs.get(0).steps.get(0);
					
					int last= r.legs.get(0).steps.size()-1;
					
					Step stop = r.legs.get(0).steps.get(last);
					
					Position startp =new Position(start.startLocation.latitude, start.startLocation.longitude);
					Position stopp= new Position(stop.endLocation.latitude, stop.endLocation.longitude);
					//System.out.println(""+startp.getLatitude()+" "+startp.getLongitude());
					//System.out.println(""+stopp.getLatitude()+" "+stopp.getLongitude());
					
					double distance= findDistance(startp, stopp);
					String id=r.taxiAlias;
			
				
				
					return new DistanceAndId(distance,id);
			}

			
			public double findDistance(Position start, Position stop){
				
				double xDist=start.getLongitude()-stop.getLongitude();
				double yDist=start.getLatitude()-stop.getLatitude();
				
				
				
				double tDist= Math.sqrt( Math.pow(xDist, 2) + Math.pow(yDist, 2));
				
				return tDist;
				
			}
		
		
	}


