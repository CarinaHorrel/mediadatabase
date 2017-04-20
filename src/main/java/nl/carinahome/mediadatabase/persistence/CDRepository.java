package nl.carinahome.mediadatabase.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.domain.CD;

/**
 * 
 * @author WCHorrel
 *
 */
@Component
public interface CDRepository extends CrudRepository <CD, Long>{
	
	List<CD> findByTitle(String title);
	List<CD> findByYear(int year);

}

