package nl.carinahome.mediadatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.carinahome.mediadatabase.domain.Artist;
import nl.carinahome.mediadatabase.domain.CD;
import nl.carinahome.mediadatabase.domain.Genre;

@Service
@Transactional
public class CDService {
	@Autowired
	private CDRepository cdRepository;

	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private GenreService genreService;
	
	public CD save(CD cd){
		//System.out.println("CD id "+ cd.getId());
		return cdRepository.save(cd);
	}

	public CD findById(Long id) {
		return cdRepository.findOne(id);
	}
	
	public Iterable <CD> findAll(){
		Iterable <CD> result = cdRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		cdRepository.delete(id);
	}	
	
	/**
	 * Maak een nieuwe cd aan in de database. Er worden wat checks uitgevoerd om database foutmeldingen te voorkomen.
	 * @param cd De id van de nieuwe cd
	 * @return de nieuwe id of anders <ul>
	 * <li>-1 als de cd een id heeft
	 * <li>-2 als title gelijk is aan null
	 * <li>-3 als de title en year al bestaat
	 * </ul>
	 */
	public long newCD(CD cd) {
		if (cd.getId() != 0) {
			return -1;
		} else if (cd.getTitle() == null) {
			return -2;
		} else {
			List<CD> cds = new ArrayList<>();
			cds = (List<CD>) this.cdRepository.findAll();
			for (int i=0 ; i<cds.size() ; i++) {	
				
				if (cds.get(i).getTitle().equals(cd.getTitle()) & cds.get(i).getYear()==(cd.getYear())) {
					System.out.println("CD id "+ cd.getId()+" CD year "+ cd.getYear());
					return -3;
				}
			}
		}
		//System.out.println(dvd);
		
		CD result = this.cdRepository.save(cd);
		return result.getId();
	}
	
	/**
	 * Methode om een artist aan een CD toe te voegen
	 * @param id de id van de CD
	 * @param atist_id de id van de Artist die toegevoegd moet worden
	 * @return true als de Artist is toegevoegd, anders false
	 */
	public boolean addArtistToCD(long id, long artist_id) {
		CD cd = this.findById(id);
		Artist artist = this.artistService.findById(artist_id);
		if (cd == null || artist == null) {
			return false;
		} else {
			for (int i=0 ; i<cd.getArtists().size() ; i++) {
				if (cd.getArtists().get(i).getId() == artist_id) {
					return false;
				}
			}
			cd.addArtist(artist);
			this.cdRepository.save(cd);
			return true;
		}
		
	}
	
	/**
	 * Methode om een genre aan een CD toe te voegen
	 * @param id de id van de CD
	 * @param genre_id de id van de Genre die toegevoegd moet worden
	 * @return true als de Genre is toegevoegd, anders false
	 */
	public boolean addGenreToCD(long id, long genre_id) {
		CD cd = this.findById(id);
		Genre genre = this.genreService.findById(genre_id);
		if (cd == null || genre == null) {
			return false;
		} else {
			for (int i=0 ; i<cd.getGenres().size() ; i++) {
				if (cd.getGenres().get(i).getId() == genre_id) {
					return false;
				}
			}
			cd.addGenre(genre);
			this.cdRepository.save(cd);
			return true;
		}
		
	}
	
	public List<CD> jojo(String title){
		return cdRepository.findByTitle(title);
	}
	
	
}