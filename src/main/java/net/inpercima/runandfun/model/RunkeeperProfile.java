package net.inpercima.runandfun.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marcel Jänicke
 * @since 11.02.2015
 */
public class RunkeeperProfile {

    private String name;

    @JsonProperty(value = "athlete_type")
    private String athleteType;

    private String location;

    private Date birthday;

    private boolean elite;

    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAthleteType() {
        return athleteType;
    }

    public void setAthleteType(String athleteType) {
        this.athleteType = athleteType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isElite() {
        return elite;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
