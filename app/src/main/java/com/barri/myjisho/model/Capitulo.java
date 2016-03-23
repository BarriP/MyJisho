package com.barri.myjisho.model;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Barri on 23/03/2016.
 */
public class Capitulo extends SugarRecord{

    /* Fields */
    private String name;

    /* Relationships */
    private Coleccion colleccion;

    /* Constructors */
    public Capitulo() {} //Para el mapeador

    public Capitulo(String name, Coleccion colleccion){
        this.name = name;
        this.colleccion = colleccion;
    }

    /* Domain */
    public String getName() {
        return name;
    }
}
