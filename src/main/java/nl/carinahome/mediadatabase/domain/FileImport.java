package nl.carinahome.mediadatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class FileImport {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@Lob
	@Column(name = "blob_csv", length = 17777215)
	String bestand;

}
