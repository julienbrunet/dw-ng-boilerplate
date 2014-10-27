package fr.jbrunet.app.awesome.resources;

import com.codahale.metrics.annotation.Timed;
import fr.jbrunet.app.awesome.db.FooDAO;
import fr.jbrunet.app.awesome.pojos.Foo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Created by Julien BRUNET on 27/10/2014.
 */
@Path("/foo")
@Produces(MediaType.APPLICATION_JSON)
public class FooResource {

    private final FooDAO fooDAO;

    public FooResource(FooDAO fooDAO) {
        this.fooDAO = fooDAO;
    }

    @GET
    @Timed
    public List<Foo> search(@QueryParam("id") String id,
                         @QueryParam("label") String label,
                         @QueryParam("description") String description,
                         @QueryParam("date") Date date,
                         @QueryParam("bool") Boolean bool,
                         @QueryParam("offset") int offset,
                         @QueryParam("limit") int limit) {
        //TODO
        return null;
    }
}
