package nl.carinahome.mediadatabase.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.carinahome.mediadatabase.domain.Tekst;

@Service
@Transactional
public class TekstService {

	@Autowired
	private TekstRepository tekstRepository;
	
	public Tekst save(Tekst tekst){
		return tekstRepository.save(tekst);
	}

	public Tekst findById(Long id) {
		return tekstRepository.findOne(id);
	}
	
	public Iterable <Tekst> findAll(){
		Iterable <Tekst> result = tekstRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		tekstRepository.delete(id);
	}
	
}
