package fr.jbrunet.app.dw.ng.starterkit.test.db;

import fr.jbrunet.app.dw.ng.starterkit.db.FooDAO;
import fr.jbrunet.app.dw.ng.starterkit.pojos.Foo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Julien BRUNET on 27/10/2014.
 */
public class FooDAOTest {

    private DBI dbi;
    private FooDAO dao;

    @Before
    public void setup() {
        dbi = new DBI("jdbc:h2:mem:" + UUID.randomUUID());
        // dbi.setSQLLog(new PrintStreamLog(System.out));
        dao = dbi.open(FooDAO.class);
        dao.createTable();
    }

    @After
    public void teardown() {
        dao.close();
    }

    private void create20Foos() {
        List<Foo> foos = new ArrayList<Foo>();
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
        foos.add(new Foo(null, "foo11", "description", new Date(), false));
        foos.add(new Foo(null, "foo12", "description", new Date(), true));
        foos.add(new Foo(null, "foo13", "description", new Date(), false));
        foos.add(new Foo(null, "foo14", "description", new Date(), true));
        foos.add(new Foo(null, "foo15", "description", new Date(), false));
        foos.add(new Foo(null, "foo16", "description", new Date(), false));
        foos.add(new Foo(null, "foo17", "description", new Date(), false));
        foos.add(new Foo(null, "foo18", "description", new Date(), true));
        foos.add(new Foo(null, "foo19", "description", new Date(), true));
        foos.add(new Foo(null, "foo20", "description", new Date(), true));

        //Create
        dao.create(foos);
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
        create20Foos();

        //Read
        List<Foo> results = dao.search(null, "foo1", null, null, null, 0, -1, null, null);
        Assert.assertEquals(results.size(), 11); //Retrieve foo1%

        //Read
        List<Foo> result2 = dao.search(null, null, null, null, false, 0, -1, null, null);
        Assert.assertEquals(result2.size(), 10);
    }

    @Test
    public void SearchOrderBy() {
        create20Foos();

        List<Foo> results = dao.search(null, "foo", null, null, null, 0, -1, new String[]{"LABEL"}, false);
        Assert.assertEquals(results.get(1).getLabel(), "foo10");
        List<Foo> results2 = dao.search(null, "foo", null, null, null, 0, -1, new String[]{"ID"}, false);
        Assert.assertEquals(results2.get(1).getLabel(), "foo2");

        //Select * order by ID DESC
        List<Foo> results3 = dao.search(null, null, null, null, null, 0, -1, new String[]{"ID"}, true);
        Assert.assertEquals(results3.get(0).getLabel(), "foo20");
    }

    @Test
    public void SearchPaging() {
        create20Foos();

        //Get first page with 3 line pages
        List<Foo> results = dao.search(null, "foo", null, null, null, 0, 3, new String[]{"ID"}, false);
        Assert.assertEquals(results.size(), 3);
        Assert.assertEquals(results.get(0).getLabel(),"foo1");
        Assert.assertEquals(results.get(1).getLabel(),"foo2");
        Assert.assertEquals(results.get(2).getLabel(),"foo3");

        //Get 3rd page with 5 line pages
        results = dao.search(null, "foo", null, null, null, 5*2, 5, new String[]{"ID"}, false);
        Assert.assertEquals(results.size(), 5);
        Assert.assertEquals(results.get(0).getLabel(),"foo11");
        Assert.assertEquals(results.get(1).getLabel(),"foo12");
        Assert.assertEquals(results.get(2).getLabel(),"foo13");
        Assert.assertEquals(results.get(3).getLabel(),"foo14");
        Assert.assertEquals(results.get(4).getLabel(),"foo15");
    }

    @Test(expected = IllegalArgumentException.class)
    public void SearchWithWrongArg() {
        dao.search(null, "foo", null, null, null, 0, -1, new String[]{"UNEXISTING_COLUMN"}, false);
    }
}
