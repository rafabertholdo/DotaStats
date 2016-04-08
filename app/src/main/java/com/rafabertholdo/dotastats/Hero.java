package com.rafabertholdo.dotastats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaelgb on 07/04/2016.
 */
public class Hero implements Serializable {

    private String name;
    private int heroID;
    private String localizedName;
    private List<String> role;
    private List<Integer> rolelevels;
    private String team;
    private List<String> abilities;

    public Hero(){
        this.role = new ArrayList<>();
        this.rolelevels = new ArrayList<>();
        this.abilities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeroID() {
        return heroID;
    }

    public void setHeroID(int heroID) {
        this.heroID = heroID;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public List<Integer> getRolelevels() {
        return rolelevels;
    }

    public void setRolelevels(List<Integer> rolelevels) {
        this.rolelevels = rolelevels;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }
}
