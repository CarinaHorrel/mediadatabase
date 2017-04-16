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

import nl.carinahome.mediadatabase.domain.Artist;
import nl.carinahome.mediadatabase.domain.CD;
import nl.carinahome.mediadatabase.domain.model.ArtistModelBasic;

import nl.carinahome.mediadatabase.persistence.ArtistService;
import nl.carinahome.mediadatabase.persistence.CDService;

@Path("artist")
@Component
public class ArtistEndpoint {
	@Autowired
	private ArtistService artistService;

	@Autowired
	private CDService cdService;
	
	/**
	 * Creates a new artist
	 * @param artist the new Artist to be added to the database
	 * @return Code 202 (accepted) with the new artist id
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postArtist(Artist artist){
		Long newId = artistService.newArtist(artist);
		return Response.accepted(newId).build();
	}
	
	/**
	 * Gets an existing artist
	 * @param id the id of the Artist to be fetched
	 * @return Code 200 (ok) with the Artist or 204 (no content) if the artist does not exist
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getArtistById(@PathParam("id") Long id ) {
		Artist artist = this.artistService.findById(id);
		if (artist == null) {
			return Response.noContent().build();
		} else {
			Artist l2 = this.artistService.findById(id);
			System.out.println(artist.equals(l2));
			ArtistModelBasic result = new ArtistModelBasic(artist);
			return Response.ok(result).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listArtist(){
		List<ArtistModelBasic> result = new ArrayList<>();
		List<Artist> artists = new ArrayList<>();
		artists = (List<Artist>) artistService.findAll();
		for (int i=0 ; i<artists.size() ; i++ ) {
			ArtistModelBasic amb = new ArtistModelBasic(artists.get(i));
			result.add(amb);
		}
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteArtistrById(@PathParam("id") Long id){
		Artist artist = this.artistService.findById(id);
		if (artist == null) {
			return Response.noContent().build();
		}
		List<CD> cds = new ArrayList<>();
		cds = (List<CD>) cdService.findAll();
		for (int i=0 ; i<cds.size() ; i++ ) {
			CD cd = cds.get(i);
			if (cd.removeOneArtist(artist)) {
				this.cdService.save(cd);
			}
		}
		this.cdService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putArtist(Artist artist) {
		//System.out.println(artist.getId());
		Artist result = this.artistService.save(artist);
		return Response.accepted(result).build();
	}
	
}
