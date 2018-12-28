package play.learn.java.design.adapter1;

public class FishingBoatAdapter implements RowingBoat {
	
	private FishingBoat boat;
	
	public FishingBoatAdapter() {
		boat = new FishingBoat();
	}

	@Override
	public void row() {
		// TODO Auto-generated method stub
		boat.sail();
	}

}
