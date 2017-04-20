package nl.carinahome.mediadatabase.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.domain.Book;

/**
 * 
 * @author WCHorrel
 *
 */
@Component
public interface BookRepository extends CrudRepository <Book, Long>{
	
	List<Book> findByTitle(String title);
	List<Book> findByYear(int year);

}
