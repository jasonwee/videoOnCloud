package play.learn.java.design.mediator;

// abstract mediator
public interface Mediator {
	
	void book();
	void view();
	void search();
	void registerView(BtnView v);
	void registerSearch(BtnSearch s);
	void registerBook(BtnBook b);
	void registerDisplay(LblDisplay d);
}
