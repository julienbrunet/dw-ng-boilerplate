package fr.jbrunet.app.awesome.db;

import fr.jbrunet.app.awesome.db.mappers.FooMapper;
import fr.jbrunet.app.awesome.pojos.Foo;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Julien BRUNET on 27/10/2014.
 */
@RegisterMapper(FooMapper.class)
public interface FooDAO {

    @SqlUpdate(
            "CREATE TABLE IF NOT EXISTS foo (" +
            "    ID BIGINT NOT NULL AUTO_INCREMENT," +
            "    LABEL VARCHAR(255)," +
            "    DESCRIPTION VARCHAR(255)," +
            "    DATE DATE," +
            "    BOOL VARCHAR   (255)" +
            ")")
    public void createTable();

    @SqlUpdate(
            "INSERT INTO foo (label, description, date, bool) " +
            "VALUES(:label, :description, :date, :bool)"
    )
    @GetGeneratedKeys
    long create(@BindBean Foo foo);

    @SqlQuery(
            "SELECT id, label, description, date, bool FROM foo " +
            "LIMIT = :pageSize" +
            "OFFSET = :offset"
    )
    List<Foo> searchPage(@Bind("id") String id, @Bind("offset") int offset, @Bind("pageSize") int pageSize);

    @SqlQuery("SELECT id, label, description, date, bool FROM foo WHERE id=:id LIMIT 1")
    Foo searchById(@Bind("id") Long id);

    /**
     * close with no args is used to close the connection
     */
    void close();
}
