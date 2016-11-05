package play.learn.java.design.chainofresponsibility;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CheckAuthority {

	public static void main(String[] args) {
		ManagerPPower manager = new ManagerPPower();
		DirectorPPower director = new DirectorPPower();
		VicePresidentPPower vp = new VicePresidentPPower();
		PresidentPPower president = new PresidentPPower();
		manager.setSuccessor(director);
		director.setSuccessor(vp);
		vp.setSuccessor(president);
		
		try {
			while (true) {
				System.out.println("Enter the amount to check who should approve your expenditure.");
				System.out.println(">");
				double d = Double.parseDouble(new BufferedReader(new InputStreamReader(System.in)).readLine());
				manager.processRequest(new PurchaseRequest(d, "General"));
			}
		} catch (Exception e) {
			System.exit(1);
		}

	}

}
