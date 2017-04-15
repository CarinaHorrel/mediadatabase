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

import nl.carinahome.mediadatabase.domain.Actor;
import nl.carinahome.mediadatabase.domain.DVD;
import nl.carinahome.mediadatabase.domain.model.ActorModelBasic;

import nl.carinahome.mediadatabase.persistence.ActorService;
import nl.carinahome.mediadatabase.persistence.DVDService;

@Path("actor")
@Component
public class ActorEndpoint {
	@Autowired
	private ActorService actorService;

	@Autowired
	private DVDService dvdService;
	
	/**
	 * Creates a new actor
	 * @param actor the new Actor to be added to the database
	 * @return Code 202 (accepted) with the new actor id
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postActor(Actor actor){
		Long newId = actorService.newActor(actor);
		return Response.accepted(newId).build();
	}
	
	/**
	 * Gets an existing actor
	 * @param id the id of the Actor to be fetched
	 * @return Code 200 (ok) with the Actor or 204 (no content) if the actor does not exist
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getActorById(@PathParam("id") Long id ) {
		Actor actor = this.actorService.findById(id);
		if (actor == null) {
			return Response.noContent().build();
		} else {
			Actor l2 = this.actorService.findById(id);
			System.out.println(actor.equals(l2));
			ActorModelBasic result = new ActorModelBasic(actor);
			return Response.ok(result).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listActor(){
		List<ActorModelBasic> result = new ArrayList<>();
		List<Actor> actors = new ArrayList<>();
		actors = (List<Actor>) actorService.findAll();
		for (int i=0 ; i<actors.size() ; i++ ) {
			ActorModelBasic dmb = new ActorModelBasic(actors.get(i));
			result.add(dmb);
		}
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteActorById(@PathParam("id") Long id){
		Actor actor = this.actorService.findById(id);
		if (actor == null) {
			return Response.noContent().build();
		}
		List<DVD> dvds = new ArrayList<>();
		dvds = (List<DVD>) dvdService.findAll();
		for (int i=0 ; i<dvds.size() ; i++ ) {
			DVD dvd = dvds.get(i);
			if (dvd.removeOneActor(actor)) {
				this.dvdService.save(dvd);
			}
		}
		this.actorService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putActor(Actor actor) {
		//System.out.println(actor.getId());
		Actor result = this.actorService.save(actor);
		return Response.accepted(result).build();
	}
	
}
