package play.learn.java.design.partial_response;

import java.util.HashMap;
import java.util.Map;

// https://java-design-patterns.com/patterns/partial-response/
public class PartialResponseDemo {

	public static void main(String[] args) throws Exception {
		Map<Integer, Video> videos = new HashMap<>();
		videos.put(1, new Video(1, "Avatar", 178, "epic science fiction film", "James Cameron", "English"));
		videos.put(2, new Video(2, "Godzilla Resurgence", 120, "Action & drama movie|", "Hideaki Anno", "Japanese"));
		videos.put(3, new Video(3, "Interstellar", 169, "Adventure & Sci-Fi", "Christopher Nolan", "English"));
		VideoResource videoResource = new VideoResource(new FieldJsonMapper(), videos);

		System.out.println("Retrieving full response from server:-");
		System.out.println("Get all video information:");
		String videoDetails = videoResource.getDetails(1);
		System.out.println(videoDetails);

		System.out.println("----------------------------------------------------------");

		System.out.println("Retrieving partial response from server:-");
		System.out.println("Get video @id, @title, @director:");
		String specificFieldsDetails = videoResource.getDetails(3, "id", "title", "director");
		System.out.println(specificFieldsDetails);

		System.out.println("Get video @id, @length:");
		String videoLength = videoResource.getDetails(3, "id", "length");
		System.out.println(videoLength);
	}

}
