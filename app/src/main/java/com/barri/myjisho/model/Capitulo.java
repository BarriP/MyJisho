package com.barri.myjisho.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Barri on 23/03/2016.
 */
public class Capitulo extends SugarRecord{

    /* Fields */
    private String name;

    /* Relationships */
    private Coleccion coleccion;

    public List<Traduccion> getTraducciones(){
        return Traduccion.find(Traduccion.class,"capitulo = ?", String.valueOf(getId()));
    }

    /* Constructors */
    public Capitulo() {} //Para el mapeador

    public Capitulo(String name, Coleccion coleccion){
        this.name = name;
        this.coleccion = coleccion;
    }

    /* Domain */
    public String getName() {
        return name;
    }
}
