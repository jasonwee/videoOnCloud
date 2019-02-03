package play.learn.java.design.mediator1;

public interface PartyMember {

	void joinedParty(Party party);

	void partyAction(Action action);

	void act(Action action);
}
