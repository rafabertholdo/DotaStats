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
    private String attributePrimary;
    private String attackCapabilities;

    private int attributeBaseStrength;
    private float attributeStrengthGain;

    private int attributeBaseIntelligence;
    private float attributeIntelligenceGain;

    private int attributeBaseAgility;
    private float attributeAgilityGain;

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

    public String getAttributePrimary() {
        return attributePrimary;
    }

    public void setAttributePrimary(String attributePrimary) {
        this.attributePrimary = attributePrimary;
    }

    public String getAttackCapabilities() {
        return attackCapabilities;
    }

    public void setAttackCapabilities(String attackCapabilities) {
        this.attackCapabilities = attackCapabilities;
    }

    public String getAttackCapability(){
        if(this.attackCapabilities.equals("DOTA_UNIT_CAP_MELEE_ATTACK")){
            return "Melee";
        }else if(this.attackCapabilities.equals("DOTA_UNIT_CAP_RANGED_ATTACK")){
            return "Ranged";
        } else {
            return "";
        }
    }

    public String getBaseStrength(int level) {
        return String.valueOf((int)(attributeBaseStrength + (level * attributeStrengthGain)));
    }

    public int getAttributeBaseStrength() {
        return attributeBaseStrength;
    }

    public void setAttributeBaseStrength(int attributeBaseStrength) {
        this.attributeBaseStrength = attributeBaseStrength;
    }

    public float getAttributeStrengthGain() {
        return attributeStrengthGain;
    }

    public void setAttributeStrengthGain(float attributeStrengthGain) {
        this.attributeStrengthGain = attributeStrengthGain;
    }

    public String getBaseInteligence(int level) {
        return String.valueOf((int)(attributeBaseIntelligence + (level * attributeIntelligenceGain)));
    }

    public int getAttributeBaseIntelligence() {
        return attributeBaseIntelligence;
    }

    public void setAttributeBaseIntelligence(int attributeBaseIntelligence) {
        this.attributeBaseIntelligence = attributeBaseIntelligence;
    }

    public float getAttributeIntelligenceGain() {
        return attributeIntelligenceGain;
    }

    public void setAttributeIntelligenceGain(float attributeIntelligenceGain) {
        this.attributeIntelligenceGain = attributeIntelligenceGain;
    }

    public String getBaseAgility(int level) {
        return String.valueOf((int)(attributeBaseAgility + (level * attributeAgilityGain)));
    }


    public int getAttributeBaseAgility() {
        return attributeBaseAgility;
    }

    public void setAttributeBaseAgility(int attributeBaseAgility) {
        this.attributeBaseAgility = attributeBaseAgility;
    }

    public float getAttributeAgilityGain() {
        return attributeAgilityGain;
    }

    public void setAttributeAgilityGain(float attributeAgilityGain) {
        this.attributeAgilityGain = attributeAgilityGain;
    }
}