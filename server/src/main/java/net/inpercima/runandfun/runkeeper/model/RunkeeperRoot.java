package net.inpercima.runandfun.runkeeper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marcel Jänicke
 * @since 11.02.2015
 */
public class RunkeeperRoot {

    @JsonProperty(value = "change_logs")
    private String[] changeLogs;

    public String[] getChangeLogs() {
        return changeLogs;
    }

    public void setChangeLogs(String[] changeLogs) {
        this.changeLogs = changeLogs;
    }

}
