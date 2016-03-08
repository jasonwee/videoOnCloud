package play.learn.java.design.adapter;

public class MediaAdapter implements MediaPlayer {
	
	AdvancedMediaPlayer advanceMediaPlayer;
	
	public MediaAdapter(String audioType) {
		if (audioType.equalsIgnoreCase("vlc")) {
			advanceMediaPlayer =new VlcPlayer(); 
		} else if (audioType.equalsIgnoreCase("mp4")) {
			advanceMediaPlayer = new Mp4Player();
		}
	}

	@Override
	public void play(String audioType, String fileName) {
		if (audioType.equalsIgnoreCase("vlc")) {
			advanceMediaPlayer.playVlc(fileName);
		} else if (audioType.equalsIgnoreCase("mp4")) {
			advanceMediaPlayer.playMp4(fileName);
		}
	}

}
