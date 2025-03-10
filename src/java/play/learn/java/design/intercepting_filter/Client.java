package play.learn.java.design.intercepting_filter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;

	private transient FilterManager filterManager;
	private JLabel jl;
	private JTextField[] jtFields;
	private JTextArea[] jtAreas;
	private JButton clearButton;
	private JButton processButton;

	/**
	 * Constructor
	 */
	public Client() {
		super("Client System");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(300, 300);
		jl = new JLabel("RUNNING...");
		jtFields = new JTextField[3];
		for (int i = 0; i < 3; i++) {
			jtFields[i] = new JTextField();
		}
		jtAreas = new JTextArea[2];
		for (int i = 0; i < 2; i++) {
			jtAreas[i] = new JTextArea();
		}
		clearButton = new JButton("Clear");
		processButton = new JButton("Process");

		setup();
	}

	private void setup() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		add(jl, BorderLayout.SOUTH);
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(6, 2));
		panel.add(new JLabel("Name"));
		panel.add(jtFields[0]);
		panel.add(new JLabel("Contact Number"));
		panel.add(jtFields[1]);
		panel.add(new JLabel("Address"));
		panel.add(jtAreas[0]);
		panel.add(new JLabel("Deposit Number"));
		panel.add(jtFields[2]);
		panel.add(new JLabel("Order"));
		panel.add(jtAreas[1]);
		panel.add(clearButton);
		panel.add(processButton);

		clearButton.addActionListener(e -> {
			for (JTextArea i : jtAreas) {
				i.setText("");
			}
			for (JTextField i : jtFields) {
				i.setText("");
			}
		});

		processButton.addActionListener(e -> {
			Order order = new Order(jtFields[0].getText(), jtFields[1].getText(), jtAreas[0].getText(),
					jtFields[2].getText(), jtAreas[1].getText());
			jl.setText(sendRequest(order));
		});

		JRootPane rootPane = SwingUtilities.getRootPane(processButton);
		rootPane.setDefaultButton(processButton);
		setVisible(true);
	}

	public void setFilterManager(FilterManager filterManager) {
		this.filterManager = filterManager;
	}

	public String sendRequest(Order order) {
		return filterManager.filterRequest(order);
	}
}
