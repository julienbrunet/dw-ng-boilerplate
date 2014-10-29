package fr.jbrunet.app.awesome.db;

import fr.jbrunet.app.awesome.pojos.Foo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.util.*;

/**
 * Created by Julien BRUNET on 27/10/2014.
 */
public class FooDAOTest {

    private DBI dbi;
    private FooDAO dao;

    @Before
    public void setup() {
        //dbi = new DBI("jdbc:h2:./database" + UUID.randomUUID());
        dbi = new DBI("jdbc:h2:mem:" + UUID.randomUUID());
        //dbi.setSQLLog(new PrintStreamLog(System.out));
        dao = dbi.open(FooDAO.class);
        dao.createTable();
    }

    @After
    public void teardown() {
        dao.close();
    }

    @Test
    public void CreateAndSearchById() {
        //Create
        Long id1 = dao.create(new Foo(null, "foo1", "description", new Date(), false));
        Assert.assertNotNull(id1);
        Long id2 = dao.create(new Foo(null, "foo2", "description", new Date(), false));
        Assert.assertNotNull(id2);

        //Read
        Foo f1 = dao.searchById(id1);
        Assert.assertNotNull(f1);
        Assert.assertEquals(f1.getLabel(), "foo1");

        Foo f2 = dao.searchById(id2);
        Assert.assertNotNull(f2);
        Assert.assertEquals(f2.getLabel(), "foo2");
    }

    @Test
    public void Search() {
        List<Foo> foos = new ArrayList();
        foos.add(new Foo(null, "foo1",  "description", new Date(), false));
        foos.add(new Foo(null, "foo2",  "description", new Date(), true));
        foos.add(new Foo(null, "foo3",  "description", new Date(), false));
        foos.add(new Foo(null, "foo4",  "description", new Date(), true));
        foos.add(new Foo(null, "foo5",  "description", new Date(), false));
        foos.add(new Foo(null, "foo6",  "description", new Date(), false));
        foos.add(new Foo(null, "foo7",  "description", new Date(), false));
        foos.add(new Foo(null, "foo8",  "description", new Date(), true));
        foos.add(new Foo(null, "foo9",  "description", new Date(), true));
        foos.add(new Foo(null, "foo10", "description", new Date(), true));

        //Create
        dao.create(foos);

        //Read
        List<Foo> results = dao.search(null, "foo", null, null, null, 0, -1, new String[]{"LABEL"});
        Assert.assertEquals(results.get(1).getLabel(), "foo10");

        List<Foo> results2 = dao.search(null, "foo", null, null, null, 0, -1, new String[]{"ID"});
        Assert.assertEquals(results2.get(1).getLabel(), "foo2");

    }

    @Test(expected = IllegalArgumentException.class)
    public void SearchWithWrongArg() {
        List<Foo> results = dao.search(null, "foo", null, null, null, 0, -1, new String[]{"UNEXISTING_COLUMN"});
    }
}
