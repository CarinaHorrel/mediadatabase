package nl.carinahome.mediadatabase.domain.model;

import nl.carinahome.mediadatabase.domain.Actor;

public class ActorModelBasic {
	private Actor actor;
	
	public ActorModelBasic(Actor actor) {
		this.actor = actor;
	}
	
	public long getId() {
		return this.actor.getId();
	}
	
	public String getFirstName() {
		return this.actor.getFirstName();
	}
	
	public String getLastName() {
		return this.actor.getLastName();
	}
}


