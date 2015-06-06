package play.learn.java;

import java.util.Date;

public interface LearnDefaultMethod {
	
	// the good old abstract method. 
	public String getFoo();
	
    // implementation for static method
    static public String getZoneId (String zoneString) {
    	return "";
    }
    
    // default  keyword
    default public Date getZonedDateTime(String zoneString) {
        return null;
    }
    

}
