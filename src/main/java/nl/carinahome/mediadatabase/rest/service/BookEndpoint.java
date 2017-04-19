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

import nl.carinahome.mediadatabase.domain.Book;
import nl.carinahome.mediadatabase.domain.model.BookModelBasic;
import nl.carinahome.mediadatabase.persistence.BookService;

@Path("book")
@Component
public class BookEndpoint {
	@Autowired
	private BookService bookService;
	
	/**
	 * Creates a new Book
	 * @param dvd the new Book to be added to the database
	 * @return Code 202 (accepted) with the new dvd id
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postBook(Book book){
		Long newId = bookService.newBook(book);
		return Response.accepted(newId).build();
	}
	
	/**
	 * Gets an existing book
	 * @param id the id of the Book to be fetched
	 * @return Code 200 (ok) with the Book or 204 (no content) if the book does not exist
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getBookById(@PathParam("id") Long id ) {
		Book book = this.bookService.findById(id);
		if (book == null) {
			return Response.noContent().build();
		} else {
			Book l2 = this.bookService.findById(id);
			System.out.println(book.equals(l2));
			BookModelBasic result = new BookModelBasic(book);
			return Response.ok(result).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listBook(){
		List<BookModelBasic> result = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		books = (List<Book>) bookService.findAll();
		for (int i=0 ; i<books.size() ; i++ ) {
			BookModelBasic dmb = new BookModelBasic(books.get(i));
			result.add(dmb);
		}
		return Response.ok(result).build();
	}
		
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteBookById(@PathParam("id") Long id){
		Book book = this.bookService.findById(id);
		if (book == null) {
			return Response.noContent().build();
		}
		book.removeAllWriters();
		book.removeAllGenres();
		this.bookService.deleteById(id);
		return Response.accepted().build();
	}

	/**
	 * FaerieRose: je bewaart bij deze PUT de book die je binnen krijgt. Mocht book inderdaad al bestaan
	 *   en lege velden hebben dan worden alle bestaande gegevens overschreven. Ik zal de Endpoint zo aanpassen
	 *   dat de writers en genres bewaard blijven
	 * @param book
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putBook(Book book) {
		long id = book.getId();
		if (id == 0) {
			return Response.accepted().build();
		}
		Book result = this.bookService.findById(id);
		result.bookCopy(book);
		this.bookService.save(result);
		return Response.accepted(result).build();
	}
	
	/**
	 * Methode om een writer aan een Book toe te voegen
	 * @param id de id van de Book
	 * @param writer de id van de Writer die toegevoegd moet worden
	 * @return true als de Writer is toegevoegd, anders false
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/writer/{writer_id}")
	public Response addWriterToBook(@PathParam("id") Long id, @PathParam("writer_id") Long writer_id) {
		return Response.accepted(this.bookService.addWriterToBook(id, writer_id)).build();
	}
	
	/**
	 * Methode om een genre aan een Book toe te voegen
	 * @param id de id van de Book
	 * @param genre_id de id van de Genre die toegevoegd moet worden
	 * @return true als de Genre is toegevoegd, anders false
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/genre/{genre_id}")
	public Response addGenreToBook(@PathParam("id") Long id, @PathParam("genre_id") Long genre_id) {
		return Response.accepted(this.bookService.addGenreToBook(id, genre_id)).build();
	}
}
//	/**
//	 * Gets all books in DB
//	 * @return Code 200 (ok) with the Student or 204 (no content) if there are no students in DB
//	 */	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getBooks() {
//		Iterable<Book> result = this.bookService.findAll();
//		if (result == null) {
//			return Response.noContent().build();
//		} else {
//			return Response.ok(result).build();
//		}
//	}
//	
//}
