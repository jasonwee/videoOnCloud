package play.learn.java.design.mediator;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BtnView extends JButton implements Command {
	
	Mediator med;
	
	BtnView(ActionListener al, Mediator m) {
		super("View");
		addActionListener(al);
		med = m;
		med.registerView(this);
	}

	@Override
	public void execute() {
		med.view();

	}

}
