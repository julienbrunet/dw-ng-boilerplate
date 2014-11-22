package fr.jbrunet.app.dw.ng.starterkit.db;

import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;

/**
 * Created by Julien BRUNET on 28/10/2014.
 */
public abstract class AbstractDAO<T> implements GetHandle {

    /**
     * Build select clause
     * @param tableName
     * @param columns
     * @return the select clause with all columns given in args, or select * clause if no column in arg
     */
    protected StringBuffer getSelectClause(String tableName, String ... columns) {

        if(tableName==null || tableName.length()==0) throw new IllegalArgumentException("Empty table name");

        StringBuffer select = new StringBuffer("SELECT ");
        if(columns==null || columns.length==0) select.append("* ");

        for (int i = 0; i < columns.length; i++) {
            select.append(columns[i]);
            if(i!=columns.length-1) select.append(",");
            select.append(" ");
        }
        select.append("FROM ").append(tableName).append(" ");

        return select;
    }

    /**
     * Check if given column exists for this object
     * @param col
     * @param columns
     * @return
     */
    protected boolean columnExists(String col, String ... columns) {
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            if(column.toUpperCase().equals(col.toUpperCase())) return true;
        }
        return false;
    }

    /**
     * close with no args is used to close the connection
     */
    public void close() {};
}
