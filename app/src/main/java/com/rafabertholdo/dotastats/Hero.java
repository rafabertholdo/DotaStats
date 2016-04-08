package com.rafabertholdo.dotastats;

import java.io.Serializable;

/**
 * Created by rafaelgb on 07/04/2016.
 */
public class Hero implements Serializable {

    private String name;
    private int id;
    private String localizedName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }
}
