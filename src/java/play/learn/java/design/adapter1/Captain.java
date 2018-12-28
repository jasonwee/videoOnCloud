package play.learn.java.design.adapter1;

public class Captain implements RowingBoat {
	
	private RowingBoat rowingBoat;
	
	public Captain(RowingBoat rowingBoat) {
		this.rowingBoat = rowingBoat;
	}

	@Override
	public void row() {
		rowingBoat.row();
	}

}
