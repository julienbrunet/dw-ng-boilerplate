package fr.jbrunet.app.awesome.pojos;

import java.util.Date;

/**
 * Created by Julien BRUNET on 27/10/2014.
 */
public class Foo {
    private Long id;
    private String label;
    private String description;
    private Date date;
    private boolean bool;

    public Foo(){}

    public Foo(Long id, String label, String description, Date date, boolean bool) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.date = date;
        this.bool = bool;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}

