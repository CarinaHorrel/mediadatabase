package nl.carinahome.mediadatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.carinahome.mediadatabase.domain.Artist;

@Service
@Transactional
public class ArtistService {
	@Autowired
	private ArtistRepository artistRepository;

	public Artist save(Artist artist){
		System.out.println("Artist id "+ artist.getId());
		return artistRepository.save(artist);
	}

	public Artist findById(Long id) {
		return artistRepository.findOne(id);
	}
	
	public Iterable <Artist> findAll(){
		Iterable <Artist> result = artistRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		artistRepository.delete(id);
	}
	
	/**
	 * Maak een nieuwe artist aan in de database. Er worden wat checks uitgevoerd om database foutmeldingen te voorkomen.
	 * @param actor De id van de nieuwe artist
	 * @return de nieuwe id of anders <ul>
	 * <li>-1 als de artist een id heeft
	 * <li>-2 als artistname  is aan null
	 * <li>-3 als de artist al bestaat
	 * </ul>
	 */
	public long newArtist(Artist artist) {
		System.out.println(artist);
		if (artist.getId() != 0) {
			return -1;
		} else if (artist.getArtistName() == null) {
			return -2;
		} else {
			//System.out.println(artist);
			List<Artist> artists = new ArrayList<>();
			artists = (List<Artist>) this.artistRepository.findAll();
			System.out.println("Size=" + artists.size());
			for (int i=0 ; i<artists.size() ; i++) {
				//System.out.println(artist + "   " + i);

				if (artists.get(i).getArtistName().equals(artist.getArtistName())  ) {
					return -3;
				}
			}
		}
		System.out.println(artist);

		Artist result = this.artistRepository.save(artist);
		return result.getId();
	}
	
}
