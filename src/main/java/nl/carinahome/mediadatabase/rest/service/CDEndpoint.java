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

import nl.carinahome.mediadatabase.domain.CD;
import nl.carinahome.mediadatabase.domain.model.CDModelBasic;
import nl.carinahome.mediadatabase.persistence.CDService;

@Path("cd")
@Component
public class CDEndpoint {
	@Autowired
	private CDService cdService;
	
	/**
	 * Creates a new cd
	 * @param cd the new CD to be added to the database
	 * @return Code 202 (accepted) with the new cd id
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postCD(CD cd){
		Long newId = cdService.newCD(cd);
		return Response.accepted(newId).build();
	}
	
	/**
	 * Gets an existing cd
	 * @param id the id of the CD to be fetched
	 * @return Code 200 (ok) with the CD or 204 (no content) if the dvd does not exist
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getCDById(@PathParam("id") Long id ) {
		CD cd = this.cdService.findById(id);
		if (cd == null) {
			return Response.noContent().build();
		} else {
			CD l2 = this.cdService.findById(id);
			System.out.println(cd.equals(l2));
			CDModelBasic result = new CDModelBasic(cd);
			return Response.ok(result).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listCD(){
		List<CDModelBasic> result = new ArrayList<>();
		List<CD> cds = new ArrayList<>();
		cds = (List<CD>) cdService.findAll();
		for (int i=0 ; i<cds.size() ; i++ ) {
			CDModelBasic cmb = new CDModelBasic(cds.get(i));
			result.add(cmb);
		}
		return Response.ok(result).build();
	}
		
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteCDById(@PathParam("id") Long id){
		CD cd = this.cdService.findById(id);
		if (cd == null) {
			return Response.noContent().build();
		}
		cd.removeAllArtists();
		cd.removeAllGenres();
		this.cdService.deleteById(id);
		return Response.accepted().build();
	}

	/**
	 * FaerieRose: je bewaart bij deze PUT de cd die je binnen krijgt. Mocht deze cd inderdaad al bestaan
	 *   en lege velden hebben dan worden alle bestaande gegevens overschreven. Ik zal de Endpoint zo aanpassen
	 *   dat de acteurs en genres bewaard blijven
	 * @param dvd
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCD(CD cd) {
		long id = cd.getId();
		if (id == 0) {
			return Response.accepted().build();
		}
		CD result = this.cdService.findById(id);
		result.cdCopy(cd);
		this.cdService.save(result);
		return Response.accepted(result).build();
	}
	
	/**
	 * Methode om een artist aan een CD toe te voegen
	 * @param id de id van de CD
	 * @param actor_id de id van de Artist die toegevoegd moet worden
	 * @return true als de Artist is toegevoegd, anders false
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/artist/{artist_id}")
	public Response addArtistToCD(@PathParam("id") Long id, @PathParam("artist_id") Long artist_id) {
		return Response.accepted(this.cdService.addArtistToCD(id, artist_id)).build();
	}
	
	/**
	 * Methode om een genre aan een CD toe te voegen
	 * @param id de id van de CD
	 * @param genre_id de id van de Genre die toegevoegd moet worden
	 * @return true als de Genre is toegevoegd, anders false
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/genre/{genre_id}")
	public Response addGenreToCD(@PathParam("id") Long id, @PathParam("genre_id") Long genre_id) {
		return Response.accepted(this.cdService.addGenreToCD(id, genre_id)).build();
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
