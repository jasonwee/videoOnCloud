package play.learn.java.design.dirtyflag;

import java.util.ArrayList;
import java.util.List;

public class World {
	
	private List<String> countries;
	  private DataFetcher df;

	  public World() {
	    this.countries = new ArrayList<String>();
	    this.df = new DataFetcher();
	  }

	  /**
	   * 
	   * Calls {@link DataFetcher} to fetch data from back-end.
	   * 
	   * @return List of strings
	   */
	  public List<String> fetch() {
	    List<String> data = df.fetch();

	    countries = data.isEmpty() ? countries : data;

	    return countries;
	  }

}
