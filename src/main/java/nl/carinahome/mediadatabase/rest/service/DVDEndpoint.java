package nl.carinahome.mediadatabase.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.domain.DVD;
import nl.carinahome.mediadatabase.domain.model.DVDModelBasic;
import nl.carinahome.mediadatabase.persistence.DVDService;

@Path("dvd")
@Component
public class DVDEndpoint {
	@Autowired
	private DVDService dvdService;
	
	/**
	 * Creates a new dvd
	 * @param dvd the new DVD to be added to the database
	 * @return Code 202 (accepted) with the new dvd id
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postDVD(DVD dvd){
		Long newId = dvdService.newDVD(dvd);
		return Response.accepted(newId).build();
	}
	
	/**
	 * Gets an existing dvd
	 * @param id the id of the DVD to be fetched
	 * @return Code 200 (ok) with the DVD or 204 (no content) if the dvd does not exist
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getDVDById(@PathParam("id") Long id ) {
		DVD dvd = this.dvdService.findById(id);
		if (dvd == null) {
			return Response.noContent().build();
		} else {
			DVD l2 = this.dvdService.findById(id);
			System.out.println(dvd.equals(l2));
			DVDModelBasic result = new DVDModelBasic(dvd);
			return Response.ok(result).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listDVD(){
		List<DVDModelBasic> result = new ArrayList<>();
		List<DVD> dvds = new ArrayList<>();
		dvds = (List<DVD>) dvdService.findAll();
		for (int i=0 ; i<dvds.size() ; i++ ) {
			DVDModelBasic dmb = new DVDModelBasic(dvds.get(i));
			result.add(dmb);
		}
		return Response.ok(result).build();
	}
		
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteDVDById(@PathParam("id") Long id){
		DVD dvd = this.dvdService.findById(id);
		if (dvd == null) {
			return Response.noContent().build();
		}
		dvd.removeAllActors();
		dvd.removeAllGenres();
		this.dvdService.deleteById(id);
		return Response.accepted().build();
	}

	/**
	 * FaerieRose: je bewaart bij deze PUT de dvd die je binnen krijgt. Mocht deze dvd inderdaad al bestaan
	 *   en lege velden hebben dan worden alle bestaande gegevens overschreven. Ik zal de Endpoint zo aanpassen
	 *   dat de acteurs en genres bewaard blijven
	 * @param dvd
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putDVD(DVD dvd) {
		long id = dvd.getId();
		if (id == 0) {
			return Response.accepted().build();
		}
		DVD result = this.dvdService.findById(id);
		result.dvdCopy(dvd);
		this.dvdService.save(result);
		return Response.accepted(result).build();
	}
	
	/**
	 * Methode om een actor aan een DVD toe te voegen
	 * @param id de id van de DVD
	 * @param actor_id de id van de Actor die toegevoegd moet worden
	 * @return true als de Actor is toegevoegd, anders false
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/actor/{actor_id}")
	public Response addActorToDVD(@PathParam("id") Long id, @PathParam("actor_id") Long actor_id) {
		return Response.accepted(this.dvdService.addActorToDVD(id, actor_id)).build();
	}
	
	/**
	 * Methode om een genre aan een DVD toe te voegen
	 * @param id de id van de DVD
	 * @param genre_id de id van de Genre die toegevoegd moet worden
	 * @return true als de Genre is toegevoegd, anders false
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/genre/{genre_id}")
	public Response addGenreToDVD(@PathParam("id") Long id, @PathParam("genre_id") Long genre_id) {
		return Response.accepted(this.dvdService.addGenreToDVD(id, genre_id)).build();
	}
}
//	/**
//	 * Gets all dvds in DB
//	 * @return Code 200 (ok) with the Student or 204 (no content) if there are no students in DB
//	 */	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getDVDs() {
//		Iterable<DVD> result = this.dvdService.findAll();
//		if (result == null) {
//			return Response.noContent().build();
//		} else {
//			return Response.ok(result).build();
//		}
//	}
//	
//}
