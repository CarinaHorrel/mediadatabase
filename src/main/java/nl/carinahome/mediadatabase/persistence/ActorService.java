package nl.carinahome.mediadatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.carinahome.mediadatabase.domain.Actor;

@Service
@Transactional
public class ActorService {
	@Autowired
	private ActorRepository actorRepository;

	public Actor save(Actor actor){
		System.out.println("Actor id "+ actor.getId());
		return actorRepository.save(actor);
	}

	public Actor findById(Long id) {
		return actorRepository.findOne(id);
	}
	
	public Iterable <Actor> findAll(){
		Iterable <Actor> result = actorRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		actorRepository.delete(id);
	}
	
	/**
	 * Maak een nieuwe actor aan in de database. Er worden wat checks uitgevoerd om database foutmeldingen te voorkomen.
	 * @param actor De id van de nieuwe actor
	 * @return de nieuwe id of anders <ul>
	 * <li>-1 als de actor een id heeft
	 * <li>-2 als firstname and lastname gelijk is aan null
	 * <li>-3 als de actor al bestaat
	 * </ul>
	 */
	public long newActor(Actor actor) {
		System.out.println(actor);
		if (actor.getId() != 0) {
			return -1;
		} else if (actor.getFirstName() == null || actor.getLastName() == null) {
			return -2;
		} else {
			//System.out.println(actor);
			List<Actor> actors = new ArrayList<>();
			actors = (List<Actor>) this.actorRepository.findAll();
			System.out.println("Size=" + actors.size());
			for (int i=0 ; i<actors.size() ; i++) {
				//System.out.println(actor + "   " + i);

				if (actors.get(i).getFirstName().equals(actor.getFirstName()) & actors.get(i).getLastName().equals(actor.getLastName())   ) {
					return -3;
				}
			}
		}
		System.out.println(actor);

		Actor result = this.actorRepository.save(actor);
		return result.getId();
	}
	
}
