package nl.carinahome.mediadatabase.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.domain.FileImport;

@Component
public interface FileImportRepository extends CrudRepository <FileImport, Long>{

}
