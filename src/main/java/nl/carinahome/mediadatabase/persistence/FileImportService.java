package nl.carinahome.mediadatabase.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.carinahome.mediadatabase.domain.FileImport;

@Service
@Transactional
public class FileImportService {
	@Autowired
	private FileImportRepository fileImportRepository;

	public FileImport save(FileImport fileImport){
		return fileImportRepository.save(fileImport);
	}

	public FileImport findById(Long id) {
		return fileImportRepository.findOne(id);
	}
	
	public Iterable <FileImport> findAll(){
		Iterable <FileImport> result = fileImportRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		fileImportRepository.delete(id);
	}
	
}
