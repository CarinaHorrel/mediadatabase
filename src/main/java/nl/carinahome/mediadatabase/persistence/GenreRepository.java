package nl.carinahome.mediadatabase.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.domain.Genre;

@Component
public interface GenreRepository extends CrudRepository <Genre, Long>{

}
