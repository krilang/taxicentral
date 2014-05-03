package no.ntnu.item.ttm4115.termassignment.TaxiType;

public enum TaxiType {
	CARGOTAXI, MAXITAXI,TAXI;


	public static TaxiType stringToEnum(String str){
		String s=str.toUpperCase();
		if ( s.equals(CARGOTAXI.toString()) || s.contains("CARGO") ){
			return CARGOTAXI;
		}else if(s.equals(MAXITAXI.toString()) || s.contains("MAXI")){
			return MAXITAXI;
		}
		else{
			return TAXI;
		}
		
	}
}

