package org.just4fun.paymentsystem;

public class DebitCard extends Card {
	
	public DebitCard(String nameOnCard, String number, String cvv, String expirationDate) {
		super(nameOnCard, number, cvv, expirationDate);
	}

	@Override
	protected String getType() {
		return "debit";
	}

	@Override
	protected void executeTransaction(int cents) {
		// contact bank to execute transaction
	}

}
