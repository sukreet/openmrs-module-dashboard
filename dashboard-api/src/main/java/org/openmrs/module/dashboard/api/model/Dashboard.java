package org.openmrs.module.dashboard.api.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dashboard {
    @JsonProperty("name")
    private String name;

    @JsonProperty("requiredPrivileges")
    private ArrayList<String> requiredPrivileges;

    @JsonProperty("sections")
    private ArrayList<Object> sections;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRequiredPrivileges() {
        return requiredPrivileges;
    }

    public void setRequiredPrivileges(ArrayList<String> requiredPrivileges) {
        this.requiredPrivileges = requiredPrivileges;
    }

    public ArrayList<Object> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Object> sections) {
        this.sections = sections;
    }
}
