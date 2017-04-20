package nl.carinahome.mediadatabase.domain.model;

import java.util.ArrayList;
import java.util.List;

import nl.carinahome.mediadatabase.domain.Artist;
import nl.carinahome.mediadatabase.domain.CD;
import nl.carinahome.mediadatabase.domain.Genre;


public class CDModelBasic {
	private CD cd;

	public CDModelBasic(CD cd) {
		this.cd = cd;
	}

	public long getId() {
		return this.cd.getId();
	}
	public String getTitle() {
		return this.cd.getTitle();
	}
	public int getYear() {
		return this.cd.getYear();
	}
	public String getOrigin() {
		return this.cd.getOrigin();
	}
	
	public String getRemarks() {
		return this.cd.getRemarks();
	}

	public boolean isChecked() {
		return this.cd.isChecked();
	}
	/**
	 * To prevent going in circles, only Artist id's are returned
	 * @return the Artist id's
	 */
	//	public List<Long> getArtistsById() {
	//		List<Long> artistIds = new ArrayList<>();
	//		for (int i=0 ; i<this.cd.getArtists().size() ; i++) {
	//			artistIds.add(this.cd.getArtists().get(i).getId());
	//		}
	//		return artistIds;
	//	}

	/**
	 * We geven alleen de id's van de Artist terug.<br>
	 * De Artist zelf kunnen dan via de API van Artist worden opgevraagd.  
	 * @return Id's van artists behorend bij de CD.
	 */
	public List<Long> getArtistById() {
		List<Long> artistIds = new ArrayList<>();
		for (Artist artist : this.cd.getArtists()) {
			artistIds.add(artist.getId());
		}
		return artistIds;
	}
	public List<Artist> getArtists() {
		return this.cd.getArtists();
	}


	/**
	 * To prevent going in circles, only Genre id's are returned
	 * @return the Genre id's
	 */
	//	public List<Long> getGenresById() {
	//		List<Long> genreIds = new ArrayList<>();
	//		for (int i=0 ; i<this.cd.getGenres().size() ; i++) {
	//			genreIds.add(this.cd.getGenres().get(i).getId());
	//		}
	//		return genreIds;
	//	}

	/**
	 * We geven alleen de id's van de Genres terug.<br>
	 * De Genres zelf kunnen dan via de API van Genre worden opgevraagd.  
	 * @return Id's van genres behorend bij de CD.
	 */
	public List<Long> getGenresById() {
		List<Long> genreIds = new ArrayList<>();
		for (Genre genre : this.cd.getGenres()) {
			genreIds.add(genre.getId());
		}
		return genreIds;
	}
	public List<Genre> getGenres() {
		return this.cd.getGenres();
	}



}
