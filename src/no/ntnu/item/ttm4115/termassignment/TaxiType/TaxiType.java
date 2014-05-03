package no.ntnu.item.ttm4115.termassignment.TaxiType;

public enum TaxiType {
	CARGOTAXI, MAXITAXI,TAXI;


	public static TaxiType stringToEnum(String str){
		String s=str.toUpperCase();
		if (s.equals(CARGOTAXI.toString())){
			return CARGOTAXI;
		}else if(s.equals(MAXITAXI.toString())){
			return MAXITAXI;
		}else if(s.equals(TAXI.toString())){
			return TAXI;
		}else{
			return null;
		}
		
	}
}

