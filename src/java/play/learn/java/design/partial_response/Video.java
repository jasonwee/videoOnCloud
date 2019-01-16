package play.learn.java.design.partial_response;

public class Video {
	
	  private final Integer id;
	  private final String title;
	  private final Integer length;
	  private final String description;
	  private final String director;
	  private final String language;

	  /**
	   * @param id          video unique id
	   * @param title       video title
	   * @param length      video length in minutes
	   * @param description video description by publisher
	   * @param director    video director name
	   * @param language    video language {private, public}
	   */
	  public Video(Integer id, String title, Integer length, String description, String director, String language) {
	    this.id = id;
	    this.title = title;
	    this.length = length;
	    this.description = description;
	    this.director = director;
	    this.language = language;
	  }

	  /**
	   * @return json representaion of video
	   */
	  @Override
	  public String toString() {
	    return "{"
	        + "\"id\": " + id + ","
	        + "\"title\": \"" + title + "\","
	        + "\"length\": " + length + ","
	        + "\"description\": \"" + description + "\","
	        + "\"director\": \"" + director + "\","
	        + "\"language\": \"" + language + "\","
	        + "}";
	  }

}
