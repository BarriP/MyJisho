package com.barri.myjisho.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Barri on 23/03/2016.
 */
public class Coleccion extends SugarRecord{

    /* Fields */
    private String name;

    /* Relationships */
    public List<Capitulo> getCapitulos(){
        return Capitulo.find(Capitulo.class,"colleccion = ?", String.valueOf(getId()));
    }

    /* Constructors */
    public Coleccion() {} //Para el mapeador

    public Coleccion(String name){
        this.name = name;
    }

    /* Domain */
    public String getName() {
        return name;
    }
}
