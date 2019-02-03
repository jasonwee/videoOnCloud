package play.learn.java.design.mediator1;

import java.util.ArrayList;
import java.util.List;

public class PartyImpl implements Party {
	private final List<PartyMember> members;

	public PartyImpl() {
		members = new ArrayList<>();
	}

	@Override
	public void act(PartyMember actor, Action action) {
		for (PartyMember member : members) {
			if (!member.equals(actor)) {
				member.partyAction(action);
			}
		}
	}

	@Override
	public void addMember(PartyMember member) {
		members.add(member);
		member.joinedParty(this);
	}

}
