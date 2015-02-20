package net.inpercima.runandfun.model;

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

    private String birthday;

    private boolean elite;

    private String gender;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAthleteType() {
        return athleteType;
    }

    public void setAthleteType(final String athleteType) {
        this.athleteType = athleteType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(final String birthday) {
        this.birthday = birthday;
    }

    public boolean isElite() {
        return elite;
    }

    public void setElite(final boolean elite) {
        this.elite = elite;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

}
