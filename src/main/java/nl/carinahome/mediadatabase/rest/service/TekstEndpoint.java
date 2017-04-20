package nl.carinahome.mediadatabase.rest.service;

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

import nl.carinahome.mediadatabase.domain.Tekst;
import nl.carinahome.mediadatabase.persistence.TekstService;

/**
 * Voor de aanvraag van verzoeken
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-06
 */
@Path("verzoek")
@Component
public class TekstEndpoint {

	@Autowired
	private TekstService tekstService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postTekst(Tekst tekst){
		System.out.println("pre@POST: (" + tekst.getId() + ") " + tekst.getHttpMethod() + " " + tekst.getUrl());
		Tekst result = tekstService.save(tekst);
		System.out.println("@POST: (" + result.getId() + ") " + tekst.getHttpMethod() + " " + tekst.getUrl());
		return Response.accepted(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getTekstById(@PathParam("id") Long id ) {
		System.out.println("@GET: (" + id + ")");
		Tekst result = this.tekstService.findById(id);
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listTekst(){
		System.out.println("@GET: Got the list!");
		Iterable <Tekst> result = tekstService.findAll();
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteTekstById(@PathParam("id") Long id){
		System.out.println("@DELETE: (" + id+ ")");
		this.tekstService.deleteById(id);
		return Response.accepted(id).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putTekst(Tekst tekst) {
		System.out.println("pre@PUT: (" + tekst.getId() + ") " + tekst.getHttpMethod() + " " + tekst.getUrl());
		this.tekstService.save(tekst);
		Tekst result = tekstService.save(tekst);
		System.out.println("@PUT: (" + result.getId() + ") " + tekst.getHttpMethod() + " " + tekst.getUrl());
		return Response.accepted(result).build();
	}
}
