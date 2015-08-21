package play.learn.java.design.mediator;

// concrete mediator
public class ParticipantMediator implements Mediator {
	
	BtnView btnView;
	BtnSearch btnSearch;
	BtnBook btnBook;
	LblDisplay show;
	
	@Override
	public void registerView(BtnView v) {
		btnView = v;
	}

	@Override
	public void registerSearch(BtnSearch s) {
		btnSearch = s;
	}

	@Override
	public void registerBook(BtnBook b) {
		btnBook = b;
	}

	@Override
	public void registerDisplay(LblDisplay d) {
		show = d;
	}
	
	@Override
	public void book() {
		btnBook.setEnabled(false);
		btnView.setEnabled(true);
		btnSearch.setEnabled(true);
		show.setText("booking...");
	}

	@Override
	public void view() {
		btnView.setEnabled(false);
		btnSearch.setEnabled(true);
		btnBook.setEnabled(true);
		show.setText("viewing...");
	}

	@Override
	public void search() {
		btnSearch.setEnabled(false);
		btnView.setEnabled(true);
		btnBook.setEnabled(true);
		show.setText("searching...");
	}



}
