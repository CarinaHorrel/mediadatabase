package nl.carinahome.mediadatabase.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import nl.carinahome.mediadatabase.rest.service.ActorEndpoint;
import nl.carinahome.mediadatabase.rest.service.ArtistEndpoint;
import nl.carinahome.mediadatabase.rest.service.CDEndpoint;
import nl.carinahome.mediadatabase.rest.service.DVDEndpoint;
import nl.carinahome.mediadatabase.rest.service.GenreEndpoint;


//import nl.carinahome.mediadatabase.rest.service.FileImportEndpoint;

/**
 * 
 * @author WCHorrel
 *
 */
@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig(){
		register(DVDEndpoint.class);
		register(GenreEndpoint.class);
		register(ActorEndpoint.class);
		register(CDEndpoint.class);
		register(ArtistEndpoint.class);
		//register(FileImportEndpoint.class);
	}
}
