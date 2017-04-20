package nl.carinahome.mediadatabase.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.domain.Artist;

@Component
public interface ArtistRepository extends CrudRepository <Artist, Long>{

}