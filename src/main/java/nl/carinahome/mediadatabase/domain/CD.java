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

import nl.carinahome.mediadatabase.domain.CD;


@Entity
public class CD {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private int year;
	private String origin;
	private String remarks;
	private boolean checked;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Genre> genres = new ArrayList<Genre>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Artist> artists = new ArrayList<Artist>();
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
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	/**
	 * @return the artists
	 */
	public List<Artist> getArtists() {
		return artists;
	}
	/**
	 * @param artists the artists to set
	 */
	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}
	
	
	/* =====================================
	   Removing and adding artists by C. Horrel
       ===================================== */
	
	public void removeArtistFromArtists(Artist artist) {
		this.artists.remove(artist);
	}
	
	public boolean isLinkedArtist(Artist linkedArtist) {
		for (Artist artist : artists) {
			if (artist.getId() == linkedArtist.getId()) {
				return true;
			}
		}
		return false;
	}
	
	/* =====================================
	   Removing and adding artists by C. Horrel
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
	public void addArtist(Artist artist){
		this.artists.add(artist);
	}
	public void removeAllArtists() {
		this.artists.clear();
	}
	public boolean removeOneArtist(Artist artist) {
		return this.artists.remove(artist);
	}
	
	public void cdCopy(CD cd) {
		this.setRemarks(cd.getRemarks());
		this.setTitle(cd.getTitle());
		this.setYear(cd.getYear());
		this.setOrigin(cd.getOrigin());
		
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
			
		CD other = (CD) obj;		
		if (this.artists == null) {
			if (other.artists != null)
				return false;
		} else if (!this.artists.equals(other.artists))
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
	



