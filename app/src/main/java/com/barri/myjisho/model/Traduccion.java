package com.barri.myjisho.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Barri on 23/03/2016.
 */
public class Traduccion extends SugarRecord{
    /* Fields */
    private String name;
    private String description;
    private int page;

    /* Relationships */
    private Capitulo capitulo;

    /* Constructors */
    public Traduccion() {} //Para el mapeador

    public Traduccion(String name, String description, int page, Capitulo capitulo){
        this.name = name;
        this.description = description;
        this.page = page;
        this.capitulo = capitulo;
    }

    /* Domain */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPage() {
        return page;
    }
}
