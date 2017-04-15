package nl.carinahome.mediadatabase.rest.service;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

public class FileImportEndpoint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Lob
	@Column(name = "blob_csv", length = 17777215)
	private String bestand;
	
}
