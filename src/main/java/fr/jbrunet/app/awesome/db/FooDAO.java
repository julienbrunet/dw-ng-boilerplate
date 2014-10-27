package fr.jbrunet.app.awesome.db;

import fr.jbrunet.app.awesome.db.mappers.FooMapper;
import fr.jbrunet.app.awesome.pojos.Foo;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;
import java.util.List;

/**
 * Created by Julien BRUNET on 27/10/2014.
 */
@RegisterMapper(FooMapper.class)
public abstract class FooDAO {

    @SqlUpdate(
            "CREATE TABLE IF NOT EXISTS foo (" +
                    "    ID BIGINT NOT NULL AUTO_INCREMENT," +
                    "    LABEL VARCHAR(255)," +
                    "    DESCRIPTION VARCHAR(255)," +
                    "    DATE DATE," +
                    "    BOOL VARCHAR   (255)" +
                    ")")
    public abstract void createTable();

    @SqlUpdate(
            "INSERT INTO foo (label, description, date, bool) " +
                    "VALUES(:label, :description, :date, :bool)"
    )
    @GetGeneratedKeys
    public abstract long create(@BindBean Foo foo);

    @SqlQuery("SELECT id, label, description, date, bool FROM foo WHERE id=:id LIMIT 1")
    public abstract Foo searchById(@Bind("id") Long id);

    //TODO UPDATE, DELETE

    @SqlQuery(
            "SELECT id, label, description, date, bool FROM foo " +
            "<whereClause>" +
            "<orderClause>" +
            "LIMIT = :pageSize" +
            "OFFSET = :offset"
    )
    List<Foo> searchPage(Long id,
                         String label,
                         String description,
                         Date date,
                         boolean bool,
                         int offset,
                         int pageSize) {
        //TODO
        return null;
    }

    /**
     * close with no args is used to close the connection
     */
    public abstract void close();
}
