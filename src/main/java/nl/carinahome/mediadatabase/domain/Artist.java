package nl.carinahome.mediadatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String artistName;
	
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
	 * @return the artistName
	 */
	public String getArtistName() {
		return artistName;
	}
	/**
	 * @param artistName the artistName to set
	 */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
//		result = prime * result + (int) (id ^ (id >>> 32));
//		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
			
		Artist other = (Artist) obj;
		
		if (this.artistName == null) {
			if (other.artistName != null)
				return false;
		} else if (!this.artistName.equals(other.artistName))
			return false;
				
		if (this.id == other.id) {
			return true;
		} else {
			return false;
		}
			
		} else {  // part (getClass() != obj.getClass())
			return false;
		}
	}
}
		
		

	

	
	

	
	

