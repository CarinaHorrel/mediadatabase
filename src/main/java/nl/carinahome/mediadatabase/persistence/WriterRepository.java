package nl.carinahome.mediadatabase.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.domain.Writer;

@Component
public interface WriterRepository extends CrudRepository <Writer, Long>{

}