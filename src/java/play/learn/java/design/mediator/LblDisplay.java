package play.learn.java.design.mediator;

import java.awt.Font;

import javax.swing.JLabel;

public class LblDisplay extends JLabel {
	
	Mediator med;
	
	LblDisplay(Mediator m) {
		super("Just start...");
		med = m;
		med.registerDisplay(this);
		setFont(new Font("Arial", Font.BOLD, 24));
	}

}
