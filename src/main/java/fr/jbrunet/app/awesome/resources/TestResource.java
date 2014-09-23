package fr.jbrunet.app.awesome.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by Julien BRUNET on 23/09/2014.
 */
@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {
    @GET
    @Timed
    public String sayHello(@QueryParam("name") String name) {
        return "Hello" + name;
    }
}
