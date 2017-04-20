package nl.carinahome.mediadatabase.domain.model;

import java.util.ArrayList;
import java.util.List;

import nl.carinahome.mediadatabase.domain.Writer;
import nl.carinahome.mediadatabase.domain.Book;
import nl.carinahome.mediadatabase.domain.Genre;

public class BookModelBasic {
	private Book book;

	public BookModelBasic(Book book) {
		this.book = book;
	}

	public long getId() {
		return this.book.getId();
	}
	public String getTitle() {
		return this.book.getTitle();
	}
	public int getYear() {
		return this.book.getYear();
	}
	
	public long getIsbn() {
		return this.book.getIsbn();
	}
	public String getOrigin() {
		return this.book.getOrigin();
	}
	
	public String getRemarks() {
		return this.book.getRemarks();
	}

	public boolean isChecked() {
		return this.book.isChecked();
	}
	/**
	 * To prevent going in circles, only Writer id's are returned
	 * @return the Writer id's
	 */
	//	public List<Long> getWritersById() {
	//		List<Long> writerIds = new ArrayList<>();
	//		for (int i=0 ; i<this.book.getWriters().size() ; i++) {
	//			writerIds.add(this.book.getWriters().get(i).getId());
	//		}
	//		return writerIds;
	//	}

	/**
	 * We geven alleen de id's van de Writers terug.<br>
	 * De Writers zelf kunnen dan via de API van Writer worden opgevraagd.  
	 * @return Id's van writers behorend bij de Book.
	 */
	public List<Long> getWritersById() {
		List<Long> writerIds = new ArrayList<>();
		for (Writer writer : this.book.getWriters()) {
			writerIds.add(writer.getId());
		}
		return writerIds;
	}
	public List<Writer> getWriters() {
		return this.book.getWriters();
	}


	/**
	 * To prevent going in circles, only Genre id's are returned
	 * @return the Genre id's
	 */
	//	public List<Long> getGenresById() {
	//		List<Long> genreIds = new ArrayList<>();
	//		for (int i=0 ; i<this.book.getGenres().size() ; i++) {
	//			genreIds.add(this.book.getGenres().get(i).getId());
	//		}
	//		return genreIds;
	//	}

	/**
	 * We geven alleen de id's van de Genres terug.<br>
	 * De Genres zelf kunnen dan via de API van Genre worden opgevraagd.  
	 * @return Id's van genres behorend bij de Book.
	 */
	public List<Long> getGenresById() {
		List<Long> genreIds = new ArrayList<>();
		for (Genre genre : this.book.getGenres()) {
			genreIds.add(genre.getId());
		}
		return genreIds;
	}
	public List<Genre> getGenres() {
		return this.book.getGenres();
	}

}
