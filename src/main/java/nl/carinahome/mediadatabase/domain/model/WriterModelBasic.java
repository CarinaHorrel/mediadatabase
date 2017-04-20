package nl.carinahome.mediadatabase.domain.model;

import nl.carinahome.mediadatabase.domain.Writer;

public class WriterModelBasic {
	private Writer writer;
	
	public WriterModelBasic(Writer writer) {
		this.writer = writer;
	}
	
	public long getId() {
		return this.writer.getId();
	}
	
	public String getFirstName() {
		return this.writer.getFirstName();
	}
	
	public String getLastName() {
		return this.writer.getLastName();
	}
}


