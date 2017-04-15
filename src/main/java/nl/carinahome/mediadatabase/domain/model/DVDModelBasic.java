package nl.carinahome.mediadatabase.domain.model;

import java.util.ArrayList;
import java.util.List;

import nl.carinahome.mediadatabase.domain.Actor;
import nl.carinahome.mediadatabase.domain.DVD;
import nl.carinahome.mediadatabase.domain.Genre;

public class DVDModelBasic {
	private DVD dvd;
	
	public DVDModelBasic(DVD dvd) {
		this.dvd = dvd;
	}
	
	public long getId() {
		return this.dvd.getId();
	}
	public String getTitle() {
		return this.dvd.getTitle();
	}
	public int getYear() {
		return this.dvd.getYear();
	}
	public String getOrigin() {
		return this.dvd.getOrigin();
	}
	public boolean isBonus() {
		return this.dvd.isBonus();
	}
	
	public String getRemarks() {
		return this.dvd.getRemarks();
	}
	
	/**
	 * To prevent going in circles, only Actor id's are returned
	 * @return the Actor id's
	 */
	public List<Long> getActorsById() {
		List<Long> actorIds = new ArrayList<>();
		for (int i=0 ; i<this.dvd.getActors().size() ; i++) {
			actorIds.add(this.dvd.getActors().get(i).getId());
		}
		return actorIds;
	}
	
	public List<Actor> getActors() {
		return this.dvd.getActors();
	}
	
	
	/**
	 * To prevent going in circles, only Genre id's are returned
	 * @return the Genre id's
	 */
	public List<Long> getGenresById() {
		List<Long> genreIds = new ArrayList<>();
		for (int i=0 ; i<this.dvd.getGenres().size() ; i++) {
			genreIds.add(this.dvd.getGenres().get(i).getId());
		}
		return genreIds;
	}
	
	public List<Genre> getGenres() {
		return this.dvd.getGenres();
	}
	
}

