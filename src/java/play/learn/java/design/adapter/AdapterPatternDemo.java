package play.learn.java.design.adapter;

public class AdapterPatternDemo {

	public static void main(String[] args) {
		AudioPlayer audioPlayer = new AudioPlayer();
		
		audioPlayer.play("mp3", "Ghosts.mp3");
		audioPlayer.play("mp3", "Dangerous.mp3");
		audioPlayer.play("mp4", "You are not alone.mp4");
		audioPlayer.play("vlc", "Who is it.vlc");
		audioPlayer.play("avi", "mind me.avi");
	}

}
