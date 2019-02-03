package play.learn.java.design.mediator1;

public abstract class PartyMemberBase implements PartyMember {

	protected Party party;

	@Override
	public void joinedParty(Party party) {
		System.out.printf("%s joins the party", this);
		this.party = party;
	}

	@Override
	public void partyAction(Action action) {
		System.out.printf("%s %s", this, action.getDescription());
	}

	@Override
	public void act(Action action) {
		if (party != null) {
			System.out.printf("%s %s", this, action);
			party.act(this, action);
		}
	}

	@Override
	public abstract String toString();

}
