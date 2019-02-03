package play.learn.java.design.mediator1;

public interface Party {
	void addMember(PartyMember member);

	void act(PartyMember actor, Action action);
}
