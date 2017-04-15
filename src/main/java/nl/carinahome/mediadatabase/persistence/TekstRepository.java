package nl.carinahome.mediadatabase.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.domain.Tekst;

@Component
public interface TekstRepository extends CrudRepository <Tekst, Long>{

}
