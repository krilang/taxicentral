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
				
				return  RouteDistance.distFrom(start.getLatitude(), start.getLongitude(), stop.getLatitude(), stop.getLongitude());
				
				}
			
			
			public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
			    double earthRadius = 3958.75;
			    double dLat = Math.toRadians(lat2-lat1);
			    double dLng = Math.toRadians(lng2-lng1);
			    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
			               Math.sin(dLng/2) * Math.sin(dLng/2);
			    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			    double dist = earthRadius * c;

			    int meterConversion = 1609;

			    return (double) ((dist * meterConversion)/1000);
			    }
		
		
	}


