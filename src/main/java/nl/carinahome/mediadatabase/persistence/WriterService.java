package nl.carinahome.mediadatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.carinahome.mediadatabase.domain.Writer;

@Service
@Transactional
public class WriterService {
	@Autowired
	private WriterRepository writerRepository;

	public Writer save(Writer writer){
		System.out.println("Writer id "+ writer.getId());
		return writerRepository.save(writer);
	}

	public Writer findById(Long id) {
		return writerRepository.findOne(id);
	}
	
	public Iterable <Writer> findAll(){
		Iterable <Writer> result = writerRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		writerRepository.delete(id);
	}
	
	/**
	 * Maak een nieuwe writer aan in de database. Er worden wat checks uitgevoerd om database foutmeldingen te voorkomen.
	 * @param writer De id van de nieuwe writer
	 * @return de nieuwe id of anders <ul>
	 * <li>-1 als de writer een id heeft
	 * <li>-2 als firstname and lastname gelijk is aan null
	 * <li>-3 als de writer al bestaat
	 * </ul>
	 */
	public long newWriter(Writer writer) {
		System.out.println(writer);
		if (writer.getId() != 0) {
			return -1;
		} else if (writer.getFirstName() == null || writer.getLastName() == null) {
			return -2;
		} else {
			//System.out.println(actor);
			List<Writer> writers = new ArrayList<>();
			writers = (List<Writer>) this.writerRepository.findAll();
			System.out.println("Size=" + writers.size());
			for (int i=0 ; i<writers.size() ; i++) {
				//System.out.println(writer + "   " + i);

				if (writers.get(i).getFirstName().equals(writer.getFirstName()) & writers.get(i).getLastName().equals(writer.getLastName())   ) {
					return -3;
				}
			}
		}
		System.out.println(writer);

		Writer result = this.writerRepository.save(writer);
		return result.getId();
	}
	
}
