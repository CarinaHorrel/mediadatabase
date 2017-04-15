package nl.carinahome.mediadatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.carinahome.mediadatabase.domain.Actor;
import nl.carinahome.mediadatabase.domain.DVD;
import nl.carinahome.mediadatabase.domain.Genre;

@Service
@Transactional
public class DVDService {
	@Autowired
	private DVDRepository dvdRepository;

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private GenreService genreService;
	
	public DVD save(DVD dvd){
		//System.out.println("DVD id "+ dvd.getId());
		return dvdRepository.save(dvd);
	}

	public DVD findById(Long id) {
		return dvdRepository.findOne(id);
	}
	
	public Iterable <DVD> findAll(){
		Iterable <DVD> result = dvdRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		dvdRepository.delete(id);
	}	
	
	/**
	 * Maak een nieuwe dvd aan in de database. Er worden wat checks uitgevoerd om database foutmeldingen te voorkomen.
	 * @param dvd De id van de nieuwe dvd
	 * @return de nieuwe id of anders <ul>
	 * <li>-1 als de dvd een id heeft
	 * <li>-2 als title gelijk is aan null
	 * <li>-3 als de title al bestaat
	 * </ul>
	 */
	public long newDVD(DVD dvd) {
		if (dvd.getId() != 0) {
			return -1;
		} else if (dvd.getTitle() == null) {
			return -2;
		} else {
			List<DVD> dvds = new ArrayList<>();
			dvds = (List<DVD>) this.dvdRepository.findAll();
			for (int i=0 ; i<dvds.size() ; i++) {	
				
				if (dvds.get(i).getTitle().equals(dvd.getTitle()) & dvds.get(i).getYear()==(dvd.getYear())) {
					System.out.println("DVD id "+ dvd.getId()+" DVD year "+ dvd.getYear());
					return -3;
				}
			}
		}
		//System.out.println(dvd);
		
		DVD result = this.dvdRepository.save(dvd);
		return result.getId();
	}
	
	/**
	 * Methode om een actor aan een DVD toe te voegen
	 * @param id de id van de DVD
	 * @param actor_id de id van de Actor die toegevoegd moet worden
	 * @return true als de Actor is toegevoegd, anders false
	 */
	public boolean addActorToDVD(long id, long actor_id) {
		DVD dvd = this.findById(id);
		Actor actor = this.actorService.findById(actor_id);
		if (dvd == null || actor == null) {
			return false;
		} else {
			for (int i=0 ; i<dvd.getActors().size() ; i++) {
				if (dvd.getActors().get(i).getId() == actor_id) {
					return false;
				}
			}
			dvd.addActor(actor);
			this.dvdRepository.save(dvd);
			return true;
		}
		
	}
	
	/**
	 * Methode om een genre aan een DVD toe te voegen
	 * @param id de id van de DVD
	 * @param genre_id de id van de Genre die toegevoegd moet worden
	 * @return true als de Genre is toegevoegd, anders false
	 */
	public boolean addGenreToDVD(long id, long genre_id) {
		DVD dvd = this.findById(id);
		Genre genre = this.genreService.findById(genre_id);
		if (dvd == null || genre == null) {
			return false;
		} else {
			for (int i=0 ; i<dvd.getGenres().size() ; i++) {
				if (dvd.getGenres().get(i).getId() == genre_id) {
					return false;
				}
			}
			dvd.addGenre(genre);
			this.dvdRepository.save(dvd);
			return true;
		}
		
	}
	
//	public List<DVD> jojo(String title){
//		return dvdRepository.findByTitle(title);
//	}
	
	
}