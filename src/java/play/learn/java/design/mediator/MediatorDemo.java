package play.learn.java.design.mediator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MediatorDemo extends JFrame implements ActionListener {
	
	Mediator med = new ParticipantMediator();
	
	MediatorDemo() {
		JPanel p = new JPanel();
		p.add(new BtnView(this, med));
		p.add(new BtnBook(this, med));
	    p.add(new BtnSearch(this, med));
	    getContentPane().add(new LblDisplay(med), "North");
        getContentPane().add(p, "South");
        setSize(400, 200);
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Command command = (Command)e.getSource();
		command.execute();
	}

	public static void main(String[] args) {
		new MediatorDemo();
	}

}
