package fr.jbrunet.app.dw.ng.starterkit.resources;

import com.codahale.metrics.annotation.Timed;
import fr.jbrunet.app.dw.ng.starterkit.db.FooDAO;
import fr.jbrunet.app.dw.ng.starterkit.pojos.Foo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Iterator;
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
    public List<Foo> search(
            @QueryParam("id") Long id,
            @QueryParam("label") String label,
            @QueryParam("description") String description,
            @QueryParam("date") Date date,
            @QueryParam("bool") Boolean bool,
            @QueryParam("orderBy") List<String> orderBy,
            @QueryParam("orderDirection") String orderDirection,
            @QueryParam("page") Integer page,
            @QueryParam("pageSize") Integer pageSize) {

        boolean desc = false;
        if(orderDirection!=null&&orderDirection.equals("DESC")) desc = true;

        String[] order = null;
        if(orderBy!=null && orderBy.size()>0) {
            order = new String[]{};
            int i=0;
            for (Iterator<String> iterator = orderBy.iterator(); iterator.hasNext(); ) {
                String next = iterator.next();
                order[i]= next;
                i++;
            }
        }

        int limit = -1;
        if(pageSize!=null) limit = pageSize.intValue();

        int offset = 0;
        if(page!=null) {
            if(limit > 0) offset = page.intValue()*limit;
            else offset = page.intValue();
        }

        //Add a wait to simulate long loading time for the client
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return fooDAO.search(id, label, description, date, bool, offset, limit, order, new Boolean(desc));
    }
}
