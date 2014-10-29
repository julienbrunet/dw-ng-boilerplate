package fr.jbrunet.app.awesome.resource;

import fr.jbrunet.app.awesome.db.FooDAO;
import fr.jbrunet.app.awesome.pojos.Foo;
import fr.jbrunet.app.awesome.resources.FooResource;
import io.dropwizard.testing.junit.DropwizardClientRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Julien BRUNET on 29/10/2014.
 */
public class FooResourceTest {

    static final private DBI dbi = new DBI("jdbc:h2:mem:" + UUID.randomUUID());
    static final private FooDAO dao = dbi.open(FooDAO.class);

    @ClassRule
    public final static DropwizardClientRule dropwizard = new DropwizardClientRule(new FooResource(dao));

    @Before
    public void setup() {
        dao.createTable();
    }

    @Test
    public void testGetFoo() throws IOException {
        Long id1 = dao.create(new Foo(null, "foo1", "description", new Date(), false));

        final URL url = new URL(dropwizard.baseUri() + "/foo");
        final String response = new BufferedReader(new InputStreamReader(url.openStream())).readLine();
        Assert.assertTrue(response.contains("foo1"));
    }

}
