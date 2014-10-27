package fr.jbrunet.app.awesome.db.mappers;

import fr.jbrunet.app.awesome.pojos.Foo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FooMapper implements ResultSetMapper<Foo>
{
    public Foo map(int index, ResultSet r, StatementContext ctx) throws SQLException
    {
        Foo f = new Foo();

        f.setId( r.getLong("id"));
        f.setLabel(r.getString("label"));
        f.setDescription(r.getString("Description"));
        f.setDate(r.getDate("date"));
        f.setBool(r.getBoolean("bool"));

        return f;
    }
}