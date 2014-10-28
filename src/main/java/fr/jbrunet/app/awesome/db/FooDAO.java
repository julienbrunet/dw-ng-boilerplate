package fr.jbrunet.app.awesome.db;

import fr.jbrunet.app.awesome.db.mappers.FooMapper;
import fr.jbrunet.app.awesome.pojos.Foo;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;
import java.util.List;

/**
 * Created by Julien BRUNET on 28/10/2014.
 */
@RegisterMapper(FooMapper.class)
public abstract class FooDAO extends AbstractDAO<Foo> {

    @SqlUpdate(
    "CREATE TABLE IF NOT EXISTS foo (" +
            "    ID BIGINT NOT NULL AUTO_INCREMENT," +
            "    LABEL VARCHAR(255)," +
            "    DESCRIPTION VARCHAR(255)," +
            "    DATE DATE," +
            "    BOOL VARCHAR   (255)" +
            ")")
    public void createTable(){};


    public List<Foo> searchPage(Long id,
                                String label,
                                String description,
                                Date date,
                                boolean bool,
                                int offset,
                                int pageSize,
                                List<String> orderBy) {


        //TODO
        return null;
    }
}
