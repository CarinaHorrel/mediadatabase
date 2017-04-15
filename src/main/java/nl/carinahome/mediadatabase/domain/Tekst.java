package nl.carinahome.mediadatabase.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Bestemd voor documentatie tijdens ontwikkeling
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-03-23
 */

@Entity
public class Tekst {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String aanvrager;
	private String httpMethod;
	private String url;
	private String meegeven;
	private String terug;
	private String omschrijving;
	private String status;
	
	public long getId() {
		return id;
	}

	public String getHttpMethod() {
		return httpMethod;
	}


	public String getAanvrager() {
		return aanvrager;
	}

	public void setAanvrager(String aanvrager) {
		this.aanvrager = aanvrager;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getMeegeven() {
		return meegeven;
	}


	public void setMeegeven(String meegeven) {
		this.meegeven = meegeven;
	}


	public String getTerug() {
		return terug;
	}


	public void setTerug(String terug) {
		this.terug = terug;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
