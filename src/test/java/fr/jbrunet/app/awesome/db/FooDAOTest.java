package fr.jbrunet.app.awesome.db;

import fr.jbrunet.app.awesome.pojos.Foo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

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
        //dbi.setSQLLog(new PrintStreamLog(System.out));
        foo = dbi.open(FooDAO.class);
        foo.createTable();
    }

    @After
    public void teardown() {
        foo.close();
    }

    @Test
    public void FooCreateAndSearchById() {
        //Create
        Long id1 = foo.create(new Foo(null, "foo1", "description", new Date(), false));
        Assert.assertNotNull(id1);
        Long id2 = foo.create(new Foo(null, "foo2", "description", new Date(), false));
        Assert.assertNotNull(id2);

        //Read
        Foo f1 = foo.searchById(id1);
        Assert.assertNotNull(f1);
        Assert.assertEquals(f1.getLabel(), "foo1");

        Foo f2 = foo.searchById(id2);
        Assert.assertNotNull(f2);
        Assert.assertEquals(f2.getLabel(), "foo2");
    }
}
