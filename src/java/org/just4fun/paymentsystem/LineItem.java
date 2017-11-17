package org.just4fun.paymentsystem;

public class LineItem {
	
	public LineItem(String description, int costInCents) {
		this.description = description;
		this.costInCents = costInCents;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCostInCents() {
		return costInCents;
	}

	public void setCostInCents(int costInCents) {
		this.costInCents = costInCents;
	}

	private String description;
	private int costInCents;

}
