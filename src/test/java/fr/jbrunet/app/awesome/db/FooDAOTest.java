package fr.jbrunet.app.awesome.db;

import fr.jbrunet.app.awesome.pojos.Foo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.logging.PrintStreamLog;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Julien BRUNET on 27/10/2014.
 */
public class FooDAOTest {

    private DBI dbi;
    private FooDAO foo;

    @Before
    public void setup() {
        //dbi = new DBI("jdbc:h2:./database" + UUID.randomUUID());
        dbi = new DBI("jdbc:h2:mem:" + UUID.randomUUID());
        dbi.setSQLLog(new PrintStreamLog(System.out));
        foo = dbi.open(FooDAO.class);
        foo.createTable();
    }

    @After
    public void teardown() {
        foo.close();
    }

    @Test
    public void FooCRUD() {

        //Create
        Long id = foo.create(new Foo(null, "label", "description", new Date(), false));
        Assert.assertNotNull(id);

        //Read
        Foo f = foo.searchById(id);
        Assert.assertNotNull(f);
        Assert.assertEquals(f.getLabel(), "label");
    }
}
