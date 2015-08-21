package play.learn.java.design.mediator;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BtnBook extends JButton implements Command {
	
	Mediator med;
	
	BtnBook(ActionListener al, Mediator m) {
		super("Book");
		addActionListener(al);
		med = m;
		med.registerBook(this);
	}

	@Override
	public void execute() {
		med.book();
	}

}
