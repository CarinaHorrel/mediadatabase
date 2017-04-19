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

import nl.carinahome.mediadatabase.domain.Writer;
import nl.carinahome.mediadatabase.domain.Book;
import nl.carinahome.mediadatabase.domain.model.WriterModelBasic;

import nl.carinahome.mediadatabase.persistence.WriterService;
import nl.carinahome.mediadatabase.persistence.BookService;

@Path("writer")
@Component
public class WriterEndpoint {
	@Autowired
	private WriterService writerService;

	@Autowired
	private BookService bookService;
	
	/**
	 * Creates a new writer
	 * @param actor the new Writer to be added to the database
	 * @return Code 202 (accepted) with the new writer id
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postWriter(Writer writer){
		Long newId = writerService.newWriter(writer);
		return Response.accepted(newId).build();
	}
	
	/**
	 * Gets an existing writer
	 * @param id the id of the Writer to be fetched
	 * @return Code 200 (ok) with the Writer or 204 (no content) if the actor does not exist
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getWriterById(@PathParam("id") Long id ) {
		Writer writer = this.writerService.findById(id);
		if (writer == null) {
			return Response.noContent().build();
		} else {
			Writer l2 = this.writerService.findById(id);
			System.out.println(writer.equals(l2));
			WriterModelBasic result = new WriterModelBasic(writer);
			return Response.ok(result).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listWriter(){
		List<WriterModelBasic> result = new ArrayList<>();
		List<Writer> writers = new ArrayList<>();
		writers = (List<Writer>) writerService.findAll();
		for (int i=0 ; i<writers.size() ; i++ ) {
			WriterModelBasic dmb = new WriterModelBasic(writers.get(i));
			result.add(dmb);
		}
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteWriterById(@PathParam("id") Long id){
		Writer writer = this.writerService.findById(id);
		if (writer == null) {
			return Response.noContent().build();
		}
		List<Book> books = new ArrayList<>();
		books = (List<Book>) bookService.findAll();
		for (int i=0 ; i<books.size() ; i++ ) {
			Book book = books.get(i);
			if (book.removeOneWriter(writer)) {
				this.bookService.save(book);
			}
		}
		this.writerService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putWriter(Writer writer) {
		//System.out.println(writer.getId());
		Writer result = this.writerService.save(writer);
		return Response.accepted(result).build();
	}
	
}
