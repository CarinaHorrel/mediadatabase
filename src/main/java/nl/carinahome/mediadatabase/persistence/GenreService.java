package nl.carinahome.mediadatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.carinahome.mediadatabase.domain.Genre;

@Service
@Transactional
public class GenreService {
	@Autowired
	private GenreRepository genreRepository;

	public Genre save(Genre genre){
		System.out.println("Genre id "+ genre.getId());
		return genreRepository.save(genre);
	}

	public Genre findById(Long id) {
		return genreRepository.findOne(id);
	}
	
	public Iterable <Genre> findAll(){
		Iterable <Genre> result = genreRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		genreRepository.delete(id);
	}
	/**
	 * Maak een nieuwe genre aan in de database. Er worden wat checks uitgevoerd om database foutmeldingen te voorkomen.
	 * @param genre De id van de nieuwe genre
	 * @return de nieuwe id of anders <ul>
	 * <li>-1 als de genre een id heeft
	 * <li>-2 als genreName gelijk is aan null
	 * <li>-3 als de genre al bestaat
	 * </ul>
	 */
	public long newGenre(Genre genre) {
		if (genre.getId() != 0) {
			return -1;
		} else if (genre.getGenreName() == null) {
			return -2;
		} else {
			List<Genre> genres = new ArrayList<>();
			genres = (List<Genre>) this.genreRepository.findAll();
			for (int i=0 ; i<genres.size() ; i++) {
				if (genres.get(i).getGenreName().equals(genre.getGenreName())) {
					return -3;
				}
			}
		}
		Genre result = this.genreRepository.save(genre);
		return result.getId();
	}
	
}
