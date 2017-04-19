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

import nl.carinahome.mediadatabase.domain.Book;


@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private int year;
	
	private long isbn;
	private String origin;
	private String remarks;
	private boolean checked;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Genre> genres = new ArrayList<Genre>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Writer> writers = new ArrayList<Writer>();
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
	 * @return the isbn
	 */
	public long getIsbn() {
		return isbn;
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(long isbn) {
		this.isbn = isbn;
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
	 * @return the remarks
	 */
	
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
	 * @return the artists
	 */
	public List<Writer> getWriters() {
		return writers;
	}
	/**
	 * @param artists the artists to set
	 */
	public void setWriters(List<Writer> writers) {
		this.writers = writers;
	}
	
	
	/* =====================================
	   Removing and adding artists by C. Horrel
       ===================================== */
	
	public void removeWriterFromWriters(Writer writer) {
		this.writers.remove(writer);
	}
	
	public boolean isLinkedWriter(Writer linkedWriter) {
		for (Writer writer : writers) {
			if (writer.getId() == linkedWriter.getId()) {
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
	Removing and adding writer
   ===================================== */
	public void addWriter(Writer writer){
		this.writers.add(writer);
	}
	public void removeAllWriters() {
		this.writers.clear();
	}
	public boolean removeOneWriter(Writer writer) {
		return this.writers.remove(writer);
	}
	
	public void bookCopy(Book book) {
		this.setRemarks(book.getRemarks());
		this.setTitle(book.getTitle());
		this.setYear(book.getYear());
		this.setIsbn(book.getIsbn());
		this.setOrigin(book.getOrigin());
		this.setChecked(book.isChecked());
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((writers == null) ? 0 : writers.hashCode());
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
			
		Book other = (Book) obj;		
		if (this.writers == null) {
			if (other.writers != null)
				return false;
		} else if (!this.writers.equals(other.writers))
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
	



