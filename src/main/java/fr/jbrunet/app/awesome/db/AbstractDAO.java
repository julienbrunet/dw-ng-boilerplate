package fr.jbrunet.app.awesome.db;

import fr.jbrunet.app.awesome.pojos.Foo;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Define;

import java.util.List;

/**
 * Created by Julien BRUNET on 28/10/2014.
 */
public abstract class AbstractDAO<T> {

    @SqlUpdate("INSERT INTO foo (label, description, date, bool) VALUES(:label, :description, :date, :bool)")
    @GetGeneratedKeys
    public abstract long create(@BindBean T t);

    @SqlQuery("SELECT id, label, description, date, bool FROM foo WHERE id=:id LIMIT 1")
    public abstract T searchById(@Bind("id") Long id);

    //TODO UPDATE, DELETE

    @SqlQuery(
            "SELECT id, label, description, date, bool FROM foo " +
                    "<whereClause> " +
                    "<orderClause> " +
                    "LIMIT = :pageSize" +
                    "OFFSET = :offset"
    )
    public abstract List<Foo> search(@Define("whereClause") String whereClause, @Define("orderClause") String orderClause, int offset, int pageSize);

    /**
     * close with no args is used to close the connection
     */
    public void close() {};
}
