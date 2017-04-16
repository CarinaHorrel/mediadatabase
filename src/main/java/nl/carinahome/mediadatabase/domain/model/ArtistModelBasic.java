package nl.carinahome.mediadatabase.domain.model;

import nl.carinahome.mediadatabase.domain.Artist;

public class ArtistModelBasic {
	private Artist artist;
	
	public ArtistModelBasic(Artist artist) {
		this.artist = artist;
	}
	
	public long getId() {
		return this.artist.getId();
	}
	
	public String getArtistName() {
		return this.artist.getArtistName();
	}
}

