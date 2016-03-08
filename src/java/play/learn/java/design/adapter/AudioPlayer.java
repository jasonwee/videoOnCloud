package play.learn.java.design.adapter;

public class AudioPlayer implements MediaPlayer {
	
	MediaAdapter mediaAdapter;

	@Override
	public void play(String audioType, String fileName) {
		// inbu8ilt support to play mp3 music files.
		if (audioType.equalsIgnoreCase("mp3")) {
			System.out.println("Playing mp3 file. Name: " + fileName);
		}
		// mediaadapter is providing support to play other file formats.
		else if (audioType.equalsIgnoreCase("vlc") || audioType.equalsIgnoreCase("mp4")) {
			mediaAdapter = new MediaAdapter(audioType);
			mediaAdapter.play(audioType, fileName);
		} else {
			System.out.println("invalid media." + audioType + " format not supported.");
		}

	}

}
