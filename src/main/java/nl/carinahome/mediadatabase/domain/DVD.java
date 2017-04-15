package nl.carinahome.mediadatabase.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import nl.carinahome.mediadatabase.domain.DVD;


@Entity
public class DVD {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique=true, nullable=false)
	private String title;
	
	private int year;
	private String origin;
	private boolean bonus;
	private String remarks;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Genre> genres = new ArrayList<Genre>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Actor> actors = new ArrayList<Actor>();
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the bonus
	 */
	public boolean isBonus() {
		return bonus;
	}
	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the genres
	 */
	public List<Genre> getGenres() {
		return genres;
	}
	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	/**
	 * @return the actors
	 */
	public List<Actor> getActors() {
		return actors;
	}
	/**
	 * @param actors the actors to set
	 */
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	
	
	/* =====================================
	   Removing and adding actors by C. Horrel
       ===================================== */
	
	public void removeActorFromActors(Actor actor) {
		this.actors.remove(actor);
	}
	
	public boolean isLinkedActor(Actor linkedActor) {
		for (Actor actor : actors) {
			if (actor.getId() == linkedActor.getId()) {
				return true;
			}
		}
		return false;
	}
	
	/* =====================================
	   Removing and adding actors by C. Horrel
    ===================================== */
	
	public void removeGenreFromGenres(Genre genre) {
		this.genres.remove(genre);
	}
	
	public boolean isLinkedGenre(Genre linkedGenre) {
		for (Genre genre : genres) {
			if (genre.getId() == linkedGenre.getId()) {
				return true;
			}
		}
		return false;
	}
	
	
/* =====================================
	Removing and adding genres
   ===================================== */
	public void addGenre(Genre genre){
		this.genres.add(genre);
	}
	public void removeAllGenres() {
		this.genres.clear();
	}

	// added by C. Horrel: not sure if it will happen
	// If type mistake and it is added to list of genres, 
	// then it would be nice, if it possible to delete.
	
	public boolean removeOneGenre(Genre genre) {
		return this.genres.remove(genre);
	}
	
/* =====================================
	Removing and adding actors
   ===================================== */
	public void addActor(Actor actor){
		this.actors.add(actor);
	}
	public void removeAllActors() {
		this.actors.clear();
	}
	public boolean removeOneActor(Actor actor) {
		return this.actors.remove(actor);
	}
	
	public void dvdCopy(DVD dvd) {
		this.setRemarks(dvd.getRemarks());
		this.setTitle(dvd.getTitle());
		this.setYear(dvd.getYear());
		this.setOrigin(dvd.getOrigin());
		this.setBonus(dvd.isBonus());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((actors == null) ? 0 : actors.hashCode());
//		result = prime * result + (int) (id ^ (id >>> 32));
//		result = prime * result + ((title == null) ? 0 : title.hashCode());
//		result = prime * result + year;
//		return result;
//	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * Deze methode overrides de standaard equals methode, zodat 
	 * twee objecten gelijk zijn, wanneer ze van dezelfde class zijn
	 * en hun id gelijk is.
	 * @param obj Het object dat vergeleken moet worden op gelijkheid.
	 */
	@Override
	public boolean equals(Object obj) {
		// standaard vergelijkingen/voorwaarden
		if (this == obj) return true;
		if (obj == null) return false;
		
		// Classes ongelijk -> nooit gelijk
		if (this.getClass() == obj.getClass()) {
			// Tot dus ver alles goed, dus tijd om te gaan casten naar Actor en de id's te gaan vergelijken
			
		DVD other = (DVD) obj;		
		if (this.actors == null) {
			if (other.actors != null)
				return false;
		} else if (!this.actors.equals(other.actors))
			return false;
		if (this.genres == null) {
			if (other.genres != null)
				return false;
		} else if (!this.genres.equals(other.genres))
			return false;
		
		if (id != other.id)
			return false;
		
		if (this.title == null) {
			if (other.title != null)
				return false;
		} else if (!this.title.equals(other.title))
			return false;
		
		if (this.year == other.year){
			return true; 
		}else {
			return false;
		}
		
		}else {  // part (getClass() != obj.getClass())
			return false;
		}
	}
}
	



