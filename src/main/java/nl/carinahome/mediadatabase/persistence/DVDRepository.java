package nl.carinahome.mediadatabase.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.domain.DVD;

/**
 * 
 * @author WCHorrel
 *
 */
@Component
public interface DVDRepository extends CrudRepository <DVD, Long>{
	
	List<DVD> findByTitle(String title);
	List<DVD> findByYear(int year);

}
