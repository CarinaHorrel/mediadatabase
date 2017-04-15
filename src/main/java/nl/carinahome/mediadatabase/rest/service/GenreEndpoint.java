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
import nl.carinahome.mediadatabase.domain.Genre;
import nl.carinahome.mediadatabase.domain.model.GenreModelBasic;
import nl.carinahome.mediadatabase.persistence.DVDService;
import nl.carinahome.mediadatabase.persistence.GenreService;

@Path("genre")
@Component
public class GenreEndpoint {
	@Autowired
	private GenreService genreService;
	
	/*Added by C.Horrel */
	@Autowired
	private DVDService dvdService;
	/**
	 * Creates a new genre
	 * @param actor the new Genre to be added to the database
	 * @return Code 202 (accepted) with the new genre id
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postGenre(Genre genre){
		Long newId = genreService.newGenre(genre);
		return Response.accepted(newId).build();
	}
	
	/**
	 * Gets an existing genre
	 * @param id the id of the Genre to be fetched
	 * @return Code 200 (ok) with the Genre or 204 (no content) if the genre does not exist
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getGenreById(@PathParam("id") Long id ) {
		Genre genre = this.genreService.findById(id);
		if (genre == null) {
			return Response.noContent().build();
		} else {
			Genre l2 = this.genreService.findById(id);
			System.out.println(genre.equals(l2));
			GenreModelBasic result = new GenreModelBasic(genre);
			return Response.ok(result).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGenre(){
		List<GenreModelBasic> result = new ArrayList<>();
		List<Genre> genres = new ArrayList<>();
		genres = (List<Genre>) genreService.findAll();
		for (int i=0 ; i<genres.size() ; i++ ) {
			GenreModelBasic dmb = new GenreModelBasic(genres.get(i));
			result.add(dmb);
		}
		return Response.ok(result).build();
	}
	/* replaced by below code
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteGenreById(@PathParam("id") Long id){
		this.genreService.deleteById(id);
		return Response.accepted().build();
	}
*/
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteGenreById(@PathParam("id") Long id){
		Genre genre = this.genreService.findById(id);
		if (genre == null) {
			return Response.noContent().build();
		}
		List<DVD> dvds = new ArrayList<>();
		dvds = (List<DVD>) dvdService.findAll();
		for (int i=0 ; i<dvds.size() ; i++ ) {
			DVD dvd = dvds.get(i);
			if (dvd.removeOneGenre(genre)) {
				this.dvdService.save(dvd);
			}
		}
		this.genreService.deleteById(id);
		return Response.accepted().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putGenre(Genre genre) {
		Genre result = this.genreService.save(genre);
		return Response.accepted(result).build();
	}
	
}
