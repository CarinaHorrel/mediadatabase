package nl.carinahome.mediadatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.carinahome.mediadatabase.domain.Writer;
import nl.carinahome.mediadatabase.domain.Book;
import nl.carinahome.mediadatabase.domain.Genre;

@Service
@Transactional
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private WriterService writerService;
	
	@Autowired
	private GenreService genreService;
	
	public Book save(Book book){
		//System.out.println("Book id "+ book.getId());
		return bookRepository.save(book);
	}

	public Book findById(Long id) {
		return bookRepository.findOne(id);
	}
	
	public Iterable <Book> findAll(){
		Iterable <Book> result = bookRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		bookRepository.delete(id);
	}	
	
	/**
	 * Maak een nieuwe book aan in de database. Er worden wat checks uitgevoerd om database foutmeldingen te voorkomen.
	 * @param book De id van de nieuwe book
	 * @return de nieuwe id of anders <ul>
	 * <li>-1 als de book een id heeft
	 * <li>-2 als title gelijk is aan null
	 * <li>-3 als de title en year al bestaat
	 * </ul>
	 */
	public long newBook(Book book) {
		if (book.getId() != 0) {
			return -1;
		} else if (book.getTitle() == null) {
			return -2;
		} else {
			List<Book> books = new ArrayList<>();
			books = (List<Book>) this.bookRepository.findAll();
			for (int i=0 ; i<books.size() ; i++) {	
				
				if (books.get(i).getTitle().equals(book.getTitle()) & books.get(i).getYear()==(book.getYear())) {
					System.out.println("Book id "+ book.getId()+" Book year "+ book.getYear());
					return -3;
				}
			}
		}
		//System.out.println(book);
		
		Book result = this.bookRepository.save(book);
		return result.getId();
	}
	
	/**
	 * Methode om een actor aan een Book toe te voegen
	 * @param id de id van de Book
	 * @param actor_id de id van de Actor die toegevoegd moet worden
	 * @return true als de Actor is toegevoegd, anders false
	 */
	public boolean addWriterToBook(long id, long writer_id) {
		Book book = this.findById(id);
		Writer writer = this.writerService.findById(writer_id);
		if (book == null || writer == null) {
			return false;
		} else {
			for (int i=0 ; i<book.getWriters().size() ; i++) {
				if (book.getWriters().get(i).getId() == writer_id) {
					return false;
				}
			}
			book.addWriter(writer);
			this.bookRepository.save(book);
			return true;
		}
		
	}
	
	/**
	 * Methode om een genre aan een Book toe te voegen
	 * @param id de id van de Book
	 * @param genre_id de id van de Genre die toegevoegd moet worden
	 * @return true als de Genre is toegevoegd, anders false
	 */
	public boolean addGenreToBook(long id, long genre_id) {
		Book book = this.findById(id);
		Genre genre = this.genreService.findById(genre_id);
		if (book == null || genre == null) {
			return false;
		} else {
			for (int i=0 ; i<book.getGenres().size() ; i++) {
				if (book.getGenres().get(i).getId() == genre_id) {
					return false;
				}
			}
			book.addGenre(genre);
			this.bookRepository.save(book);
			return true;
		}
		
	}
	
	public List<Book> jojo(String title){
		return bookRepository.findByTitle(title);
	}
	
	
}