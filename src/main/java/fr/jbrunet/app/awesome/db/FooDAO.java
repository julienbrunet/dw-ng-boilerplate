package fr.jbrunet.app.awesome.db;

import fr.jbrunet.app.awesome.db.mappers.FooMapper;
import fr.jbrunet.app.awesome.pojos.Foo;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Julien BRUNET on 28/10/2014.
 */
@RegisterMapper(FooMapper.class)
public abstract class FooDAO extends AbstractDAO<Foo> {

    private final static String TABLE_NAME = "FOO";
    private final static String[] COLUMNS = {"ID", "LABEL", "DESCRIPTION", "DATE", "BOOL"};

    @SqlUpdate(
            "CREATE TABLE IF NOT EXISTS foo (" +
            "    ID BIGINT NOT NULL AUTO_INCREMENT," +
            "    LABEL VARCHAR(255)," +
            "    DESCRIPTION VARCHAR(255)," +
            "    DATE DATE," +
            "    BOOL VARCHAR   (255)" +
            ")")
    public abstract void createTable();

    /**
     * Create Object
     * @param fooObject
     * @return Id the the created object
     */
    @SqlUpdate("INSERT INTO foo (label, description, date, bool) VALUES(:label, :description, :date, :bool)")
    @GetGeneratedKeys
    public abstract long create(@BindBean Foo fooObject);

    /**
     * Batch Create Object
     * @param fooObjects
     * @return Id the the created object
     */
    @SqlBatch("INSERT INTO foo (label, description, date, bool) VALUES(:label, :description, :date, :bool)")
    public abstract void create(@BindBean List<Foo> fooObjects);

    /**
     * Search Foo object by its id
     * @param id
     * @return Found Foo object or null if not found
     */
    @SqlQuery("SELECT id, label, description, date, bool FROM foo WHERE id=:id LIMIT 1")
    public abstract Foo searchById(@Bind("id") Long id);

    //TODO UPDATE, DELETE

    /**
     * Search with all Foo object attributes
     * @param id
     * @param label
     * @param description
     * @param date
     * @param bool
     * @param offset
     * @param pageSize
     * @param orderBy
     * @return Found Foo object or null if not found
     */
    public List<Foo> search(Long id,
                                String label,
                                String description,
                                Date date,
                                Boolean bool,
                                int offset,
                                int pageSize,
                                String[] orderBy,
                                Boolean orderByDesc) {

        //Build select clause
        StringBuffer select = getSelectClause(TABLE_NAME, COLUMNS);

        //Build where clause
        StringBuffer where = new StringBuffer("WHERE 1=1");
        if( id!=null ) where.append(" AND ID=").append(id);
        if( label!=null ) where.append(" AND LABEL LIKE '%").append(label).append("%'");
        if( description!=null ) where.append(" AND DESCRIPTION LIKE '%").append(description).append("%'");
        if( bool!=null && bool) where.append(" AND BOOL='TRUE'");
        if( bool!=null && !bool) where.append(" AND BOOL='FALSE'");
        where.append(" ");

        //Build order clause
        StringBuffer order = new StringBuffer();
        if(orderBy!=null && orderBy.length>0 && orderByDesc!=null) {
            order.append("ORDER BY ");
            for (int i = 0; i < orderBy.length; i++) {
                String orderCol = orderBy[i];
                if(!columnExists(orderCol, COLUMNS)) throw new IllegalArgumentException("build orderBy clause error : column "+orderCol+" does not exist");
                order.append(orderCol.toUpperCase());
                if(i!=orderBy.length-1) select.append(",");
                order.append(" ");
            }

            if(orderByDesc) order.append("DESC ");
            else order.append("ASC ");
        }

        String query = select.append(where).append(order).append(" LIMIT "+pageSize+" OFFSET "+offset+" ").toString();
        Handle h = getHandle();
        Query<Map<String, Object>> q = h.createQuery(query);
        Query<Foo> q2 = q.map(new FooMapper());
        return q2.list();
    }
}
